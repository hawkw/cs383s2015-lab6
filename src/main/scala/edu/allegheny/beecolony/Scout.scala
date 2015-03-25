package edu.allegheny.beecolony

import java.net.ServerSocket

/**
 * Contains the Scout robot behavior
 * @author Hawk Weisman
 */
object Scout extends App with Robot {
  val server = new ServerSocket(port)
  val moves: Stream[Coordinate] = {
    val scale = 8.0f   // inches between points
    def loop(i: Int): Stream[Coordinate] = {
      val x = i * scale
      (x *  1,x *  1)   #:: (x * -1,x * 1)  #:: (x * 1f,x * -1) #::
        (x * -1,x * -1) #:: (x *  1f,x * 1) #:: loop(i + 1)
    }

    loop(1)
  }
  var seen: Set[Color] = Set()

  def socket = server accept

  for { point <- moves } {
    goTo(point)
    val color = checkColor
    if (!(seen contains color)) {
      seen += color
      // TODO: tell the other robot
    }
  }

}
