import java.io.FileNotFoundException;
import java.util.Scanner;

public class MoreMenu {

    public static void moreOperation() throws FileNotFoundException {
        while (true) {
            System.out.println("=========More Menu=========");
            System.out.println("1. Enter Administrator Menu");
            System.out.println("2. Enter root Menu");
            System.out.println("3. System Shutdown");
            System.out.println("4. Developer List");
            System.out.println("5. Version");
            System.out.println("6. Back");
            System.out.print("Enter a choice: ");
            Scanner input = new Scanner(System.in);
            int options = input.nextInt();
            switch (options) {
                case 1:
                    System.out.print("Enter an Administrator User Name: ");
                    String adminUserName = input.next();
                    System.out.print("Enter an Administrator Password: ");
                    String adminPassword = input.next();
                    MainMenu.adminOperation(adminUserName, adminPassword);
                    break;
                case 2:
                    System.out.print("Enter root password: ");
                    String rootPassword = input.next();
                    MainMenu.rootOperation(rootPassword);
                    break;
                case 3:
                    System.out.println("System is shutting down...");
                    System.exit(0);
                    break;
                case 4:
                    System.out.println("=========Developer List=========");
                    System.out.println("1. Liu Yuxin");
                    System.out.println("--------------------------------");
                    Waiter.waiter();
                    break;
                case 5:
                    System.out.println("Version: 1.0");
                    Waiter.waiter();
                    break;
                case 6:
                    break;
            }
        }
    }
}
