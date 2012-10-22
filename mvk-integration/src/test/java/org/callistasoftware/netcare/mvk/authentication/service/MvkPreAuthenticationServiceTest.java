/**
 * Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.callistasoftware.netcare.mvk.authentication.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;

import org.callistasoftware.netcare.mvk.authentication.service.api.AuthenticationResult;
import org.callistasoftware.netcare.mvk.authentication.service.impl.MvkPreAuthenticationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/mvk-test-config.xml")
@ActiveProfiles("qa")
public class MvkPreAuthenticationServiceTest {

	@Autowired
	private MvkPreAuthenticationServiceImpl service;

	private GrantedAuthority createRole(final String name) {
		return new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return name;
			}
		};
	}
	
	private class Patient extends User {

		public Patient() {
			super("patient", "", true, true, true, true, Collections.singletonList(createRole("ROLE_USER")));
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	private class CareActor extends User {

		public CareActor() {
			super("careactor", "", true, true, true, true, Collections.singletonList(createRole("ROLE_ADMIN")));
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	@Test
	public void testAuthWhenAlreadyAuthenticatedAsCareActor() throws Exception {
		final MvkPreAuthenticationServiceTestCallback cb = Mockito.mock(MvkPreAuthenticationServiceTestCallback.class);
		final PreAuthenticatedAuthenticationToken token = Mockito.mock(PreAuthenticatedAuthenticationToken.class);
		
		final CareActor cg = new CareActor();
		
		Mockito.when(token.getPrincipal()).thenReturn(cg);
		Mockito.when(cb.verifyPrincipal(token.getPrincipal())).thenReturn(cg);
		
		this.service.setCallback(cb);
		UserDetails details = this.service.loadUserDetails(token);
		assertNotNull(details);
		assertTrue(details instanceof CareActor);
		
		/*
		 * Verifications
		 */
		Mockito.verify(cb, Mockito.times(1)).verifyPrincipal(token.getPrincipal());
		Mockito.verifyNoMoreInteractions(cb);
	}
	
	@Test
	public void testAuthWhenAlreadyAuthenticatedAsPatient() throws Exception {
		final MvkPreAuthenticationServiceTestCallback cb = Mockito.mock(MvkPreAuthenticationServiceTestCallback.class);
		final PreAuthenticatedAuthenticationToken token = Mockito.mock(PreAuthenticatedAuthenticationToken.class);
		
		final Patient p = new Patient();
		
		Mockito.when(token.getPrincipal()).thenReturn(p);
		Mockito.when(cb.verifyPrincipal(token.getPrincipal())).thenReturn(p);
		
		this.service.setCallback(cb);
		UserDetails details = this.service.loadUserDetails(token);
		
		assertNotNull(details);
		assertTrue(details instanceof Patient);
		
		/*
		 * Verifications
		 */
		Mockito.verify(cb, Mockito.times(1)).verifyPrincipal(token.getPrincipal());
		Mockito.verifyNoMoreInteractions(cb);
	}
	
	@Test
	public void testAuthWhenMissingUser() throws Exception {
		final MvkPreAuthenticationServiceTestCallback cb = Mockito.mock(MvkPreAuthenticationServiceTestCallback.class);
		final PreAuthenticatedAuthenticationToken token = Mockito.mock(PreAuthenticatedAuthenticationToken.class);
		
		final AuthenticationResult fromMvk = Mockito.mock(AuthenticationResult.class);
		
		Mockito.when(token.getPrincipal()).thenReturn(fromMvk);
		Mockito.when(cb.lookupPrincipal(fromMvk)).thenThrow(new UsernameNotFoundException("Bla bla"));
		Mockito.when(cb.createMissingUser(fromMvk)).thenReturn(new Patient());
		
		this.service.setCallback(cb);
		UserDetails details = this.service.loadUserDetails(token);
		assertNotNull(details);
		assertTrue(details instanceof Patient);
		
		Mockito.verify(cb, Mockito.times(0)).verifyPrincipal(token.getPrincipal());
		Mockito.verify(cb, Mockito.times(1)).lookupPrincipal(fromMvk);
		Mockito.verify(cb, Mockito.times(1)).createMissingUser(fromMvk);
		Mockito.verifyNoMoreInteractions(cb);
	}
	
	@Test
	public void testAuthForCareActor() throws Exception {
		final MvkPreAuthenticationServiceTestCallback cb = Mockito.mock(MvkPreAuthenticationServiceTestCallback.class);
		final PreAuthenticatedAuthenticationToken token = Mockito.mock(PreAuthenticatedAuthenticationToken.class);
		
		final AuthenticationResult fromMvk = Mockito.mock(AuthenticationResult.class);
		
		Mockito.when(token.getPrincipal()).thenReturn(fromMvk);
		Mockito.when(cb.lookupPrincipal(fromMvk)).thenReturn(new CareActor());
		
		this.service.setCallback(cb);
		final UserDetails details = this.service.loadUserDetails(token);
		
		assertNotNull(details);
		assertTrue(details instanceof CareActor);
		
		Mockito.verify(cb, Mockito.times(0)).verifyPrincipal(token.getPrincipal());
		Mockito.verify(cb, Mockito.times(1)).lookupPrincipal(fromMvk);
		Mockito.verifyNoMoreInteractions(cb);
	}
	
	@Test
	public void testAuthForPatient() throws Exception {
		final MvkPreAuthenticationServiceTestCallback cb = Mockito.mock(MvkPreAuthenticationServiceTestCallback.class);
		final PreAuthenticatedAuthenticationToken token = Mockito.mock(PreAuthenticatedAuthenticationToken.class);
		
		final AuthenticationResult fromMvk = Mockito.mock(AuthenticationResult.class);
		
		Mockito.when(token.getPrincipal()).thenReturn(fromMvk);
		Mockito.when(cb.lookupPrincipal(fromMvk)).thenReturn(new Patient());
		
		this.service.setCallback(cb);
		final UserDetails details = this.service.loadUserDetails(token);
		
		assertNotNull(details);
		assertTrue(details instanceof Patient);
		
		Mockito.verify(cb, Mockito.times(0)).verifyPrincipal(token.getPrincipal());
		Mockito.verify(cb, Mockito.times(1)).lookupPrincipal(fromMvk);
		Mockito.verifyNoMoreInteractions(cb);
	}
	
}
