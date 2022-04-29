import java.util.Scanner;

public class MoreMenu {

    public static void moreOperation() {
        while (true) {
            System.out.println("=========More Menu=========");
            System.out.println("1. Enter administrator menu");
            System.out.println("2. System shutdown");
            System.out.println("3. Developer list");
            System.out.println("4. Version");
            System.out.println("5. Exit the more menu");
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
                    System.out.println("System is shutting down...");
                    System.exit(0);
                    break;
                case 3:
                    System.out.println("Developer: Liu Yuxin");
                    Waiter.waiter();
                    break;
                case 4:
                    System.out.println("Version: 1.0");
                    Waiter.waiter();
                    break;
                case 5:
                    return;
            }
        }
    }
}
