package edu.allegheny.beecolony

import java.net.Socket

import scala.collection.mutable

/**
 * Created by hawk on 3/29/15.
 */
object Worker extends App with Robot{
  val scoutIP = "10.0.0.36" // the Scout robot's IP address
  // change this as appropriate

  override def socket: Socket = retry(new Socket(scoutIP, port))

  val queue = mutable.Queue[Coordinate]()
}
