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

}
