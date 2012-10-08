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
package org.callistasoftware.netcare.commons.auth.api;


import java.util.Set;

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;

/**
 * Author: Henrik Rostam
 */
public interface UserInterface {

    Set<AuthorityInterface> getAuthorities();

    void setAuthorities(Set<AuthorityInterface> authorities);

    Set<CareUnitInterface> getCareUnits();

    void setCareUnits(Set<CareUnitInterface> careUnits);

    void addCareUnit(CareUnitInterface careUnit);

    String getId();

    String getUsername();

    void setUsername(String userName);

    String getFirstname();

    void setFirstname(String firstName);

    String getLastname();

    void setLastname(String lastName);

    Boolean isEnabled();

    void setEnabled(Boolean enabled);

    String getPassword();

    void setPassword(String password);

    void setId(String id);

    @Override
    String toString();
}
