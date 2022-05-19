import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

public class ServerLog {
    PrintStream out;

    ServerLog() throws FileNotFoundException {
        out = new PrintStream("ServerLog.txt");
    }

    public void add(String message) {
        String line = "[" + new Date() + "] " + message;
        System.out.println(line);
        out.println(line);
    }
}
