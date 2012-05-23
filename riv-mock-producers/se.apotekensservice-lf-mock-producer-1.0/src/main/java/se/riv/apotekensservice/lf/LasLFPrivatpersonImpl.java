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
package se.riv.apotekensservice.lf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.w3c.addressing.v1.AttributedURIType;

import riv.se_apotekensservice.lf._1.PatientResponse;
import riv.se_apotekensservice.lf._1.ReceptexpeditionsradBasResponse;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.laslfprivatperson.v1.rivtabp20.ApplicationException;
import se.riv.inera.se.apotekensservice.lf.laslfprivatperson.v1.rivtabp20.LasLFPrivatpersonResponderInterface;
import se.riv.inera.se.apotekensservice.lf.laslfprivatperson.v1.rivtabp20.SystemException;
import se.riv.se.apotekensservice.lf.laslfprivatpersonresponder.v1.LasLFPrivatpersonRequestType;
import se.riv.se.apotekensservice.lf.laslfprivatpersonresponder.v1.LasLFPrivatpersonResponseType;

@WebService(serviceName = "LasLFPrivatpersonResponderService", endpointInterface = "se.riv.inera.se.apotekensservice.lf.laslfprivatperson.v1.rivtabp20.LasLFPrivatpersonResponderInterface", portName = "LasLFPrivatpersonPort", targetNamespace = "urn:riv:inera:se.apotekensservice:lf:LasLFPrivatperson:1:rivtabp20")
public class LasLFPrivatpersonImpl implements LasLFPrivatpersonResponderInterface {

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");

    public LasLFPrivatpersonResponseType lasLFPrivatperson(
            LasLFPrivatpersonRequestType parameters,
            AttributedURIType logicalAddress, ArgosHeaderType argosHeader)
            throws SystemException, ApplicationException {
        LasLFPrivatpersonResponseType response = new LasLFPrivatpersonResponseType();
        
        PatientResponse patient = new PatientResponse();
        patient.setAvliden(true);
        patient.setEfternamn("Andersson");
        patient.setFornamn("Agda");
        patient.setPersonnummer("188801010101");
        response.setPatient(patient);
        
        ReceptexpeditionsradBasResponse recept = new ReceptexpeditionsradBasResponse();
        recept.setAktorsExpeditionsId("10101");
        
        response.getLakemedelsforteckning().add(recept);
        
        
        return response;
    }
}
