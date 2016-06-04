import Stream._

object Streams {
  println("Streams")                              //> Streams

  def ints(i: Int): Stream[Int] = i #:: ints(i+1)
  //> ints: (i: Int)Stream[Int]
  val s = ints(0) take 4                    //> s  : scala.collection.immutable.Stream[Int] = Stream(0, ?)

  s foreach (println(_))
  //> 0
  //| 1
  //| 2
  //| 3
  // List without a stream
¬  (1 to 10).toList.map {
    i => println(s"list: map $i")
      i + 10
  }.filter {
    i => println(s"list: filter $i")
      i%2 == 0
  } take 3
  //> list: map 1
  //| list: map 2
  //| list: map 3
  //| list: map 4
  //| list: map 5
  //| list: map 6
  //| list: map 7
  //| list: map 8
  //| list: map 9
  //| list: map 10
  //| list: filter 11
  //| list: filter 12
  //| list: filter 13
  //| list: filter 14
  //| list: filter 15
  //| list: filter 16
  //| list: filter 17
  //| list: filter 18
  //| list: filter 19
  //| list: filter 20
  //| res0: List[Int] = List(12, 14, 16)

  // Using a stream
  def nums(i: Int): Stream[Int] = (i) #:: nums(i+1)
  //> nums: (i: Int)Stream[Int]
  val x = nums(1)                               //> x  : Stream[Int] = Stream(1, ?)

  x.map{
    i => println(s"stream: map $i")
      i+10
  }.filter {
    i => println(s"stream: filter $i")
      i%2 == 0
  } take 3 toList                           //> stream: map 1
  //| stream: filter 11
  //| stream: map 2
  //| stream: filter 12
  //| stream: map 3
  //| stream: filter 13
  //| stream: map 4
  //| stream: filter 14
  //| stream: map 5
  //| stream: filter 15
  //| stream: map 6
  //| stream: filter 16
  //| res1: List[Int] = List(12, 14, 16)
}