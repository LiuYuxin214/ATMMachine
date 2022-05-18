import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Client ");
        sleep(1000);
        try {
            Socket socket = new Socket("localhost", 20000);
            System.out.println("Client connected to server");
        } catch (IOException e) {
            System.out.println("Cannot connect to server!");
            System.out.println("Please check the Internet connection");
            System.exit(0);
        }
    }
}
