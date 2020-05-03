package casestudy.crdt

import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

class GCounterTest extends FlatSpec with Matchers {


  it should "be possible to have map int counters" in {

    val g1 = Map("a" -> 7, "b" -> 3)
    val g2 = Map("a" -> 2, "b" -> 5)

    val counter1 = GCounter[String, Int](g1)
    val counter2 = GCounter[String, Int](g2)

    counter1.merge(counter2)(casestudy.crdt.BoundedSemiLattice.intInstance) should be(GCounter(Map("a" -> 7, "b" -> 5)))
    counter1.merge(counter2).total should be (12)
  }

}
