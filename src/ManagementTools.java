import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ManagementTools {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        management();
    }

    public static void management() throws FileNotFoundException, InterruptedException {
        while (true) {
            System.out.println("=========Management Tools Menu=========");
            System.out.println("1. Enter Administrator Menu");
            System.out.println("2. Enter root Menu");
            System.out.println("3. System Shutdown");
            System.out.println("4. Developer List");
            System.out.println("5. Version");
            System.out.println("6. Exit");
            System.out.print("Enter a choice: ");
            Scanner input = new Scanner(System.in);
            int options = input.nextInt();
            switch (options) {
                case 1:
                    System.out.print("Enter an Administrator User Name: ");
                    String adminUserName = input.next();
                    System.out.print("Enter an Administrator Password: ");
                    String adminPassword = input.next();
                    if (!new File("Administrators/" + adminUserName + ".txt").exists()) {
                        System.out.println("Invalid User Name");
                        sleep(1000);
                        break;
                    }
                    Menu.administratorOperation(adminUserName, adminPassword);
                    break;
                case 2:
                    System.out.print("Enter root password: ");
                    String rootPassword = input.next();
                    Menu.rootOperation(rootPassword);
                    break;
                case 3:
                    File file = new File("Root.txt");
                    Scanner scanner = new Scanner(file);
                    String rootPassword1 = scanner.next();
                    System.out.print("Enter root password: ");
                    String enter = input.next();
                    if (enter.equals(rootPassword1)) {
                        System.out.println("\033[31mAre you sure to shutdown the system? (Y/N)\033[0m");
                        String answer = input.next();
                        if (answer.equals("Y")) {
                            System.out.println("System is shutting down...");
                            System.exit(0);
                        } else {
                            System.out.println("Stop the process of shutting down the system");
                            Waiter.waiter();
                            break;
                        }
                    } else {
                        System.out.println("Invalid Password");
                        Waiter.waiter();
                        break;
                    }
                case 4:
                    System.out.println("=========Developer List=========");
                    System.out.println("1. Liu Yuxin");
                    System.out.println("--------------------------------");
                    Waiter.waiter();
                    break;
                case 5:
                    System.out.println("CPS 2231 Final Project");
                    System.out.println("ATM Machine Version 1.0");
                    Waiter.waiter();
                    break;
                case 6:
                    return;
            }
        }
    }
}
