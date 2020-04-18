package monad

import cats.Monad
import functor.Tree
import functor.Tree.{Branch, Leaf}

import scala.util.Either

case object TreeMonad {
  implicit val treeMonad: Monad[Tree] = new Monad[Tree] {
    override def pure[A](x: A): Tree[A] = Tree.Leaf(x)

    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = fa match {
      case Branch(left, right) => Branch(flatMap(left)(f), flatMap(right)(f))
      case Leaf(value) => f(value)
    }

    // see here on how to do it tail-recursively: https://stackoverflow.com/questions/44504790/cats-non-tail-recursive-tailrecm-method-for-monads
    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = flatMap(f(a)) {
      case Left(a) => tailRecM(a)(f)
      case Right(b) => Leaf(b)
    }
  }
}
