import cats.data.Validated

package object semigroupal {

  case class User(name: String, age: Int)

  type FormData = Map[String, String]
  type FailFast[A] = Either[List[String], A]
  type FailSlow[A] = Validated[List[String], A]
}
