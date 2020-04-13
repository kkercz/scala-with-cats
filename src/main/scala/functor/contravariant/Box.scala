package functor.contravariant

import typeclass.Printable

case class Box[A](value: A)

object Box {
  implicit def boxPrintable[A: Printable]: Printable[Box[A]] = implicitly[Printable[A]].contramap(b => b.value)
}
