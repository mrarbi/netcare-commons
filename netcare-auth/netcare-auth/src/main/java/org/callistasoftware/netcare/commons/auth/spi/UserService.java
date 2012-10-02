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
package org.callistasoftware.netcare.commons.auth.spi;


import java.util.List;
import java.util.Set;

import org.callistasoftware.netcare.commons.auth.api.AuthorityRole;
import org.callistasoftware.netcare.commons.auth.api.UserInterface;
import org.callistasoftware.netcare.commons.auth.exception.WebcertException;

/**
 * Service interface for user interactions. Create, edit and delete a user in webcert.
 */
public interface UserService {
    
    /**
     * Create user with username and password, assigned roles and careunits if existing.
     * @param user
     * @param roles
     * @param careunitIds
     * @Return {@link UserInterface}
     */
    UserInterface createUser(UserInterface user, Set<AuthorityRole> roles, List<String> careunitIds) throws WebcertException;
    
    /**
     * Delete the user with the given id. 
     * @param username
     */
    void deleteUser(String username) throws WebcertException;
    
    /**
     * Returns a list with all users in the system. 
     * @return {@link List} 
     */
    List<UserInterface> findAllUsers();
    
    /** 
     * Returns a user with the specified id. 
     * @param id The id
     * @return User
     */
    UserInterface find(String id);
    
    /**
     * Returns a user with the specified username.
     * @param username  the unique username
     * @return {@link UserInterface}
     */
    UserInterface findByUsername(String username);

    List<UserInterface> findUsersInCareUnit(String careUnitId);

}
