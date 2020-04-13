package furry

import printable.Printable
import printable.PrintableInstances._
import printable.PrintableSyntax._

case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val catPrintable: Printable[Cat] = cat =>
    s"${cat.name.print()} is a ${cat.age.print()} year-old ${cat.color.print()} cat"
}
