package casestudy.testing

import cats.Id
import org.scalatest.{FlatSpec, Matchers}

class UptimeServiceTest extends FlatSpec with Matchers {
  class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id] {
    def getUptime(hostname: String): Int =
      hosts.getOrElse(hostname, 0)
  }

  val hosts    = Map("host1" -> 10, "host2" -> 6)
  val client   = new TestUptimeClient(hosts)
  val service  = new UptimeService(client)

  behavior of "UptimeService"

  it should "calculate total uptime" in {
    val actual   = service.getTotalUptime(hosts.keys.toList)
    val expected = hosts.values.sum
    expected should be(actual)
  }

}
