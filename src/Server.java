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
        ServerLog serverLog = new ServerLog();
        serverLog.add("Server booting up...");
        sleep(1000);
        serverLog.add("Server started at " + new Date());
        serverLog.add("Server IP is " + InetAddress.getLocalHost().getHostAddress());
        serverLog.add("Server is listening on port " + args[0]);
        while (true) {
            try (ServerSocket server = new ServerSocket(Integer.parseInt(args[0]))) {
                serverLog.add("Waiting for client...");
                Socket socket = server.accept();
                serverLog.add("Client Connected");
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                int clientID = in.readInt();
                serverLog.add("Client ID: " + clientID);
                out.writeUTF(new Date().toString());
                serverLog.add("Date&Time sent to client");
                Announcement announcement = new Announcement();
                out.writeUTF(announcement.getAnnouncement());
                serverLog.add("Announcement: " + announcement.getAnnouncement());
                serverLog.add("Announcement sent to client");
                int id = in.readInt();
                if (id == -1) {
                    serverLog.add("Client disconnected");
                    in.close();
                    out.close();
                    socket.close();
                    server.close();
                    continue;
                } else if (id == -2) {
                    serverLog.add("Reset Password");
                    int resetID = in.readInt();
                    Account account = new Account(resetID);
                    account.getFromFile();
                    serverLog.add("Password reset question is: " + account.getQuestion());
                    out.writeUTF(account.getQuestion());
                    String answer = in.readUTF();
                    serverLog.add("User answer: " + answer);
                    serverLog.add("Right answer is: " + account.getAnswer());
                    if (answer.equals(account.getAnswer())) {
                        out.writeBoolean(true);
                        String newPassword = in.readUTF();
                        System.out.print("New password: " + newPassword);
                        account.setPassword(newPassword);
                        account.saveToFile();
                        serverLog.add("Password reset successfully");
                        id = resetID;
                    } else {
                        out.writeBoolean(false);
                        serverLog.add("Incorrect answer");
                        in.close();
                        out.close();
                        socket.close();
                        server.close();
                        continue;
                    }
                }
                serverLog.add("User ID: " + id);
                String password = in.readUTF();
                serverLog.add("Password: " + password);
                serverLog.add("Checking User ID and Password...");
                Account account = new Account(id);
                if (new File("Accounts/" + id + ".txt").exists()) {
                    account.getFromFile();
                    serverLog.add("User exists");
                    if (account.isFrozen()) {
                        serverLog.add("User is frozen");
                        out.writeUTF("User is frozen");
                    } else if (account.getPassword().equals(password)) {
                        serverLog.add("Password Correct");
                        out.writeUTF("Password Correct");
                        account.resetNumOfWrongPassword();
                        account.saveToFile();
                        out.writeUTF(new Date().toString());
                        while (true) {
                            int option = in.readInt();
                            double amount;
                            switch (option) {
                                case 1 -> {
                                    serverLog.add("Client wants to check balance");
                                    serverLog.add("Balance: " + account.getBalance());
                                    out.writeDouble(account.getBalance());
                                }
                                case 2 -> {
                                    serverLog.add("Client wants to withdraw");
                                    amount = in.readDouble();
                                    serverLog.add("Amount: " + amount);
                                    out.writeBoolean(account.withdraw(amount));
                                    account.saveToFile();
                                }
                                case 3 -> {
                                    serverLog.add("Client wants to deposit");
                                    amount = in.readDouble();
                                    serverLog.add("Amount: " + amount);
                                    out.writeBoolean(account.deposit(amount));
                                    account.saveToFile();
                                }
                                case 4 -> {
                                    serverLog.add("Client wants to transfer");
                                    int receiveID = in.readInt();
                                    serverLog.add("Receive ID: " + receiveID);
                                    amount = in.readDouble();
                                    serverLog.add("Amount: " + amount);
                                    out.writeBoolean(account.transfer(receiveID, amount));
                                    account.saveToFile();
                                }
                                case 5 -> {
                                    int displayOption = in.readInt();
                                    switch (displayOption) {
                                        case 2 -> {
                                            serverLog.add("Client wants to display basic information");
                                            out.writeInt(account.getUserID());
                                            out.writeDouble(account.getBalance());
                                            out.writeUTF(account.getDateCreated().toString());
                                        }
                                        case 3 -> {
                                            serverLog.add("Client wants to display transaction history");
                                            out.writeInt(account.transactions.size());
                                            for (int i = 0; i < account.transactions.size(); i++) {
                                                String line = String.format("%-5c%-7.2f%-8.2f%s %s", account.transactions.get(i).getType(), account.transactions.get(i).getAmount(), account.transactions.get(i).getBalance(), account.transactions.get(i).getUpdatedDate().toString(), account.transactions.get(i).getDescription());
                                                out.writeUTF(line);
                                            }
                                        }
                                        case 4 -> {
                                            serverLog.add("Client wants to display proportion chart");
                                            out.writeInt(account.transactions.size());
                                            for (int i = 0; i < account.transactions.size(); i++) {
                                                out.writeChar(account.transactions.get(i).getType());
                                                out.writeDouble(account.transactions.get(i).getAmount());
                                            }
                                        }
                                    }
                                }
                                case 6 -> {
                                    serverLog.add("Client wants to change password");
                                    String newPassword = in.readUTF();
                                    serverLog.add("New password: " + newPassword);
                                    account.setPassword(newPassword);
                                    serverLog.add("Password changed");
                                }
                                case 7 -> {
                                    serverLog.add("Client wants to change question and answer");
                                    account.setQuestion(in.readInt());
                                    account.setAnswer(in.readUTF());
                                    serverLog.add("Question and answer changed");
                                }
                                case 8 -> {
                                    account.saveToFile();
                                    serverLog.add("Client Log out");
                                }
                            }
                            if (option == 8) break;
                        }
                    } else {
                        serverLog.add("Wrong Password");
                        out.writeUTF("Wrong Password");
                        account.addNumOfWrongPassword();
                        serverLog.add("Number of wrong password: " + account.getNumOfWrongPassword());
                        account.saveToFile();
                        if (account.getNumOfWrongPassword() == 3) {
                            serverLog.add("ID: " + id + " will be frozen");
                            account.resetNumOfWrongPassword();
                            account.setFrozen(true);
                            account.saveToFile();
                        }
                    }
                } else {
                    serverLog.add("User not found");
                    out.writeUTF("User not found");
                }
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                serverLog.add("Error: " + e.getMessage());
            }
        }
    }
}
