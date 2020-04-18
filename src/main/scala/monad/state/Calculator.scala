package monad.state

import cats.data.State
import cats.implicits._

case object Calculator {
  type CalcState[A] = State[List[Int], A]

  def evalInput(input: String): Int = evalAll(input.split("\\s+")).runA(Nil).value

  def evalAll(symbols: Seq[String]): CalcState[Int] = symbols.filter(!_.isEmpty).foldLeft(0.pure[CalcState]) { (a, b) =>
    a.flatMap(_ => evalOne(b))
  }

  def evalOne(sym: String): CalcState[Int] = sym match {
    case "+" => operator(_ + _)
    case "-" => operator(_ - _)
    case "*" => operator(_ * _)
    case "/" => operator(_ / _)
    case num => operand(num.toInt)
  }

  def operand(value: Int): CalcState[Int] = State[List[Int], Int] { state => (value :: state, value) }

  def operator(function: (Int, Int) => Int): CalcState[Int] = State[List[Int], Int] { state =>
    val (List(arg2, arg1), stackTail) = state.splitAt(2)
    val result = function(arg1, arg2)
    (result :: stackTail, result)
  }

}
