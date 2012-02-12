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

give answer on few asked questions 

a shorcut:

> mvn archetype:generate                     \

>   -DarchetypeCatalog=local                 \

>   -DarchetypeGroupId=com.example           \

>   -DarchetypeArtifactId=ejb31-archetype    \

>   -DarchetypeVersion=0.0.1-SNAPSHOT        \

>   -DgroupId=myGroupId                      \

>   -DartifactId=myArhifactId                \

>   -Dversion=myVersionId        

