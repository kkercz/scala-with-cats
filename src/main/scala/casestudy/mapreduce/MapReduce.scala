package casestudy.mapreduce

import cats._
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case object MapReduce {

  def foldMap[A, B : Monoid](seq: Seq[A])(fun: A => B): B = seq.map(fun).foldLeft(implicitly[Monoid[B]].empty)(_ |+| _) // equivalent to `seq.toVector.foldMap(fun)`

  def parallelFoldMap[A, B : Monoid](values: Seq[A])(f: A => B): Future[B] = {
    val groupSize = (values.size.toDouble / Runtime.getRuntime.availableProcessors).ceil.toInt
    values
      .grouped(groupSize)
      .toVector
      .traverse(group => Future(group.toVector.foldMap(f)))
      // or: .traverse(group => Future(implicitly[Foldable[Vector]].foldMap(group.toVector)(f)))
      // note that foldMap requires the items to have a Monoid type class
      .map(_.combineAll)
      // or: .map(implicitly[Monoid[B]].combineAll(_))
  }

}
