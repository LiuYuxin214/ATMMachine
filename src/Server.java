import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static java.lang.Thread.sleep;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.print("[" + new Date() + "]");
        System.out.println("Server booting up...");
        sleep(1000);
        System.out.print("[" + new Date() + "]");
        System.out.println("Server started at " + new Date());
        System.out.print("[" + new Date() + "]");
        System.out.println("Server IP is " + InetAddress.getLocalHost().getHostAddress());
        System.out.print("[" + new Date() + "]");
        System.out.println("Server is listening on port " + args[0]);
        while (true) {
            try (ServerSocket server = new ServerSocket(Integer.parseInt(args[0]))) {
                System.out.print("[" + new Date() + "]");
                System.out.println("Waiting for client...");
                Socket socket = server.accept();
                System.out.print("[" + new Date() + "]");
                System.out.println("Client Connected");
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                int clientID = in.readInt();
                System.out.print("[" + new Date() + "]");
                System.out.println("Client ID: " + clientID);
                out.writeUTF(new Date().toString());
                System.out.print("[" + new Date() + "]");
                System.out.println("Date&Time sent to client");
                Announcement announcement = new Announcement();
                out.writeUTF(announcement.getAnnouncement());
                System.out.print("[" + new Date() + "]");
                System.out.println("Announcement: " + announcement.getAnnouncement());
                System.out.print("[" + new Date() + "]");
                System.out.println("Announcement sent to client");
                int id = in.readInt();
                if (id == -1) {
                    System.out.print("[" + new Date() + "]");
                    System.out.println("Client disconnected");
                    in.close();
                    out.close();
                    socket.close();
                    server.close();
                    continue;
                } else if (id == -2) {
                    System.out.print("[" + new Date() + "]");
                    System.out.println("Reset Password");
                    int resetID = in.readInt();
                    Account account = new Account(resetID);
                    account.getFromFile();
                    System.out.print("[" + new Date() + "]");
                    System.out.print("Password reset question is: ");
                    System.out.println(account.getQuestion());
                    out.writeUTF(account.getQuestion());
                    String answer = in.readUTF();
                    System.out.print("[" + new Date() + "]");
                    System.out.println("User answer: " + answer);
                    System.out.print("[" + new Date() + "]");
                    System.out.println("Right answer is: " + account.getAnswer());
                    if (answer.equals(account.getAnswer())) {
                        out.writeBoolean(true);
                        String newPassword = in.readUTF();
                        System.out.print("[" + new Date() + "]");
                        System.out.print("New password: " + newPassword);
                        account.setPassword(newPassword);
                        account.saveToFile();
                        System.out.print("[" + new Date() + "]");
                        System.out.println("Password reset successfully");
                        id = resetID;
                    } else {
                        out.writeBoolean(false);
                        System.out.print("[" + new Date() + "]");
                        System.out.println("Incorrect answer");
                        in.close();
                        out.close();
                        socket.close();
                        server.close();
                        continue;
                    }
                }
                System.out.print("[" + new Date() + "]");
                System.out.println("User ID: " + id);
                String password = in.readUTF();
                System.out.print("[" + new Date() + "]");
                System.out.println("Password: " + password);
                System.out.print("[" + new Date() + "]");
                System.out.println("Checking User ID and Password...");
                Account account = new Account(id);
                if (new File("Accounts/" + id + ".txt").exists()) {
                    account.getFromFile();
                    System.out.print("[" + new Date() + "]");
                    System.out.println("User exists");
                    if (account.isFrozen()) {
                        System.out.print("[" + new Date() + "]");
                        System.out.println("User is frozen");
                        out.writeUTF("User is frozen");
                    } else if (account.getPassword().equals(password)) {
                        System.out.print("[" + new Date() + "]");
                        System.out.println("Password Correct");
                        out.writeUTF("Password Correct");
                        account.resetNumOfWrongPassword();
                        account.saveToFile();
                        out.writeUTF(new Date().toString());
                        while (true) {
                            int option = in.readInt();
                            double amount;
                            switch (option) {
                                case 1 -> {
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Client wants to check balance");
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Balance: " + account.getBalance());
                                    out.writeDouble(account.getBalance());
                                }
                                case 2 -> {
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Client wants to withdraw");
                                    amount = in.readDouble();
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Amount: " + amount);
                                    out.writeBoolean(account.withdraw(amount));
                                    account.saveToFile();
                                }
                                case 3 -> {
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Client wants to deposit");
                                    amount = in.readDouble();
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Amount: " + amount);
                                    out.writeBoolean(account.deposit(amount));
                                    account.saveToFile();
                                }
                                case 4 -> {
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Client wants to transfer");
                                    int receiveID = in.readInt();
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Receive ID: " + receiveID);
                                    amount = in.readDouble();
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Amount: " + amount);
                                    out.writeBoolean(account.transfer(receiveID, amount));
                                    account.saveToFile();
                                }
                                case 5 -> {
                                    int displayOption = in.readInt();
                                    switch (displayOption) {
                                        case 2 -> {
                                            System.out.print("[" + new Date() + "]");
                                            System.out.println("Client wants to display basic information");
                                            out.writeInt(account.getUserID());
                                            out.writeDouble(account.getBalance());
                                            out.writeUTF(account.getDateCreate().toString());
                                        }
                                        case 3 -> {
                                            System.out.print("[" + new Date() + "]");
                                            System.out.println("Client wants to display transaction history");
                                            out.writeInt(account.transactions.size());
                                            for (int i = 0; i < account.transactions.size(); i++) {
                                                String line = String.format("%-5c%-7.2f%-8.2f%s %s", account.transactions.get(i).getType(), account.transactions.get(i).getAmount(), account.transactions.get(i).getBalance(), account.transactions.get(i).getUpdatedDate().toString(), account.transactions.get(i).getDescription());
                                                out.writeUTF(line);
                                            }
                                        }
                                        case 4 -> {
                                            System.out.print("[" + new Date() + "]");
                                            System.out.println("Client wants to display proportion chart");
                                            out.writeInt(account.transactions.size());
                                            for (int i = 0; i < account.transactions.size(); i++) {
                                                out.writeChar(account.transactions.get(i).getType());
                                                out.writeDouble(account.transactions.get(i).getAmount());
                                            }
                                        }
                                    }
                                }
                                case 6 -> {
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Client wants to change password");
                                    String newPassword = in.readUTF();
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("New password: " + newPassword);
                                    account.setPassword(newPassword);
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Password changed");
                                }
                                case 7 -> {
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Client wants to change question and answer");
                                    account.setQuestion(in.readInt());
                                    account.setAnswer(in.readUTF());
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Question and answer changed");
                                }
                                case 8 -> {
                                    account.saveToFile();
                                    System.out.print("[" + new Date() + "]");
                                    System.out.println("Client Log out");
                                }
                            }
                            if (option == 8) break;
                        }
                    } else {
                        System.out.print("[" + new Date() + "]");
                        System.out.println("Wrong Password");
                        out.writeUTF("Wrong Password");
                        account.addNumOfWrongPassword();
                        System.out.print("[" + new Date() + "]");
                        System.out.println("Number of wrong password: " + account.getNumOfWrongPassword());
                        account.saveToFile();
                        if (account.getNumOfWrongPassword() == 3) {
                            System.out.print("[" + new Date() + "]");
                            System.out.println("ID: " + id + " will be frozen");
                            account.resetNumOfWrongPassword();
                            account.setFrozen(true);
                            account.saveToFile();
                        }
                    }
                } else {
                    System.out.print("[" + new Date() + "]");
                    System.out.println("User not found");
                    out.writeUTF("User not found");
                }
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                System.out.print("[" + new Date() + "]");
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
