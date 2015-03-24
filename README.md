# cs383s2015-lab6

## Project Description
This lab assignment is an implementation of an artificial bee colony algorithm.
It uses LeJOS to control LEGO EV3 robots, which are assigned one of two roles:
*scout* and *employed*. These bees cooperate to locate a food sources
(represented by colored paper) and retrieve them: scout bees search for colors
and report their location to a employed bee, which will "retrieve" that color
(recording the color value) and return to a predetermined "hive" location. This
procedure will be repeated until no more distinct colors are left to be found.
