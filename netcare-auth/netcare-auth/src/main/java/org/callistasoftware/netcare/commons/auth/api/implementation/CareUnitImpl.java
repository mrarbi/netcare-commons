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
