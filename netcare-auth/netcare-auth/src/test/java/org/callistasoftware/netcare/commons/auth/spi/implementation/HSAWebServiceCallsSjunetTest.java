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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.callistasoftware.netcare.commons.auth.spi.implementation.HSAWebServiceCalls;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/auth-sjunet-test.config.xml")
public class HSAWebServiceCallsSjunetTest {
	
    @Autowired
    private HSAWebServiceCalls client;

    @Test
    public void testPingMethod() throws Exception {
		try{
			client.callPing();
		} catch (Exception ex) {
			fail();
			assertNotNull(ex);
		}
	}
}
