# Introduction #

This module handles server side authentication for users with SITHS card. It also grabs users' detailed information from HSA web services. It takes care of communication with HSA and validation process of SITHS card. User only need to use it's browser and SITHS card with Net iD plug-in to log in.

# Details #

This module has two responsibilities. We will try to explain them one by one:

### SITHS Authentication ###

**Workflow:**

![![](http://wiki.netcare-commons.googlecode.com/git/SITHS%20Authentication%20diagram.png)](http://wiki.netcare-commons.googlecode.com/git/SITHS%20Authentication%20diagram.png)

The work flow for SITHS authentication starts with,
  * user going to a protected URL.
  * Spring security triggers x509 based authentication.
  * The browser prompts user to insert their SITH card.
  * Net iD plugin takes over, prompting user to enter their password.
  * Net iD plugin sends user certificate to our server to authenticate the user.
  * Spring security verifies user certificate with configured trust store.
  * Spring security logs user in, extracts serial number of the certificate and passes it as user name to userdetails service.



**Configuration:**

SITHS authentication part is mostly configured out of box in this module. You would need to copy and include example [web-security.xml](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/testApplication/src/main/webapp/WEB-INF/web-security.xml) from [testApplication](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/testApplication/). As well as some of security filters mentioned in [web.xml](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/testApplication/src/main/webapp/WEB-INF/web.xml). Then, you will have to configure your application server's with a HTTPS port. Example [truststore](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/testApplication/src/main/resources/truststore.jks) and [server](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/testApplication/src/main/resources/server.jks) certificate store are also available in src/main/resources folder of [testApplication](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/testApplication/).



**Note:**

SITHS card's serial number has to be either 12 characters person number or HSA Id. That helps the module resolve and do a look up for user information from HSA web services.





### HSA Web Services ###

**Workflow:**

After user authentication is successful, the work flow for getting information from HSA web services starts with,
  * userDetails service checks if serial number of card is a swedish person number.
  * If it is a person number, it asks HSA for HSA ID for that person number.
  * When it has HSA ID, it asks HSA for all the relevant user details for HSA ID alongwith care units it belongs to.
  * It stores that information in an instance of '[MedicalPersonalUserImpl](https://code.google.com/p/netcare-commons/source/browse/src/main/java/org/callistasoftware/netcare/commons/auth/api/implementation/MedicalPersonalUserImpl.java?repo=auth)' class.
  * This class instance and all attributes are easily accessible from `SecurityContextHolder` as shown in testApplication.


**Configuration:**

HSA communication is configured in '[auth.config.properties](https://code.google.com/p/netcare-commons/source/browse/src/main/resources/auth.config.properties?repo=auth)' file found in resources module. You will also need to configure your web.xml file with `contextConfigLocation` parameter and include 'auth.config.xml' from classpath.

You would need a client certificate as well as client truststore to successfully configure communications. HSA should be contacted to get client credentials. And SITHS public CA can be added to a simple truststore and be used as client truststore.