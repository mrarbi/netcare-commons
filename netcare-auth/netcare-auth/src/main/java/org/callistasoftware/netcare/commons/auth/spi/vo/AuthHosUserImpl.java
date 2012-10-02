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
package org.callistasoftware.netcare.commons.auth.spi.vo;

import java.io.Serializable;

import org.callistasoftware.netcare.commons.auth.api.HosUserInterface;


public class AuthHosUserImpl implements HosUserInterface, Serializable {
    private static final long serialVersionUID = 1L;

    private String hsaId;

    private String name;

    private String prescriptionCode;

    private boolean doctor;

    public void setHsaId(String hsaId) {
        this.hsaId = hsaId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrescriptionCode(String prescriptionCode) {
        this.prescriptionCode = prescriptionCode;
    }

    public void setDoctor(boolean doctor) {
        this.doctor = doctor;
    }

    public String getHsaId() {
        return hsaId;
    }

    public String getName() {
        return name;
    }

    public String getPrescriptionCode() {
        return prescriptionCode;
    }

    public boolean isDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return "AuthHosUserImpl [hsaId=" + hsaId + ", name=" + name + ", prescriptionCode=" + prescriptionCode
                + ", doctor=" + doctor + "]";
    }

}
