package edu.allegheny.beecolony

import java.io.ObjectOutputStream
import java.net.ServerSocket

import scala.collection.mutable

/**
 * Contains the Scout robot behavior
 * @author Hawk Weisman
 */
object Scout extends App with Robot with Communication {

  val maxColors = 4 // the maximum number of colors
  val server = new ServerSocket(port)

  println("before stream")
  val moves: Stream[Coordinate] = {
    val scale = 8.0f   // inches between points
    def loop(i: Int): Stream[Coordinate] = {
      println(s"evaling stream($i)")
      val x = i * scale
      (x *  1,x *  1) #:: (x * -1,x * 1) #:: (x * 1,x * -1) #::
      (x * -1,x * -1) #:: (x *  1,x * 1) #:: loop(i + 1)
    }

    loop(1)
  }
  val seen = mutable.Set[Color]()

  // this SHOULD block on the socket until the remote host connects,
  val socket = retry({println(s"Waiting on $port"); server accept})// meaning that we won't start actually
  // start searching until the worker connects; i am assuming this
  // will work correctly with the way DelayedInit works, but I can't
  // guarantee it.
  //  -- Hawk, 03/19/15
  println("Somebody wants to be friends with me!")
  val output = socket getOutputStream
  val serialize = new ObjectOutputStream(output)

  for { (point, _) <- moves
        .zip(Stream continually seen.size)
        .takeWhile{ case (_: Coordinate, size: Int) => size < maxColors }
  } {
    goTo(point)
    while (isMoving) {
      checkColor match {
        case Some(color) if !(seen contains color) =>
          serialize writeObject location // serialize the current coordinate
          // and send it to the worker
          seen += color // we've seen this color
        case _ => {}
      }
      // continue
    }
  }

  serialize.close() // closing resources? what is this, C?
  output.close()    // TODO: monadic
  socket.close()
  server.close()
}
