package utils

import java.io.{File, PrintWriter}


object CurriedFunctions extends App {

  // example of a method
  def sum(a: Int, b: Int) = a + b

  // example of a function
  val simpleFunction = (a: Int) => a + 5

  // this has exactly the same effect as function above but is more verbose
  val simpleFunction2 = new Function[Int, Int] {
    def apply(a: Int) = a + 5
  }

  /******************************************
  FUNCTIONS THAT RETURN FUNCTIONS
  ******************************************/

  // here we create a function that will return a function
  def add(number: Int) = {
    new Function[Int, Int] {
      def apply(a: Int) = a + number
    }
  }
  // we can use it like this:
  val add5 = add(5) // which will give us a function add5 that has a hardcoded number 5 in it
  assert( add5(100) == 105)
  // above call to 'add5(100)' actually compiles to: add5.apply(5), we just call method apply on object of type: Function[Int. Int]
  assert( add5(100) == add5.apply(100) )


  // here we also create a function that will return a function
  // it is exactly the same as above but syntax is more concise
  def add_v2(number: Int) = {
    (a: Int) => a + number
  }

  // this has the same effect as 2 methods above, syntax is even more concise
  def add_v3(number: Int)(a: Int) = number + a
  // this is an example of a curried function, 'curried' means that we have more than
  // 1 set of parameters and brackets, but it is not about brackets, it is about a new function that is returned

  /*******************************************/

  // we can call add_v3 with both parameters at the same time, like this:
  assert( add_v3(100)(200) == 300 ) // this means that after 1st set of braces we get a function
  // and immediately use this function
  // btw. we could call both 'add' and 'add_v2' functions with 2 parameters as well: add(4)(2) or add_v2(10)(20)


  /*******************************************
NEW FUNCTIONS THAT ARE MORE SPECIALIZED
    ******************************************/
  // all of the below vals define new functions that have some initial number
  // hardcoded
  val add50 = add(50)
  assert( add50(10) == 60 )

  val add99 = add_v2(99)
  assert( add99(1) == 100 )


  // Reusing a Curried Fn....
  val add1000 = add_v3(1000) _ // please note the underscore, explanation below ↓
  assert( add1000(100) == 1100 )

  /*****************************************/



  /**********************************************************************
CONVERTING A REGULAR (UNCURRIED) FUNCTIONS TO A CURRIED FUNCTION
    **********************************************************************/

  // if we have a regular functions, we can convert it to curried function like this:
  val sum2 = (sum _).curried // underscore after sum informs scala compiler that we didn't forget
  // to pass arguments and want to treat it as a partially applied function instead
  // previously we could only do this
  sum(1,2)
  // now we can do this
  sum2(1)(2)
  // and both will give the same result when called with 2 parameters
  assert( sum(1,2) == sum2(1)(2))

  val add10UsingSum2 = sum2(10)(_)
  assert(add10UsingSum2(100) == 110)

  /****************************************/




  /************************************
ANONYMOUS CURRIED FUNCTION
    ***********************************/
  val sum_yetAnother = (a: Int) => (b: Int) => a + b
  // method that has similar effect would look like this: def sum_yetAnother(a: Int)(b: Int) = a + b

  val add6 = sum_yetAnother(6) // here add6 is equal to a function: (b: Int) => 6 + b
  assert( add6(1) == 7 )

  /***********************************/




  /**********************************************************************
COMMON USAGE OF AN ANONYMOUS CURRIED FUNCTIONS IN OUR SERVICES
    **********************************************************************/

  def doAction() = AuthorizedForXYZ {
    request =>
      user =>
        /* some calculations that is based on a user/request */ // <- this is a curried anonymous function
        Result("success")
  }

  case class Request()
  case class User(name: String)
  case class Result(status: String)

  def AuthorizedForXYZ(f: (Request) => (User) => Result ): Result = {
    val user = User("some name") // this user might have been obtained from a session for example
    val request = Request() // request was provided by play-framework

    if (someCheckThatThisUserCanAccessTheService) {
      f(request)(user) // if user can access we execute whatever code was passed here
    } else {
      redirectToErrorPage
    }
  }

  val someCheckThatThisUserCanAccessTheService = true // dummy implementation
  val redirectToErrorPage = Result("error") // dummy implementation

  /**************************************************************/






  /************************************************
LOAN PATTERN
    ************************************************/

  // this is how we can write to a file
  val printWriter = new PrintWriter(new File("/tmp/curriedFunctions"))
  printWriter.write("hello")
  printWriter.close() // we have to remember to close this resource
  // it is very verbose to do this all the time

  // this looks cleaner and looks like an in-build language construct
  writeToFile("/tmp/konrad"){
    pw =>
      pw.write("something")
      pw.write("\n")
      pw.write("sth else")
  }

  def writeToFile(fileName: String)(f: PrintWriter => Any): Unit = {
    val printWriter = new PrintWriter(new File(fileName))
    f(printWriter)
    printWriter.close()
  }

  /**********************************************/




  /*******************
IMPLICITS
    ******************/

  implicit val request = Request()
  def doSth(msg: String)(implicit request: Request) { // implicit parameter can only be placed as last parameter list
    println(request.toString + " " + msg) // if we want to have explicit/regular parameters as well
  } // we have to used a curried function
  doSth("my msg") // we don't have to pass a request

  /********************/



  /*********************************************
MORE THAN ONE SET OF VARARGS
    ********************************************/

  // with carried functions you can do this:
  def sumManyNumbers(x: Int*)(y: Int*) = x.sum + y.sum
  assert( sumManyNumbers(1,2,3)(10,20,30) == 66)

  /********************************************/



  /***************************************************
DEPENDENCY INJECTION AT THE FUNCTION LEVEL
    *************************************************/
  // this is an alternative to implicits

  // we have a function that has some business logic
  // it can use any repository
  def findUser(repo: Repo)(id: Int): Option[User] = {
    /* some calculations */
    repo.getById(id)
  }

  // in the actual application we provide one implementation
  def findUserFromNps = findUser(NpsUserRepo) _
  // and use this specialized version e.g. like this: findUserFromNps(15)

  // but for testing we want to provide a mocked repository
  object MockedNpsDb extends Repo {
    val data = Map(1 -> User("James"), 2 -> User("konrad"))
    def getById(id: Int) = data.get(id)
  }
  // and we test if our business logic is correct
  assert(findUser(MockedNpsDb)(id = 1) == Some(User("James")))


  trait Repo {
    def getById(id: Int): Option[User]
  }

  object NpsUserRepo extends Repo {
    def getById(id: Int) = ??? // call to back-end system
  }

  /************************************************/
}