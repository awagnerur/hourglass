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

      /*
        In the following logic, we do the followings:
        - enter the for loop where the counter will be the lowest Integer
          we are trying to make with the two hourglasses
        - within the for loop, the first while is simply testing for whether
          we have reached c (the minutes we are trying to make). We set the
          variable reachedC to true when we did, resulting the for loop to
          proceed to the next number
        - if within the while loop we can make the desired number (counter)
          we return true breaking both loops
        - in the meantime, we keep track of the counters for both a and b,
          so we know what combination we've tried. It's important, because
          we don't increase one after the other, we need to only increase the
          one that is currently resulting in a lower number:
          (counterA * a > counterB * b)
        - the method "findCounterBeforeTimeToReach" is the key
        - key number pairs to test flaws with: 4-7-9 and 7-11-15, 5-8-11.
          For example, with 7-11-15 the simple GCD would give false positive,
          because we can't make that happen from the beginning
        - for testing: 13-17-35 should be possible but my algorithm wouldn't
          find it
      */

      for (int counter = 1; counter < (b) + 1; counter++) {
        System.out.println("in the for loop with counter being " + counter);
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
                b, a, c, counter, counterB, counterA, counter, doDebug)) {

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

      System.out.println("-- c - (counterB * b) % innerCounter = " + (c - (counterB * b)) % innerCounter);

      if (doDebug) {
        doDebug(a, b, c, counter, counterA, counterB, innerCounter);
      }

      if ((c - (counterB * b)) % innerCounter == 0) {
        //System.out.println("why are we not here???");
        return true;
      }

      // while ((counterA * a + innerCounter) < (c + 1) ) {
      //   int remainder = (counterA * a + innerCounter) % c;
      //   //System.out.println("remainder from (counterA * a + innerCounter) % c: " + remainder);
      //   if (remainder == 0 || remainder == a || remainder == b ||
      //       remainder == a - innerCounter || remainder == b - innerCounter) {
      //
      //     return true;
      //   }
      //   innerCounter = innerCounter + counter;
      // }

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
