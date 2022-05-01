import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AccountMaker {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the file: ");
        int n = input.nextInt();
        System.out.print("Enter the balance of the file: ");
        double balance = input.nextDouble();
        System.out.print("Enter the password of the file: ");
        String password = input.next();
        System.out.print("Enter the question of the file: ");
        int question = input.nextInt();
        System.out.print("Enter the answer of the file: ");
        String answer = input.next();
        Account[] accounts = new Account[n];
        PrintWriter pw = new PrintWriter("AccountList.txt");
        for (int i = 0; i < n; i++) {
            accounts[i] = new Account(i, balance, password);
            accounts[i].setDateCreate();
            accounts[i].setQuestion(question);
            accounts[i].setAnswer(answer);
            accounts[i].saveToFile();
            pw.print(accounts[i].getUserID() + " ");
        }
        pw.close();
        PrintWriter pw2 = new PrintWriter("numOfAccounts.txt");
        pw2.print(n);
        pw2.close();
        System.out.println("Accounts created successfully!");
    }
}
