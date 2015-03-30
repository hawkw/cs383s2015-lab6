package edu.allegheny.beecolony

import java.io.ObjectInputStream
import java.net.Socket

import scala.collection.mutable

/**
 * Created by hawk on 3/29/15.
 * @author Hawk Weisman <hi@hawkweisman.me>
 */
object Worker extends App with Robot with Communication {
  val scoutIP = "10.0.0.36" // the Scout robot's IP address
  // change this as appropriate

  override def socket: Socket = retry(new Socket(scoutIP, port))

  val queue = mutable.Queue[Coordinate]()

  val input = socket.getInputStream
  val deserialize = new ObjectInputStream(input)

  Stream continually{
    // apparently this is better than `while (true)`,
    // at the very least it does not upset the code style deal.
    // TODO: make it stop some way other than the user holding down the quit button
    queue.enqueue(
      // TODO: consider using a typesafe object io library
      // but maybe adding external dependencies is bad since
      // the robot does not have a lot of memory
      deserialize.readObject().asInstanceOf[Coordinate]
    )
    goTo(queue dequeue())
    checkColor foreach ( _ => goTo (0,0) ) // if we see a new color, go back to origin
  }
  deserialize.close()
  input.close() // TODO: write cleanly function or something
  socket.close() // with-resource monad? IDK.

}
