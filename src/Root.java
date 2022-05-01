import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Root {

    private String password;

    public Root() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Root.txt");
        writer.println(password);
        writer.close();
    }

    public void getFromFile() throws FileNotFoundException {
        File file = new File("Root.txt");
        Scanner reader = new Scanner(file);
        password = reader.next();
        reader.close();
    }
}
