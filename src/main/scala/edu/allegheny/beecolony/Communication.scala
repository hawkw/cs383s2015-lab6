package edu.allegheny.beecolony

import java.net.Socket

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

/**
 * Trait for a robot with communication functionality.
 * @author Hawk Weisman
 *
 * Created by hawk on 3/30/15.
 */
trait Communication {
  def socket: Socket
  val port: Int = 1234

  /**
   * Try a failable action until it succeeds
   * @param fn a function which may throw exceptions
   * @tparam T the return type of the failable function
   * @return the result of that function once it succeeds
   */
  @tailrec final def retry[T](fn: => T): T = Try { fn } match {
    case Success(fully) => fully
    case Failure(_)     => retry(fn)
  }
}
