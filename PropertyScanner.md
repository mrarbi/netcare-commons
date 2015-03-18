# Introduction #

Property scanner is a maven plugin. It scans all your properties files as well as Jsp and Java files. And, prints out all the unused or undefined properties.



# Details #

The plugin runs in process-resources phase:

```
# mvn process-sources
```


It first goes through all properties files in the configured properties directory. Then it gathers all tokens from JSP files in all subdirectories of configured script directory. It also goes through al the java directories and gathers all tokens. The search pattern for token has a default value but is completely configurable.

The tokens are matched against properties. The missing properties, that are being used in code but are not present in properties files, are printed out as maven output. And the extra properties, the ones that are in properties files but not in code, are also printed out.


# Configuration #

All the configurable options are present in `MyMojo.java` file. An example configuration is:

```
	 <plugin>
	     <groupId>org.callistasoftware.maven.plugins</groupId>
	     <artifactId>property-scanner-plugin</artifactId>
	     <version>1.0-SNAPSHOT</version>
	     <configuration>
	         <javaSourceDirectory>${project.build.sourceDirectory}</javaSourceDirectory>
	         <scriptSourceDirectory>${project.build.sourceDirectory}/../webapp/WEB-INF/pages/jsp</scriptSourceDirectory>
	         <propertiesDirectories>
	             <propertiesDirectory>${project.build.sourceDirectory}/../resources</propertiesDirectory>
	         </propertiesDirectories>
	     </configuration>
         <executions>
          <execution>
            <phase>process-sources</phase>
            <goals>
              <goal>check-strings</goal>
            </goals>
          </execution>
        </executions>
</plugin>
```