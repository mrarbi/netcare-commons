## Generellt ##

Om du redan vet hur SAML authentisering fungera och bara vill konfigurera modulen i din applikation. Gå [hit](UsingSAMLAuthenticationModule.md).

Logica tillhandahåller en Identity Provider (IdP) som ger möjlighet att logga in på myndigheters e-tjänster med hjälp av en e-legitimation. Denna IdP-tjänst baseras på en standarden SAML 2.0. För att koppla upp sig mot IdP-tjänsten behöver en Service Provider (SP) sättas upp. Kommunikationen mellan IdP och SP sker sedan via HTTP-protokollet med så kallad "Web Browser SSO". Detta innebär kortfattat att all kommunikation från SP:n till IdP:n sker med HTTP Redirect (GET) och all kommunikation från IdP:n till SP:n sker med HTTP POST.

#### Vid vanlig SP initierad inloggning ####

  1. Klienten hämtar en sida som kräver autentisering.
  1. SP kontrollerar om klienten har en inloggad session. Om ej, genereras ett SAML ”authentication request”-meddelande och riktar om klienten till IdP med meddelandet.
  1. Klienten skickar ”authentication request”-meddelandet med GET -metoden till IdP.
  1. Om klienten inte redan är inloggad mot IdP tillkommer steg för eventuellt val av autentiseringmetod samt autentisering.
  1. Efter autentiseringen skickas en websida tillbaka till klienten. Websidan innehåller responsen samt kod för att klienten skall posta den till SP.
  1. Responsen postas till SP.
  1. SP kontrollerar att responsen stämmer. Om den stämmer loggas användaren in och den önskade sidan returneras.

![![](http://wiki.netcare-commons.googlecode.com/git/idP%20flow.png)](http://wiki.netcare-commons.googlecode.com/git/idP%flow.png)

## Metadata ##

För att använda IdP-tjänsten behöver metadata läsas in, denna finns på:

  * Test: https://m10-mg-local.funktionstjanster.se/samlv2/idp/metadata/0
  * Produktion: https://m11-mg-local.funktionstjanster.se/samlv2/idp/metadata/0

### Certifikat ###

Spring SAML verifierar att svaret från IdP är korrekt (XML-C14N) med hjälp av Logicas publika nyckel för IdP tjänsten. Signeringen görs av samma SSL-certifikat som skyddar själva inloggningssidan.

### Attribut ###

Följande attribut skickas med i svaret från IdP:n och kan användas av SP.

| Subject\_SerialNumber | Svenskt personnummer med 12-siffror. |
|:----------------------|:-------------------------------------|
| Subject\_GivenName | Förnamn. Ett eller flera, beror på olika CA |
| Subject\_Surname | Efternamn |
| Subject\_CommonName | För och efternamn |
| Subject\_CountryName | Landskod, SE=Sverige |
| Gender | F (female)/M (male) Använd näst sista siffra personnummer -- jämn siffra F, udda M. |
| dateOfBirth | YYYYMMDD (ISO 8601) 8 första siffrorna i Subject\_SerialNumber |
| age | Ålder i år Räknas fram från 8 första siffrorna i Subject\_SerialNumber |
| Issuer\_CommonName | Namn på CA |
| Issuer\_OrganizationName | Organisationsnamn på CA (Utgivaren av e-legitimationen) |
| SecurityLevelDescription | Inloggningsmetodens läsbara namn, Text beskrivning av autentiseringsmetod. |
| SecurityLevel | QAA nivå. (3 för e-legitimation på fil och 4 för e-legitimation på kort) |
| ValidationOcspResponce (c?) | Signerat ”kvitto” från CA om spärrkontroll skett med OCSP. Tomt om CRL använts. |
| sn\_id | Personnummer med 10 siffror istället för 12. (position 3-12 i OSIF:Subject.SerialNumber) |
| CertificateSerialNumber | e-legitimationen/klientcertifikatets unika serienummer. Kan användas främst i forenstic syfte. Förväxla ej med personnummer i Subject\_SerialNumber. |

## Implementation i Spring ##

För att kommunicera med Logica:s IdP-tjänst används en SAML-modul till Spring Security (https://github.com/SpringSource/spring-security-saml/). Detta gör att man sömlöst kan integrera SAML-autentisering i sin nuvarande Spring-lösning. Dokumentation finns [här](https://jira.springsource.org/secure/attachment/15148/SpringSecurity+SAML+-+documentation.pdf).

Följande konfiguration används i API Gateway och är verifierad att fungera med BankID och Mobilt BankID.
```
<?xml version="1.0" encoding="UTF-8" ?>
<!--

       Copyright 2011,2012 Callista Enterprise AB

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

-->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:security="http://www.springframework.org/schema/security"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
              http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.1.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.1.xsd">

    <!-- Enable auto-wiring -->
    <context:annotation-config/>
    <context:component-scan base-package="
        org.springframework.security.saml,
        org.callistasoftware.netcare.commons.saml"/>

    <!-- Unsecured pages -->
<!--     <security:http security="none" pattern="/saml/web/**"/> -->
<!--     <security:http security="none" pattern="/logout.jsp"/> -->
<!--     <security:http security="none" pattern="/favicon.ico"/> -->

    <bean id="samlFilter" class="org.springframework.security.web.FilterChainProxy">
        <security:filter-chain-map request-matcher="ant">
            <security:filter-chain pattern="/saml/login/**" filters="samlEntryPoint"/>
            <security:filter-chain pattern="/saml/logout/**" filters="samlLogoutFilter"/>
            <security:filter-chain pattern="/saml/metadata/**" filters="metadataDisplayFilter"/>
            <security:filter-chain pattern="/saml/SSO/**" filters="samlWebSSOProcessingFilter"/>
            <security:filter-chain pattern="/saml/SSOHoK/**" filters="samlWebSSOHoKProcessingFilter"/>
            <security:filter-chain pattern="/saml/SingleLogout/**" filters="samlLogoutProcessingFilter"/>
        </security:filter-chain-map>
    </bean>

    <!-- Handler deciding where to redirect user after successful login -->
    <bean id="successRedirectHandler"
          class="org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler">
        <property name="defaultTargetUrl" value="${saml.successful.login.url}"/>
    </bean>
    <!--
    Use the following for interpreting RelayState coming from unsolicited response as redirect URL:
    -->
<!--     <bean id="successRedirectHandler" class="org.springframework.security.saml.SAMLRelayStateSuccessHandler"> -->
<!--        <property name="defaultTargetUrl" value="${saml.successful.login.url}" /> -->
<!--     </bean> -->

    <!-- Handler for successful logout -->
    <bean id="successLogoutHandler" class="org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler">
        <property name="defaultTargetUrl" value="${saml.successful.logout.url}"/>
    </bean>

    <!-- Register authentication manager with SAML provider -->
    <security:authentication-manager alias="authenticationManager">
        <security:authentication-provider ref="samlAuthenticationProvider"/>
    </security:authentication-manager>

    <!-- Logger for SAML messages and events -->
    <bean id="samlLogger" class="org.springframework.security.saml.log.SAMLDefaultLogger"/>

    <!-- Central storage of cryptographic keys -->
    <bean id="keyManager" class="org.springframework.security.saml.key.JKSKeyManager">
        <constructor-arg value="${saml.keystore.file}"/>
        <constructor-arg type="java.lang.String" value="${saml.keystore.password}"/>
        <constructor-arg>
            <map>
                <entry key="${saml.keystore.alias}" value="${saml.keystore.password}"/>
            </map>
        </constructor-arg>
        <constructor-arg type="java.lang.String" value="${saml.keystore.alias}"/>
    </bean>

    <!-- Entry point to initialize authentication, default values taken from properties file -->
    <bean id="samlEntryPoint" class="org.springframework.security.saml.SAMLEntryPoint">
        <property name="defaultProfileOptions">
            <bean class="org.springframework.security.saml.websso.WebSSOProfileOptions">
                <property name="includeScoping" value="false"/>
            </bean>
        </property>
    </bean>

    <!-- Filter automatically generates default SP metadata -->
    <bean id="metadataGeneratorFilter" class="org.springframework.security.saml.metadata.MetadataGeneratorFilter">
        <constructor-arg>
            <bean class="org.springframework.security.saml.metadata.MetadataGenerator">
                <property name="includeDiscovery" value="false"/>
                <property name="requestSigned" value="false"/>
                <property name="wantAssertionSigned" value="true"/>
                <property name="signMetadata" value="true"/>
                <property name="entityAlias" value="defaultAlias"/>
            </bean>
        </constructor-arg>
    </bean>

    <!-- The filter is waiting for connections on URL suffixed with filterSuffix and presents SP metadata there -->
    <bean id="metadataDisplayFilter" class="org.springframework.security.saml.metadata.MetadataDisplayFilter"/>

    <!-- IDP Metadata configuration - paths to metadata of IDPs in circle of trust is here -->
    <!-- Do no forget to call iniitalize method on providers -->
    <bean id="metadata" class="org.springframework.security.saml.metadata.CachingMetadataManager">
        <constructor-arg>
            <list>
                <bean class="org.opensaml.saml2.metadata.provider.HTTPMetadataProvider">
                    <!-- URL containing the metadata -->
                    <constructor-arg>
                        <value type="java.lang.String">${saml.metadata.url}</value>
                    </constructor-arg>
                    <!-- Timeout for metadata loading in ms -->
                    <constructor-arg>
                        <value type="int">5000</value>
                    </constructor-arg>
                    <property name="parserPool" ref="parserPool"/>
                </bean>
            </list>
        </constructor-arg>
        <!-- OPTIONAL used when one of the metadata files contains information about this service provider -->
        <!-- <property name="hostedSPName" value=""/> -->
        <!-- OPTIONAL property: can tell the system which IDP should be used for authenticating user by default. -->
        <property name="defaultIDP" value="${saml.metadata.url}"/>
    </bean>

    <!-- SAML Authentication Provider responsible for validating of received SAML messages -->
    <bean id="samlAuthenticationProvider" class="org.springframework.security.saml.SAMLAuthenticationProvider">
        <property name="userDetails" ref="citizenAuthenticationService" />
    </bean>

    <!-- Provider of default SAML Context -->
    <bean id="contextProvider" class="org.springframework.security.saml.context.SAMLContextProviderImpl"/>

    <!-- Processing filter for WebSSO profile messages -->
    <bean id="samlWebSSOProcessingFilter" class="org.springframework.security.saml.SAMLProcessingFilter">
        <property name="authenticationManager" ref="authenticationManager"/>
        <property name="authenticationSuccessHandler" ref="successRedirectHandler"/>
    </bean>

    <!-- Processing filter for WebSSO Holder-of-Key profile -->
    <bean id="samlWebSSOHoKProcessingFilter" class="org.springframework.security.saml.SAMLWebSSOHoKProcessingFilter">
        <property name="authenticationManager" ref="authenticationManager"/>
        <property name="authenticationSuccessHandler" ref="successRedirectHandler"/>
    </bean>

    <!-- Logout handler terminating local session -->
    <bean id="logoutHandler"
          class="org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler">
        <property name="invalidateHttpSession" value="false"/>
    </bean>

    <!-- Override default logout processing filter with the one processing SAML messages -->
    <bean id="samlLogoutFilter" class="org.springframework.security.saml.SAMLLogoutFilter">
        <constructor-arg ref="successLogoutHandler"/>
        <constructor-arg ref="logoutHandler"/>
        <constructor-arg ref="logoutHandler"/>
    </bean>

    <!-- Filter processing incoming logout messages -->
    <!-- First argument determines URL user will be redirected to after successful global logout -->
    <bean id="samlLogoutProcessingFilter" class="org.springframework.security.saml.SAMLLogoutProcessingFilter">
        <constructor-arg ref="successLogoutHandler"/>
        <constructor-arg ref="logoutHandler"/>
    </bean>

    <!-- Class loading incoming SAML messages from httpRequest stream -->
    <bean id="processor" class="org.springframework.security.saml.processor.SAMLProcessorImpl">
        <constructor-arg>
            <list>
                <ref bean="redirectBinding"/>
                <ref bean="postBinding"/>
                <ref bean="artifactBinding"/>
                <ref bean="soapBinding"/>
                <ref bean="paosBinding"/>
            </list>
        </constructor-arg>
    </bean>

    <!-- SAML 2.0 WebSSO Assertion Consumer -->
    <bean id="webSSOprofileConsumer" class="org.springframework.security.saml.websso.WebSSOProfileConsumerImpl"/>

    <!-- SAML 2.0 Holder-of-Key WebSSO Assertion Consumer -->
    <bean id="hokWebSSOprofileConsumer" class="org.springframework.security.saml.websso.WebSSOProfileConsumerHoKImpl"/>

    <!-- SAML 2.0 Web SSO profile -->
    <bean id="webSSOprofile" class="org.springframework.security.saml.websso.WebSSOProfileImpl"/>

    <!-- SAML 2.0 Holder-of-Key Web SSO profile -->
    <bean id="hokWebSSOProfile" class="org.springframework.security.saml.websso.WebSSOProfileConsumerHoKImpl"/>

    <!-- SAML 2.0 ECP profile -->
    <bean id="ecpprofile" class="org.springframework.security.saml.websso.WebSSOProfileECPImpl"/>

    <!-- SAML 2.0 Logout Profile -->
    <bean id="logoutprofile" class="org.springframework.security.saml.websso.SingleLogoutProfileImpl"/>

    <!-- Bindings, encoders and decoders used for creating and parsing messages -->
    <bean id="postBinding" class="org.springframework.security.saml.processor.HTTPPostBinding">
        <constructor-arg ref="parserPool"/>
        <constructor-arg ref="velocityEngine"/>
    </bean>

    <bean id="redirectBinding" class="org.springframework.security.saml.processor.HTTPRedirectDeflateBinding">
        <constructor-arg ref="parserPool"/>
    </bean>

    <bean id="artifactBinding" class="org.springframework.security.saml.processor.HTTPArtifactBinding">
        <constructor-arg ref="parserPool"/>
        <constructor-arg ref="velocityEngine"/>
        <constructor-arg>
            <bean class="org.springframework.security.saml.websso.ArtifactResolutionProfileImpl">
                <constructor-arg>
                    <bean class="org.apache.commons.httpclient.HttpClient"/>
                </constructor-arg>
                <property name="processor">
                    <bean id="soapProcessor" class="org.springframework.security.saml.processor.SAMLProcessorImpl">
                        <constructor-arg ref="soapBinding"/>
                    </bean>
                </property>
            </bean>
        </constructor-arg>
    </bean>

    <bean id="soapBinding" class="org.springframework.security.saml.processor.HTTPSOAP11Binding">
        <constructor-arg ref="parserPool"/>
    </bean>

    <bean id="paosBinding" class="org.springframework.security.saml.processor.HTTPPAOS11Binding">
        <constructor-arg ref="parserPool"/>
    </bean>

    <!-- Initialization of OpenSAML library-->
    <bean class="org.springframework.security.saml.SAMLBootstrap"/>

    <!-- Initialization of the velocity engine -->
    <bean id="velocityEngine" class="org.springframework.security.saml.util.VelocityFactory"
          factory-method="getEngine"/>

    <!-- XML parser pool needed for OpenSAML parsing -->
    <bean id="parserPool" class="org.opensaml.xml.parse.BasicParserPool" scope="singleton"/>

</beans>
```


  * **samlFilter** definierar endpoints för inloggning och utloggning. När användare går in på "/saml/login" skapas en SAML-fråga som skickas till IdP:n. IdP:n anropar sedan "/saml/SSO/" med HTTP POST med SAML-svaret efter att användaren autentiserat sig.
  * **samlEntryPoint** hanterar SAML-requesten till IdP:n. Denna använder sig av org.springframework.security.saml.websso.WebSSOProfileImpl för Webbrowser SSO. För att HTTP redirect ska användas sätts propertyn "binding" till "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect". Anropet cachas för att kunna verifieras mot svaret från IdP:n.
  * **metadataGeneratorFilter** sätter metadata till SAML-requesten. Konfigureras med att man inte ska kunna välja IdP då Logica:s IdP alltid kommer användas (includeDiscovery=false) och att anrop inte ska vara signerat (requestSigned = false), krypteringen räcker. Däremot är svaret signerat (wantAssertionSigned=true, signMetadata=true).
  * **metadata** sätter metadata från Logica:s IdP. Detta läses just nu in lokalt då denna information inte ska förändras. Det är dock möjligt att ange en URL istället för att hämta denna information direkt från IdP:n.
  * **samlAuthenticationProvider** verifierar SAML-svar från IdP:n och sätter Spring-specifik användarinfo. Här används klassen "org.apigw.authserver.saml.FunktionstjansterUserDetailsService" för att skapa ett User-objekt med username satt till personnumret som returneras i SAML-svaret ("sn\_id"). "webSSOprofileConsumer" används för att verifiera svaret.
  * **samlWebSSOProcessingFilter** hanterar SAML-svar från IdP:n och verifierar att denna information är korrekt med hjälp av `samlAuthenticationProvider`.
  * **keyManager** hanterar certifikaten som används genom att peka ut en keystore. I denna skall finnas certifikatet för att anropa Logicas IdP.
  * **samlLogger** sköter loggning. Genom att sätta variabeln "logMessages" till "true" kommer fullständig SAML-meddelanden loggas.




## Konfiguration ##

För konfigureringsdetaljer gå [hit](UsingSAMLAuthenticationModule.md).