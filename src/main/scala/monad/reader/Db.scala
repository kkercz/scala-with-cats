package monad.reader

import cats.data.Reader
import cats.implicits._

case class Db(usernames: Map[Int, String], passwords: Map[String, String])

object Db {
  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] = Reader(_.usernames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] = Reader(_.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for (
      username <- findUsername(userId);
      validPass <- username.map(username => checkPassword(username, password)).getOrElse(false.pure[DbReader])
    ) yield validPass
}
