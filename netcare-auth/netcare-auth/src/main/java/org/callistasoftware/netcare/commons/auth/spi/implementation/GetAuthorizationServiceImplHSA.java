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
package org.callistasoftware.netcare.commons.auth.spi.implementation;

import org.callistasoftware.netcare.commons.auth.api.CareGiverInterface;
import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;
import org.callistasoftware.netcare.commons.auth.api.MedicalPersonalUser;
import org.callistasoftware.netcare.commons.auth.spi.AuthorizationService;
import org.callistasoftware.netcare.commons.auth.spi.vo.AuthCareGiverImpl;
import org.callistasoftware.netcare.commons.auth.spi.vo.AuthCareUnitImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetCareUnitResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaUnitResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetMiuForPersonResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.LookupHsaObjectType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.MiuInformationType;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAuthorizationServiceImplHSA implements AuthorizationService {
    private static final Logger log = LoggerFactory.getLogger(GetAuthorizationServiceImplHSA.class);

    @Autowired
    private HSAWebServiceCalls client;
    
    private SecurityContext securityContext;
    
    void setSecurityContext(final SecurityContext context) {
    	this.securityContext = context;
    }
    
    SecurityContext getSecurityContext() {
    	if (this.securityContext == null) {
    		return SecurityContextHolder.getContext();
    	}
    	
    	return this.securityContext;
    }
    
    void setClient(final HSAWebServiceCalls client) {
    	this.client = client;
    }
    
    public List<CareUnitInterface> getAuthorizedCareUnitsForHosPerson(String hosPersonHsaId) throws Exception {
        // Initialize result value
        List<CareUnitInterface> careUnits = new ArrayList<CareUnitInterface>();

        // Set hos person hsa ID
        LookupHsaObjectType parameters = new LookupHsaObjectType();
        parameters.setHsaIdentity(hosPersonHsaId);

        try {
            GetMiuForPersonResponseType response = client.callMiuRights(parameters);

            for (MiuInformationType miu : response.getMiuInformation()) {
                // Check that MiuPurpose is "Vård och behandling"
                if (miu.getMiuPurpose().equalsIgnoreCase("Vård och behandling")) {
                    // Add data as a new CareUnit and CareGiver					
                    AuthCareUnitImpl unit = new AuthCareUnitImpl();
                    unit.setHsaId(miu.getCareUnitHsaIdentity());
                    unit.setName(miu.getCareUnitName());
                    AuthCareGiverImpl giver = new AuthCareGiverImpl();
                    giver.setHsaId(miu.getCareGiver());
                    giver.setName(miu.getCareGiverName());
                    unit.setCareGiver(giver);
                    careUnits.add(unit);
                }
            }

        } catch (Exception ex) {
            log.error("Exception=" + ex.getMessage());
            throw new Exception(ex);
        }
        return careUnits;
    }

	public boolean isAuthorizedForCareUnit(final MedicalPersonalUser user, String hsaId) {
		
		if (this.hasAccessTo(user, hsaId)) {
			return true;
		}
		
		/*
		 * Check parent and add to list
		 */
		try {
			final GetCareUnitResponseType cu = this.client.callGetCareunit(hsaId);
			final String parent = cu.getCareUnitHsaIdentity();
			if (parent != null && parent.trim().length() > 0) {
				
				final CareGiverInterface careGiver = user.getCareUnit(parent).getCareGiver();
				final GetHsaUnitResponseType response = this.client.callGetHsaunit(hsaId);
				
				final AuthCareUnitImpl impl = new AuthCareUnitImpl();
				impl.setHsaId(hsaId);
				impl.setName(response.getName());
				impl.setCareGiver(careGiver);
				
				user.addCareUnit(impl);
				
				return this.isAuthorizedForCareUnit(user, hsaId);
				
			} else {
				return false;
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Exception when trying to contact HSA for user permission", e);
		}
	}
	
	private boolean hasAccessTo(final MedicalPersonalUser user, final String careUnitId) {
		log.debug("Checking permission on careUnit {}", careUnitId);
        for(CareUnitInterface careUnit : user.getCareUnits()) {
            if(careUnit.getHsaId().equals(careUnitId)) {
                log.debug("User does have permission on careUnit {}", careUnitId);             
                return true;
            }
        }
        
        log.debug("User does not have permission on careUnit {}", careUnitId);
        return false;
	}
}
