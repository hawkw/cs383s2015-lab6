package edu.allegheny.beecolony

import java.io.ObjectOutputStream
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

  // this SHOULD block on the socket until the remote host connects,
  val socket = retry(server accept)// meaning that we won't start actually
  // start searching until the worker connects; i am assuming this
  // will work correctly with the way DelayedInit works, but I can't
  // guarantee it.
  //  -- Hawk, 03/19/15
  val output = new ObjectOutputStream(socket getOutputStream)

  for { point <- moves } { // TODO: this should terminate when we've seen all the colors
    goTo(point)
    val color = checkColor
    if (!(seen contains color)) {
      output writeObject location // serialize the current coordinate
      // and send it to the worker
      seen += color // we've seen this color
    } // continue
  }
}
