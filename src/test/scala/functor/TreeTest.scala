package functor

import cats._
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

class TreeTest extends FlatSpec with Matchers {

  behavior of "Tree"

  it should "be a functor" in {
    val tree: Tree[Int] = Tree.Branch(
      Tree.Branch(
        Tree.Leaf(1),
        Tree.Leaf(2)),
      Tree.Leaf(3))

    val treePlusOne = Tree.Branch(
      Tree.Branch(
        Tree.Leaf(2),
        Tree.Leaf(3)),
      Tree.Leaf(4))

    implicitly[Functor[Tree]].map(tree)(_ + 1) should be(treePlusOne)
    Functor[Tree].map(tree)(_ + 1) should be(treePlusOne)
    tree.map(_ + 1) should be(treePlusOne)
  }
}
