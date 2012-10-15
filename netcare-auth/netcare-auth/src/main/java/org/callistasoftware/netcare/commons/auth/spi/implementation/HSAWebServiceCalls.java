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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3.wsaddressing10.AttributedURIType;
import org.callistasoftware.netcare.commons.auth.hsaws.v3.HsaWsResponderInterface;
import org.callistasoftware.netcare.commons.auth.hsaws.v3.HsaWsResponderService;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.*;

import javax.xml.ws.BindingProvider;
import java.net.URL;

@Service
public class HSAWebServiceCalls implements InitializingBean {
    
    private static final Logger log = LoggerFactory.getLogger(HSAWebServiceCalls.class);
    
    private AttributedURIType logicalAddressHeader = new AttributedURIType();;
    
    private AttributedURIType messageId = new AttributedURIType();

    private HsaWsResponderInterface serverInterface;

    @Value("${hsa.ws.services.url}")
    private String hsaUrl;
    
    @Value("${hsa.ws.service.logicaladdress}")
    private String hsaLogicalAddress;

    /**
     * @param hsaUrl the hsaUrl to set
     */
    public void setHsaUrl(String hsaUrl) {
        this.hsaUrl = hsaUrl;
    }

    /**
     * @param hsaLogicalAddress the hsaLogicalAddress to set
     */
    public void setHsaLogicalAddress(String hsaLogicalAddress) {
        this.hsaLogicalAddress = hsaLogicalAddress;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
    	log.debug("expecting these properties to be already set {}, {}", hsaUrl, hsaLogicalAddress);
        logicalAddressHeader.setValue(hsaLogicalAddress);
        
        serverInterface = getServerInterface(hsaUrl);
    }
    
    /**
     * Help method to test access to HSA
     * @throws Exception 
     */
    public void callPing() throws Exception {

        try {
            PingType pingtype = new PingType();
            PingResponseType response = serverInterface.ping(logicalAddressHeader, messageId, pingtype);
            log.debug("Response:" + response.getMessage());

        } catch (Exception ex) {
            log.warn("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }

    /**
     * Method used to get miuRights for a HoS Person
     * @param parameters
     * @return
     * @throws Exception 
     */
    public GetMiuForPersonResponseType callMiuRights(GetMiuForPersonType parameters) throws Exception {
        try {
            GetMiuForPersonResponseType response = serverInterface.getMiuForPerson(logicalAddressHeader, messageId,
                    parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }

    /**
     * Method to retrieve data for a hsa unit
     * @param hsaId
     * @throws Exception 
     */
    public GetCareUnitResponseType callGetCareunit(String hsaId) throws Exception {
        LookupHsaObjectType parameters = new LookupHsaObjectType();
        parameters.setHsaIdentity(hsaId);

        try {
            GetCareUnitResponseType response = serverInterface.getCareUnit(logicalAddressHeader, messageId, parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }

    /**
     * Method to retrieve the caregiver for a hsa unit
     * @param hsaId
     * @throws Exception 
     */
    public GetHsaUnitResponseType callGetHsaunit(String hsaId) throws Exception {
        LookupHsaObjectType parameters = new LookupHsaObjectType();
        parameters.setHsaIdentity(hsaId);

        try {
            GetHsaUnitResponseType response = serverInterface.getHsaUnit(logicalAddressHeader, messageId, parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }
    
    /**
     * Method to retrieve attributes for a HoS Person
     * @param parameters
     * @return
     * @throws Exception 
     */
    public HsawsSimpleLookupResponseType callHsawsSimpleLookup(HsawsSimpleLookupType parameters) throws Exception {
        try {
            HsawsSimpleLookupResponseType response = serverInterface.hsawsSimpleLookup(logicalAddressHeader, messageId,
                    parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
       }
    }
    
    /**
     * Method to retrieve attributes for a HoS Person
     * @param parameters
     * @return
     * @throws Exception 
     */
    public GetHsaPersonResponseType callGetHsaPerson(GetHsaPersonType parameters) throws Exception {
        try {
        	GetHsaPersonResponseType response = serverInterface.getHsaPerson(logicalAddressHeader, messageId,
                    parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
       }
    }

   
    private HsaWsResponderInterface getServerInterface(String wsUrl) {
        try {
        	log.debug("wsUrl is {}", wsUrl);
            // Get URL to wsdl file
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL wsdlUrl = loader.getResource("schemas/wsdl/HsaWsInteraction_3.14.wsdl");

            // Create web service client stub		
            HsaWsResponderService hwrs = new HsaWsResponderService(wsdlUrl); 
            HsaWsResponderInterface hwri = hwrs.getHsaWsResponderPort();
            
            // Set web service server url
            BindingProvider provider = (BindingProvider) hwri;
            provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
            return hwri;
        } catch (Throwable ex) {
        	log.error(ex.toString());
            log.error("Exception={}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
