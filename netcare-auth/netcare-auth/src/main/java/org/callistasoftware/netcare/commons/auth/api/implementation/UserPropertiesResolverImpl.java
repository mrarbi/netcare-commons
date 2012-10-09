/**
 *    Copyright 2011,2012 Callista Enterprise AB
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
