import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        while (true) {
            ServerSocket server = new ServerSocket(20000);
            Socket socket = server.accept();
            System.out.println("Client Connected");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            int id = in.readInt();
            System.out.println("Client ID: " + id);


        }
    }
}
