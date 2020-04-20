package semigroupal

import cats.data.Validated
import cats.implicits._

case object FormValidation {

  def readUser(form: FormData): FailSlow[User] = {
    val validatedName = readName(form).toValidated
    val validatedAge = readAge(form).toValidated
    val validatedAll: Validated[List[String], (String, Int)] = (validatedName, validatedAge).tupled
    validatedAll.map({ case (name, age) => User(name, age) })
  }

  // or just:
  def readUserWithMapN(form: FormData): FailSlow[User] = (readName(form).toValidated, readAge(form).toValidated).mapN(User.apply)

  // This would apply validations sequentially, so it's not what we want
  def readUserSequentially(form: FormData): FailSlow[User] =
    readName(form).toValidated
      .andThen(name => readAge(form).toValidated
        .andThen(age => Validated.Valid(User(name, age))))

  def readName(form: FormData): FailFast[String] = for (
    name <- getValue("name")(form);
    notBlankName <- nonBlank("name")(name)
  ) yield notBlankName

  def readAge(form: FormData): FailFast[Int] = for (
    age <- getValue("age")(form);
    intAge <- parseInt("age")(age);
    validAge <- nonNegative("age")(intAge)
  ) yield validAge

  def getValue(name: String)(form: FormData): FailFast[String] = form.get(name).toRight(List(s"Missing param: $name"))

  def parseInt(name: String)(str: String): FailFast[Int] = str.toIntOption.toRight(List(s"$name is not a number: $str"))

  def nonBlank(name: String)(data: String): FailFast[String] = Right(data).ensure(List(s"$name must not be blank"))(_.nonEmpty)

  def nonNegative(name: String)(data: Int): FailFast[Int] = Right(data).ensure(List(s"$name must be non-negative"))(_ >= 0)

}
