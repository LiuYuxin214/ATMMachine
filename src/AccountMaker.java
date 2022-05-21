import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AccountMaker {

    public static void main(String[] args) throws FileNotFoundException {
        if (!new File("Accounts").exists() || !new File("Accounts").isDirectory()) {
            new File("Accounts").mkdir();
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the Accounts: ");
        int n = input.nextInt();
        System.out.print("Enter the balance of the Accounts: ");
        double balance = input.nextDouble();
        System.out.print("Enter the password of the Accounts: ");
        String password = input.next();
        System.out.println("*==================Question List==================*");
        System.out.println("|--> 1. What is your favorite color?              |");
        System.out.println("|--> 2. What is your favorite animal?             |");
        System.out.println("|--> 3. What is your favorite food?               |");
        System.out.println("|--> 4. What is your favorite movie?              |");
        System.out.println("|--> 5. What is your favorite sport?              |");
        System.out.println("*=================================================*");
        System.out.print("Choice a question of the Accounts: ");
        int question = input.nextInt();
        System.out.print("Enter the answer of the Accounts: ");
        String answer = input.next();
        Account[] accounts = new Account[n];
        for (int i = 0; i < n; i++) {
            accounts[i] = new Account(i, balance, password);
            accounts[i].setDateCreate();
            accounts[i].setQuestion(question);
            accounts[i].setAnswer(answer);
            accounts[i].saveToFile();
        }
        System.out.println("Accounts created successfully!");
    }
}
