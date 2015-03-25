package edu.allegheny.beecolony

/**
 * Contains the Scout robot behavior
 * @author Hawk Weisman
 */
object Scout extends App with Robot {
  val increment = 2 // change this to increase the width by which the loop increases
  val moves: Stream[Coordinate] = {
    def loop(i: Int): Stream[Coordinate] =
      (i * 1,i * 1)   #:: (i * -1,i * 1)  #::
      (i * 1,i * -1)  #:: (i * -1,i * -1) #:: loop(i + increment)
    loop(1)
  }
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
