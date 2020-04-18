package monad

import cats.implicits._
import functor.Tree
import functor.Tree.{Branch, Leaf}
import monad.TreeMonad._
import org.scalatest.{FlatSpec, Matchers}

class TreeMonadTest extends FlatSpec with Matchers {

  behavior of "Tree monad"

  it should "enable for comprehensions" in {
    val initialTree: Tree[Int] = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))

    val expectedDoubledTree: Tree[Int] = Branch(
      Branch(
        Branch(Leaf(1), Leaf(1)),
        Branch(Leaf(2), Leaf(2))),
      Branch(Leaf(3), Leaf(3)))

    val doubledTree: Tree[Int] = for (
      x <- initialTree ;
      doubled <- Branch(Leaf(x), Leaf(x)): Tree[Int]
    ) yield doubled

    doubledTree should be(expectedDoubledTree)
  }
}