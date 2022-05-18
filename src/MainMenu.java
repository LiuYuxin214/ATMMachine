import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class MainMenu {

    public static void userOperation(int id, String password) throws FileNotFoundException, InterruptedException {

        Account account = new Account(id);
        account.getFromFile();
        Announcement announcement = new Announcement();
        if (account.isFreeze()) {
            System.out.println("Your account is frozen. Please contact the administrator.");
            Waiter.waiter();
            return;
        }
        if (account.getPassword().equals(password)) {
            account.resetNumOfWrongPassword();
            while (true) {
                System.out.println("Welcome to our ATM, " + account.getUserID());
                System.out.print("Announcement: ");
                System.out.println(announcement.getAnnouncement());
                System.out.print("Date&Time: ");
                Date CurrentDate = new Date();
                System.out.println(CurrentDate);
                System.out.println();
                System.out.println("==========Main Menu==========");
                System.out.println("-------Basic Functions-------");
                System.out.println("1. Check balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("---------View Details--------");
                System.out.println("5. Display...");
                System.out.println("-----------Security----------");
                System.out.println("6. Change Password");
                System.out.println("7. Change Question and Answer");
                System.out.println("------------Exit-------------");
                System.out.println("8. Exit");
                System.out.println("=============================");
                System.out.print("Enter a choice: ");
                Scanner input = new Scanner(System.in);
                double amount;
                int option = input.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.println("The balance is $" + account.getBalance());
                        Waiter.waiter();
                    }
                    case 2 -> {
                        System.out.print("Enter an amount to withdraw: ");
                        amount = input.nextDouble();
                        account.withdraw(amount);
                        Waiter.waiter();
                    }
                    case 3 -> {
                        System.out.print("Enter an amount to deposit: ");
                        amount = input.nextDouble();
                        account.deposit(amount);
                        Waiter.waiter();
                    }
                    case 4 -> {
                        System.out.print("Enter an account number to transfer to: ");
                        int accountNumber = input.nextInt();
                        System.out.print("Enter an amount to transfer: ");
                        amount = input.nextDouble();
                        account.transfer(accountNumber, amount);
                        Waiter.waiter();
                    }
                    case 5 -> {
                        System.out.println("1. Display All");
                        System.out.println("2. Display Basic Information");
                        System.out.println("3. Display Transaction");
                        System.out.println("4. Display Proportion Chart of Deposits and Withdrawals");
                        System.out.println("5. Back");
                        System.out.print("Enter a choice: ");
                        int option2 = input.nextInt();
                        switch (option2) {
                            case 1:
                                account.displayAll();
                                Waiter.waiter();
                                break;
                            case 2:
                                account.displayBasicInformation();
                                Waiter.waiter();
                                break;
                            case 3:
                                account.displayTransactions();
                                Waiter.waiter();
                                break;
                            case 4:
                                account.displayProportionChart();
                                Waiter.waiter();
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Invalid choice");
                                sleep(1000);
                                break;
                        }
                    }
                    case 6 -> {
                        System.out.print("Enter a new password: ");
                        String newPassword = input.next();
                        account.setPassword(newPassword);
                        System.out.println("Password changed");
                        sleep(1000);
                    }
                    case 7 -> {
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
                        sleep(1000);
                    }
                    case 8 -> {
                        account.saveToFile();
                        System.out.println("Thank you for using our ATM");
                        sleep(1000);
                        return;
                    }
                    default -> {
                        System.out.println("Invalid choice");
                        sleep(1000);
                    }
                }
            }
        } else {
            System.out.println("Invalid password");
            account.addNumOfWrongPassword();
            account.saveToFile();
            if (account.getNumOfWrongPassword() == 3) {
                account.setFreeze(true);
                account.resetNumOfWrongPassword();
                account.saveToFile();
                System.out.println("You have entered the wrong password 3 times. Your account has been locked.");
                sleep(3000);
            } else {
                System.out.println("If you want to reset the password, enter 1");
                System.out.println("If you want to exit, enter 2");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.println("Your password reset question is: ");
                        System.out.println(account.getQuestion());
                        System.out.print("Enter the answer to your password reset question: ");
                        String answer = sc.next();
                        if (answer.equals(account.getAnswer())) {
                            System.out.print("Enter a new password: ");
                            String newPassword = sc.next();
                            account.setPassword(newPassword);
                            account.saveToFile();
                            System.out.println("Password reset successfully, please login again.");
                        } else {
                            System.out.println("Incorrect answer");
                        }
                        sleep(3000);
                    }
                    case 2 -> {
                        System.out.println("Thank you for using our ATM");
                        sleep(1000);
                    }
                }
            }
        }
    }

    public static void administratorOperation(String username, String password) throws FileNotFoundException, InterruptedException {
        Administrator admin = new Administrator(username);
        admin.getFromFile();
        if (admin.getPassword().equals(password)) {
            while (true) {
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
                        int newID;
                        while (true) {
                            System.out.print("Enter a new ID: ");
                            newID = input.nextInt();
                            if (new File("Accounts/" + newID + ".txt").exists()) {
                                System.out.println("ID already exists");
                            } else {
                                break;
                            }
                        }
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
                        newAccount.saveToFile();
                        admin.addLog('C', newAccount.getUserID());
                        System.out.println("Account created successfully");
                        sleep(1000);
                        break;
                    case 2:
                        System.out.println("=======================================Display all accounts=======================================");
                        System.out.println("ID Balance Password Creation date                 Question                           Answer Freeze");
                        System.out.println("--------------------------------------------------------------------------------------------------");
                        File[] accountFiles = new File("Accounts").listFiles();
                        assert accountFiles != null;
                        for (File accountFile : accountFiles) {
                            int id = Integer.parseInt(accountFile.getName().substring(0, accountFile.getName().length() - 4));
                            Account account = new Account(id);
                            account.getFromFile();
                            System.out.printf("%-3d%-8.2f%-9s%-28s  %s       %-7s%-6b\n", account.getUserID(), account.getBalance(), account.getPassword(), account.getDateCreate().toString(), account.getQuestion(), account.getAnswer(), account.isFreeze());
                        }
                        System.out.println("--------------------------------------------------------------------------------------------------");
                        Waiter.waiter();
                        break;
                    case 3:
                        System.out.println("=========Search account=========");
                        System.out.print("Enter an ID: ");
                        int searchID = input.nextInt();
                        if (new File("Accounts/" + searchID + ".txt").exists()) {
                            Account searchAccount = new Account(searchID);
                            searchAccount.getFromFile();
                            System.out.println("ID Balance Password Creation date                 Question                           Answer Freeze");
                            System.out.println("--------------------------------------------------------------------------------------------------");
                            System.out.printf("%-3d%-8.2f%-9s%-28s  %s       %-7s%-6b\n", searchAccount.getUserID(), searchAccount.getBalance(), searchAccount.getPassword(), searchAccount.getDateCreate().toString(), searchAccount.getQuestion(), searchAccount.getAnswer(), searchAccount.isFreeze());
                            searchAccount.displayTransactions();
                        } else {
                            System.out.println("Account does not exist");
                        }
                        Waiter.waiter();
                        break;
                    case 4:
                        System.out.println("=========Edit account=========");
                        System.out.print("Enter an ID: ");
                        int editID = input.nextInt();
                        if (new File("Accounts/" + editID + ".txt").exists()) {
                            Account editAccount = new Account(editID);
                            editAccount.getFromFile();
                            System.out.println("ID Balance Password Creation date                 Question                           Answer Freeze");
                            System.out.println("--------------------------------------------------------------------------------------------------");
                            System.out.printf("%-3d%-8.2f%-9s%-28s  %s       %-7s%-6b\n", editAccount.getUserID(), editAccount.getBalance(), editAccount.getPassword(), editAccount.getDateCreate().toString(), editAccount.getQuestion(), editAccount.getAnswer(), editAccount.isFreeze());
                            System.out.println("Edit what? ");
                            System.out.println("1. Balance");
                            System.out.println("2. Password");
                            System.out.println("3. Question");
                            System.out.println("4. Answer");
                            System.out.println("5. All");
                            System.out.println("6. Reset Creation date to current date");
                            System.out.println("7. Freeze/Unfreeze account");
                            System.out.println("8. Back");
                            System.out.print("Enter an choice: ");
                            int editChoice = input.nextInt();
                            switch (editChoice) {
                                case 1:
                                    System.out.print("Enter new balance: ");
                                    int editBalance = input.nextInt();
                                    editAccount.setBalance(editBalance);
                                    editAccount.saveToFile();
                                    System.out.println("Balance updated");
                                    sleep(1000);
                                    break;
                                case 2:
                                    System.out.print("Enter new password: ");
                                    String editPassword = input.next();
                                    editAccount.setPassword(editPassword);
                                    editAccount.saveToFile();
                                    System.out.println("Password updated");
                                    sleep(1000);
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
                                    sleep(1000);
                                    break;
                                case 4:
                                    System.out.print("Enter new answer: ");
                                    String editAnswer = input.next();
                                    editAccount.setAnswer(editAnswer);
                                    editAccount.saveToFile();
                                    System.out.println("Answer updated");
                                    sleep(1000);
                                    break;
                                case 5:
                                    System.out.print("Enter new balance: ");
                                    int editBalance1 = input.nextInt();
                                    editAccount.setBalance(editBalance1);
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
                                    sleep(1000);
                                    break;
                                case 6:
                                    editAccount.setDateCreate();
                                    editAccount.setDateCreate();
                                    editAccount.saveToFile();
                                    System.out.println("Creation date updated");
                                    sleep(1000);
                                    break;
                                case 7:
                                    System.out.println("1. Freeze this account");
                                    System.out.println("2. Unfreeze this account");
                                    System.out.println("3. Back");
                                    System.out.print("Choice: ");
                                    switch (input.nextInt()) {
                                        case 1:
                                            editAccount.setFreeze(true);
                                            editAccount.saveToFile();
                                            System.out.println("Account frozen");
                                            sleep(1000);
                                            break;
                                        case 2:
                                            editAccount.setFreeze(false);
                                            editAccount.saveToFile();
                                            System.out.println("Account unfrozen");
                                            sleep(1000);
                                            break;
                                        case 3:
                                            break;
                                        default:
                                            System.out.println("Invalid choice");
                                            sleep(1000);
                                            break;
                                    }
                                    break;
                                case 8:
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    sleep(1000);
                                    break;

                            }
                            admin.addLog('E', editAccount.getUserID());
                            break;
                        }
                    case 5:
                        System.out.println("=========Delete an account=========");
                        System.out.print("Enter an ID: ");
                        int deleteID = input.nextInt();
                        if (new File("Accounts/" + deleteID + ".txt").exists()) {
                            System.out.println("Are you sure you want to delete this account? (Y/N)");
                            String deleteAnswer = input.next();
                            if (deleteAnswer.equals("Y")) {
                                File deleteFile = new File("Accounts/" + deleteID + ".txt");
                                if (deleteFile.delete()) {
                                    admin.addLog('D', deleteID);
                                    System.out.println("Account deleted successfully");
                                } else {
                                    System.out.println("Account deletion failed");
                                }
                            } else {
                                System.out.println("Account not deleted");
                            }
                        } else {
                            System.out.println("Account does not exist");
                        }
                        sleep(1000);
                        break;
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
                        sleep(1000);
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
                                sleep(1000);
                                break;
                            case 2:
                                System.out.println("=========Change Administrator Password=========");
                                System.out.print("Enter new password: ");
                                String newPassword1 = input.next();
                                admin.setPassword(newPassword1);
                                admin.saveToFile();
                                System.out.println("Administrator password changed successfully");
                                sleep(1000);
                                break;
                            case 3:
                                break;
                        }
                    case 8:
                        admin.saveToFile();
                        System.out.println("Thank you for using");
                        sleep(1000);
                        return;
                }
            }
        } else {
            System.out.println("Wrong Password");
            sleep(1000);
        }
    }

    public static void rootOperation(String password) throws FileNotFoundException, InterruptedException {
        Root root = new Root();
        root.getFromFile();
        if (root.getPassword().equals(password)) {
            while (true) {
                System.out.println("Welcome to the root menu");
                System.out.println("These operations may lead to destructive errors in the system. Please pay attention!");
                System.out.println("=========Root Menu=========");
                System.out.println("1. Add new Administrator");
                System.out.println("2. Display All Administrators");
                System.out.println("3. Display Logs");
                System.out.println("4. Edit Administrators' Information");
                System.out.println("5. Delete Administrator");
                System.out.println("6. Change Root Password");
                System.out.println("7. Clear all data and restore factory settings(Use with care)");
                System.out.println("8. Exit");
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
                        sleep(1000);
                        break;
                    case 2:
                        System.out.println("=========Display all accounts=========");
                        System.out.println("User Name            Password");
                        System.out.println("--------------------------------------");
                        File[] adminFiles = new File("Administrators").listFiles();
                        assert adminFiles != null;
                        for (File adminFile : adminFiles) {
                            Administrator admin = new Administrator(adminFile.getName().substring(0, adminFile.getName().length() - 4));
                            admin.getFromFile();
                            System.out.printf("%-21s%s\n", admin.getUserName(), admin.getPassword());
                        }
                        System.out.println("--------------------------------------");
                        Waiter.waiter();
                        break;
                    case 3:
                        System.out.println("=============Display Logs=============");
                        System.out.print("Enter the administrator's user name: ");
                        String adminUserName = input.next();
                        if (new File("Administrators/" + adminUserName + ".txt").exists()) {
                            Administrator adminLog = new Administrator(adminUserName);
                            adminLog.getFromFile();
                            adminLog.disLog();
                            Waiter.waiter();
                        } else {
                            System.out.println("Administrator does not exist");
                            sleep(1000);
                        }

                        break;
                    case 4:
                        System.out.println("=========Edit Administrator's Information=========");
                        System.out.print("Enter user name of the administrator you want to edit: ");
                        String userNameToEdit = input.next();
                        if (new File("Administrators/" + userNameToEdit + ".txt").exists()) {
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
                                    sleep(1000);
                                    break;
                                case 2:
                                    System.out.print("Enter new password: ");
                                    String newPasswordToEdit = input.next();
                                    adminToEdit.setPassword(newPasswordToEdit);
                                    adminToEdit.saveToFile();
                                    System.out.println("Password changed successfully");
                                    sleep(1000);
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
                                    sleep(1000);
                                    break;
                                case 4:
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    sleep(1000);
                                    break;
                            }

                        } else {
                            System.out.println("Administrator does not exist");
                            sleep(1000);
                        }
                    case 5:
                        System.out.println("=========Delete Administrator=========");
                        System.out.print("Enter user name: ");
                        String userNameToDelete = input.next();
                        if (new File("Administrators/" + userNameToDelete + ".txt").exists()) {
                            System.out.println("Are you sure you want to delete this administrator? (Y/N)");
                            String deleteAnswer = input.next();
                            if (deleteAnswer.equals("Y")) {
                                File deleteFile = new File("Administrators/" + userNameToDelete + ".txt");
                                if (deleteFile.delete()) {
                                    System.out.println("Administrator deleted successfully");
                                    sleep(1000);
                                } else {
                                    System.out.println("Administrator cannot be deleted");
                                    sleep(3000);
                                }
                            } else {
                                System.out.println("Administrator not deleted");
                                sleep(1000);
                            }
                        } else {
                            System.out.println("Administrator does not exist");
                            sleep(1000);
                        }
                        Waiter.waiter();
                        break;
                    case 6:
                        System.out.println("=========Change Root Password=========");
                        System.out.print("Enter new password: ");
                        String newPassword1 = input.next();
                        root.setPassword(newPassword1);
                        root.saveToFile();
                        System.out.println("Root password changed successfully");
                        sleep(1000);
                        break;
                    case 7:
                        System.out.println("=========Clear all data and restore factory settings=========");
                        System.out.println("Are you sure you want to clear all data and restore factory settings? (Y/N)");
                        String clearAnswer = input.next();
                        if (clearAnswer.equals("Y")) {
                            System.out.println("All accounts and administrators will be cleared, and the root password will return to 123456. To continue, please enter \"Yes\"");
                            String clearAnswer2 = input.next();
                            if (clearAnswer2.equals("Yes")) {
                                File[] files = new File("Administrators").listFiles();
                                assert files != null;
                                for (File file : files) {
                                    if (!file.delete())
                                        System.out.println("Could not delete file: " + file.getName());
                                }
                                File[] files2 = new File("Accounts").listFiles();
                                assert files2 != null;
                                for (File file : files2) {
                                    if (file.delete())
                                        System.out.println("Deleted file: " + file.getName());
                                }
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
                    case 8:
                        System.out.println("Thank you for using");
                        sleep(1000);
                        return;
                }
            }

        } else {
            System.out.println("Wrong Root Password");
            sleep(1000);
        }
    }
}