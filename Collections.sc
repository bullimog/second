import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

val k = "0;1;2;3;4;5"
//val k = "Nothing happened, it's bloody broken"
val values = k.split(";").toList.map(s => safeToInt(s))
def safeToInt(s:String):Int = {
  Try(s.toInt) match{
    case Success(n) => n
    case Failure(n) => 0
  }
}

def take6Regardless = takeRegardless(6) _
def takeRegardless(num:Int)(l:List[Int]):List[Int]={
  padListTo(num)(l.take(num))
}

@tailrec
def padListTo(num:Int)(l:List[Int]):List[Int]={
  if(l.length < num) padListTo(num) (l:+0)
  else  l
}

def getTime:Int = {getValue(0)}
def getDigital:Int = {getValue(1)}
def getAnalogue1:Int = {getValue(2)}
def getAnalogue2:Int = {getValue(3)}
def getCounter1:Int = {getValue(4)}
def getCounter2:Int = {getValue(5)}


def getValue(position:Int):Int = {
  val numbers = take6Regardless(values)
  numbers.slice(position,position+1).head
}

val d = getDigital
val a1 = getAnalogue1
val a2 = getAnalogue2
val c1 = getCounter1
val c2 = getCounter2