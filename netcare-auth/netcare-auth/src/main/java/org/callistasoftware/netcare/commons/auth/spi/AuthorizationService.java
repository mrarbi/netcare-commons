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
