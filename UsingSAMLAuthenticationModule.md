# Introduction #

For details on how module and SAML Authentication works. Please go [here](SAMLAuthenticationModule.md).

This page will take you through configurations that you should make in your project to be able to use SAML authentication module successfully. An example application using the module can be found [here](https://code.google.com/p/netcare-commons/source/browse/#git%2Fnetcare-auth%2FsamlTestApplication).


# Details #

To be able to use the module, you will have to make three changes to your project.

  1. Add some configurations in your Spring Security configuration.
  1. Import saml-config.xml file from classpath as a resource.
  1. Define a few saml specific properties in a .properties file in your project and load it using property place holder.

### 1. Import SAML configuration ###

You will need to import saml configuration (saml-config.xml) from class path preferably in your Spring Security configuration . Alternatively, you can override the configuration by loading a modified local saml-config.xml.

```
        ...

        <import resource="classpath*:/saml-config.xml" />

        ...
```

[Example - using import resource](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/samlTestApplication/src/main/webapp/WEB-INF/web-security.xml)

### 2. SAML properties ###
Saml module expects values for following properties to be already loaded in the context:

  1. saml.successful.login.url : URL where we want user to end up after successful login
  1. saml.successful.logout.url : URL where we want user to end up after successful log out
  1. saml.metadata.url : URL for xml file describing our identity provider
  1. saml.citizen.roles : Role to be assigned to a logged in user
  1. saml.keystore.file : Keystore file
  1. saml.keystore.alias : Valid certificate inside the above keystore
  1. saml.keystore.password : Password for keystore

```
        ...

        <context:property-placeholder location="classpath:/my-saml.properties"/>

        <import resource="classpath*:/saml-config.xml" />

        ...
```

[Example properties file](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/samlTestApplication/src/main/resources/sample-application.properties)

### 3. Entry point configuration ###

You will need to add following configuration to your Spring Security XML configuration

```
        ...

        <context:property-placeholder location="classpath:/my-saml.properties"/>

        <import resource="classpath*:/saml-config.xml" />

	<security:http entry-point-ref="samlEntryPoint">
		 <security:intercept-url pattern="/**" access="${saml.citizen.roles}" />
		 <security:custom-filter before="FIRST" ref="metadataGeneratorFilter"/>
		 <security:custom-filter after="BASIC_AUTH_FILTER" ref="samlFilter"/>
		 <security:anonymous />
	</security:http>

        ...
```

The configuration above specifies and lets you manage entry point configuration for spring security in saml context. Also, it gives you option to override configuration of metadataGeneratorFilter bean already defined in `saml-config.xml` without need to recompiling it from source. Details on what each of those properties does can be found [here](SAMLAuthenticationModule.md).

[Example web-security.xml](https://code.google.com/p/netcare-commons/source/browse/netcare-auth/samlTestApplication/src/main/webapp/WEB-INF/web-security.xml)