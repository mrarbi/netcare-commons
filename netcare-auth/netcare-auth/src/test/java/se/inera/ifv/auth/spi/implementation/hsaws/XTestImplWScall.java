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
///*
// * Inera Webcert - Sjukintygsapplikation
// *
// * Copyright (C) 2010-2011 Inera AB (http://www.inera.se)
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// *
// */
//package se.inera.ifv.auth.spi.implementation.hsaws;
//
//import org.w3.wsaddressing10.AttributedURIType;
//import se.inera.ifv.hsaws.v3.HsaWsFault;
//import se.inera.ifv.hsaws.v3.HsaWsResponderInterface;
//import se.inera.ifv.hsawsresponder.v3.*;
//import se.inera.ifv.hsawsresponder.v3.GetHsaUnitResponseType.TelephoneNumbers;
//
//import javax.jws.WebService;
//import javax.xml.ws.Endpoint;
//
//@WebService(
//		serviceName = "HsaWsResponderService", 
//		endpointInterface="se.inera.ifv.hsaws.v3.HsaWsResponderInterface", 
//		portName = "HsaWsResponderPort", 
//		targetNamespace = "urn:riv:hsa:HsaWs:3",
//		wsdlLocation = "schemas/wsdl/HsaWsInteraction_3.8.wsdl")
//public class XTestImplWScall implements HsaWsResponderInterface{
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//        Object implementor = new XTestImplWScall();
//        String address = "http://localhost:12000/HsaWsTest";
//        Endpoint.publish(address, implementor);
//        System.out.println("Producer ready...");
//        try {
//			Thread.sleep(60 * 60 * 1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println("Producer exiting");
//        System.exit(0);
//    }
//	
//	public GetHsaUnitResponseType getHsaUnit(AttributedURIType logicalAddress,
//			AttributedURIType id, LookupHsaObjectType parameters)
//			throws HsaWsFault {
//		// Read indata
//		
//		// Prepare test outdata
//		GetHsaUnitResponseType response = new GetHsaUnitResponseType();
//		response.setEmail("qwer@hotmail.com");
//		response.setName("Vårdenhet X");
//		AddressType adressLines = new AddressType();
//		adressLines.getAddressLine().add("Storgatan 19");
//		adressLines.getAddressLine().add("41252 LULEÅ");
//		response.setPostalAddress(adressLines);
//		response.setPostalCode("41252");
//		TelephoneNumbers telNumbers = new TelephoneNumbers();
//		telNumbers.getTelephoneNumber().add("031-12345678");
//		response.setTelephoneNumbers(telNumbers);
//	
//		return response;
//	}
//
//	public GetMiuForPersonResponseType getMiuForPerson(
//			AttributedURIType logicalAddress, AttributedURIType id,
//			LookupHsaObjectType parameters) throws HsaWsFault {
//		// Read indata
//		
//		// Prepare test outdata
//		GetMiuForPersonResponseType response = new GetMiuForPersonResponseType();
//		MiuInformationType miu1 = new MiuInformationType();
//		miu1.setCareGiver("Caregiver 1 HSAId");
//		miu1.setCareGiverName("Caregiver 1");
//		miu1.setCareUnitHsaIdentity("Careunit A HSAId");
//		miu1.setCareUnitName("Careunit A Name");
//		miu1.setHsaIdentity("");
//		HsaSystemRolesType hsaSystemRoles = new HsaSystemRolesType();
//		hsaSystemRoles.getHsaSystemRole().add("");
//		miu1.setHsaSystemRoles(hsaSystemRoles);
//		HsaTitlesType hsaTitles = new HsaTitlesType();
//		hsaTitles.getHsaTitle().add("LEG");
//		miu1.setHsaTitles(hsaTitles );
//		miu1.setMiuName("");
//		miu1.setMiuPurpose("");
//		MiuRightsType miuRights = new MiuRightsType();
//		miuRights.getMiuRight().add("Läsa;dia;SJF");
//		miuRights.getMiuRight().add("Läsa;fun;SJF");
//		miu1.setMiuRights(miuRights );
//		miu1.setPersonalPrescriptionCode("");
//		
//		response.getMiuInformation().add(miu1);
//		
//		return response;
//	}
//
//
//	public HsawsSimpleLookupResponseType hsawsSimpleLookup(
//			AttributedURIType logicalAddress, AttributedURIType id,
//			HsawsSimpleLookupType parameters) throws HsaWsFault {
//		return null;
//	}
//
//	public PingResponseType ping(AttributedURIType logicalAddress,
//			AttributedURIType id, PingType parameters) throws HsaWsFault {
//		PingResponseType response = new PingResponseType();
//		response.setMessage("message");
//		return response;
//	}
//
//	// Not used
//	public GetCareUnitResponseType getCareUnit(
//			AttributedURIType logicalAddress, AttributedURIType id,
//			LookupHsaObjectType parameters) throws HsaWsFault {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	// Not used
//	public GetHospPersonResponseType getHospPerson(
//			AttributedURIType logicalAddress, AttributedURIType id,
//			GetHospPersonType parameters) throws HsaWsFault {
//		return null;
//	}
//
//	// Not used
//	public VpwGetPublicUnitsResponseType vpwGetPublicUnits(
//			AttributedURIType logicalAddress, AttributedURIType id,
//			VpwGetPublicUnitsType parameters) throws HsaWsFault {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	// Not used
//	public GetInformationListResponseType getInformationList(
//			AttributedURIType logicalAddress, AttributedURIType id,
//			GetInformationListType parameters) throws HsaWsFault {
//		return null;
//	}
//
//	// Not used
//	public IsAuthorizedToSystemResponseType isAuthorizedToSystem(
//			AttributedURIType logicalAddress, AttributedURIType id,
//			IsAuthorizedToSystemType parameters) throws HsaWsFault {
//		return null;
//	}
//
//}
