package example

abstract class Animal {
  def name: String
}

case class Cat(name: String) extends Animal
//case class Tabby(override val name: String) extends Cat(name)
//case class Lion(override val name: String) extends Cat(name)

case class Dog(name: String) extends Animal
