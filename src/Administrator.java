import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Administrator {

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

    public void saveToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Administrator " + userName + ".txt");
        writer.print(userName + " ");
        writer.print(password);
        writer.close();
    }

    public void getFromFile() throws FileNotFoundException {
        File file = new File("Administrator " + userName + ".txt");
        Scanner reader = new Scanner(file);
        userName = reader.next();
        password = reader.next();
        reader.close();
    }
}
