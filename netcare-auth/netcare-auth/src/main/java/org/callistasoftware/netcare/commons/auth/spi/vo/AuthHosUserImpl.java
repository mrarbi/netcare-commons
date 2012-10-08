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
