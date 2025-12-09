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
}

class TupleComparator {
  static <A extends Comparable<A>, B extends Comparable<B>> int
  compare(Tuple<A, B> x, Tuple<A, B> y) {
    return x.b.equals(y.b) ? y.a.compareTo(x.a)
                           : x.b.compareTo(y.b); // descending on b
  }
}

class Counter {
  int a;

  Counter() { a = 0; }

  Counter(int start) { a = start; }

  Integer next() {
    int out = a;
    a++;
    return out;
  }
}

class Main {
  static List<List<Integer>> input;

  public static void main(String[] args) {
    var s = new Scanner(System.in);
    input = new ArrayList<>();
    while (s.hasNextLine()) {
      String p = s.nextLine();
      input.add(p.chars().mapToObj(b -> b - '0').toList());
    }
    s.close();

    partOne();
    partTwo();
  }

  private static void partOne() {
    var x = input.stream()
                .mapToInt(xs -> {
                  if (xs.size() == 0) {
                    return 0;
                  }
                  final Counter c = new Counter();
                  Tuple<Integer, Integer> first =
                      xs.subList(0, xs.size() - 1)
                          .stream()
                          .map(i -> new Tuple<>(c.next(), i))
                          .collect(Collectors.maxBy(TupleComparator::compare))
                          .orElseThrow();

                  Integer second = xs.subList(first.a + 1, xs.size())
                                       .stream()
                                       .mapToInt(i -> i)
                                       .max()
                                       .orElseThrow();
                  return first.b * 10 + second;
                })
                .sum();
    System.out.println(x);
  }

  private static void partTwo() {
    var x = input.stream()
                .mapToLong(xs -> {
                  if (xs.size() == 0) {
                    return 0;
                  }
                  int n = 12;
                  int at = 0;
                  long output = 0;
                  for (int i : IntStream.range(1, n).toArray()) {
                    final Counter c = new Counter(at);
                    Tuple<Integer, Integer> res =
                        xs.subList(at, xs.size() - n + i)
                            .stream()
                            .map(p -> new Tuple<>(c.next(), p))
                            .collect(Collectors.maxBy(TupleComparator::compare))
                            .orElseThrow();
                    at = res.a + 1;
                    output += res.b * Math.pow(10, n - i);
                  }
                  return output;
                })
                .sum();
    System.out.println(x);
  }
}
