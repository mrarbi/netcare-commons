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
package org.callistasoftware.netcare.mvk.authentication.service.api;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface PreAuthenticationCallback {

	/**
	 * If the user is non existing and the component
	 * is configured to automatically create non
	 * existing users. This method will allow creation
	 * of such users.
	 * @return
	 */
	UserDetails createMissingUser(final AuthenticationResult auth);
	
	/**
	 * This method is responsible for casting the principal to
	 * correct type.
	 * 
	 * The method MUST return null if the type of the principal
	 * could not be determined
	 * 
	 * @param principal
	 * @return
	 */
	UserDetails verifyPrincipal(final Object principal);
	
	/**
	 * This method is responsible for checking the authentication
	 * result and lookup the user based on what is found.
	 * 
	 * 
	 * @param auth
	 * @throws UsernameNotFoundException if the username could not be found
	 * @return never null, the principal if exist
	 */
	UserDetails lookupPrincipal(final AuthenticationResult auth) throws UsernameNotFoundException;
}
