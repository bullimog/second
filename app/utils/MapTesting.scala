package utils

case class User( id: Int,
                 firstName: String,
                 lastName: String,
                 age: Int,
                 gender: Option[String])

object UserRepository {
  private val users = Map(1 -> User(1, "John", "Doe", 32, Some("male")),
    2 -> User(2, "Johanna", "Doe", 30, None))
  def findById(id: Int): Option[User] = users.get(id)
  def findAll = users.values
}


object MapTesting extends App {
  val user1 = UserRepository.findById(1)
  if (user1.isDefined) {
    println(user1.get.firstName)
  } // will print "John"


  val age = UserRepository.findById(1).map(_.age) // age is Some(32)



  val opt = Option(1.0)

  val a = opt match {
    case Some(x) => x + 1
    case None => "a"
  }

  val b = opt map (_ + 1) getOrElse "a"

  // fold(default value, when empty) (fn using the Option value)
  val c:String = opt.fold("gg")("gg" + _)



}
