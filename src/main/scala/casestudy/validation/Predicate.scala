package casestudy.validation

import cats._
import cats.data.Validated
import cats.implicits._

sealed trait Predicate[E, A] {

  def apply(value: A)(implicit s: Semigroup[E]): Validated[E, A] = this match {
    case Predicate.Pure(f) => f(value)
    case Predicate.And(left, right) => (left(value), right(value)).mapN((_, _) => value)
    case Predicate.Or(left, right) =>
      left(value).fold(
        error => right(value).fold(error2 => (error |+| error2).invalid, success => success.valid),
        success => success.valid)
  }

  final def and(that: Predicate[E, A]): Predicate[E, A] = Predicate.And(this, that)
  final def or(that: Predicate[E, A]): Predicate[E, A] = Predicate.Or(this, that)
}

object Predicate {
  case class Pure[E, A](f: A => Validated[E, A]) extends Predicate[E, A]
  case class And[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]
  case class Or[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

  def pure[E, A](f: A => Validated[E, A]): Predicate[E, A] = Predicate.Pure(f)

  def lift[E, A](err: E, fn: A => Boolean): Predicate[E, A] = pure(a => if(fn(a)) a.valid else err.invalid)
}
