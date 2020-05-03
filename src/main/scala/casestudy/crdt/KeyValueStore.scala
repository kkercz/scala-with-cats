package casestudy.crdt

trait KeyValueStore[F[_,_]] {

  def put[K, V](store: F[K, V])(k: K, v: V): F[K, V]

  def get[K, V](store: F[K, V])(k: K): Option[V]

  def values[K, V](store: F[K, V]): Seq[V]

  def keys[K, V](store: F[K, V]): Set[K]

  def getOrElse[K, V](store: F[K, V])(k: K, default: V): V = get(store)(k).getOrElse(default)
}

object KeyValueStore {
  implicit class KvsOps[F[_,_], K, V](f: F[K, V]) {
    def put(key: K, value: V)(implicit kvs: KeyValueStore[F]): F[K, V] = kvs.put(f)(key, value)

    def get(key: K)(implicit kvs: KeyValueStore[F]): Option[V] = kvs.get(f)(key)

    def getOrElse(key: K, default: V)(implicit kvs: KeyValueStore[F]): V = kvs.getOrElse(f)(key, default)

    def values(implicit kvs: KeyValueStore[F]): Seq[V] = kvs.values(f)

    def keys(implicit kvs: KeyValueStore[F]): Set[K] = kvs.keys(f)
  }

  implicit def mapKVStore: KeyValueStore[Map] = new KeyValueStore[Map] {
    override def put[K, V](store: Map[K, V])(k: K, v: V): Map[K, V] = store.updated(k, v)

    override def get[K, V](store: Map[K, V])(k: K): Option[V] = store.get(k)

    override def values[K, V](store: Map[K, V]): Seq[V] = store.values.toSeq

    override def keys[K, V](store: Map[K, V]): Set[K] = store.keys.toSet
  }
}
