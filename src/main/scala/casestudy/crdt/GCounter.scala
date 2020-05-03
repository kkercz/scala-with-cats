package casestudy.crdt

import casestudy.crdt.KeyValueStore.KvsOps
import cats.Monoid
import cats.implicits._

case class GCounter[K, V](store: Map[K, V]) {
  def increment(key: K, value: V)(implicit bsl: BoundedSemiLattice[V]): GCounter[K, V] = {
    val total = store.getOrElse(key, bsl.empty) |+| value
    GCounter(store.put(key, total))
  }

  def merge(that: GCounter[K, V])(implicit bsl: BoundedSemiLattice[V]): GCounter[K, V] = GCounter(this.store |+| that.store)

  def total(implicit m: Monoid[V]): V = store.values.toList.combineAll
}


