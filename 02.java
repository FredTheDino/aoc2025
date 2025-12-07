import java.util.*;

class Tuple<A, B> {
  public A a;
  public B b;

  public Tuple(A a, B b) {
    this.a = a;
    this.b = b;
  }
}

class Main {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    s.useDelimiter("[,\n ]");
    Vector<Tuple<Long, Long>> input = new Vector<>();
    while (s.hasNext()) {
      String p = s.next();
      Scanner i = new Scanner(p);
      i.useDelimiter("-");
      long a = i.nextLong();
      long b = i.nextLong();
      i.close();
      input.add(new Tuple<Long, Long>(a, b));
    }
    s.close();

    partOne(input);
    partTwo(input);
  }

  private static void partOne(Vector<Tuple<Long, Long>> input) {
    long sum = 0;
    for (Tuple<Long, Long> t : input) {
      for (long x = t.a; x <= t.b; x++) {
        if (String.format("%d", x).matches("^(\\d+)(\\1)$")) {
          sum += x;
        }
      }
    }
    System.out.printf("Part one: %d\n", sum);
  }

  private static void partTwo(Vector<Tuple<Long, Long>> input) {
    long sum = 0;
    for (Tuple<Long, Long> t : input) {
      for (long x = t.a; x <= t.b; x++) {
        if (String.format("%d", x).matches("^(\\d+)\\1+$")) {
          sum += x;
        }
      }
    }
    System.out.printf("Part two: %d\n", sum);
  }
}
