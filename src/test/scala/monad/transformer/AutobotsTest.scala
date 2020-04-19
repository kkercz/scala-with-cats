package monad.transformer

import monad.transformer.Autobots.{Response, canSpecialMove}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class AutobotsTest extends FlatSpec with Matchers {

  behavior of "Autobots"

  it should "check if two autobots can perform a special move" in {

    result(canSpecialMove("unknown", "Jazz")) should be (Left("Unknown bot: unknown"))
    result(canSpecialMove("Bumblebee", "Jazz")) should be (Right(false))
    result(canSpecialMove("Hot Rod", "Jazz")) should be (Right(true))

  }

  def result[A](response: Response[A]): Either[String, A] = Await.result(response.value, 1.second)
}