package furry

import cats._
import cats.implicits._
import printable.Printable
import printable.PrintableInstances._
import printable.PrintableSyntax._

case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val catPrintable: Printable[Cat] = cat =>
    s"${cat.name.print()} is a ${cat.age.print()} year-old ${cat.color.print()} cat"

  implicit val catShow: Show[Cat] = cat => s"${cat.name.show} is a ${cat.age.show} year-old ${cat.color.show} cat"

  implicit val catEq: Eq[Cat] = (c1, c2) => c1 == c2
}
