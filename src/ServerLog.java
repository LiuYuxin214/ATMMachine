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
        System.out.println(dateTime);
        out = new PrintStream("ServerLogs/ServerLog_" + dateTime + ".txt");
    }

    public void add(String message) {
        String line = "[" + new Date() + "] " + message;
        System.out.println(line);
        out.println(line);
    }
}
