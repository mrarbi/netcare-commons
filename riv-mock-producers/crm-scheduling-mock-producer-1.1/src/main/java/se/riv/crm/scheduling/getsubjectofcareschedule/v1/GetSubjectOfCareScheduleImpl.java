/**
 * Copyright 2009 Sjukvardsradgivningen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public

 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the

 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,

 *   Boston, MA 02111-1307  USA
 */
package se.riv.crm.scheduling.getsubjectofcareschedule.v1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.jws.WebService;

import riv.interoperability.headers._1.ActorType;
import se.riv.crm.scheduling.getsubjectofcareschedule.v1.rivtabp21.GetSubjectOfCareScheduleResponderInterface;
import se.riv.crm.scheduling.getsubjectofcarescheduleresponder.v1.GetSubjectOfCareScheduleResponseType;
import se.riv.crm.scheduling.getsubjectofcarescheduleresponder.v1.GetSubjectOfCareScheduleType;
import se.riv.crm.scheduling.v1.TimeslotType;

@WebService(serviceName = "GetSubjectOfCareScheduleResponderService", endpointInterface = "se.riv.crm.scheduling.getsubjectofcareschedule.v1.rivtabp21.GetSubjectOfCareScheduleResponderInterface", portName = "GetSubjectOfCareSchedulePort", targetNamespace = "urn:riv:crm:scheduling:GetSubjectOfCareSchedule:1:rivtabp21")
public class GetSubjectOfCareScheduleImpl implements GetSubjectOfCareScheduleResponderInterface {

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");

    @Override
    public GetSubjectOfCareScheduleResponseType getSubjectOfCareSchedule(
            String arg0, ActorType arg1, GetSubjectOfCareScheduleType parameters) {
        GetSubjectOfCareScheduleResponseType response = new GetSubjectOfCareScheduleResponseType();

        String subjectOfCareID = parameters.getSubjectOfCare();

        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100J",
                        "Vårdcentralen kullen", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100J",
                        "Vårdcentralen kullen", new Date(), new Date()));
        response.getTimeslotDetail().add(
                regularHealthCheck(subjectOfCareID, "HSASERVICES-100G",
                        "Vårdcentralen berget", new Date(), new Date()));

        return response;
    }

    private TimeslotType highBloodPreasure(String subjectOfCareId,
            String facility, String facilityName, Date startTime, Date endTime) {

        TimeslotType timeslot = new TimeslotType();
        timeslot.setBookingId(String.valueOf(new Random().nextInt()));
        timeslot.setCancelBookingAllowed(false);
        timeslot.setCareTypeID("Blodtryck");
        timeslot.setCareTypeName("Tidbokning för koll av blodtryck");
        timeslot.setEndTimeExclusive(format.format(endTime));
        timeslot.setHealthcareFacility(facility);
        timeslot.setHealthcareFacilityName(facilityName);
        timeslot.setIsInvitation(true);
        timeslot.setMessageAllowed(true);
        timeslot.setPerformer("HSASERVICES-1006");
        timeslot.setPurpose("Anledning är tidigare högt blodtryck");
        timeslot.setReason("Kontaktorsak som patient uppger vid bokning är högt blodtryck");
        timeslot.setRebookingAllowed(false);
        timeslot.setResourceID(String.valueOf(new Random().nextInt()));
        timeslot.setResourceName("Namn på bokad resurs");
        timeslot.setStartTimeInclusive(format.format(startTime));
        timeslot.setSubjectOfCare(subjectOfCareId);
        timeslot.setTimeTypeID("Identitet för tidstyp");
        timeslot.setTimeTypeName("Tidstyp för det bokade besöket.");
        return timeslot;
    }

    private TimeslotType regularHealthCheck(String subjectOfCareId,
            String facility, String facilityName, Date startTime, Date endTime) {

        TimeslotType timeslot = new TimeslotType();
        timeslot.setBookingId(String.valueOf(new Random().nextInt()));
        timeslot.setCancelBookingAllowed(false);
        timeslot.setCareTypeID("Hälsokontroll");
        timeslot.setCareTypeName("Tidbokning för allmän hälsokontroll");
        timeslot.setEndTimeExclusive(format.format(startTime));
        timeslot.setHealthcareFacility(facility);
        timeslot.setHealthcareFacilityName(facilityName);
        timeslot.setIsInvitation(true);
        timeslot.setMessageAllowed(true);
        timeslot.setPerformer("HSASERVICES-3456");
        timeslot.setPurpose("Anledning är allmän hälsokontroll");
        timeslot.setReason("Kontaktorsak som patient uppger är allmän hälsokontroll");
        timeslot.setRebookingAllowed(false);
        timeslot.setResourceID(String.valueOf(new Random().nextInt()));
        timeslot.setResourceName("Namn på bokad resurs");
        timeslot.setStartTimeInclusive(format.format(endTime));
        timeslot.setSubjectOfCare(subjectOfCareId);
        timeslot.setTimeTypeID("Identitet för tidstyp");
        timeslot.setTimeTypeName("Tidstyp för det bokade besöket.");
        return timeslot;
    }

}
