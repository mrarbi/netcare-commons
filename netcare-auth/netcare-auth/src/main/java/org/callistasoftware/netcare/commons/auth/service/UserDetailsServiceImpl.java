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
package org.callistasoftware.netcare.commons.auth.service;

import org.callistasoftware.netcare.commons.auth.api.AuthorityInterface;
import org.callistasoftware.netcare.commons.auth.api.UserInterface;
import org.callistasoftware.netcare.commons.auth.api.UserPropertiesResolver;
import org.callistasoftware.netcare.commons.auth.api.implementation.MedicalPersonalUserImpl;
import org.callistasoftware.netcare.commons.auth.spi.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @author Pär Wenåker
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private static final String HSA = "hsa";
    
//    @Autowired
//    private UserService userService;
    
    @Value("${lookup.strategy}")
    private String lookupStrategy;
    
    @Autowired
    private UserPropertiesResolver userPropertiesResolver;

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
//        log.debug("Loading user: {}", username);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//
//        log.debug("Using lookup strategy: {}", this.lookupStrategy);
//        
//        /*
//         * If user has chosen HSA as lookup strategy, we do not involve
//         * the local database at all. They are all assigned the ROLE_USER
//         * and permissions are on the care unit level.
//         */
//        UserDetails userDetails = null;
//        if (!lookupStrategy.equalsIgnoreCase(HSA)) {
//        	userDetails = findUserInLocalStore(username);
//        }
//
//        // Add authorities from local store
//        if (userDetails != null) {
//            authorities.addAll(userDetails.getAuthorities());
//        }
//        String tempUser = "Rajiv";
        authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
        MedicalPersonalUserImpl user = new MedicalPersonalUserImpl(username, true, true, true, true, authorities);

        userPropertiesResolver.resolve(user);

        log.debug("User: {}", user);
        return user;
    }

//    protected UserDetails findUserInLocalStore(String username) {
//        org.springframework.security.core.userdetails.User userDetails = null;
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//        try {
//            log.debug("Trying to find user with username {}, service {}", username, userService);
//            UserInterface userInterface = userService.findByUsername(username);
//            if (userInterface == null) {
//                log.debug("No user found {user}");
//                return null;
//            }
//            Set<AuthorityInterface> authorityInterfaces = userInterface.getAuthorities();
//            for (AuthorityInterface authorityInterface : authorityInterfaces) {
//                grantedAuthorities.add(new GrantedAuthorityImpl(authorityInterface.getAuthority().name()));
//            }
//            log.debug("Granted authorites {}", grantedAuthorities);
//            
//            // TODO: Password is null - OK? 
//            String password = userInterface.getPassword();
//            if (password == null) {
//                password = "";
//            }
//            userDetails = new org.springframework.security.core.userdetails.User(userInterface.getUsername(), password, true, true, true, true, grantedAuthorities);
//        } catch (UsernameNotFoundException ex) {
//            // Ignore
//        }
//        log.debug("User: {} {} found in local store with authorities {}", new Object[]{username, userDetails == null ? "NOT" : "", grantedAuthorities});
//        return userDetails;
//    }

    /**
     * @param userPropertiesResolver the userPropertiesResolver to set
     */
    public void setUserPropertiesResolver(UserPropertiesResolver userPropertiesResolver) {
        this.userPropertiesResolver = userPropertiesResolver;
    }

//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

}
