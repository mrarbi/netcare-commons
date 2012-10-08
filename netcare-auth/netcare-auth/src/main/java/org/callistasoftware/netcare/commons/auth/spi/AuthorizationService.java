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

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;
import org.callistasoftware.netcare.commons.auth.api.MedicalPersonalUser;


/**
* Service provider interface for retrieving authorization information. The service interface should be implemented
* for each service provider that should answer these authorization questions. 
*/

public interface AuthorizationService {
	
	/**
	 * Check whether the principal is authorized for a certain
	 * facility
	 * @param hsaId
	 * @return
	 */
	boolean isAuthorizedForCareUnit(final MedicalPersonalUser user, final String hsaId);

    /**
     * Returns a list of careunits that a HoS person are authorized to work at.
     * @param hosPersonsnHsaId
     * @return true or false
     */
    List<CareUnitInterface> getAuthorizedCareUnitsForHosPerson(String hosPersonHsaId) throws Exception;

}
