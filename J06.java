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

class Counter {
  Long a;

  Counter() { a = 0l; }

  Counter(Long start) { a = start; }

  Long next() { return a++; }
}

class Helper {
  static <T> Stream<Tuple<Long, T>> enumerate(Stream<T> it, long a) {
    var c = new Counter(a);
    return it.map(t -> new Tuple<>(c.next(), t));
  }

  static <T> Stream<Tuple<Long, T>> enumerate(Stream<T> it) {
    return enumerate(it, 0l);
  }

  static Stream<Tuple<Long, Integer>> enumerate(IntStream it) {
    return enumerate(it.boxed());
  }
}

class Main {
  static ArrayList<ArrayList<String>> numbers;
  static ArrayList<String> ops;

  static ArrayList<String> numbersR;
  static String opsR;

  public static void main(String[] args) {
    numbers = new ArrayList<>();
    numbersR = new ArrayList<>();
    ops = new ArrayList<>();
    var s = new Scanner(System.in);
    while (s.hasNextLine()) {
      var n = s.nextLine();
      var p = new ArrayList<>(Arrays.asList(n.strip().split("[ ]+")));
      if (p.isEmpty())
        break;
      if (p.get(0).equals("*") || p.get(0).equals("+")) {
        opsR = n;
        ops = p;
        break;
      }
      numbers.add(p);
      numbersR.add(n);
    }
    s.close();

    partOne();
    partTwo();
  }

  public static void partOne() {
    var x = Helper.enumerate(ops.stream())
                .mapToLong(t -> {
                  LongStream p = numbers.stream().mapToLong(
                      xs -> Long.parseLong(xs.get(t.a.intValue())));

                  if (t.b.equals("*")) {
                    return p.reduce(1, (a, b) -> a * b);
                  } else {
                    return p.sum();
                  }
                })
                .sum();
    System.out.println(x);
  }

  public static void partTwo() {
    var sum = 0l;
    for (var i = 0; i < opsR.length(); i++) {
      if (opsR.charAt(i) == ' ')
        continue;
      var q = Math.min(or(opsR.indexOf('*', i + 1), opsR.length() + 1),
                       or(opsR.indexOf('+', i + 1), opsR.length() + 1));

      var stream = IntStream.range(i, q - 1).mapToLong(ii -> {
        String num = "";
        for (String x : numbersR) {
          num = num + x.charAt(ii);
        }
        return Long.parseLong(num.strip());
      });

      if (opsR.charAt(i) == '*') {
        sum += stream.reduce(1, (x, y) -> x * y);
      } else {
        sum += stream.sum();
      }
    }
    System.out.println(sum);
  }

  private static int or(int indexOf, int i) {
    return indexOf == -1 ? i : indexOf;
  }
}
