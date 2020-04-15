package monad.writer

import org.scalatest.{FlatSpec, Matchers}

class LoggingFactorialTest extends FlatSpec with Matchers {

  behavior of "LoggingFactorial"

  it should "compute correct result" in {
    LoggingFactorial.factorial(4).value should be(24)
  }

  it should "save the log messages in the correct order" in {
    val fact = LoggingFactorial.factorial(4)
    fact.written should be(Vector("fact 0 1", "fact 1 1", "fact 2 2", "fact 3 6", "fact 4 24"))
  }
}
