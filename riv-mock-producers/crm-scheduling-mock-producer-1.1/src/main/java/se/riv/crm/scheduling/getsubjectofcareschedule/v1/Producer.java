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

import javax.xml.ws.Endpoint;

public class Producer {

    //	protected Producer() throws Exception {
    //		System.out.println("Starting Producer");
    //
    //		// Loads a cxf configuration file to use
    //		final SpringBusFactory bf = new SpringBusFactory();
    //		final URL busFile = this.getClass().getClassLoader().getResource("cxf-producer.xml");
    //		final Bus bus = bf.createBus(busFile.toString());
    //
    //		SpringBusFactory.setDefaultBus(bus);
    //
    //		final Object implementor = new ProducerImpl();
    //		final String address = "https://localhost:21000/vp/FindContent/1/rivtabp21";
    //		Endpoint.publish(address, implementor);
    //	}

    protected Producer() throws Exception {
        System.out.println("Starting Producer");
        final Object implementor = new GetSubjectOfCareScheduleImpl();
        final String address = "http://localhost:9091/Schedulr/ws/GetSubjectOfCareSchedule";
        Endpoint.publish(address, implementor);
    }

    public static void main(String[] args) throws Exception {

        new Producer();
        System.out.println("Producer ready...");

        int minutes = 20;
        while (minutes>0) {
            System.out.println("Producer will exit in " + minutes + " minutes...");
            Thread.sleep(60 * 1000);
            minutes--;
        }

        System.out.println("Producer exiting");
        System.exit(0);
    }
}
