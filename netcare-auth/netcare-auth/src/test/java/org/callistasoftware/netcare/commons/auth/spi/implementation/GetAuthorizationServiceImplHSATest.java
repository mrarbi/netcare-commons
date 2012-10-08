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

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;
import org.callistasoftware.netcare.commons.auth.api.MedicalPersonalUser;
import org.callistasoftware.netcare.commons.auth.spi.implementation.GetAuthorizationServiceImplHSA;
import org.callistasoftware.netcare.commons.auth.spi.implementation.HSAWebServiceCalls;
import org.callistasoftware.netcare.commons.auth.spi.vo.AuthCareGiverImpl;
import org.callistasoftware.netcare.commons.auth.spi.vo.AuthCareUnitImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetCareUnitResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaUnitResponseType;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GetAuthorizationServiceImplHSATest {

	@Mock private MedicalPersonalUser user;
	@Mock private HSAWebServiceCalls client;
	
	private Set<CareUnitInterface> units = new HashSet<CareUnitInterface>();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		when(user.getCareUnits()).thenReturn(units);
	}
	
	@After
	public void teardown() {
		this.units = new HashSet<CareUnitInterface>();
	}
	
	@Test
	public void testUserIsGranted() throws Exception {
		final String hsa = "A1";
		
		final AuthCareUnitImpl cu = new AuthCareUnitImpl();
		cu.setHsaId("A1");
		units.add(cu);
		
		final GetAuthorizationServiceImplHSA service = this.getService();
		
		boolean result = service.isAuthorizedForCareUnit(user, hsa);
		assertTrue(result);
	}
	
	@Test
	public void testFetchSubUnit() throws Exception {
		final String subunit = "Subunit";
		final String careUnit = "CareUnitWithMiu";
		
		/*
		 * Return the parent unit for our subunit
		 */
		final GetCareUnitResponseType response = new GetCareUnitResponseType();
		response.setCareUnitHsaIdentity(careUnit);
		when(client.callGetCareunit(subunit)).thenReturn(response); 
		
		/*
		 * Return the care giver for the parent unit
		 */
		final AuthCareGiverImpl cg = new AuthCareGiverImpl();
		cg.setHsaId("CareGiver");
		cg.setName("Care Giver");
		 
		final AuthCareUnitImpl cu = new AuthCareUnitImpl();
		cu.setCareGiver(cg);
		
		when(user.getCareUnit(careUnit)).thenReturn(cu);
		
		/*
		 * Return the subunit info
		 */
		final GetHsaUnitResponseType hsaUnit = new GetHsaUnitResponseType();
		hsaUnit.setName("Sub Unit");
		when(client.callGetHsaunit(subunit)).thenReturn(hsaUnit);
		
		/*
		 * Spy on the interface and intercept the call made to addCareUnit
		 */
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				
				units.add((CareUnitInterface) invocation.getArguments()[0]);
				return null;
				
			}
		}).when(user).addCareUnit(any(CareUnitInterface.class));
		
		final GetAuthorizationServiceImplHSA service = this.getService();
		
		boolean result = service.isAuthorizedForCareUnit(user, subunit);
		assertTrue(result);
		
		/*
		 * Test again and make sure we are not fetching from
		 * HSA
		 */
		result = service.isAuthorizedForCareUnit(user, subunit);
		
		verify(client, times(1)).callGetCareunit(subunit);
		verify(client, times(1)).callGetHsaunit(subunit);
	}
	
	private GetAuthorizationServiceImplHSA getService() {
		final GetAuthorizationServiceImplHSA service = new GetAuthorizationServiceImplHSA();
		service.setClient(client);
		
		return service;
	}
	
}
