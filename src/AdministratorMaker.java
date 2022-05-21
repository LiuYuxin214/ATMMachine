import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdministratorMaker {

    public static void main(String[] args) throws FileNotFoundException {
        if (!new File("Administrators").exists() || !new File("Administrators").isDirectory()) {
            new File("Administrators").mkdir();
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the Administrators: ");
        int n = input.nextInt();
        System.out.print("Enter the user name of the Administrators: ");
        String userName = input.next();
        System.out.print("Enter the password of the Administrators: ");
        String password = input.next();
        Administrator[] a = new Administrator[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Administrator(userName + i, password);
            a[i].saveToFile();
        }
        System.out.println("Administrators created successfully!");
    }
}
