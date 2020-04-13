package functor.contravariant

import functor.contravariant.Box.boxPrintable
import org.scalatest.{FlatSpec, Matchers}
import typeclass.Printable
import typeclass.PrintableInstances._
import typeclass.PrintableSyntax._

class BoxTest extends FlatSpec with Matchers {

  behavior of "Box"

  it should "print values inside" in {
    implicitly[Printable[Box[String]]].print(Box("a cat")) should be ("a cat")
    Box(true).print() should be ("yes")
  }
}
