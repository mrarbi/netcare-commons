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
