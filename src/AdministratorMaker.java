import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AdministratorMaker {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the file: ");
        int n = input.nextInt();
        System.out.print("Enter the password of the file: ");
        String password = input.next();
        Administrator[] a = new Administrator[n];
        PrintWriter pw = new PrintWriter("AdministratorList.txt");
        for (int i = 0; i < n; i++) {
            a[i] = new Administrator(i + "", password);
            a[i].saveToFile();
            pw.println(a[i].getUserName() + " ");
        }
        pw.close();
        PrintWriter pw2 = new PrintWriter("numOfAdministrators.txt");
        pw2.print(n);
        pw2.close();
        System.out.println("Administrators created successfully!");
    }
}
