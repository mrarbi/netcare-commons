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

/**
 * @author Pär Wenåker
 *
 */
public class CareGiverImpl implements CareGiverInterface {
    private String id;
    private String name;
    
    public CareGiverImpl(String id, String name, String email) {
        this.id = id;
        this.name = name;
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.CareGiver#getId()
     */
    public String getHsaId() {
        return id;
    }
    
    /* (non-Javadoc)
     * @see se.inera.ifv.auth.userdetails.CareGiver#getName()
     */
    public String getName() {
        return name;
    }

	public String getId() {
		return this.id;
	}

	public String getEmail() {
		return this.id;
	}
}
