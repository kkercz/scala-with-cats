package monad.state

import monad.state.Calculator.{evalAll, evalOne}
import org.scalatest.{FlatSpec, Matchers}

class CalculatorTest extends FlatSpec with Matchers {

  behavior of "Calculator"

  it should "have a working evalOne method" in {
    evalOne("42").runA(Nil).value should be (42)

    val program = for {
      _   <- evalOne("1")
      _   <- evalOne("2")
      ans <- evalOne("+")
    } yield ans
    program.runA(Nil).value should be(3)

    evalOne("1").flatMap(_ => evalOne("2")).runS(Nil).value should be(List(2, 1))
  }

  it should "have evalAll method" in {
    val program = for {
      _   <- evalAll(List("1", "2", "+"))
      _   <- evalAll(List("3", "4", "+"))
      ans <- evalOne("*")
    } yield ans

    program .runA(Nil).value should be (21)
  }

  it should "eval string inputs" in {
    Calculator.evalInput("") should be (0)
    Calculator.evalInput("1 2 + 3 4 + *") should be (21)
  }
}