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

import org.callistasoftware.netcare.commons.auth.api.CareGiverInterface;


public class AuthCareGiverImpl implements CareGiverInterface, Serializable {
    private static final long serialVersionUID = 1L;

    private String hsaId;

    private String name;
    
    private String email;

    public void setHsaId(String hsaId) {
        this.hsaId = hsaId;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(final String email) {
    	this.email = email;
    }

    public String getHsaId() {
        return hsaId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AuthCareGiverImpl [hsaId=" + hsaId + ", name=" + name + "]";
    }

	public String getEmail() {
		return this.email;
	}

	public String getId() {
		return this.getHsaId();
	}
}
