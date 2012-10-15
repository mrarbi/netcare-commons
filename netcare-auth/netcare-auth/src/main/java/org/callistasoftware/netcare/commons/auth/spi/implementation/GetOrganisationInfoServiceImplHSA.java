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

import java.util.List;

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;
import org.callistasoftware.netcare.commons.auth.api.HosUserInterface;
import org.callistasoftware.netcare.commons.auth.spi.OrganisationInfoService;
import org.callistasoftware.netcare.commons.auth.spi.implementation.HSAWebServiceCalls;
import org.callistasoftware.netcare.commons.auth.spi.vo.AuthCareUnitImpl;
import org.callistasoftware.netcare.commons.auth.spi.vo.AuthHosUserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.AttributeListType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.AttributeValueListType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.AttributeValuePairType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.ExactType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetCareUnitResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaPersonHsaUserType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaPersonResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaPersonResponseType.UserInformations;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaPersonType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaUnitResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.HsawsSimpleLookupResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.HsawsSimpleLookupType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.SearchOperatorExact;

@Service
public class GetOrganisationInfoServiceImplHSA implements OrganisationInfoService {

    private static final Logger log = LoggerFactory.getLogger(GetOrganisationInfoServiceImplHSA.class);

    @Autowired
    private HSAWebServiceCalls client;
    
    public CareUnitInterface getCareUnitFullInfo(String careUnitHsaId) throws Exception {
        log.debug("Calling HSA in getCareUnitFullInfo");
        
        AuthCareUnitImpl careUnit = new AuthCareUnitImpl();

        // Make call to get full information about the hsa unit
        GetHsaUnitResponseType response = client.callGetHsaunit(careUnitHsaId);
        
        // Set returned values
        careUnit.setEmail(response.getEmail());
        careUnit.setName(response.getName());
        careUnit.setHsaId(careUnitHsaId);
        if (response.getTelephoneNumbers() != null && response.getTelephoneNumbers().getTelephoneNumber() != null && response.getTelephoneNumbers().getTelephoneNumber().size() > 0) {
            careUnit.setPhone(response.getTelephoneNumbers().getTelephoneNumber().get(0));
        }

        String tempPostalCodePostalCity = "";
        String tempPostalAdress = "";
        if (response.getPostalAddress() != null && response.getPostalAddress().getAddressLine() != null && response.getPostalAddress().getAddressLine().size() > 0) {
            // Here we expect postalcode +  postalcity on last address line. All lines above goes into postal address 
            for (int i = 0 ; i < response.getPostalAddress().getAddressLine().size() ;i++) {
                if (i == response.getPostalAddress().getAddressLine().size() - 1) {
                	tempPostalCodePostalCity = response.getPostalAddress().getAddressLine().get(i).trim();
                } else {
                	tempPostalAdress = tempPostalAdress + response.getPostalAddress().getAddressLine().get(i);
                }
            }
        }
        
        // Extract postalcode and postalcity from extracted line
        if (tempPostalCodePostalCity != null && tempPostalCodePostalCity.length() > 7 && tempPostalCodePostalCity.substring(0, 1).matches("[0-9]+") ) {
        	careUnit.setPostalCode(tempPostalCodePostalCity.substring(0, 6).trim());
        	careUnit.setPostalCity(tempPostalCodePostalCity.substring(6, tempPostalCodePostalCity.length()).trim());   
        } else {
        	// Bad data set temp postcode and the rest in postalcity
        	careUnit.setPostalCode("XXXXX");
        	careUnit.setPostalCity(tempPostalCodePostalCity);   
        }
      
        // Set postaladress as parsed from above
        careUnit.setPostalAddress(tempPostalAdress);
      
        // Arbetsplatskod finns ej med i standardsvaret från HSA, så slå upp detta separat...
        careUnit.setWorkplaceCode(getWorkplaceCode(careUnitHsaId));

        return careUnit;
    }

    public String getParentCareUnit(String careUnitHsaId) throws Exception {
        log.debug("Calling HSA in getDPLCareUnit");
        
        // Make call to get information parent hsa unit
        GetCareUnitResponseType response = client.callGetCareunit(careUnitHsaId);

        // Check if we got any careunit - PDL careunit for given input
        if (response.getCareUnitHsaIdentity() != null && response.getCareUnitHsaIdentity().length() > 0) {
        	return response.getCareUnitHsaIdentity();
        }
        
        // Return empty string if we didn't find anything
        return "";
    }

    public String getHosHsaIdFromPersonNumber(String personNumber) throws Exception {
    	log.debug("Calling HSA in getHosHsaIdFromPersonNummer");

        // Create lookup parameter
        GetHsaPersonType parameters = new GetHsaPersonType();
        
        //SetLookup data
        parameters.setPersonalIdentityNumber(personNumber);

        // Make call		
        GetHsaPersonResponseType response = client.callGetHsaPerson(parameters);

        // Read response and pick values
        UserInformations userInformations = response.getUserInformations();
        
        List<GetHsaPersonHsaUserType> userInformationList = userInformations.getUserInformation();
        
        //TODO: what to do with multiple users?
        for(GetHsaPersonHsaUserType hsaUser : userInformationList){
        	return hsaUser.getHsaIdentity();
        }
        
        throw new Exception("server did not reply with valid HSA ID.");
    }

    public HosUserInterface getHosPersonFullInfo(String hosPersonHsaId) throws Exception {
        AuthHosUserImpl user = new AuthHosUserImpl();
        user.setDoctor(false);
        String sureName = "";
        String givenName = "";

        log.debug("Calling HSA in getHosPersonFullInfo");

        // Create lookup parameter
        HsawsSimpleLookupType parameters = new HsawsSimpleLookupType();

        // Set Lookup data
        ExactType lookupValue = new ExactType();
        lookupValue.setSearchAttribute("hsaIdentity");
        lookupValue.setSearchOperator(SearchOperatorExact.EXACT);
        lookupValue.setValue(hosPersonHsaId);
        parameters.setLookup(lookupValue);

        // Set attributes to return in search
        AttributeListType attributes = new AttributeListType();
        attributes.getAttribute().add("hsaTitle");
        attributes.getAttribute().add("paTitleCode");
        attributes.getAttribute().add("givenName");
        attributes.getAttribute().add("surName");
        attributes.getAttribute().add("personalPrescriptionCode");

        parameters.setAttributes(attributes);

        // Make call		
        HsawsSimpleLookupResponseType response = client.callHsawsSimpleLookup(parameters);

        // Read response and pick values		
        // Check if title is Läkare or befattning is 204010 (AT-läkare) or 204090
        for (AttributeValueListType attValList : response.getResponseValues()) {
            for (AttributeValuePairType attValue : attValList.getResponse()) {
                if (attValue.getAttribute().equalsIgnoreCase("hsaTitle")) {
                    if (attValue.getValue().contains("Läkare")) {
                        user.setDoctor(true);
                    }
                }
                if (attValue.getAttribute().equalsIgnoreCase("paTitleCode")) {
                    if (attValue.getValue().contains("204010")) {
                        user.setDoctor(true);
                    }
                }
                if (attValue.getAttribute().equalsIgnoreCase("givenName")) {
                    if (attValue.getValue().size() > 0) {
                        givenName = (attValue.getValue().get(0));
                    }
                }
                if (attValue.getAttribute().equalsIgnoreCase("surName")) {
                    if (attValue.getValue().size() > 0) {
                        sureName = (attValue.getValue().get(0));
                    }
                }
                if (attValue.getAttribute().equalsIgnoreCase("personalPrescriptionCode")) {
                    if (attValue.getValue().size() > 0) {
                        user.setPrescriptionCode(attValue.getValue().get(0));
                    }
                }
            }
        }
        
        user.setName(givenName + " " + sureName);

        return user;
    }
    
    public String getWorkplaceCode(String careUnitHsaId) throws Exception {
        String workplaceCode = "";
        
        log.debug("Calling HSA in getWorkplaceCode");

        // Create lookup parameter
        HsawsSimpleLookupType parameters = new HsawsSimpleLookupType();

        // Set Lookup data
        ExactType lookupValue = new ExactType();
        lookupValue.setSearchAttribute("hsaIdentity");
        lookupValue.setSearchOperator(SearchOperatorExact.EXACT);
        lookupValue.setValue(careUnitHsaId);
        parameters.setLookup(lookupValue);

        // Set attributes to return in search
        AttributeListType attributes = new AttributeListType();
        attributes.getAttribute().add("unitPrescriptionCode");

        parameters.setAttributes(attributes);

        // Make call            
        HsawsSimpleLookupResponseType response = client.callHsawsSimpleLookup(parameters);

        // Read response and pick values                
        for (AttributeValueListType attValList : response.getResponseValues()) {
            for (AttributeValuePairType attValue : attValList.getResponse()) {
                if (attValue.getAttribute().equalsIgnoreCase("unitPrescriptionCode")) {
                    if (attValue.getValue().size() > 0) {
                        workplaceCode = (attValue.getValue().get(0));
                    }
                }
            }
        }

        return workplaceCode;
    }

}
