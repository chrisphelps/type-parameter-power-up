package example

import org.scalatest.{FlatSpec, Matchers}

class VarianceSpec extends FlatSpec with Matchers {

  behavior of "Invariance"

  class InvariantWrapper[A](wrapped: A) {
    def unwrapped: A = wrapped
  }

  class SubWrapper[A](wrapped: A) extends InvariantWrapper(wrapped) {}

  it should "admit the defined type" in {
    def doIt(catWrapper: InvariantWrapper[Cat]) = {
      println(s"Wrapped cat: ${catWrapper.unwrapped.name}")
    }

    doIt(new InvariantWrapper(Cat("Garfield")))
  }

// Won't compile: "class Wrapper is invariant in type A"
//  it should "reject subtypes" in {
//    def doIt(animalWrapper: InvariantWrapper[Animal]) = {
//      println(s"Wrapped animal: ${animalWrapper.unwrapped.name}")
//    }
//
//    doIt(new Wrapper[Cat](Cat("Bill")))
//  }

  it should "infer the superclass" in {
    def doIt(animalWrapper: InvariantWrapper[Animal]) = {
      println(s"Wrapped animal: ${animalWrapper.unwrapped.name}")
    }

    // infers as Wrapper[Animal]. Will still dynamic dispatch but cannot call Cat methods.
    // if Wrapper is mutable, will not prevent other Animals from being assigned.
    doIt(new InvariantWrapper(Cat("Bill")))
  }

  it should "allow subclasses of the container" in {
    def doIt(animalWrapper: InvariantWrapper[Cat]) = {
      println(s"Wrapped cat: ${animalWrapper.unwrapped.name}")
    }

    doIt(new SubWrapper(Cat("Milo")))
  }


  behavior of "Covariance"

  class CovariantWrapper[+A](wrapped: A) {
    def unwrapped: A = wrapped

    // Won't compile: covariant type A occurs in contravariant position in type VarianceSpec.this.CovariantWrapper[A] of value other
    // def swap(other: CovariantWrapper[A]): CovariantWrapper[A] = other
    // we'll solve this with type bounds
  }

  it should "allow subtypes" in {
    def doIt(animalWrapper: CovariantWrapper[Animal]) = {
      println(s"Wrapped animal: ${animalWrapper.unwrapped.name}")
    }

    doIt(new CovariantWrapper[Cat](Cat("Bill")))
  }

// Won't compile: found: VarianceSpec.this.CovariantWrapper[example.Animal] required: VarianceSpec.this.CovariantWrapper[example.Cat]
//  it should "disallow supertypes" in {
//    def doIt(animalWrapper: CovariantWrapper[Cat]) = {
//      println(s"Wrapped cat: ${animalWrapper.unwrapped.name}")
//    }
//
//    doIt(new CovariantWrapper[Animal](Cat("Stimpy")))
//  }

  
}
