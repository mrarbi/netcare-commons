# Getting Started #
The mvk-integration module simplifies the integration with MVK using their SSO solution, also known as Caia. The module is an extension to spring security and prerequisities are a Spring based Java application.



## Build & Install ##
Start of by cloning the netcare-commons project. After that, navigate to the mvk-integration directory and build the module.
```
mvn clean install
```

This will install the module into your local maven repository.

## How to use ##
Start by adding a dependency to the mvk-integration module.

**pom.xml**
```
<dependency>
  <groupId>org.callistasoftware.netcare.commons</groupId>
  <artifactId>mvk-integration</artifactId>
  <version>1.0</version>
</dependency>
```

In your Spring security configuration, just add following lines and adjust any url's to suit your needs.

**applicationContext-security.xml**
```
<import resource="classpath*:/netcare-mvk-integration-security-config.xml"/>

<security:http pattern="/web/sso*" entry-point-ref="mvkEntryPoint" access-denied-page="/web/denied">
  <security:custom-filter position="PRE_AUTH_FILTER" ref="mvkPreAuthFilter"/>
</security:http>
	
<security:http use-expressions="true" entry-point-ref="mvkEntryPoint" access-denied-page="/web/denied">
  <security:intercept-url pattern="/api/**" access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')"/>
  <security:intercept-url pattern="/**" access="hasRole('ROLE_USER')"/>
  <security:logout logout-url="/web/logout"/>
</security:http>
```

**web.xml**
```
<context-param>
  <param-name>contextConfigLocation</param-name>
  <param-value>classpath*:/netcare-mvk-integration-config.xml, ..., ...</param-value>
</context-param>
```

## How it works ##
When implementing an SSO integration with MVK, you will have to let them know the url to your application which will trigger the SSO procedure. On this url, you must apply the mvkPreAuthFilter.

The mvkPreAuthFilter wil do the following:
  1. The filter is activated and fetches the guid-parameter sent from MVK
  1. The mvk integration module will now validate the guid-parameter by sending a validation request to MVK.
  1. MVK responds with information about the logged in user if the login was successful.

The next thing that will happen is that the mvkPreAuthFilter will hand over the authentication object to the mvk integration module's authentication manager. The authentication manager is responsible for verifying that the actual user does exist and can be granted access to the application.

Since the mvk-integration module does not know anything about the system that is using it, it must be passed an PreAuthenticationCallback which will determine if the user can be logged in or not. This is typically done by looking up the user in the system's persistent storage. The next section shows an example of how this callback can be implemented.

## Implement an authentication callback ##

The PreAuthenticationCallback defines three methods that must be implemented. These three methods is called depending on which state the authentication object has. When the application context is loaded the authentication manager will automatically search for a bean suitable for injection. Therefore, it is important that your implementation is scanned and registered to the system's application context.

**UserDetails createMissingUser(AuthenticationResult result)**

This method is called if the property mvk.create-missing-users is set to true. In systems where users are created upon login this setting must be set to true. The method is responsible for creating the user given the AuthenticationResult object.

**UserDetails verifyPrincipal(Object principal)**

This method is called when there already exist a principal in the authentication object. I.e a user is already logged in to the system. However, the callback must verify that the type of the principal is allowed to be signed on to the system.

**UserDetails lookupPrincipal(final AuthenticationResult result)**

This method is responsible to lookup a user based on the given authentication object. If the user is found it must be returned. If the user isn't found and the mvk.create-missing-user property is set, the authentication manager will call the createMissingUser()-method.

```
@Service
@Transactional
public class MvkPreAuthenticationCallback extends ServiceSupport implements PreAuthenticationCallback {

  @Autowired
  private CareUnitRepository cuRepo;
	
  @Autowired
  private CareGiverRepository cgRepo;
	
  @Autowired
  private PatientRepository pRepo;
	
  @Override
  public UserDetails createMissingUser(AuthenticationResult preAuthenticated) {
    getLog().info("User {} has not been here before, create the user...", preAuthenticated.getUsername());
		
    if (preAuthenticated.isCareGiver()) {
			
      getLog().debug("The user is a care giver...");
			
      final String careUnit = preAuthenticated.getCareUnitHsaId();
      CareUnitEntity cu = this.cuRepo.findByHsaId(careUnit);
      if (cu == null) {
	getLog().debug("Could not find care unit {}, create it.", careUnit);
	cu = this.createCareUnit(careUnit, preAuthenticated.getCareUnitName());
      }
			
      final CareGiverEntity cg = this.cgRepo.save(CareGiverEntity.newEntity("system-generated-name", "system-generated-name", preAuthenticated.getUsername(), cu));
      getLog().debug("Created care giver {}", cg.getFirstName());
			
      return CareGiverBaseViewImpl.newFromEntity(cg);
    } else {
			
      getLog().info("The user is a patient...");
      final PatientEntity p = this.pRepo.save(PatientEntity.newEntity("system-generated-name", "system-generated-name", preAuthenticated.getUsername()));
      getLog().debug("User {} has now been saved.", p.getCivicRegistrationNumber());
			
      return PatientBaseViewImpl.newFromEntity(p);
    }
  }
	
 
  @Override
  public UserDetails verifyPrincipal(Object principal) {
    if (principal instanceof CareGiverBaseViewImpl) {
      getLog().debug("Already authenticated as a care giver. Return principal object.");
      return (CareGiverBaseView) principal;
    } else if (principal instanceof PatientBaseViewImpl) {
      getLog().debug("Already authenticated as a patient. Return principal object.");
      return (PatientBaseView) principal;
    } else {
      return null;
    }
  }

  @Override
  public UserDetails lookupPrincipal(AuthenticationResult auth) throws UsernameNotFoundException {
    if (auth.isCareGiver()) {
      getLog().debug("The authentication result indicates that the user is a care giver. Check for the user in care giver repository");
      final CareGiverEntity cg = this.cgRepo.findByHsaId(auth.getUsername());
      if (cg == null) {
        getLog().debug("Could not find any care giver matching {}", auth.getUsername());
      } else {			
	return CareGiverBaseViewImpl.newFromEntity(cg);
      }
			
    } else {
      getLog().debug("The authentication result indicates that the user is a patient. Check for the user in patient repository");
      final PatientEntity patient =   this.pRepo.findByCivicRegistrationNumber(EntityUtil.formatCrn(auth.getUsername()));
      if (patient == null) {
        getLog().debug("Could not find any patients matching {}. Trying with care givers...", auth.getUsername());
      } else {
        return PatientBaseViewImpl.newFromEntity(patient);
      }
    }
		
    throw new UsernameNotFoundException("User " + auth.getUsername() + " could not be found in the system.");
  }
}
```

## References ##
See the list below for projects that are using the mvk-integration module.

  * [Netcare Healthplan](http://netcare.googlecode.com)
  * [Netcare Video](http://netcare-video.googlecode.com)