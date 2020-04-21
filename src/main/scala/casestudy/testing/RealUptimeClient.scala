package casestudy.testing
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case object RealUptimeClient extends UptimeClient[Future] {
  override def getUptime(hostname: String): Future[Int] = Future(hostname.length)
}
