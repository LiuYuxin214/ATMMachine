import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerLog serverLog = new ServerLog();
        serverLog.add("Server booting up...");
        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
        serverLog.add("Server started at " + new Date());
        serverLog.add("Server IP is " + InetAddress.getLocalHost().getHostAddress());
        serverLog.add("Server is listening on port " + args[0]);
        try {
            while (true) {
                serverLog.add("Waiting for client...");
                Socket socket = server.accept();
                try {
                    new ProcessOneClient(socket, serverLog);
                } catch (IOException e) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            serverLog.add("Fatal error occurred");
            server.close();
            System.exit(1);
        }
    }
}
