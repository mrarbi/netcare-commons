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
package org.callistasoftware.netcare.commons.auth.api.implementation;

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;
import org.callistasoftware.netcare.commons.auth.api.MedicalPersonalUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.*;

/**
 * @author Pär Wenåker
 *
 */
public class MedicalPersonalUserImpl extends User implements MedicalPersonalUser {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String perscriptionCode;
    private boolean doctor;
    private Map<String, CareUnitInterface> careUnits = new HashMap<String, CareUnitInterface>();
    
    public MedicalPersonalUserImpl(String username, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.MedicalPersonalUser#getCareUnit(java.lang.String)
     */
    public CareUnitInterface getCareUnit(String careUnitId) {
        return careUnits.get(careUnitId);
    }
    
    
    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.spi.authentication.MedicalPersonalUser#getId()
     */
    public String getHsaId() {
        return getUsername();
    }
    
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.MedicalPersonalUser#getName()
     */
    public String getName() {
        return name;
    }
    
    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.entity.HosUserInterface#getPrescriptionCode()
     */
    public String getPrescriptionCode() {
        return perscriptionCode;
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.entity.HosUserInterface#isDoctor()
     */
    public boolean isDoctor() {
        return doctor;
    }
    
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.MedicalPersonalUser#getCareUnits()
     */
    public Set<CareUnitInterface> getCareUnits() {
        return Collections.unmodifiableSet(new HashSet<CareUnitInterface>(careUnits.values()));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPerscriptionCode(String perscriptionCode) {
        this.perscriptionCode = perscriptionCode;
    }
    
    public void setDoctor(boolean doctor) {
        this.doctor = doctor;
    }
    
    public void addCareUnit(CareUnitInterface careUnit) {
        this.careUnits.put(careUnit.getHsaId(), careUnit);
    }

    @Override
    public String toString() {
        return "MedicalPersonalUserImpl [name=" + name + ", perscriptionCode=" + perscriptionCode + ", doctor="
                + doctor + ", careUnits=" + careUnits + "]";
    }
}
