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

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.w3c.addressing.v1.AttributedURIType;

import riv.se_apotekensservice.lf._1.ArtikelinformationBasResponse;
import riv.se_apotekensservice.lf._1.AtkomstloggResponse;
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
        
        PatientResponse patient = createPatient("Agda", "Andersson", "188801010101");
        response.setPatient(patient);
        
        ReceptexpeditionsradBasResponse recept = createRecept(); 
        response.getLakemedelsforteckning().add(recept);
        
        AtkomstloggResponse logg = createLogg();
        response.getLogglista().add(logg);
        
        return response;
    }
    
    protected PatientResponse createPatient(String givenname, String surname, String ssn){
        PatientResponse patient = new PatientResponse();
        patient.setAvliden(false);
        patient.setEfternamn(surname);
        patient.setFornamn(givenname);
        patient.setPersonnummer(ssn);
        return patient;
    }
    
    protected ReceptexpeditionsradBasResponse createRecept(){
        ReceptexpeditionsradBasResponse recept = new ReceptexpeditionsradBasResponse();
        recept.setAktorsExpeditionsId("1");
        recept.setAntalForpackningar(2);
        recept.setDoseringstext("En om dagen");
        recept.setExpeditionsId("12");
        try {
            recept.setExpeditionsdatum(DatatypeFactory.newInstance().newXMLGregorianCalendarDate(2012, 4, 20, 1));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        recept.setForskrivararbetsplatsnamn("Vårdcentralen kusten, Kärna");
        recept.setForskrivarnamn("Görel Jöfeldt");
        recept.setMangd("100 mg");
        recept.setRadnummer(0);
        
        ArtikelinformationBasResponse artikelInfo = new ArtikelinformationBasResponse();
        artikelInfo.setProduktnamn("Kåvepenin");
        artikelInfo.setAtckod("J01CE02");
        artikelInfo.setForpackningsstorlek("125 mg");
        
        recept.setArtikelinformation(artikelInfo);
        return recept;
    }
    
    protected AtkomstloggResponse createLogg(){
        AtkomstloggResponse logg = new AtkomstloggResponse();
        logg.setAnvandarnamn("abc");
        logg.setArbetsplatsnamn("Apoteket Eken");
        logg.setArbetsplatsort("Kärna");
        try {
            logg.setAtkomstdatum(DatatypeFactory.newInstance().newXMLGregorianCalendarDate(2012, 5, 12, 1));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        
        return logg;
    }
    
}
