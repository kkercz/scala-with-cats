package printable

import furry.Cat
import org.scalatest.{FlatSpec, Matchers}
import printable.PrintableInstances._
import printable.PrintableSyntax._

class PrintableTest extends FlatSpec with Matchers {

  behavior of "Printable type class"

  it should "be possible to print primitive types" in {
    Printable.print(42) should be("42")
    Printable.print("42") should be("42")
  }

  it should "be possible to print cats with the extension method" in {
    Cat("Kotek", 21, "yellow").print() should be("Kotek is a 21 year-old yellow cat")
  }
}
