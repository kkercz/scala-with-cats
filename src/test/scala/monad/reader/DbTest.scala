package monad.reader

import org.scalatest.{FlatSpec, Matchers}

class DbTest extends FlatSpec with Matchers {

  behavior of "DB readers"

  it should "check login pairs correctly" in {
    val users = Map(1 -> "dade", 2 -> "kate", 3 -> "margo")
    val passwords = Map("dade"  -> "zerocool", "kate"  -> "acidburn", "margo" -> "secret")
    val db = Db(users, passwords)

    Db.checkLogin(1, "zerocool").run(db) should be (true)
    Db.checkLogin(4, "davinci").run(db) should be (false)
  }
}