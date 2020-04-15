package monad.eval

import org.scalatest.{FlatSpec, Matchers}

class RightFolderTest extends FlatSpec with Matchers {

  behavior of "RightFolder"

  it should "be stack-safe" in {
    RightFolder.foldRight((1 to 100000).toList, 0L)(_ + _) should be(5000050000L)
  }
}