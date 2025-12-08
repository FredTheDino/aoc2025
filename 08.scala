import scala.io.StdIn
import scala.collection.mutable

def parse = {
  val r = raw"(\d+),(\d+),(\d+)".r
  Iterator
    .continually(StdIn.readLine())
    .takeWhile(_ != null)
    .collect { case r(x, y, z) => (x.toInt, y.toInt, z.toInt) }
}

def sq(a: Long): Long = a * a

def distanceSq(a: (Int, Int, Int), b: (Int, Int, Int)): Long  =
  sq(a._1 - b._1) + sq(a._2 - b._2) + sq(a._3 - b._3)

enum Node[T]:
  case End(a: mutable.HashSet[T])
  case Fwd(a: T)

class UnionFind[T] {
  var m: mutable.HashMap[T, Node[T]] = mutable.HashMap.empty

  def get(a: T): (T, mutable.HashSet[T]) = m.get(a) match
    case None => (a, mutable.HashSet(a))
    case Some(Node.Fwd(aa)) => {
      val (fwd, node) = get(aa)
      m.update(a, Node.Fwd(fwd))
      (fwd, node)
    }
    case Some(Node.End(s)) => (a, s)

  def merge(a: T, b: T): Unit = {
      (get(a), get(b)) match
      case ((aa, _), (bb, _)) if aa == bb => ()
      case ((aa, as), (bb, bs)) => {
        m.update(aa, Node.End(as.union(bs)))
        m.update(bb, Node.Fwd(aa))
      }
  }

  def sets(): Iterable[mutable.HashSet[T]] = m.values.collect({ case Node.End(a) => a })
}

def p2(pos: List[(Int, Int, Int)], dists: List[(Long, ((Int, Int, Int), (Int, Int, Int)))]): Int =
  val merged = UnionFind[(Int, Int, Int)]()
  for (_, (a, b)) <- dists do {
    merged.merge(a, b)
    if merged.get(a)._2.size == pos.size then {
      println((a, b))
      return (a._1 * b._1)
    }
  }
  -1

object Main05 extends App {
  val pos = parse.toList.sorted
  val dists = 
      pos
        .zipWithIndex
        .flatMap { case (p, i) => pos.drop(i + 1).map((q) => (distanceSq(p, q), (p, q))) }
        .sorted
  
  val merged = UnionFind[(Int, Int, Int)]()
  for (_, (a, b)) <- dists.take(1000) do {
    merged.merge(a, b)
  }
  println(merged.sets().toList.map(_.size).sortBy(_ * -1).take(3).reduce((a, b) => a * b))

  println(p2(pos, dists))
}
