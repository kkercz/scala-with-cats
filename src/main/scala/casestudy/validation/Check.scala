package casestudy.validation

import cats.Semigroup
import cats.data.Validated

sealed trait Check[E, A, B] {

  def apply(value: A)(implicit s: Semigroup[E]): Validated[E, B]

  def map[C](f: B => C): Check[E, A, C] = Check.Map(this, f)

  def andThen[C](that: Check[E, B, C]): Check[E, A, C] = Check.AndThen(this, that)
}

object Check {

  case class Pure[E, A](pred: Predicate[E, A]) extends Check[E, A, A] {
    override def apply(value: A)(implicit s: Semigroup[E]): Validated[E, A] = pred(value)
  }

  case class PureFun[E, A, B](f: A => Validated[E, B]) extends Check[E, A, B] {
    override def apply(value: A)(implicit s: Semigroup[E]): Validated[E, B] = f(value)
  }

  case class Map[E, A, B, C](check: Check[E, A, B], f: B => C) extends Check[E, A, C] {
    override def apply(value: A)(implicit s: Semigroup[E]): Validated[E, C] = check(value).map(f)
  }

  case class AndThen[E, A, B, C](first: Check[E, A, B], andThen: Check[E, B, C]) extends Check[E, A, C] {
    override def apply(value: A)(implicit s: Semigroup[E]): Validated[E, C] = first(value).andThen(b => andThen(b))
  }

  def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] = Pure(pred)

  def withFunction[E, A, B](f: A => Validated[E, B]): Check[E, A, B] = PureFun(f)
}


