import com.sun.source.tree.SwitchExpressionTree;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Account[] users = new Account[10];
        Boolean logged = false;
        int id = 11;
        Scanner waiter = new Scanner(System.in);
        for(int i = 0; i <= 9; i++) {
            users[i] = new Account(i,0);
        }
        while(true) {
            System.out.print("Enter an ID: ");
            Scanner input = new Scanner(System.in);
            String enter = input.nextLine();
            if(enter.equals("exit")) {
                System.exit(0);
            }
            else if(enter.matches("[0-9]")) {
                id = Integer.parseInt(enter);
            }
            else {
                System.out.println("User not found");
                System.out.println("Press enter to continue");
                waiter.nextLine();
                continue;
            }
            double amount;
            for(int i = 0; i <= 9; i++) {
                if(users[i].getUserID() == id) {
                    logged = true;
                }
            }
            if(logged) {
                while(true) {
                    DisplayMainMenu.show();
                    int choice = input.nextInt();
                    switch(choice) {
                        case 1:
                            System.out.println("The balance is " + users[id].getBalance());
                            System.out.println("Press enter to continue");
                            waiter.nextLine();
                            break;
                        case 2:
                            System.out.print("Enter an amount to withdraw: ");
                            amount = input.nextDouble();
                            users[id].withdraw(amount);
                            break;
                        case 3:
                            System.out.print("Enter an amount to deposit: ");
                            amount = input.nextDouble();
                            users[id].deposit(amount);
                            break;
                        case 4:
                            users[id].displayAll();
                            System.out.println("Press enter to continue");
                            waiter.nextLine();
                            break;
                        case 5:
                            System.out.println("Thank you for using our ATM");
                            System.out.println("Press enter to continue");
                            waiter.nextLine();
                            break;
                        default:
                            System.out.println("Invalid choice");
                            System.out.println("Press enter to continue");
                            waiter.nextLine();
                    }
                    if(choice == 5) {
                        logged = false;
                        break;
                    }
                }
            }
            else{
                System.out.println("User not found");
                System.out.println("Press enter to continue");
                waiter.nextLine();
            }

        }
    }
}
