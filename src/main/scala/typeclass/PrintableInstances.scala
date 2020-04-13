package typeclass

case object PrintableInstances {
  implicit val intPrintable: Printable[Int] = (a: Int) => a.toString
  implicit val stringPrintable: Printable[String] = (a: String) => a
  implicit val booleanPrintable: Printable[Boolean] = (a: Boolean) => if (a) "yes" else "no"
}
