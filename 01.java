import java.util.Scanner;
import java.util.Vector;

class Main {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    Vector<Integer> dirs = new Vector<>();
    while (s.hasNext()) {
      String line = s.nextLine();
      boolean left = line.charAt(0) == 'L';
      int i = Integer.parseInt(line.substring(1));
      dirs.add(left ? -i : i);
    }

    partOne(dirs);
    partTwo(dirs);
  }

  private static void partOne(Vector<Integer> dirs) {
    int at = 50;
    int count = 0;
    for (Integer i : dirs) {
      at += i + 100;
      at %= 100;
      if (at == 0) {
        count += 1;
      }
    }
    System.out.println(String.format("partOne: %d", count));
  }

  private static void partTwo(Vector<Integer> dirs) {
    int at = 50;
    int count = 0;
    for (Integer i : dirs) {
      int d = i < 0 ? 99 : 1;
      int diff = 0;
      for (int x = 0; x < Math.abs(i); x++) {
        at += d;
        at %= 100;
        if (at == 0) { diff++; }
      }
      count += diff;
    }
    System.out.println(String.format("partTwo: %d", count));
  }
}
