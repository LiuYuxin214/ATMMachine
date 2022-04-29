import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainMenu {

    public static void userOperation(int id) throws FileNotFoundException {

        Account account = new Account(id, 0);
        account.getFromFile();
        while (true) {
            System.out.println("Welcome to our ATM, "+account.getUserID());
            System.out.println("=========Main Menu=========");
            System.out.println("1. Check balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Display All");
            System.out.println("5. Exit");
            System.out.print("Enter a choice: ");
            Scanner input = new Scanner(System.in);
            double amount;
            int option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.println("The balance is " + account.getBalance());
                    Waiter.waiter();
                    break;
                case 2:
                    System.out.print("Enter an amount to withdraw: ");
                    amount = input.nextDouble();
                    account.withdraw(amount);
                    break;
                case 3:
                    System.out.print("Enter an amount to deposit: ");
                    amount = input.nextDouble();
                    account.deposit(amount);
                    break;
                case 4:
                    account.displayAll();
                    Waiter.waiter();
                    break;
                case 5:
                    account.saveToFile();
                    System.out.println("Thank you for using our ATM");
                    Waiter.waiter();
                    return;
                default:
                    System.out.println("Invalid choice");
                    Waiter.waiter();
            }
        }
    }
    public static void adminOperation(String username,String password) {
        while (true) {
            System.out.println("Welcome to the administrator menu, " + username);
            System.out.println("=========Administrator Menu=========");
            System.out.println("1. Create new account");
            System.out.println("2. Delete account");
            System.out.println("3. Display all accounts");
            System.out.println("4. Exit");
            System.out.print("Enter a choice: ");
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Enter a user ID: ");
            }
        }
    }
}