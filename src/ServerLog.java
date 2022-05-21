import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

public class ServerLog {
    PrintStream out;

    ServerLog() throws FileNotFoundException {
        if (!new File("ServerLogs").exists() || !new File("ServerLogs").isDirectory()) {
            new File("ServerLogs").mkdir();
        }
        String dateTime = new Date().toString().replaceAll(" ", "_").replaceAll(":", "-");
        out = new PrintStream("ServerLogs/ServerLog_" + dateTime + ".txt");
    }

    public void add(String message) {
        String line = "[" + new Date() + "] " + message;
        System.out.println(line);
        out.println(line);
    }

    public void addClient(int clientID, String message) {
        String line = "[" + new Date() + "] " + "Client " + clientID + ": " + message;
        System.out.println(line);
        out.println(line);
    }

    public void addClientUser(int clientID, int id, String message) {
        String line = "[" + new Date() + "] " + "Client " + clientID + " User " + id + ": " + message;
        System.out.println(line);
        out.println(line);
    }
}
