import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainMenu {

    public static void userOperation(int id,String password) throws FileNotFoundException {

        Account account = new Account(id);
        account.getFromFile();
        if (account.getPassword().equals(password)) {
            while (true) {
                System.out.println("Welcome to our ATM, " + account.getUserID());
                System.out.println("=========Main Menu=========");
                System.out.println("1. Check balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Display All");
                System.out.println("5. Exit");
                System.out.println("6. Change Password");
                System.out.println("7. Change Question and Answer");
                System.out.println("8. Transfer");
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
                    case 6:
                        System.out.print("Enter a new password: ");
                        String newPassword = input.next();
                        account.setPassword(newPassword);
                        break;
                    case 7:
                        System.out.println("1. What is your favorite color?");
                        System.out.println("2. What is your favorite animal?");
                        System.out.println("3. What is your favorite food?");
                        System.out.println("4. What is your favorite movie?");
                        System.out.println("5. What is your favorite sport?");
                        System.out.print("Enter a new question: ");
                        int newQuestion = input.nextInt();
                        System.out.print("Enter a new answer: ");
                        String newAnswer = input.next();
                        account.setAnswer(newQuestion,newAnswer);
                        break;
                    case 8:
                        System.out.print("Enter an account number to transfer to: ");
                        int accountNumber = input.nextInt();
                        System.out.print("Enter an amount to transfer: ");
                        amount = input.nextDouble();
                        Account transferAccount = new Account(accountNumber);
                        transferAccount.deposit(amount);
                        account.withdraw(amount);
                        transferAccount.saveToFile();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        Waiter.waiter();
                }
            }
        }
        else {
            System.out.println("Invalid password");
            System.out.println("If you want to reset the password, enter 1");
            System.out.println("If you want to exit, enter 2");
            Scanner sc = new Scanner(System.in);
            int choice=sc.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("Your password reset question is: ");
                    System.out.println(account.getQuestion());
                    System.out.print("Enter the answer to your password reset question: ");
                    String answer = sc.next();
                    if (answer.equals(account.getAnswer())) {
                        System.out.print("Enter a new password: ");
                        String newPassword = sc.next();
                        account.setPassword(newPassword);
                        account.saveToFile();
                        System.out.println("Password reset successfully");
                        Waiter.waiter();
                        return;
                    }
                    else {
                        System.out.println("Incorrect answer");
                        Waiter.waiter();
                        return;
                    }
                case 2:
                    System.out.println("Thank you for using our ATM");
                    Waiter.waiter();
                    return;
            }
        }
    }
    public static void adminOperation(String username,String password) throws FileNotFoundException {
        File file = new File("Administrator.txt");
        Scanner adminFile = new Scanner(file);
        String trueUserName = adminFile.nextLine();
        String truePassword = adminFile.nextLine();
        if (username.equals(trueUserName) && password.equals(truePassword)) {
            while (true) {
                File numOfAccountsFile = new File("numOfAccounts.txt");
                Scanner numOfAccountsFileInput = new Scanner(numOfAccountsFile);
                int maxID = numOfAccountsFileInput.nextInt() - 1;
                System.out.println("Welcome to the administrator menu, " + username);
                System.out.println("=========Administrator Menu=========");
                System.out.println("1. Create new account");
                System.out.println("2. Display all accounts");
                System.out.println("3. Exit");
                System.out.print("Enter a choice: ");
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("=========Create new account=========");
                        System.out.println("ID: " + (maxID + 1));
                        System.out.print("Enter a password: ");
                        String newPassword = input.next();
                        System.out.println("1. What is your favorite color?");
                        System.out.println("2. What is your favorite animal?");
                        System.out.println("3. What is your favorite food?");
                        System.out.println("4. What is your favorite movie?");
                        System.out.println("5. What is your favorite sport?");
                        System.out.print("Enter a question: ");
                        int newQuestion = input.nextInt();
                        System.out.print("Enter an answer: ");
                        String newAnswer = input.next();
                        System.out.print("Enter a balance: ");
                        double newBalance = input.nextDouble();
                        Account newAccount = new Account(maxID + 1, newBalance, newPassword);
                        newAccount.setAnswer(newQuestion, newAnswer);
                        newAccount.resetDate();
                        maxID++;
                        PrintWriter numOfAccountsFileOutput = new PrintWriter(numOfAccountsFile);
                        numOfAccountsFileOutput.println(maxID + 1);
                        numOfAccountsFileOutput.close();
                        newAccount.saveToFile();
                        System.out.println("Account created successfully");
                        Waiter.waiter();
                        break;
                    case 2:
                        System.out.println("=========Display all accounts=========");
                        System.out.println("ID Balance Password Question Answer");
                        for (int i = 0; i <= maxID; i++) {
                            Account account = new Account(i);
                            account.getFromFile();
                            System.out.println(account.getUserID() + " " + account.getBalance() + " " + account.getPassword() + " " + account.getQuestion() + " " + account.getAnswer());
                        }
                        Waiter.waiter();
                        break;
                    case 3:
                        System.out.println("Thank you for using");
                        Waiter.waiter();
                        return;
                }
            }
        }
        else
        {
            System.out.println("Wrong Username or Password");
            Waiter.waiter();
            return;
        }
    }
}