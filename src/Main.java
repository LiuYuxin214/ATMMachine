import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        while (true) {
            System.out.print("Enter an ID (Type \"s\" to system menu): ");
            Scanner input = new Scanner(System.in);
            String enter = input.nextLine();
            if (enter.matches("\\d+")) {
                int id = Integer.parseInt(enter);
                if (new File("Accounts/" + id + ".txt").exists()) {
                    System.out.print("Enter your password: ");
                    String password = input.nextLine();
                    MainMenu.userOperation(id, password);
                } else {
                    System.out.println("User not found");
                    Waiter.waiter();
                }
            } else if (enter.equals("s")) {
                SystemMenu.moreOperation();
            } else {
                System.out.println("User not found");
                Waiter.waiter();
            }
        }
    }
}
