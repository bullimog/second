package controllers
 /*
 http://danielwestheide.com/blog/2013/01/30/the-neophytes-guide-to-scala-part-11-currying-and-partially-applied-functions.html
  */

case class Email( subject: String,
                  text: String,
                  sender: String,
                  recipient: String)




object MoreCurrying extends App with MoreCurrying{}


trait MoreCurrying {

  val email = Email("subject", "text", "sender", "recipient")
  type EmailFilter = Email => Boolean

  type IntPairPred = (Int, Int) => Boolean
  def sizeConstraint(pred: IntPairPred, n: Int, email: Email) = pred(email.text.length, n)

  val gt: IntPairPred = _ > _
  val ge: IntPairPred = _ >= _
  val lt: IntPairPred = _ < _
  val le: IntPairPred = _ <= _
  val eq: IntPairPred = _ == _

  //pre-pop the predicate, to make a new Fn.... (still need to defne types, though)
  val minimumSize: (Int, Email) => Boolean = sizeConstraint(ge, _: Int, _: Email)
  val maximumSize: (Int, Email) => Boolean = sizeConstraint(le, _: Int, _: Email)

  //pre-pop size constraint, to make a new Fn...
  val constr20: (IntPairPred, Email) => Boolean = sizeConstraint(_: IntPairPred, 20, _: Email)
  val constr30: (IntPairPred, Email) => Boolean = sizeConstraint(_: IntPairPred, 30, _: Email)


  //just turn the method into a Fn, as is, so it can be passed as a val...
  val sizeConstraintFn: (IntPairPred, Int, Email) => Boolean = sizeConstraint _

  //pre-pop the size constraint, to further partially compete the Fn
  val min20: EmailFilter = minimumSize(20, _: Email)
  val max20: EmailFilter = maximumSize(20, _: Email)

  //...or to the same effect as above, add the predicate
  val min20a: EmailFilter = constr20(ge, _: Email)
  val max20a: EmailFilter = constr20(le, _: Email)

  //============================ More Advanced =====================

  //a method with curried parameters...
  def sizeConstraintCurried(pred: IntPairPred)(n: Int)(email: Email): Boolean = pred(email.text.length, n)


  // create a Fn, from a method with curried parameters.
  // It takes an IntPairPred and returns a function that takes an Int and returns a new function.
  // That last function, finally, takes an Email and returns a Boolean.
  // IntPairPred => Int => Email => Boolean:
  val sizeConstraintFnCurried: IntPairPred => Int => Email => Boolean = sizeConstraintCurried

  //can invoke it like this...
  val result:Boolean = sizeConstraintFnCurried(gt) (1) (email)
  //or...


  //create partially applied Fns with only predicate defined
  //NB: The order of the parameters is important - the ones you want to partially apply must come first
  val minSize: Int => Email => Boolean = sizeConstraintCurried(ge)
  val maxSize: Int => Email => Boolean = sizeConstraintCurried(le)

  //partially applied Fn with size and predicate defined
  val min20b: Email => Boolean = minSize(20)
  val max20b: Email => Boolean = maxSize(20)

  //Just needs an email parameter, same as above, but in one step...
  val min20c: Email => Boolean = sizeConstraintFnCurried(ge)(20)
  val max20c: Email => Boolean = sizeConstraintFnCurried(le)(20)


  //====================== currying existing Fns ==================

  val sum: (Int, Int) => Int = _ + _
  val sumCurried: Int => Int => Int = sum.curried






}
