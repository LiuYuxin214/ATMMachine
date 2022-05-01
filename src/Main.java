import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        File accountList = new File("AccountList.txt");
        Scanner accountListInput = new Scanner(accountList);
        ArrayList<Integer> accountListArray = new ArrayList<>();
        while (accountListInput.hasNextInt()) {
            int n = accountListInput.nextInt();
            accountListArray.add(n);
        }
        while (true) {
            System.out.print("Enter an ID (Type \"more\" to see more options): ");
            Scanner input = new Scanner(System.in);
            String enter = input.nextLine();
            if (enter.matches("\\d+")) {
                int id = Integer.parseInt(enter);
                if (accountListArray.contains(id)) {
                    System.out.print("Enter your password: ");
                    String password = input.nextLine();
                    MainMenu.userOperation(id, password);
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
