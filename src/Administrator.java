import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrator {

    private String userName;
    private String password;

    private final ArrayList<Log> logs = new ArrayList<>();

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

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator(String userName, String password) {
        return this.userName.equals(userName) && this.password.equals(password);
    }

    public void addLog(char type, int account) {
        logs.add(new Log(type, account));
    }

    public void disLog() {
        System.out.println("   Logs");
        System.out.println("Type Account");
        System.out.println("------------");
        for (int i = 0; i < logs.size(); i++) {
            System.out.printf("%-5c%d\n", logs.get(i).getType(), logs.get(i).getAccount());
        }
    }

    public void saveToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Administrator " + userName + ".txt");
        writer.print(userName + " ");
        writer.println(password);
        for (int i = 0; i < logs.size(); i++) {
            writer.println(logs.get(i).getType() + " " + logs.get(i).getAccount());
        }
        writer.close();
    }

    public void getFromFile() throws FileNotFoundException {
        File file = new File("Administrator " + userName + ".txt");
        Scanner reader = new Scanner(file);
        userName = reader.next();
        password = reader.next();
        while (reader.hasNext()) {
            logs.add(new Log(reader.next().charAt(0), reader.nextInt()));
        }
        reader.close();
    }
}
