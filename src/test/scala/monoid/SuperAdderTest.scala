package monoid

import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

class SuperAdderTest extends FlatSpec with Matchers {

  behavior of "SuperAdder"

  it should "add a list of numbers" in {
    SuperAdder.add(List[Int]()) should be (0)
    SuperAdder.add(List(1, 2, 3)) should be (6)
  }

  it should "add a list of optionals" in {
    SuperAdder.add(List(Some(1), Some(2), Option.empty)) should be (Some(3))
  }

  it should "add a list of any semigroups" in {
    SuperAdder.add(List(Order(1, 2), Order(3, 5))) should be (Order(4, 7))
  }
}
