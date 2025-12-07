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

class TupleHelper {
  public static ArrayList<Tuple<Integer, Integer>>
  neighbors(Tuple<Integer, Integer> i) {
    var out = new ArrayList<Tuple<Integer, Integer>>();
    for (var x : IntStream.range(-1, 2).toArray()) {
      for (var y : IntStream.range(-1, 2).toArray()) {
        if (x == 0 && y == 0)
          continue;
        out.add(new Tuple<>(i.a + x, i.b + y));
      }
    }
    return out;
  }
}

class Main {
  static HashSet<Tuple<Integer, Integer>> input;

  public static void main(String[] args) {
    var s = new Scanner(System.in);
    input = new HashSet<>();
    var y = 0;
    while (s.hasNextLine()) {
      y++;
      String p = s.nextLine();
      for (var x = 0; x < p.length(); x++) {
        if (p.charAt(x) == '@') {
          input.add(new Tuple<>(x, y));
        }
      }
    }
    s.close();

    partOne();
    partTwo();
  }

  private static void partOne() {
    System.out.println(
        input.stream()
            .mapToInt(p
                      -> TupleHelper.neighbors(p)
                                     .stream()
                                     .mapToInt(i -> input.contains(i) ? 1 : 0)
                                     .sum() < 4
                             ? 1
                             : 0)
            .sum());
  }

  private static void partTwo() {
    var x = input.stream().collect(Collectors.toSet());
    var start = x.size();
    while (true) {
      var remove = x.stream()
                        .filter(p
                                -> TupleHelper.neighbors(p)
                                           .stream()
                                           .mapToInt(i -> x.contains(i) ? 1 : 0)
                                           .sum() < 4)
                        .collect(Collectors.toSet());
      
      if (!x.removeAll(remove))
        break;
    }

    System.out.println(start - x.size());
  }
}
