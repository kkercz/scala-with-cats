package casestudy.crdt

import cats.kernel.CommutativeMonoid

trait BoundedSemiLattice[A] extends CommutativeMonoid[A] {

  def combine(a1: A, a2: A): A

  def empty: A

}

object BoundedSemiLattice {
  implicit def setBoundedSemiLattice[A](): BoundedSemiLattice[Set[A]] = new BoundedSemiLattice[Set[A]] {
    override def combine(a1: Set[A], a2: Set[A]): Set[A] = a1.union(a2)

    override def empty: Set[A] = Set.empty[A]
  }

  implicit val intInstance: BoundedSemiLattice[Int] = new BoundedSemiLattice[Int] {
      def combine(a1: Int, a2: Int): Int = a1.max(a2)

      val empty: Int = 0
    }
}
