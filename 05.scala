import scala.io.StdIn
import scala.util.control.Breaks._
import scala.collection.mutable.ListBuffer
import scala.math.Ordered.orderingToOrdered
import scala.math.Ordering.Implicits.infixOrderingOps

val parse = () => {
  val range = raw"(\d+)-(\d+)".r
  val id = raw"(\d+)".r

  val ranges: ListBuffer[(Long, Long)] = ListBuffer()
  val ids: ListBuffer[Long] = ListBuffer()
  while util.Try(StdIn.readLine()).toOption match
      case Some(range(a, b)) =>
        ranges.append((a.toLong, b.toLong))
        true
      case Some(id(id)) =>
        ids.append(id.toLong)
        true
      case Some("") => true
      case _        =>
        false
  do {}
  (ranges.toList, ids.toList)
}

object Main05 extends App {
  val (ranges, ids) = parse()
  val a =
    ids.count((x) => ranges.exists({ case (a, b) => a <= x && x <= b }))
  val (b, _) = ranges.sorted.foldLeft((0L, 0L))((x, l) => {
    val mm = l._1.max(x._2)
    val uu = l._2 + 1;
    val dd = (uu - mm).max(0)
    (x._1 + dd, uu.max(mm))
  })
  println(s"$a $b")
}
