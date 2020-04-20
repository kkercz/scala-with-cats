package semigroupal

import cats.data.Validated.{Invalid, Valid}
import org.scalatest.{FlatSpec, Matchers}

class FormValidationTest extends FlatSpec with Matchers {

  behavior of "Web forms"

  it should "return all parse errors for user from params" in {
    FormValidation.readUser(Map("age" -> "-1")) should be(Invalid(List("Missing param: name", "age must be non-negative")))
  }

  it should "parse user from params" in {
    FormValidation.readUser(Map("name" -> "Zenon", "age" -> "42")) should be(Valid(User("Zenon", 42)))
  }
}