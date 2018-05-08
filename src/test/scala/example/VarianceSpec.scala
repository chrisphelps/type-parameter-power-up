package example

import org.scalatest.{FlatSpec, Matchers}

class VarianceSpec extends FlatSpec with Matchers {

  behavior of "Invariance"

  class Wrapper[A](wrapped: A) {
    def unwrapped: A = wrapped
  }

  it should "admit the defined type" in {
    def doIt(catWrapper: Wrapper[Cat]) = {
      print(s"Wrapped cat: ${catWrapper.unwrapped.name}")
    }

    doIt(new Wrapper(Cat("Garfield")))
  }

// Won't compile: "class Wrapper is invariant in type A"
//  it should "reject subtypes" in {
//    def doIt(animalWrapper: Wrapper[Animal]) = {
//      print(s"Wrapped animal: ${animalWrapper.unwrapped.name}")
//    }
//
//    doIt(new Wrapper[Cat](Cat("Bill")))
//  }

  it should "infer the superclass" in {
    def doIt(animalWrapper: Wrapper[Animal]) = {
      print(s"Wrapped animal: ${animalWrapper.unwrapped.name}")
    }

    // infers as Wrapper[Animal]. Will still dynamic dispatch but cannot call Cat methods.
    // if Wrapper is mutable, will not prevent other Animals from being assigned.
    doIt(new Wrapper(Cat("Bill")))
  }

}
