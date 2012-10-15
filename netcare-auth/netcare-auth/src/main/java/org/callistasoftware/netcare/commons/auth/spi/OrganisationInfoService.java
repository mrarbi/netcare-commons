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

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;
import org.callistasoftware.netcare.commons.auth.api.HosUserInterface;


/**
* Service provider interface for retrieving organization information. The service interface should be implemented
* for each service provider that should answer these organization questions. 
*/
public interface OrganisationInfoService {

    /**
     * Return information about a careunit.
     * @param careunitHsaId
     * @return CareUnit
     */
    public CareUnitInterface getCareUnitFullInfo(String careUnitHsaId) throws Exception ;

    /**
     * Return information about a parent careunit.
     * @param careunitHsaId
     * @return CareUnitId as a String
     */
    public String getParentCareUnit(String careUnitHsaId) throws Exception ;

    /**
     * Return information about a hosPerson.
     * @param hosPersonHsaId
     * @return User
     */
    public HosUserInterface getHosPersonFullInfo(String hosPersonHsaId) throws Exception;

    /**
     * Returns hosPersonHsaId.
     * @param personNumber
     * @return String
     */
    public String getHosHsaIdFromPersonNumber(String personNumber) throws Exception;
}
