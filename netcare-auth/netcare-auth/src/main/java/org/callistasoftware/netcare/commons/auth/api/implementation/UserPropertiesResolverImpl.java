/*
 * Inera Webcert - Sjukintygsapplikation
 *
 * Copyright (C) 2010-2011 Inera AB (http://www.inera.se)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.callistasoftware.netcare.commons.auth.api.implementation;

import java.util.ArrayList;
import java.util.List;

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;
import org.callistasoftware.netcare.commons.auth.api.HosUserInterface;
import org.callistasoftware.netcare.commons.auth.api.UserPropertiesResolver;
import org.callistasoftware.netcare.commons.auth.spi.AuthorizationService;
import org.callistasoftware.netcare.commons.auth.spi.OrganisationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Pär Wenåker
 *
 */

@Service
public class UserPropertiesResolverImpl implements UserPropertiesResolver {

    private static final Logger log = LoggerFactory.getLogger(UserPropertiesResolverImpl.class);
    
    @Autowired
    private OrganisationInfoService organisationInfoService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.UserPropertiesResolver#resolve(se.inera.ifv.auth.userdetails.MedicalPersonalUser)
     */
    public void resolve(MedicalPersonalUserImpl user) {
        
        log.debug("Looking up user with HSA-ID={}", user.getHsaId());
        HosUserInterface hosUser;
        try {
            hosUser = organisationInfoService.getHosPersonFullInfo(user.getHsaId());
        } catch (Exception e) {
            log.debug("Caught an exception {}", e.toString());
            log.debug("Could not get PersonFullInfo from organisation Info service for User HSA-ID={}", user.getHsaId());
            throw new SecurityException("authorization.failed");
        }
        
        log.debug("Found user with HSA-ID={}, user={}", user.getHsaId(), hosUser.toString());
        
        user.setName(hosUser.getName());
        user.setPerscriptionCode(hosUser.getPrescriptionCode());
        user.setDoctor(hosUser.isDoctor());
        
        log.debug("Looking up care units for user with HSA-ID={}", user.getHsaId());
        List<CareUnitInterface> careUnits;
        try {
            careUnits = authorizationService.getAuthorizedCareUnitsForHosPerson(user.getHsaId());
        } catch (Exception e) {
            log.error("Call for get suthorized careunits failed:" + e.getMessage());
            careUnits = new ArrayList<CareUnitInterface>();
        }
        
        log.debug("Found {} careUnits for User with HSA-ID={}", careUnits.size(), user.getHsaId());
        logCareUnits(careUnits);
        for(CareUnitInterface careUnit : careUnits) {
            user.addCareUnit(careUnit);
        }
        log.debug("resolved user: {}", user);
    }

    /**
     * @param careUnits
     */
    private void logCareUnits(List<CareUnitInterface> careUnits) {
        StringBuilder sb = new StringBuilder();
        for(CareUnitInterface careUnit : careUnits) {
            sb.append(careUnit.getHsaId()).append(" ");
        }
        log.debug("Careunits: {}", sb.toString());
    }

}
