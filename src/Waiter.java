import java.util.Scanner;

public class Waiter {

    public static void waiter() {
        Scanner waiter = new Scanner(System.in);
        System.out.println("Press enter to continue");
        waiter.nextLine();
    }

    public static void waiterWithoutMessage() {
        Scanner waiter = new Scanner(System.in);
        waiter.nextLine();
    }
}
