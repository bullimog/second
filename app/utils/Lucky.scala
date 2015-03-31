package utils

import scala.math.pow

object Lucky {

  val colVal = (myInt: Int, col: Int) => ((myInt/pow(10,col-1)) % 10).toInt

  def numOfDigits(num: Int) = {
    @scala.annotation.tailrec
    def run(num: Int, digits: Int): Int =
      if(num > 0) run(num / 10, digits + 1)
      else        digits

    run(math.abs(num), 0)
  }

  def zipToSingleDigit(num: Int) = {
    @scala.annotation.tailrec
    def sumDigits(num: Int, acc: Int, col: Int): Int =
      if(col > 0) sumDigits(num, colVal(num, col)+acc, col-1)
      else acc + colVal(num, col)

    @scala.annotation.tailrec
    def recursiveSum(num: Int): Int =
      if(numOfDigits(num) > 1) recursiveSum(sumDigits(num, 0, numOfDigits(num)))
      else num

    recursiveSum(num)
  }



  //#################################################################
  // Workings below here



  def zipup(num: Int) = {
    @scala.annotation.tailrec
    def run(num: Int, acc: Int, col: Int): Int =
      if(col > 0) run(num, colVal(num, col)+acc, col-1)
      else acc + colVal(num, col)

    run(num, 0, numOfDigits(num))
  }

  def crunch(myInt: Int) : Int = {

    myInt % 10
  }

  val units = (myInt: Int) => myInt % 10

  val tens = (myInt: Int) => (myInt/10) % 10
  val hundreds = (myInt: Int) => (myInt/100) % 10





  def colVal3(myInt: Int, col: Int): Int = {

    printf("myInt = " + myInt)
    val pwr = pow(10, col)
    printf("10^col = " + pwr)
    val divNum = myInt / pwr
    printf("myInt / 10^col")
    val result: Int = (divNum % 10).toInt
    println("result: " + result)
    result

  }


  def findLucky(myInt: Int): Int = {
    var acc = 0
    for(i <- 1 until digitCount(myInt)){
      acc += extractDigit(myInt, i)
    }
    acc
  }


  def findLucky2(myInt: Int):Int = {
    findLucky3(myInt, 1, 0, digitCount(myInt))
   }

  private def findLucky3(myInt: Int, digit:Int, acc:Int, size:Int): Int = {
    if (digit >= size) acc
    else findLucky3(myInt, digit+1, acc+extractDigit(myInt, digit), size)
  }


//  for(i <- 1 until digitCount(myInt)){
//    acc += extractDigit(myInt, i)
//  }


  def digitCount(myInt:Int): Int = {
    myInt.toString.length
  }



  def extractDigit(bigInt:Int, digit:Int) : Int={
    (bigInt / 10^(digit-1)) % 10
  }

}
