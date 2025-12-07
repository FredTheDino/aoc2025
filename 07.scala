import scala.io.StdIn
import scala.collection.mutable

def parse =
  Iterator
    .continually(StdIn.readLine())
    .takeWhile(_ != null)
    .map(
      _.zipWithIndex
        .collect { case (c, i) if c != '.' => i }
        .toSet
    )

def p2(
    mem: mutable.Map[(Int, Int), Long],
    forks: Set[(Int, Int)],
    at: (Int, Int)
): Long = {
  lazy val res =
    at match
      case (200, _)                 => 1
      case at if forks.contains(at) =>
        p2(mem, forks, (at._1, at._2 + 1)) +
          p2(mem, forks, (at._1, at._2 - 1))
      case at => p2(mem, forks, (at._1 + 1, at._2))
  mem.getOrElseUpdate(at, res)
}

object Main05 extends App {
  val head :: forks = parse.toList: @unchecked

  val a = forks
    .foldLeft((head, 0)) { case ((sx, tot), xs) =>
      val forks = sx intersect xs
      val nn = sx
        .removedAll(forks)
        .union(forks map (_ + 1))
        .union(forks map (_ - 1))
      (nn, tot + forks.size)
    }

  val forksAsSet = forks.zipWithIndex.flatMap { case (xs, col) =>
    xs map (row => (col, row))
  }.toSet

  val b =
    p2(mutable.HashMap.empty, forksAsSet, (0, head.head))

  println(s"${a._2} $b")
}
