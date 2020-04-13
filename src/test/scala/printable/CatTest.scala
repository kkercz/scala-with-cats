package printable

import cats.implicits._
import furry.Cat
import org.scalatest.{FlatSpec, Matchers}

class CatTest extends FlatSpec with Matchers {

  val kotek: Cat = Cat("Kotek", 21, "yellow")
  val czarna: Cat = Cat("Czarna", 11, "black and white")

  behavior of "Cat"

  it should "be possible to show a cat" in {
    kotek.show should be("Kotek is a 21 year-old yellow cat")
  }

  it should "be possible to compare cats with equality" in {
    kotek.eqv(kotek) should be(true)
    kotek.eqv(czarna) should be(false)
    kotek.some.eqv(none[Cat]) should be(false)
  }
}
