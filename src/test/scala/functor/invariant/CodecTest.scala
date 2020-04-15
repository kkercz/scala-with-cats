package functor.invariant

import functor.contravariant.Box
import org.scalatest.{FlatSpec, Matchers}

class CodecTest extends FlatSpec with Matchers {

  behavior of "Codec"

  it should "code and encode stuff" in {

    Codec.encode[Box[Double]](Box(1.0)) should be("1.0")
    Codec.encode[Box[String]](Box("2.0")) should be("2.0")
    Codec.decode[String]("3.0") should be("3.0")
    Codec.decode[Box[Double]]("4.0") should be(Box(4.0))
  }
}
