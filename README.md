# cs383s2015-lab6

[![Codacy Badge](https://www.codacy.com/project/badge/7378757016b7487084f11b23e064667c)](https://www.codacy.com)

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

To list all available Gradle tasks, type `./gradlew tasks`. The following tasks may be of particular interest:

+ `./gradlew build` compiles and tests the project
+ `./gradlew workerJar` assembles the Worker jarfile.
+ `./gradlew scoutJar` assembles the Scout jarfile.
+ `./gradlew deployEV3` will deploy all assembled jarfiles to an EV3 brick if you have one connected. The SSH configuration for the EV3 brick is stored in `gradle.properties`

Note that you may string multiple tasks together, as in `./gradlew workerJar scoutJar deployEV3`. The Jar tasks will build the project if it has not already been built.

Implementation Details
----------------------

The code for this project is found in the [source
folder](src/main/scala/edu/allegheny/beecolony). The
[Scout.scala](src/main/scala/edu/allegheny/beecolony/Scout.scala) and
[Worker.scala](src/main/scala/edu/allegheny/beecolony/Worker.scala) files
contain the code for the two types of robot in the multi-robot system. Both of
these Scala objects use the Robot trait, which is defined in
[Robot.scala](src/main/scala/edu/allegheny/beecolony/Robot.scala). This allows
the common features between the Worker and Scout files to be abstracted away
into a single file.

### Scout.scala
Our `Scout` program uses an infinite stream of Cartesian-style coordinates to
determine the square-spiral path it takes; the logic for this is defined in the
`moves` value, which uses a generator function (`loop`) to produce the stream.
When told to follow these points, the EV3 robot moves square shapes that get
progressively larger (by 8 inches, to be exact), giving the `Scout` a
reasonable degree of coverage around the hive location.

`Scout` loops over this list of points and calls the `goTo` function of its
`Navigator` object to travel in the spiral shape. While it travels from point
to point along this spiral, it checks the input from its color sensor, and if
this input represents a color source, it sends its current location to the
worker robot.

