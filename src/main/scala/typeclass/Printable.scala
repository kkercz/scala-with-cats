package typeclass

trait Printable[A] {
  def print(a: A): String

  def contramap[B](f: B => A): Printable[B] = (b: B) => Printable.this.print(f(b))
}

object Printable {
  def print[A : Printable](a: A): String = implicitly[Printable[A]].print(a)
}
