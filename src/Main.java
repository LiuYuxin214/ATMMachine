import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        while (true) {
            System.out.println("Welcome to ATM!");
            System.out.println("Type \"m\" to management tools menu. If you forget your password, type \"f\".");
            System.out.print("Enter an ID : ");
            Scanner input = new Scanner(System.in);
            String enter = input.nextLine();
            if (enter.matches("\\d+")) {
                int id = Integer.parseInt(enter);
                if (new File("Accounts/" + id + ".txt").exists()) {
                    System.out.print("Enter your password: ");
                    String password = input.nextLine();
                    Menu.userOperation(id, password);
                } else {
                    System.out.println("User not found");
                    sleep(1000);
                }
            } else if (enter.equals("m")) {
                ManagementTools.management();
            } else if (enter.equals("f")) {
                System.out.print("Enter the ID that you forget password: ");
                int id = input.nextInt();
                Account account = new Account(id);
                account.getFromFile();
                System.out.println("Your password reset question is: ");
                System.out.println(account.getQuestion());
                System.out.print("Enter the answer to your password reset question: ");
                String answer = input.next();
                if (answer.equals(account.getAnswer())) {
                    System.out.print("Enter a new password: ");
                    String newPassword = input.next();
                    account.setPassword(newPassword);
                    account.saveToFile();
                    System.out.println("Password reset successfully, please login again.");
                } else {
                    System.out.println("Incorrect answer");
                }
                sleep(3000);
            } else {
                System.out.println("User not found");
                sleep(1000);
            }
        }
    }
}
