
def moreThan(comparedTo: Int)(value: Int):Boolean = {
  value > comparedTo
}
val moreThan20 = moreThan(20) _

def lessThan(comparedTo: Int)(value: Int):Boolean = {
  value < comparedTo
}
val lessThan30 = lessThan(30) _


//def lessThan30(i: Int): Boolean = {
//  println(s"\n$i less than 30?")
//  i < 30
//}
//
//def moreThan20(i: Int): Boolean = {
//  println(s"$i more than 20?")
//  i > 20
//}

val l = List(1, 25, 40, 5, 23)
val l1 = l.filter(lessThan30)
val l2 = l1.filter(moreThan20)

//Withfiler evaluates lazily...
// So, good for very long Lists
val la = List(1, 25, 40, 5, 23)
val lb = la.withFilter(lessThan30)
val lc = lb.withFilter(moreThan20)


lc.foreach(
  v => println(" result="+v)
)

