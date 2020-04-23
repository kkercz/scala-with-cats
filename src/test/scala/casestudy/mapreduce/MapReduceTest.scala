package casestudy.mapreduce

import casestudy.mapreduce.MapReduce.foldMap
import cats.Monoid
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class MapReduceTest extends FlatSpec with Matchers {

  implicit val numberMultiplyMonoid: Monoid[Int] = new Monoid[Int] {
    override def empty: Int = 1

    override def combine(x: Int, y: Int): Int = x * y
  }

  behavior of "MapReduce"

  it should "have a working single-threaded foldMap method" in {
    foldMap(Vector(1,2,3,4))(identity) should be (10)
    foldMap(Vector(1,2,3,4))(identity)(numberMultiplyMonoid) should be (24)
    foldMap(Vector(1, 2, 3))(_.toString + "! ") should be ("1! 2! 3! ")
    foldMap("Hello world!".toVector)(_.toString.toUpperCase) should be ("HELLO WORLD!")
    foldMap((1 to 1000).toVector)(_ * 1000) should be (500500000)
  }

  it should "have a working multi-threaded foldMap method" in {
    parallelFoldMap(Vector(1,2,3,4))(identity) should be (10)
    parallelFoldMap(Vector(1,2,3,4))(identity)(numberMultiplyMonoid) should be (24)
    parallelFoldMap(Vector(1, 2, 3))(_.toString + "! ") should be ("1! 2! 3! ")
    parallelFoldMap("Hello world!".toVector)(_.toString.toUpperCase) should be ("HELLO WORLD!")
    parallelFoldMap((1 to 1000).toVector)(_ * 1000) should be (500500000)
  }

  def parallelFoldMap[A, B : Monoid](values: Seq[A])(f: A => B): B = Await.result(MapReduce.parallelFoldMap(values)(f), 1.second)
}
