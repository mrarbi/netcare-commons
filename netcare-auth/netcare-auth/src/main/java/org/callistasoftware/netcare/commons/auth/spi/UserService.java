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
