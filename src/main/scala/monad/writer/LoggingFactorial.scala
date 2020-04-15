package monad.writer

import cats._
import cats.data.Writer
import cats.implicits._

case object LoggingFactorial {

  type Logged[A] = Writer[Vector[String], A]

  def factorial(n: Int): Writer[Vector[String], Int] = {
    for (
      result <- if (n == 0) 1.writer(Vector[String]()) else factorial(n - 1).map(_ * n);
      _ <- Vector(s"fact $n $result").tell
    ) yield result
  }

}
