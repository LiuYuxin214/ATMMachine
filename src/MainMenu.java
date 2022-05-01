import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class MainMenu {

    public static void userOperation(int id, String password) throws FileNotFoundException, InterruptedException {

        Account account = new Account(id);
        account.getFromFile();
        Announcement announcement = new Announcement();
        if (account.getPassword().equals(password)) {
            while (true) {
                System.out.println("Welcome to our ATM, " + account.getUserID());
                System.out.print("Announcement: ");
                System.out.println(announcement.getAnnouncement());
                System.out.println("=========Main Menu=========");
                System.out.println("1. Check balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Display All");
                System.out.println("6. Change Password");
                System.out.println("7. Change Question and Answer");
                System.out.println("8. Exit");
                System.out.print("Enter a choice: ");
                Scanner input = new Scanner(System.in);
                double amount;
                int option = input.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("The balance is $" + account.getBalance());
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
                        System.out.print("Enter an account number to transfer to: ");
                        int accountNumber = input.nextInt();
                        System.out.print("Enter an amount to transfer: ");
                        amount = input.nextDouble();
                        Account transferAccount = new Account(accountNumber);
                        transferAccount.getFromFile();
                        transferAccount.deposit(amount);
                        account.withdraw(amount);
                        transferAccount.saveToFile();
                        break;
                    case 5:
                        account.displayAll();
                        account.displayGraph();
                        Waiter.waiter();
                        break;
                    case 6:
                        System.out.print("Enter a new password: ");
                        String newPassword = input.next();
                        account.setPassword(newPassword);
                        System.out.println("Password changed");
                        Waiter.waiter();
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
                        account.setQuestion(newQuestion);
                        account.setAnswer(newAnswer);
                        System.out.println("Question and answer changed");
                        Waiter.waiter();
                        break;
                    case 8:
                        account.saveToFile();
                        System.out.println("Thank you for using our ATM");
                        sleep(1000);
                        return;
                    default:
                        System.out.println("Invalid choice");
                        Waiter.waiter();
                }
            }
        } else {
            System.out.println("Invalid password");
            System.out.println("If you want to reset the password, enter 1");
            System.out.println("If you want to exit, enter 2");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
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
                    } else {
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

    public static void administratorOperation(String username, String password) throws FileNotFoundException, InterruptedException {
        Administrator admin = new Administrator(username);
        admin.getFromFile();
        if (admin.isAdministrator(username, password)) {
            while (true) {
                File numOfAccountsFile = new File("numOfAccounts.txt");
                Scanner numOfAccountsFileInput = new Scanner(numOfAccountsFile);
                int numOfAccounts = numOfAccountsFileInput.nextInt();
                int maxID = numOfAccounts - 1;
                File accountList = new File("AccountList.txt");
                Scanner accountListInput = new Scanner(accountList);
                ArrayList<Integer> accountListArray = new ArrayList<Integer>();
                while (accountListInput.hasNextInt()) {
                    accountListArray.add(accountListInput.nextInt());
                }
                System.out.println("Welcome to the administrator menu, " + username);
                System.out.println("=========Administrator Menu=========");
                System.out.println("1. Create New Account");
                System.out.println("2. Display All Accounts");
                System.out.println("3. Search An Account");
                System.out.println("4. Edit An Account");
                System.out.println("5. Delete An Account");
                System.out.println("6. Change Announcement");
                System.out.println("7. Change Administrator User Name or Password");
                System.out.println("8. Exit");
                System.out.print("Enter a choice: ");
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("=========Create new account=========");
                        System.out.println("Recommended ID: " + (maxID + 1));
                        System.out.print("Enter a new ID: ");
                        int newID = input.nextInt();
                        System.out.print("Enter a balance: ");
                        double newBalance = input.nextDouble();
                        System.out.print("Enter a password: ");
                        String newPassword = input.next();
                        System.out.println("1. What is your favorite color?");
                        System.out.println("2. What is your favorite animal?");
                        System.out.println("3. What is your favorite food?");
                        System.out.println("4. What is your favorite movie?");
                        System.out.println("5. What is your favorite sport?");
                        System.out.print("Choice a question: ");
                        int newQuestion = input.nextInt();
                        System.out.print("Enter an answer: ");
                        String newAnswer = input.next();
                        Account newAccount = new Account(newID, newBalance, newPassword);
                        newAccount.setQuestion(newQuestion);
                        newAccount.setAnswer(newAnswer);
                        newAccount.setDateCreate();
                        maxID++;
                        PrintWriter numOfAccountsFileOutput = new PrintWriter(numOfAccountsFile);
                        numOfAccountsFileOutput.println(numOfAccounts + 1);
                        numOfAccountsFileOutput.close();
                        newAccount.saveToFile();
                        accountListArray.add(newAccount.getUserID());
                        PrintWriter accountListOutput = new PrintWriter(accountList);
                        for (int i = 0; i < accountListArray.size(); i++) {
                            accountListOutput.print(accountListArray.get(i) + " ");
                        }
                        accountListOutput.close();
                        System.out.println("Account created successfully");
                        Waiter.waiter();
                        break;
                    case 2:
                        System.out.println("=========Display all accounts=========");
                        System.out.println("ID Balance Password Creation date Question Answer");
                        System.out.println("-------------------------------------------------");
                        for (int i = 0; i < accountListArray.size(); i++) {
                            Account account = new Account(accountListArray.get(i));
                            account.getFromFile();
                            System.out.println(account.getUserID() + " " + account.getBalance() + " " + account.getPassword() + " " + account.getDateCreate() + " " + account.getQuestion() + " " + account.getAnswer());
                        }
                        Waiter.waiter();
                        break;
                    case 3:
                        System.out.println("=========Search account=========");
                        System.out.print("Enter an ID: ");
                        int searchID = input.nextInt();
                        if (accountListArray.contains(searchID)) {
                            Account searchAccount = new Account(searchID);
                            searchAccount.getFromFile();
                            System.out.println("ID Balance Password Creation date Question Answer");
                            System.out.println("-------------------------------------------------");
                            System.out.println(searchAccount.getUserID() + " " + searchAccount.getBalance() + " " + searchAccount.getDateCreate() + " " + searchAccount.getPassword() + " " + searchAccount.getQuestion() + " " + searchAccount.getAnswer());
                            searchAccount.displayTransactions();
                            Waiter.waiter();
                            break;
                        } else {
                            System.out.println("Account does not exist");
                            Waiter.waiter();
                            break;
                        }
                    case 4:
                        System.out.println("=========Edit account=========");
                        System.out.print("Enter an ID: ");
                        int editID = input.nextInt();
                        if (accountListArray.contains(editID)) {
                            Account editAccount = new Account(editID);
                            editAccount.getFromFile();
                            System.out.println("ID Balance Password Creation date Question Answer");
                            System.out.println("-------------------------------------------------");
                            System.out.println(editAccount.getUserID() + " " + editAccount.getBalance() + " " + editAccount.getDateCreate() + " " + editAccount.getPassword() + " " + editAccount.getQuestion() + " " + editAccount.getAnswer());
                            System.out.println("Edit what? ");
                            System.out.println("1. Balance");
                            System.out.println("2. Password");
                            System.out.println("3. Question");
                            System.out.println("4. Answer");
                            System.out.println("5. All");
                            System.out.println("6. Reset Creation date to current date");
                            System.out.println("7. Back");
                            System.out.print("Enter an choice: ");
                            int editChoice = input.nextInt();
                            switch (editChoice) {
                                case 1:
                                    System.out.print("Enter new balance: ");
                                    int eidtBalance = input.nextInt();
                                    editAccount.setBalance(eidtBalance);
                                    editAccount.saveToFile();
                                    System.out.println("Balance updated");
                                    Waiter.waiter();
                                    break;
                                case 2:
                                    System.out.print("Enter new password: ");
                                    String editPassword = input.next();
                                    editAccount.setPassword(editPassword);
                                    editAccount.saveToFile();
                                    System.out.println("Password updated");
                                    Waiter.waiter();
                                    break;
                                case 3:
                                    System.out.println("1. What is your favorite color?");
                                    System.out.println("2. What is your favorite animal?");
                                    System.out.println("3. What is your favorite food?");
                                    System.out.println("4. What is your favorite movie?");
                                    System.out.println("5. What is your favorite sport?");
                                    System.out.print("Choice new question: ");
                                    int editQuestion = input.nextInt();
                                    editAccount.setQuestion(editQuestion);
                                    editAccount.saveToFile();
                                    System.out.println("Question updated");
                                    Waiter.waiter();
                                    break;
                                case 4:
                                    System.out.print("Enter new answer: ");
                                    String editAnswer = input.next();
                                    editAccount.setAnswer(editAnswer);
                                    editAccount.saveToFile();
                                    System.out.println("Answer updated");
                                    Waiter.waiter();
                                    break;
                                case 5:
                                    System.out.print("Enter new balance: ");
                                    int eidtBalance1 = input.nextInt();
                                    editAccount.setBalance(eidtBalance1);
                                    System.out.print("Enter new password: ");
                                    String editPassword1 = input.next();
                                    editAccount.setPassword(editPassword1);
                                    System.out.println("1. What is your favorite color?");
                                    System.out.println("2. What is your favorite animal?");
                                    System.out.println("3. What is your favorite food?");
                                    System.out.println("4. What is your favorite movie?");
                                    System.out.println("5. What is your favorite sport?");
                                    System.out.print("Choice new question: ");
                                    int editQuestion1 = input.nextInt();
                                    editAccount.setQuestion(editQuestion1);
                                    System.out.print("Enter new answer: ");
                                    String editAnswer1 = input.next();
                                    editAccount.setAnswer(editAnswer1);
                                    editAccount.saveToFile();
                                    System.out.println("All updated");
                                    Waiter.waiter();
                                    break;
                                case 6:
                                    editAccount.setDateCreate();
                                    editAccount.setDateCreate();
                                    editAccount.saveToFile();
                                    System.out.println("Creation date updated");
                                    Waiter.waiter();
                                    break;
                                case 7:
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    Waiter.waiter();
                                    break;

                            }
                            break;
                        }
                    case 5:
                        System.out.println("=========Delete an account=========");
                        System.out.print("Enter an ID: ");
                        int deleteID = input.nextInt();
                        if (accountListArray.contains(deleteID)) {
                            System.out.println("Are you sure you want to delete this account? (Y/N)");
                            String deleteAnswer = input.next();
                            if (deleteAnswer.equals("Y")) {
                                PrintWriter numOfAccountsFileOutput2 = new PrintWriter(numOfAccountsFile);
                                numOfAccountsFileOutput2.println(numOfAccounts - 1);
                                numOfAccountsFileOutput2.close();
                                accountListArray.remove(deleteID);
                                PrintWriter accountListOutput2 = new PrintWriter(accountList);
                                for (int i = 0; i < accountListArray.size(); i++) {
                                    accountListOutput2.print(accountListArray.get(i) + " ");
                                }
                                accountListOutput2.close();
                                File deleteFile = new File("Accounts " + deleteID + ".txt");
                                deleteFile.delete();
                                System.out.println("Account deleted successfully");
                                Waiter.waiter();
                                break;
                            } else {
                                System.out.println("Account not deleted");
                                Waiter.waiter();
                                break;
                            }
                        } else {
                            System.out.println("Account does not exist");
                            Waiter.waiter();
                            break;
                        }
                    case 6:
                        Announcement announcement = new Announcement();
                        System.out.println("=========Change Announcement=========");
                        System.out.print("Now announcement: ");
                        System.out.println(announcement.getAnnouncement());
                        System.out.print("Enter new announcement: ");
                        Scanner announcementInput = new Scanner(System.in);
                        String newAnnouncement = announcementInput.nextLine();
                        announcement.setAnnouncement(newAnnouncement);
                        System.out.println("Announcement changed successfully");
                        Waiter.waiter();
                        break;
                    case 7:
                        System.out.println("=========Change Administrator User Name or Password=========");
                        System.out.println("1. Change Administrator User Name");
                        System.out.println("2. Change Password");
                        System.out.println("3. Back");
                        System.out.print("Enter your choice: ");
                        int changeAdminChoice = input.nextInt();
                        switch (changeAdminChoice) {
                            case 1:
                                System.out.println("=========Change Administrator User Name=========");
                                System.out.print("Enter new user name: ");
                                String newUserName = input.next();
                                admin.setUserName(newUserName);
                                admin.saveToFile();
                                System.out.println("Administrator user name changed successfully");
                                Waiter.waiter();
                                break;
                            case 2:
                                System.out.println("=========Change Administrator Password=========");
                                System.out.print("Enter new password: ");
                                String newPassword1 = input.next();
                                admin.setPassword(newPassword1);
                                admin.saveToFile();
                                System.out.println("Administrator password changed successfully");
                                Waiter.waiter();
                                break;
                            case 3:
                                break;
                        }
                    case 8:
                        System.out.println("Thank you for using");
                        sleep(1000);
                        return;
                }
            }
        } else {
            System.out.println("Wrong Username or Password");
            Waiter.waiter();
            return;
        }
    }

    public static void rootOperation(String password) throws FileNotFoundException, InterruptedException {
        Root root = new Root();
        root.getFromFile();
        if (root.getPassword().equals(password)) {
            while (true) {
                File numOfAdministratorFile = new File("numOfAdministrators.txt");
                Scanner numOfAdministratorFileInput = new Scanner(numOfAdministratorFile);
                int numOfAdministrators = numOfAdministratorFileInput.nextInt();
                File administratorList = new File("AdministratorList.txt");
                Scanner administratorListInput = new Scanner(administratorList);
                ArrayList<String> administratorListArray = new ArrayList<>();
                while (administratorListInput.hasNext()) {
                    administratorListArray.add(administratorListInput.next());
                }
                System.out.println("Welcome to the root menu");
                System.out.println("These operations may lead to destructive errors in the system. Please pay attention!");
                System.out.println("=========Root Menu=========");
                System.out.println("1. Add new Administrator");
                System.out.println("2. Display All Administrators");
                System.out.println("3. Edit Administrators' Information");
                System.out.println("4. Delete Administrator");
                System.out.println("5. Change Root Password");
                System.out.println("6. Clear all data and restore factory settings(Use with care)");
                System.out.println("7. Exit");
                System.out.print("Enter a choice: ");
                Scanner input = new Scanner(System.in);
                int rootChoice = input.nextInt();
                switch (rootChoice) {
                    case 1:
                        System.out.println("=========Add new Administrator=========");
                        System.out.print("Enter new user name: ");
                        String newUserName = input.next();
                        System.out.print("Enter new password: ");
                        String newPassword = input.next();
                        Administrator newAdmin = new Administrator(newUserName, newPassword);
                        newAdmin.saveToFile();
                        System.out.println("New Administrator added successfully");
                        Waiter.waiter();
                        break;
                    case 2:
                        System.out.println("=========Display all accounts=========");
                        System.out.println("User Name Password");
                        System.out.println("------------------");
                        for (int i = 0; i < numOfAdministrators; i++) {
                            Administrator admin = new Administrator(administratorListArray.get(i));
                            admin.getFromFile();
                            System.out.println(admin.getUserName() + " " + admin.getPassword());
                        }
                        Waiter.waiter();
                        break;
                    case 3:
                        System.out.println("=========Edit Administrator's Information=========");
                        System.out.print("Enter user name of the administrator you want to edit: ");
                        String userNameToEdit = input.next();
                        if (administratorListArray.contains(userNameToEdit)) {
                            Administrator adminToEdit = new Administrator(userNameToEdit);
                            adminToEdit.getFromFile();
                            System.out.println("Edit What?");
                            System.out.println("1. User Name");
                            System.out.println("2. Password");
                            System.out.println("3. All");
                            System.out.println("4. Back ");
                            System.out.print("Enter a choice: ");
                            int editChoice = input.nextInt();
                            switch (editChoice) {
                                case 1:
                                    System.out.print("Enter new user name: ");
                                    String newUserNameToEdit = input.next();
                                    adminToEdit.setUserName(newUserNameToEdit);
                                    adminToEdit.saveToFile();
                                    System.out.println("User name changed successfully");
                                    Waiter.waiter();
                                    break;
                                case 2:
                                    System.out.print("Enter new password: ");
                                    String newPasswordToEdit = input.next();
                                    adminToEdit.setPassword(newPasswordToEdit);
                                    adminToEdit.saveToFile();
                                    System.out.println("Password changed successfully");
                                    Waiter.waiter();
                                    break;
                                case 3:
                                    System.out.print("Enter new user name: ");
                                    String newUserNameToEdit1 = input.next();
                                    System.out.print("Enter new password: ");
                                    String newPasswordToEdit1 = input.next();
                                    adminToEdit.setUserName(newUserNameToEdit1);
                                    adminToEdit.setPassword(newPasswordToEdit1);
                                    adminToEdit.saveToFile();
                                    System.out.println("Administrator's information edited successfully");
                                    Waiter.waiter();
                                    break;
                                case 4:
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    Waiter.waiter();
                                    break;
                            }

                        } else {
                            System.out.println("Administrator does not exist");
                        }
                    case 4:
                        System.out.println("=========Delete Administrator=========");
                        System.out.print("Enter user name: ");
                        String userNameToDelete = input.next();
                        if (administratorListArray.contains(userNameToDelete)) {
                            System.out.println("Are you sure you want to delete this administrator? (Y/N)");
                            String deleteAnswer = input.next();
                            if (deleteAnswer.equals("Y")) {
                                administratorListArray.remove(userNameToDelete);
                                PrintWriter numOfAdministratorsFileOutput2 = new PrintWriter(numOfAdministratorFile);
                                numOfAdministratorsFileOutput2.println(numOfAdministrators - 1);
                                numOfAdministratorsFileOutput2.close();
                                administratorListArray.remove(userNameToDelete);
                                PrintWriter accountListOutput2 = new PrintWriter(administratorList);
                                for (int i = 0; i < administratorListArray.size(); i++) {
                                    accountListOutput2.println(administratorListArray.get(i));
                                }
                                accountListOutput2.close();
                                File deleteFile = new File("Administrator " + userNameToDelete + ".txt");
                                deleteFile.delete();
                                System.out.println("Administrator deleted successfully");
                                Waiter.waiter();
                                break;
                            } else {
                                System.out.println("Administrator not deleted");
                                Waiter.waiter();
                                break;
                            }
                        } else {
                            System.out.println("Administrator does not exist");
                            Waiter.waiter();
                            break;
                        }
                    case 5:
                        System.out.println("=========Change Root Password=========");
                        System.out.print("Enter new password: ");
                        String newPassword1 = input.next();
                        root.setPassword(newPassword1);
                        root.saveToFile();
                        System.out.println("Root password changed successfully");
                        Waiter.waiter();
                        break;
                    case 6:
                        System.out.println("=========Clear all data and restore factory settings=========");
                        System.out.println("Are you sure you want to clear all data and restore factory settings? (Y/N)");
                        String clearAnswer = input.next();
                        if (clearAnswer.equals("Y")) {
                            System.out.println("All accounts and administrators will be cleared, and the root password will return to 123456. To continue, please enter \"Yes\"");
                            String clearAnswer2 = input.next();
                            if (clearAnswer2.equals("Yes")) {
                                File accountListF = new File("accountList.txt");
                                File numOfAccountsF = new File("numOfAccounts.txt");
                                File administratorListF = new File("administratorList.txt");
                                File numOfAdministratorsF = new File("numOfAdministrators.txt");
                                Scanner accountLInput = new Scanner(accountListF);
                                Scanner numOfAccountsInput = new Scanner(numOfAccountsF);
                                Scanner administratorLInput = new Scanner(administratorListF);
                                Scanner numOfAdministratorsInput = new Scanner(numOfAdministratorsF);
                                ArrayList<Integer> accounts = new ArrayList<>();
                                ArrayList<String> administrators = new ArrayList<>();
                                while (numOfAccountsInput.hasNextInt()) {
                                    accounts.add(accountLInput.nextInt());
                                }
                                while (numOfAdministratorsInput.hasNext()) {
                                    administrators.add(administratorLInput.next());
                                }
                                for (int i = 0; i < accounts.size(); i++) {
                                    File account = new File("Account " + accounts.get(i) + ".txt");
                                    account.delete();
                                }
                                for (int i = 0; i < administrators.size(); i++) {
                                    File administrator = new File("Administrator " + administrators.get(i) + ".txt");
                                    administrator.delete();
                                }
                                PrintWriter accountWriter = new PrintWriter(accountListF);
                                PrintWriter numOfAccountsWriter = new PrintWriter(numOfAccountsF);
                                PrintWriter administratorWriter = new PrintWriter(administratorListF);
                                PrintWriter numOfAdministratorsWriter = new PrintWriter(numOfAdministratorsF);
                                accountWriter.close();
                                numOfAccountsWriter.println("0");
                                numOfAccountsWriter.close();
                                administratorWriter.close();
                                numOfAdministratorsWriter.println("0");
                                numOfAdministratorsWriter.close();
                                File rootF = new File("root.txt");
                                PrintWriter rootWriter = new PrintWriter(rootF);
                                rootWriter.println("123456");
                                rootWriter.close();
                                System.out.println("All accounts and administrators have been deleted.");
                                System.out.println("The root password has been reset to 123456.");
                                System.out.println("Press enter to exit the system.");
                                Waiter.waiter();
                                System.exit(0);
                            } else {
                                System.out.println("Clear data cancelled");
                                Waiter.waiter();
                                break;
                            }
                        } else {
                            System.out.println("Clear data cancelled");
                            Waiter.waiter();
                            break;
                        }
                    case 7:
                        System.out.println("Thank you for using");
                        sleep(1000);
                        return;
                }
            }

        } else {
            System.out.println("Wrong Root Password");
            Waiter.waiter();
            return;
        }
    }
}