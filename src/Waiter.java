import java.util.Scanner;

public class Waiter {

    public static void waiter() {
        Scanner waiter = new Scanner(System.in);
        System.out.println("\033[5;36mPress enter to continue\033[0m");
        waiter.nextLine();
    }

    public static void waiterWithoutMessage() {
        Scanner waiter = new Scanner(System.in);
        waiter.nextLine();
    }
}
