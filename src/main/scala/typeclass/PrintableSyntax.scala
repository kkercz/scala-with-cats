package typeclass

case object PrintableSyntax {
  implicit class PrintableOps[A : Printable](value: A) {
    def print(): String = implicitly[Printable[A]].print(value)
  }
}
