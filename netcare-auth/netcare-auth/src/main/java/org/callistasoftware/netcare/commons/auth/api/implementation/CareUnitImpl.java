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

import org.callistasoftware.netcare.commons.auth.api.CareGiverInterface;
import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;

/**
 * @author Pär Wenåker
 *
 */
public class CareUnitImpl implements CareUnitInterface {
    private String id;
    private String name;
    private String code;
    private String phone;
    private String email;
    private String postaladress;
    private String postalcode;
    private String postalcity;
    private CareGiverInterface careGiver;
    
    
    public CareUnitImpl(String id, String name, String code, CareGiverInterface careGiver) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.careGiver = careGiver;
    }
    
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.CareUnit#getId()
     */
    public String getHsaId() {
        return id;
    }
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.CareUnit#getName()
     */
    public String getName() {
        return name;
    }
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.CareUnit#getCode()
     */
    public String getWorkplaceCode() {
        return code;
    }
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.CareUnit#getCareGiver()
     */
    public CareGiverInterface getCareGiver() {
        return careGiver;
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.entity.CareUnitInterface#getPhone()
     */
    public String getPhone() {
        return phone;
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.entity.CareUnitInterface#getEmail()
     */
    public String getEmail() {
        return email;
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.entity.CareUnitInterface#getPostalAddress)
     */
    public String getPostalAddress() {
        return postaladress;
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.entity.CareUnitInterface#getPostalCode()
     */
    public String getPostalCode() {
        return postalcode;
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.webcert.core.entity.CareUnitInterface#getPostalCity()
     */
    public String getPostalCity() {
        return postalcity;
    }

	public String getId() {
		return this.getHsaId();
	}
    
}
