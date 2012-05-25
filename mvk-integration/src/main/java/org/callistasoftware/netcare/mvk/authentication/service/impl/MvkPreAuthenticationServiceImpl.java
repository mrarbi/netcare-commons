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
package org.callistasoftware.netcare.mvk.authentication.service.impl;

import org.callistasoftware.netcare.mvk.authentication.service.MvkPreAuthenticationService;
import org.callistasoftware.netcare.mvk.authentication.service.api.AuthenticationResult;
import org.callistasoftware.netcare.mvk.authentication.service.api.PreAuthenticationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class MvkPreAuthenticationServiceImpl implements MvkPreAuthenticationService {

	private final Logger log = LoggerFactory.getLogger(MvkPreAuthenticationServiceImpl.class);
	
	protected Logger getLog() {
		return log;
	}
	
	@Value("${mvk.create-missing-users}")
	private String createMissingUsers;
	
	private PreAuthenticationCallback callback;
	
	@Autowired
	public void setCallback(final PreAuthenticationCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authToken)
			throws UsernameNotFoundException {
		getLog().info("Retrieve information about user. The user is pre-authenticated...");
		
		final AuthenticationResult preAuthenticated;
		if (authToken.getPrincipal() instanceof AuthenticationResult) {
			getLog().debug("Not yet authenticated. We have an authentication result from MVK.");
			preAuthenticated = (AuthenticationResult) authToken.getPrincipal();
		} else {
			/*
			 * We already have an authenticated principal.
			 * Let callback determine whether this is a care giver or patient
			 */
			final UserDetails principal = this.callback.verifyPrincipal(authToken.getPrincipal());
			if (principal != null) {
				getLog().debug("Already authenticated. The type of authentication is: {}", principal.getClass().getSimpleName());
				return principal;
			} else {
				throw new RuntimeException("Unknown authentication.");
			}
		}
		
		/*
		 * We have an authentication result. Let's deal with it
		 */
		try {
			return this.callback.lookupPrincipal(preAuthenticated);
		} catch (final Exception e) {
			// This is ok... just create the missing user
		}
		
		if (this.createMissingUsers != null && Boolean.valueOf(this.createMissingUsers)) {
			return this.callback.createMissingUser(preAuthenticated);
		} else {
			throw new RuntimeException("Please configure whether to create missing users or not. This is done via the mvk.create-missing-users property.");
		}
	}

}
