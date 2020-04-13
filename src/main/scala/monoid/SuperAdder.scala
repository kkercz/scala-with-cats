package monoid

import cats._
import cats.implicits._

case object SuperAdder {
  def add[A : Monoid](items: Seq[A]): A = items.foldLeft(implicitly[Monoid[A]].empty)(_ |+| _)
}
