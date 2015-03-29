package edu.allegheny.beecolony

import java.io.ObjectOutputStream
import java.net.ServerSocket

/**
 * Contains the Scout robot behavior
 * @author Hawk Weisman
 */
object Scout extends App with Robot {
  val maxColors = 7 // the maximum number of colors
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
  val output = socket getOutputStream
  val serialize = new ObjectOutputStream(output)

  for { (point, _) <- moves
        .zip(Stream continually seen.size)
        .takeWhile{ case (_: Coordinate, size: Int) => size < maxColors }
  } {
    goTo(point)
    val color = checkColor
    if (!(seen contains color)) {
      serialize writeObject location // serialize the current coordinate
      // and send it to the worker
      seen += color // we've seen this color
    } // continue
  }

  serialize.close() // closing resources? what is this, C?
  output.close()    // TODO: monadic
  socket.close()
  server.close()
}
