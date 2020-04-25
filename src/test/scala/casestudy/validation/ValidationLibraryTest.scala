package casestudy.validation

import cats.data.Validated.{Invalid, Valid}
import cats.data.{NonEmptyList, Validated}
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

class ValidationLibraryTest extends FlatSpec with Matchers {

  val gt2: Predicate[List[String], Int] =
    Predicate.pure { v =>
      if (v > 2) v.valid
      else List("Must be > 2").invalid
    }

  val even: Predicate[List[String], Int] =
    Predicate.pure { v =>
      if (v % 2 == 0) v.valid
      else List("Must be even").invalid
    }

  val gt2AndEven: Predicate[List[String], Int] = gt2.and(even)
  val gt2OrEven: Predicate[List[String], Int] = gt2.or(even)

  it should "accumulate errors for predicates created by and-ing or or-ing" in {
    gt2AndEven(5) should be(Invalid(List("Must be even")))
    gt2AndEven(1) should be(Invalid(List("Must be > 2", "Must be even")))

    gt2OrEven(1) should be(Invalid(List("Must be > 2", "Must be even")))
  }

  it should "pass composite predicates" in {
    gt2AndEven(6) should be(Valid(6))
    gt2OrEven(5) should be(Valid(5))
    gt2OrEven(0) should be(Valid(0))
  }

  type Errors = NonEmptyList[String]

  def error(s: String): NonEmptyList[String] = NonEmptyList(s, Nil)

  def longerThan(n: Int): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be longer than $n characters"),
      str => str.length > n)

  val alphanumeric: Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be all alphanumeric characters"),
      str => str.forall(_.isLetterOrDigit))

  def contains(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $char"),
      str => str.contains(char))

  def containsOnce(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain exactly one $char character"),
      str => str.count(c => c == char) == 1)

  val usernameCheck: Predicate[Errors, String] = longerThan(4).and(alphanumeric)

  it should "check usernames" in {
    usernameCheck("shr") should be (Invalid(NonEmptyList.of("Must be longer than 4 characters")))
    usernameCheck("#@") should be (Invalid(NonEmptyList.of("Must be longer than 4 characters", "Must be all alphanumeric characters")))
    usernameCheck("validUsername") should be (Valid("validUsername"))
  }

  private val containsAt = containsOnce('@')
  private val validLeftPart = longerThan(0)
  private val validRightPart = contains('.').and(longerThan(2))

  val emailCheck: Check[Errors, String, String] = Check(containsAt)
    .map(str => str.split("@") match { case Array(left, right) => (left, right)})
    .andThen(Check(Predicate.pure((_: (String, String)) match { case (left, right) => (validLeftPart(left), validRightPart(right)).tupled }))
      .map({case (l, r) => l + "@" + r}))

  it should "check e-mail addresses" in {
    emailCheck("shr") should be (Invalid(NonEmptyList.of("Must contain exactly one @ character")))
    emailCheck("shr@a") should be (Invalid(NonEmptyList.of("Must contain the character .", "Must be longer than 2 characters")))
    emailCheck("shr@a.pl") should be (Valid("shr@a.pl"))
  }

  case class User(name: String, email: String)

  def createUser(username: String, email: String): Validated[Errors, User] = (usernameCheck(username), emailCheck(email)).mapN(User)

  it should "validate users" in {
    createUser("ValidUser", "valus@username.pl") should be (Valid(User("ValidUser", "valus@username.pl")))
    createUser("", "empty@invalid@email") should be (Invalid(NonEmptyList.of("Must be longer than 4 characters", "Must contain exactly one @ character")))
  }

}
