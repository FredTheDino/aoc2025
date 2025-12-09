import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
  static ArrayList<Tuple<Long, Long>> valid;
  static ArrayList<Long> ids;

  public static void main(String[] args) {
    valid = new ArrayList<>();
    ids = new ArrayList<>();
    var s = new Scanner(System.in);
    while (s.hasNextLine()) {
      String p = s.nextLine();
      if (p == "")
        break;
      var q = new Scanner(p);
      q.useDelimiter("-");
      valid.add(new Tuple<>(q.nextLong(), q.nextLong()));
      q.close();
    }

    while (s.hasNext()) {
      ids.add(s.nextLong());
    }
    s.close();

    // System.out.println(valid);
    // System.out.println(ids);

    partOne();
    partTwo();
  }

  private static void partOne() {
    var c = ids.stream()
                .filter(x -> valid.stream().anyMatch(t -> t.a <= x && x <= t.b))
                .count();
    System.out.println(c);
  }

  private static void partTwo() {
    var overlaps = new HashSet<Tuple<Long, Long>>();
    for (var x : valid) {
      var p =
          overlaps.stream()
              .filter(
                  t -> (t.a <= x.a && x.a <= t.b) || (t.a <= x.b && x.b <= t.b))
              .collect(Collectors.toCollection(HashSet::new));
      overlaps.removeAll(p);
      var lo = Math.min(
          p.stream().mapToLong(t -> t.a).min().orElse(Long.MAX_VALUE), x.a);
      var hi = Math.max(
          p.stream().mapToLong(t -> t.b).max().orElse(Long.MIN_VALUE), x.b);
      overlaps.add(new Tuple<>(lo, hi));
    }
    System.out.println(overlaps);
    var c = overlaps.stream().mapToLong(t -> t.b - t.a + 1).sum();
    System.out.println(c);
  }
}
