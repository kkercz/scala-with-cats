package monad.eval

import cats._

case object RightFolder {
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = foldRightSafe(as, acc)(fn).value

  def foldRightSafe[A, B](as: List[A], acc: B)(fn: (A, B) => B): Eval[B] = as match {
      case head :: tail =>
        Eval.defer(foldRightSafe(tail, acc)(fn).map(t => fn(head, t)))
      case Nil =>
        Eval.now(acc)
    }
}
