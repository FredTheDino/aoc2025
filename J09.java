import java.io.*;
import java.util.*;

class Tuple<A, B> {
  public A a;
  public B b;

  public Tuple(A a, B b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Tuple))
      return false;
    var other = (Tuple)o;
    return Objects.equals(a, other.a) || Objects.equals(b, other.b);
  }

  @Override
  public String toString() {
    return "<" + a.toString() + ", " + b.toString() + ">";
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b);
  }
}

class Main {
  static ArrayList<Tuple<Long, Long>> redTiles = new ArrayList<>();

  public static void main(String[] args) throws Exception {
    var s = new Scanner(new File("09.input"));
    while (s.hasNextLine()) {
      var xs = s.nextLine().split(",");
      redTiles.add(new Tuple<>(Long.parseLong(xs[0]), Long.parseLong(xs[1])));
    }
    s.close();

    System.out.println(a());
    System.out.println(b());
  }

  static Long a() {
    return redTiles.parallelStream()
        .mapToLong(
            x -> redTiles.stream().mapToLong(y -> area(x, y)).max().orElse(0))
        .max()
        .orElse(0);
  }

  static Long b() {
    for (var p : redTiles) {
      assert (isInside(p));
    }
    return redTiles.parallelStream()
        .mapToLong(x
                   -> redTiles.stream()
                          .mapToLong(y -> {
                            var j = new Tuple<>(x.a, y.b);
                            var k = new Tuple<>(y.a, x.b);
                            return isInside(x) && isInside(j) && isInside(k) &&
                                    isInside(y) && doesNotCross(x, y)
                                ? area(x, y)
                                : 0;
                          })
                          .max()
                          .orElse(0))
        .max()
        .orElse(0);
  }

  static Long area(Tuple<Long, Long> a, Tuple<Long, Long> b) {
    return (Math.abs(a.a - b.a) + 1) * (Math.abs(a.b - b.b) + 1);
  }

  static boolean isInside(Tuple<Long, Long> p) { return isInside(p, redTiles); }

  static boolean doesNotCross(Tuple<Long, Long> a, Tuple<Long, Long> b) {
    var loX = Long.min(a.a, b.a);
    var hiX = Long.max(a.a, b.a);
    var loY = Long.min(a.b, b.b);
    var hiY = Long.max(a.b, b.b);

    for (var i = 0; i < redTiles.size(); i++) {
      var f = redTiles.get(i);
      var g = redTiles.get((i + 1) % redTiles.size());
      if (f.a == g.a) {
        var lo = Long.min(f.b, g.b);
        var hi = Long.max(f.b, g.b);
        if (lo < hiY && loY < hi && loX < f.a && f.a < hiX) return false;
      } else {
        var lo = Long.min(f.a, g.a);
        var hi = Long.max(f.a, g.a);
        if (lo < hiX && loX < hi && loY < f.b && f.b < hiY) return false;
      }
    }
    return true;
  }
  static boolean isInside(Tuple<Long, Long> p,
                          ArrayList<Tuple<Long, Long>> xs) {
    var count = 0;
    var dir = 0;
    for (var i = 0; i < xs.size(); i++) {
      var a = xs.get(i);
      var b = xs.get((i + 1) % xs.size());
      var cd = a.b - b.b > 0 ? 1 : -1;
      var overlap =
          (a.b.equals(b.b)) &&
          (cd == -1 ? Math.min(a.b, b.b) < p.b : Math.min(a.b, b.b) <= p.b) &&
          (Math.min(a.a, b.a) <= p.a) && (p.a <= Math.max(a.a, b.a)) &&
          (cd != dir);
      if (overlap) {
        dir = cd;
        count++;
        if (count == 2) {
          return false;
        }
      }
    }
    return count == 1;
  }
}
