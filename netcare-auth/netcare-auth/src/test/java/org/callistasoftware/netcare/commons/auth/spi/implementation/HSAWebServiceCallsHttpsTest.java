package org.callistasoftware.netcare.commons.auth.spi.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/auth-https-test.config.xml")
public class HSAWebServiceCallsHttpsTest {
	
    @Autowired
    private HSAWebServiceCalls client;

    @Test
	public void testPingMethod() throws Exception {
		try{
//			Thread.sleep(60000);
			client.callPing();
		} catch (Exception ex) {
			fail();
			assertNotNull(ex);
		}
	}
}