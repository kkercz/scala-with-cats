package monad.transformer

import cats.data.EitherT
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

case object Autobots {

  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )

  def getPowerLevel(autobot: String): Response[Int] = powerLevels.get(autobot) match {
    case Some(value) => value.pure[Response] // or: `EitherT.right(Future(value))`
    case None => s"Unknown bot: $autobot".raiseError[Response, Int] // or: `EitherT.left(Future(s"Unknown bot: $autobot"))`
  }

  def canSpecialMove(bot1: String, bot2: String): Response[Boolean] = for (
    level1 <- getPowerLevel(bot1);
    level2 <- getPowerLevel(bot2)
  ) yield level1 + level2 >= 15

  def tacticalReport(bot1: String, bot2: String): String = Await.result(canSpecialMove(bot1, bot2).value, 1.second) match {
    case Left(error) => error
    case Right(value) => s"$bot1 and $bot2 can perform a special move: $value"
  }
}
