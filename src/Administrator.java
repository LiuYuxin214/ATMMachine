import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrator implements AdministratorInterface {

    private final ArrayList<AdministratorOperationLog> administratorOperationLogs = new ArrayList<>();
    private String userName;
    private String password;

    public Administrator() {
    }

    public Administrator(String userName) {
        this.userName = userName;
    }

    public Administrator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addLog(char type, int account) {
        administratorOperationLogs.add(new AdministratorOperationLog(type, account));
    }

    public void disLog() {
        System.out.println("   Logs");
        System.out.println("Type Account");
        System.out.println("------------");
        for (AdministratorOperationLog administratorOperationLog : administratorOperationLogs) {
            System.out.printf("%-5c%d\n", administratorOperationLog.getType(), administratorOperationLog.getAccount());
        }
        System.out.println("------------");
        System.out.println("(C = Create, E = Edit, D = Delete)");
    }


    public void saveToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Administrators/" + userName + ".txt");
        writer.println(userName + " ");
        writer.println(password);
        for (AdministratorOperationLog administratorOperationLog : administratorOperationLogs) {
            writer.println(administratorOperationLog.getType() + " " + administratorOperationLog.getAccount());
        }
        writer.close();
    }

    public void getFromFile() throws FileNotFoundException {
        File file = new File("Administrators/" + userName + ".txt");
        Scanner reader = new Scanner(file);
        userName = reader.next();
        password = reader.next();
        while (reader.hasNext()) {
            administratorOperationLogs.add(new AdministratorOperationLog(reader.next().charAt(0), reader.nextInt()));
        }
        reader.close();
    }
}
