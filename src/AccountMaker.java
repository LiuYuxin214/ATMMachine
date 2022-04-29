import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class AccountMaker {

    public static void main(String[] args) throws FileNotFoundException {
        Account[] accounts = new Account[10];
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of the file: ");
        int n=sc.nextInt();
        System.out.print("Enter the balance of the file: ");
        double b = sc.nextDouble();
        System.out.print("Enter the password of the file: ");
        String p = sc.next();
        System.out.print("Enter the question of the file: ");
        int q = sc.nextInt();
        System.out.print("Enter the answer of the file: ");
        String a = sc.next();
        for(int i = 0; i < n; i++) {
            accounts[i] = new Account(i,b,p);
            accounts[i].resetDate();
            accounts[i].setAnswer(q,a);
            accounts[i].saveToFile();
        }
        PrintWriter pw = new PrintWriter("numOfAccounts.txt");
        pw.println(n);
        pw.close();
        System.out.println("Accounts created successfully!");
    }
}
