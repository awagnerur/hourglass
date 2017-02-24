import java.util.Scanner;

public class HourGlass {

    public static void main(String[] args) {
      Boolean doDebug = false;

      if (args.length != 0 && (args[0].equals("debug") ||
          args[0].equals("-debug") || args[0].equals("--debug") ||
          args[0].equals("d") || args[0].equals("-d") ||
          args[0].equals("--d"))) {

        doDebug = true;
      }

      Scanner scan = new Scanner(System.in);

      System.out.print("First hourglass minutes (smaller number): ");
      int a = scan.nextInt();

      System.out.print("Second hourglass minutes (larger number): ");
      int b = scan.nextInt();

      if ( a > b ){
        System.out.println(
          "Please make sure the first number is smaller than the second.");
        System.exit(0);
      }

      System.out.print("Minutes to reach: ");
      int c = scan.nextInt();

      if ( c < a ){
        System.out.println(
          "Please make sure the minutes are larger than the first number.");
        System.exit(0);
      }

      System.out.println(
        "Is it possible to make " + c + " starting from 0: " +
          isPossibleFromZero(a, b, c, doDebug));
    }

    private static Boolean isPossibleFromZero(
      int a, int b, int c, Boolean doDebug) {

      if ( c % a == 0 || c % b == 0) {
        return true;
      }

      for (int counter = 1; counter < (b - a) + 1; counter++) {
        int counterA = 1;
        int counterB = 1;
        Boolean reachedC = false;
        while (!reachedC) {
          if ( (counterA * a + counter) > c || (counterB * b + counter) > c ) {
            reachedC = true;
          }

          if (counterB * b - counterA * a == counter) {
            if (
              findCounterBeforeTimeToReach(
                a, b, c, counter, counterA, counterB, counter, doDebug)) {

              return true;
            }
          }
          if (counterA * a - counterB * b == counter){
            if (
              findCounterBeforeTimeToReach(
                a, b, c, counter, counterA, counterB, counter, doDebug)) {

              return true;
            }
          }
          if (counterA * a > counterB * b) {
            ++counterB;
          }
          else {
            ++counterA;
          }
        }
      }

      return false;
    }

    private static Boolean findCounterBeforeTimeToReach(
      int a, int b, int c, int counter, int counterA, int counterB,
        int innerCounter, Boolean doDebug) {

      while ((counterA * a + innerCounter) < (c + 1) ) {
        if ((counterA * a + innerCounter) % c == 0) {
          if (doDebug) {
            doDebug(a, b, c, counter, counterA, counterB, innerCounter);
          }

          return true;
        }
        innerCounter = innerCounter + counter;
      }

      return false;
    }

    private static void doDebug(
      int a, int b, int c, int counter, int counterA, int counterB,
        int innerCounter) {

      System.out.println(
        "counter: " + counter + " counterA: " + counterA + " a: " + a +
        " innerCounter:" + innerCounter);
      System.out.println(
        "counter: " + counter + " counterB: " + counterB + " b: " + b +
          " innerCounter:" + innerCounter);
      System.out.println(
        "counterA: " + counterA + " * a: " + a + " + innerCounter:" +
          innerCounter + " = c: " + c);
    }
}
