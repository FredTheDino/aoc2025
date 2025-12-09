import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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
  static ArrayList<HashSet<Integer>> splits;
  static HashSet<Integer> beams;

  public static void main(String[] args) {
    splits = new ArrayList<>();
    beams = new HashSet<>();
    var s = new Scanner(System.in);
    while (s.hasNextLine()) {
      var n = s.nextLine();
      if (n.equals(""))
        continue;
      var x = 0;
      var set = new HashSet<Integer>();
      for (var it : n.chars().boxed().toList()) {
        x++;
        if (it == 'S')
          beams.add(x);
        if (it == '^')
          set.add(x);
      }
      splits.add(set);
    }
    s.close();
    // System.out.println(splits);
    // System.out.println(beams);

    partOne();
    System.out.println(partTwo(beams.iterator().next(), 0));
  }

  public static void partOne() {
    var bx = new HashSet<>(beams);
    var n = 0;
    for (var sx : splits) {
      var overlap = new HashSet<>(bx);
      overlap.retainAll(sx);
      n += overlap.size();
      bx.removeAll(overlap);
      bx.addAll(overlap.stream()
                    .map(i -> i + 1)
                    .collect(Collectors.toCollection(HashSet::new)));
      bx.addAll(overlap.stream()
                    .map(i -> i - 1)
                    .collect(Collectors.toCollection(HashSet::new)));

      // for (var x : IntStream.range(0, 150).toArray()) {
      //   if (bx.contains(x)) {
      //     System.out.print("|");
      //   } else if (sx.contains(x)) {
      //     System.out.print("^");
      //   } else {
      //     System.out.print(" ");
      //   }
      // }
      // System.out.println();
    }
    System.out.println(n);
  }

  static HashMap<Tuple<Integer, Integer>, Long> cache = new HashMap<>();

  public static Long partTwo(Integer x, Integer y) {
    if (splits.size() <= y)
      return 1l;
    Long out;
    var at = new Tuple<>(x, y);
    if (cache.containsKey(at)) {
      out = cache.get(at);
    } else {
      if (splits.get(y).contains(x)) {
        out = partTwo(x - 1, y + 1) + partTwo(x + 1, y + 1);
      } else {
        out = partTwo(x, y + 1);
      }
    }
    cache.putIfAbsent(at, out);
    return out;
  }
}
