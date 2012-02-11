Maven Archetype from existing project
=====================================

Sometimes, you may have an existing project, from which you like to create a Maven archetype. 
The Maven archetype plugin is really powerful and is there for you. You should first clean up 
your existing project code to remove anything you do not want to be included in the archetype. 

Then, run the following maven command,

> mvn archetype:create-from-project

After that, go to target/generated-source to find the archetype the plugin generates for you.

You many need to modify the generated code. The are two important files you should pay 
attentions to, i.e., archetype.xml and archetype-metadata.xml. The first one includes
all files you want to include in the archetype. There is one trick here, you could name
your file to include _artifactId__ as a part and the file name will be replaced with
the artifactId you specify when generate a new Maven project from the archetype.

The latter one is a control file, for instance,

> <fileSet filtered="true" packaged="true" encoding="UTF-8">
> <directory>src/main/java</directory>
> <includes>
> <include>**/*.java</include>
> </includes>
> </fileSet>

The filtered option specifies whether to replace token such as ${var} in the file and packaged="true" will replace 
the generated package name. If you work for a company,  most likely you do not want to replace the package name and 
you should remove the packaged option.

After you clean up the code, run to install the archetype to local repository. 

> mvn install 

To test the archetype, create a new project by using

> mvn archetype:generate -DarchetypeCatalog=local

This command will let you pick up an archetype from your local repository and specify group ID, artifact ID, and package. 

Finally, go the new created project directory and run

> mvn test

http://maven.apache.org/guides/mini/guide-creating-archetypes.html
http://johnjianfang.blogspot.com/2009/05/create-maven-archetype-from-existing.html
http://code.google.com/p/jianwikis/wiki/HowToCreateMavenArchetypeFromProject