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
package se.inera.ifv.auth.spi.implementation.hsaws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.callistasoftware.netcare.commons.auth.spi.implementation.GetAuthorizationServiceImplHSA;
import org.callistasoftware.netcare.commons.auth.spi.implementation.HSAWebServiceCalls;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;


/**
 * @author Pär Wenåker
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/auth-sjunet-test.config.xml")
public class HSAWebServiceCallsTestClient {
    private static final Logger log = LoggerFactory.getLogger(HSAWebServiceCallsTestClient.class);
	
    @Autowired
    private HSAWebServiceCalls client;
    
    @Value("${hsa.ws.certificate.file}")
    private String certificateFile;
    
    @Value("${hsa.ws.truststore.file}")
    private String trustStore;
    
    @Test
    public void testTrustStoreSet(){
    	
        try {
        	ResourceUtils.getFile(this.getClass().getResource(certificateFile));
        	ResourceUtils.getFile(this.getClass().getResource(trustStore));
//        	HSAWebServiceCallsTestClient.class.getResource(trustStore).getFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
        String trustStore = System.getProperty("javax.net.ssl.trustStore");
        if (trustStore == null) {
            log.debug("javax.net.ssl.trustStore is not defined");
            assertTrue(Boolean.FALSE);
        } else {
            log.debug("javax.net.ssl.trustStore = {}", trustStore);
        }
    }

//    public static void main(String[] args) {
////        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "HSAWebServiceCallsTest-applicationContext.xml", "services-config.xml"});
////        HSAWebServiceCalls client = (HSAWebServiceCalls)ctx.getBean("wsCalls");
//        try {
//            client.callPing();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    @Test
    public void testPingMethod() throws Exception {
		try{
			log.debug("certificate file adress is {}", certificateFile);
			log.debug("trust store adress is {}", trustStore);
			assertEquals(certificateFile, "test-auth/src/test/resources/testCertificates/hsaws-user.ifv.sjunet.org_auth.p12");
			
			
//			Thread.sleep(60000);
			client.callPing();
			assertTrue(Boolean.FALSE);
		} catch (Exception ex) {
			fail();
			assertNotNull(ex);
		}
	}
}
