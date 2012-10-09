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
