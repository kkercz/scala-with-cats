package casestudy.testing

trait UptimeClient[F[_]] {
  def getUptime(hostname: String): F[Int]
}