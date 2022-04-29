import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File numOfAccountsFile = new File("numOfAccounts.txt");
        Scanner numOfAccountsFileInput = new Scanner(numOfAccountsFile);
        int maxID = numOfAccountsFileInput.nextInt()-1;
        while (true) {
            System.out.print("Enter an ID (Type \"more\" to see more options): ");
            Scanner input = new Scanner(System.in);
            String enter = input.nextLine();
            if (enter.matches("\\d+")) {
                int id = Integer.parseInt(enter);
                if (id <= maxID) {
                    System.out.print("Enter your password: ");
                    String password = input.nextLine();
                    MainMenu.userOperation(id,password);
                } else {
                    System.out.println("User not found");
                    Waiter.waiter();
                }
            } else if (enter.equals("more")) {
                MoreMenu.moreOperation();
            } else {
                System.out.println("User not found");
                Waiter.waiter();
            }
        }
    }
}
