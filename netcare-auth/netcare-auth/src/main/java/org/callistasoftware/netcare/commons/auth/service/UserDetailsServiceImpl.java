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
package org.callistasoftware.netcare.commons.auth.service;

import org.callistasoftware.netcare.commons.auth.api.UserPropertiesResolver;
import org.callistasoftware.netcare.commons.auth.api.implementation.MedicalPersonalUserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Pär Wenåker
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Autowired
    private UserPropertiesResolver userPropertiesResolver;

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String serialNumber) throws UsernameNotFoundException, DataAccessException {
        log.debug("Loading user by serial number : {}", serialNumber);
        
        String hsaId = getHsaIdFromSerialNumber(serialNumber);
        
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        MedicalPersonalUserImpl user = new MedicalPersonalUserImpl(hsaId, true, true, true, true, authorities);
        userPropertiesResolver.resolve(user);

        log.debug("User: {}", user);
        return user;
    }

    /**
     * @param userPropertiesResolver the userPropertiesResolver to set
     */
    public void setUserPropertiesResolver(UserPropertiesResolver userPropertiesResolver) {
        this.userPropertiesResolver = userPropertiesResolver;
    }

    private String getHsaIdFromSerialNumber(String serialNumber){
    	//regular expression[0-9]{12}
    	if(serialNumber.matches("[0-9]{12}")){
    		log.debug("serial number of the card is a person number={}", serialNumber);
    		return userPropertiesResolver.getHsaIdFromPersonNumber(serialNumber);
    	}else{
    		log.debug("serial number of the card is a hsaId={}", serialNumber);
    		return serialNumber;
    	}
	}
}
