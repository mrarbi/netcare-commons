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
package org.callistasoftware.netcare.commons.auth.api;


/**
 * @author Pär Wenåker
 *
 */
public interface CareUnitInterface {
	
	String getId();
	
    String getHsaId();

    String getName();

    String getWorkplaceCode();

    String getPhone();
    
    String getEmail();
    
    String getPostalAddress();
    
    String getPostalCode();
    
    String getPostalCity();
    
    CareGiverInterface getCareGiver();

}