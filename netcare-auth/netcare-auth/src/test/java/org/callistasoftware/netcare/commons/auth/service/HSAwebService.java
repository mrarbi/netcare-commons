package org.callistasoftware.netcare.commons.auth.service;

import javax.jws.WebService;

import org.callistasoftware.netcare.commons.auth.hsaws.v3.HsaWsFault;
import org.callistasoftware.netcare.commons.auth.hsaws.v3.HsaWsResponderInterface;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetCareUnitListResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetCareUnitMembersResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetCareUnitResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHospPersonResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHospPersonType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaPersonResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaPersonType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetHsaUnitResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetInformationListResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetInformationListType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetMiuForPersonResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetMiuForPersonType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetPriceUnitsForAuthResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.GetPriceUnitsForAuthType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.HsawsSimpleLookupResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.HsawsSimpleLookupType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.IsAuthorizedToSystemResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.IsAuthorizedToSystemType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.LookupHsaObjectType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.PingResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.PingType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.VpwGetPublicUnitsResponseType;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.VpwGetPublicUnitsType;
import org.w3.wsaddressing10.AttributedURIType;


public class HSAwebService implements HsaWsResponderInterface {
    
	@Override
	public PingResponseType ping(AttributedURIType logicalAddress, AttributedURIType id, PingType parameters) 
			throws HsaWsFault {
		PingResponseType response = new PingResponseType();
		response.setMessage("boogie woogie?");
		return response;
	}

	@Override
	public IsAuthorizedToSystemResponseType isAuthorizedToSystem(
			AttributedURIType logicalAddress, AttributedURIType id,
			IsAuthorizedToSystemType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VpwGetPublicUnitsResponseType vpwGetPublicUnits(
			AttributedURIType logicalAddress, AttributedURIType id,
			VpwGetPublicUnitsType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetCareUnitListResponseType getCareUnitList(
			AttributedURIType logicalAddress, AttributedURIType id,
			LookupHsaObjectType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetCareUnitResponseType getCareUnit(
			AttributedURIType logicalAddress, AttributedURIType id,
			LookupHsaObjectType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetHsaUnitResponseType getHsaUnit(AttributedURIType logicalAddress,
			AttributedURIType id, LookupHsaObjectType parameters)
			throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetPriceUnitsForAuthResponseType getPriceUnitsForAuth(
			AttributedURIType logicalAddress, AttributedURIType id,
			GetPriceUnitsForAuthType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HsawsSimpleLookupResponseType hsawsSimpleLookup(
			AttributedURIType logicalAddress, AttributedURIType id,
			HsawsSimpleLookupType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetHsaPersonResponseType getHsaPerson(
			AttributedURIType logicalAddress, AttributedURIType id,
			GetHsaPersonType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetCareUnitMembersResponseType getCareUnitMembers(
			AttributedURIType logicalAddress, AttributedURIType id,
			LookupHsaObjectType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetHospPersonResponseType getHospPerson(
			AttributedURIType logicalAddress, AttributedURIType id,
			GetHospPersonType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetInformationListResponseType getInformationList(
			AttributedURIType logicalAddress, AttributedURIType id,
			GetInformationListType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetMiuForPersonResponseType getMiuForPerson(
			AttributedURIType logicalAddress, AttributedURIType id,
			GetMiuForPersonType parameters) throws HsaWsFault {
		// TODO Auto-generated method stub
		return null;
	}

}
