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
