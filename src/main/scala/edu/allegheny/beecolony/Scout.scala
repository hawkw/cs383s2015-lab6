package edu.allegheny.beecolony

/**
 * Contains the Scout robot behavior
 * @author Hawk Weisman
 */
object Scout extends App with Robot {
  val moves: Stream[Coordinate] = ??? // TODO: figure out how to generate square spiral
  var seen: Set[Color] = Set()

  for { point <- moves } {
    goTo(point)
    val color = checkColor
    if (!(seen contains color)) {
      seen += color
      // TODO: tell the other robot
    }
  }

}
