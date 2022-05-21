import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public static int numOfUsers = 0;

    public static void main(String[] args) throws IOException {
        //Prepare server
        ServerLog serverLog = new ServerLog();
        serverLog.add("ATM Machine Server Version 2.0");
        serverLog.add("Server booting up...");
        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
        serverLog.add("Server started at " + new Date());
        serverLog.add("Server IP is " + InetAddress.getLocalHost().getHostAddress());
        serverLog.add("Server is listening on port " + args[0]);
        serverLog.add("Waiting for client...");
        //Listen for client
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverLog.add("A new Client Connected");
                    numOfUsers++;
                    serverLog.add("The number of clients is " + numOfUsers);
                    new Thread(new ProcessOneClient(socket, serverLog)).start();
                } catch (IOException e) {
                    socket.close();
                    serverLog.add("Server error occurred, a user's process stopped running");
                    serverLog.add("Error: " + e.getMessage());
                    numOfUsers--;
                }
            }
        } catch (IOException e) {
            serverLog.add("Fatal error occurred, server stopped running");
            serverLog.add("Error: " + e.getMessage());
            server.close();
            System.exit(1);
        }
    }
}
