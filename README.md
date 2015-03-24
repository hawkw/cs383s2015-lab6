# cs383s2015-lab6

Will Yarbrough, Hawk Weisman, Scott Meltzer

Project Description
--------------------

This lab assignment is an implementation of an artificial bee colony algorithm.
It uses LeJOS to control LEGO EV3 robots, which are assigned one of two roles:
*scout* and *employed*. These bees cooperate to locate a food sources
(represented by colored paper) and retrieve them: scout bees search for colors
and report their location to a employed bee, which will "retrieve" that color
(recording the color value) and return to a predetermined "hive" location. This
procedure will be repeated until no more distinct colors are left to be found.

Building and Deploying
----------------------

There is a Gradle build script for compiling the project and deploying it to the robot included. 

#### Before building

You need LeJOS EV3 installed on your system, and you need to export `$EV3_HOME` to whatever directory EV3 is installed in. This is configured by default on Alden Hall systems. 

You do _not_ need Gradle or Scala installed, as the included Gradle wrapper script (`gradlew`) will download them for you and automatically manage versions.

#### Build instructions

The Gradle build script has the following tasks:

+ `./gradlew build` compiles and tests the project
+ `./gradlew workerJar` assembles the Worker jarfile.
+ `./gradlew scoutJar` assembles the Scout jarfile.
+ `./gradlew deployEV3` will deploy all assembled jarfiles to an EV3 brick if you have one connected. The SSH configuration for the EV3 brick is stored in `gradle.properties`

Note that you may string multiple tasks together, as in `./gradlew workerJar scoutJar deployEV3`. The Jar tasks will build the project if it has not already been built.
