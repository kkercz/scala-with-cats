package printable

import cats.implicits._
import furry.Cat
import org.scalatest.{FlatSpec, Matchers}

class CatTest extends FlatSpec with Matchers {

  behavior of "Cat"

  it should "be possible to show a cat" in {
    Cat("Kotek", 21, "yellow").show should be("Kotek is a 21 year-old yellow cat")
  }
}
