package controllers

/**
 * Created by graeme on 06/06/15.
 */
object InvestigateTraits extends App {

  val myAnimal:ComponentManager = new ComponentManagerImpl()
  println("myAnimal.hasScales(): " + myAnimal.hasScales)
  println("myAnimal.canWalk(): " + myAnimal.canWalk)
  myAnimal.boil()

  val myDog:ComponentManager = new ComponentManager with ComponentManagerStub
  println("myDog.hasScales(): " + myDog.hasScales)
  println("myDog.canWalk(): " + myDog.canWalk)
  myDog.boil()

}


trait ComponentManager{
  def hasScales():String
  def canWalk():String
  def boil(): Unit ={
    val b = new Boiler(this)
  }
}

trait ComponentManagerK8055 extends ComponentManager{
  override def hasScales() = {"true"}
  override def canWalk() = {"false"}

}

class ComponentManagerImpl extends ComponentManager with ComponentManagerK8055


trait ComponentManagerStub extends ComponentManager {
  override def hasScales() = {"false"}
  override def canWalk() = {"true"}
}

class Boiler(mc:ComponentManager){
  println("We're Boiling!!!")
}