MAVEN JEE EJB 3.1 template project archetype
============================================

created from MAVEN JEE EJB 3.1 template project using 

> mvn archetype:create-from-project


Instalation
-----------
quite easy, just download archetype project from GitHub

> mvn install 

New project creation
--------------------
simple as:
> mvn archetype:generate -DarchetypeCatalog=local

then choose a numer representing ejb31-archetype

> 1: local -> ejb31-archetype (ejb31-archetype)
> Choose a number: :

pleas answer on all asked questions 

a shorcut (run without any questions asked):

> mvn archetype:generate -DarchetypeCatalog=local -DarchetypeGroupId=com.example -DarchetypeArtifactId=ejb31-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=myGroupId -DartifactId=myArhifactId -Dversion=myVersionId        

Note that this is an empty project, without any java files, if you try to deploy it as is, JBOSS will complain about ear structure. 
