package typeclass

trait Printable[A] {
  def print(a: A): String
}

object Printable {
  def print[A : Printable](a: A): String = implicitly[Printable[A]].print(a)
}
