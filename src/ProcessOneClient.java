import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ProcessOneClient implements Runnable {
    ServerLog serverLog;
    State state;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private int clientID;
    private int id;

    public ProcessOneClient(Socket socket, ServerLog Log) throws IOException {
        try {
            this.socket = socket;
            this.serverLog = Log;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            serverLog.add("The connection to the client failed");
            serverLog.add("Error: " + e.getMessage());
        }
    }

    public void run() {
        try {
            clientID = in.readInt();
            out.writeUTF(new Date().toString());
            serverLog.addClient(clientID, "Date&Time sent to client");
            Announcement announcement = new Announcement();
            out.writeUTF(announcement.getAnnouncement());
            serverLog.addClient(clientID, "Announcement: " + announcement.getAnnouncement());
            serverLog.addClient(clientID, "Announcement sent to client");
            id = in.readInt();
            if (id == -1) {
                serverLog.addClient(clientID, "Client actively disconnects");
                in.close();
                out.close();
                socket.close();
                Server.numOfUsers--;
                return;
            } else if (id == -2) {
                int resetID = in.readInt();
                serverLog.addClientUser(clientID, resetID, "Reset Password");
                Account account = new Account(resetID);
                account.getFromFile();
                serverLog.addClientUser(clientID, resetID, "Password reset question is: " + account.getQuestion());
                out.writeUTF(account.getQuestion());
                String answer = in.readUTF();
                serverLog.addClientUser(clientID, resetID, "User answer: " + answer);
                serverLog.addClientUser(clientID, resetID, "Right answer is: " + account.getAnswer());
                if (answer.equals(account.getAnswer())) {
                    out.writeBoolean(true);
                    String newPassword = in.readUTF();
                    serverLog.addClientUser(clientID, resetID, "New password: " + newPassword);
                    account.setPassword(newPassword);
                    account.saveToFile();
                    serverLog.addClientUser(clientID, resetID, "Password reset successfully");
                    id = resetID;
                } else {
                    out.writeBoolean(false);
                    serverLog.addClientUser(clientID, resetID, "Incorrect answer");
                    in.close();
                    out.close();
                    socket.close();
                    return;
                }
            }
            serverLog.addClientUser(clientID, id, "User ID: " + id);
            String password = in.readUTF();
            serverLog.addClientUser(clientID, id, "Password: " + password);
            serverLog.addClientUser(clientID, id, "Checking User ID and Password...");
            Account account = new Account(id);
            if (new File("Accounts/" + id + ".txt").exists()) {
                account.getFromFile();
                serverLog.addClientUser(clientID, id, "User " + id + " exists");
                if (account.isFrozen()) {
                    serverLog.addClientUser(clientID, id, "User " + id + " is frozen");
                    out.writeUTF("User is frozen");
                } else if (account.getPassword().equals(password)) {
                    serverLog.addClientUser(clientID, id, "User " + id + " Password Correct");
                    out.writeUTF("Password Correct");
                    account.resetNumOfWrongPassword();
                    account.saveToFile();
                    out.writeUTF(new Date().toString());
                    while (true) {
                        int option = in.readInt();
                        double amount;
                        switch (option) {
                            case 1 -> {
                                serverLog.addClientUser(clientID, id, "Request: check Balance" + " Balance: " + account.getBalance());
                                out.writeDouble(account.getBalance());
                            }
                            case 2 -> {
                                amount = in.readDouble();
                                serverLog.addClientUser(clientID, id, "Request: withdraw" + " Amount: " + amount);
                                state = account.withdraw(amount);
                                if (state.getResult()) {
                                    serverLog.addClientUser(clientID, id, "Withdraw successfully");
                                    out.writeBoolean(state.getResult());
                                    out.writeUTF(state.getMessage());
                                } else {
                                    serverLog.addClientUser(clientID, id, "Withdraw failed: " + state.getMessage());
                                    out.writeBoolean(state.getResult());
                                    out.writeUTF("\033[31m" + state.getMessage() + "\033[0m");
                                }
                                account.saveToFile();
                            }
                            case 3 -> {
                                amount = in.readDouble();
                                serverLog.addClientUser(clientID, id, "Request: deposit" + " Amount: " + amount);
                                state = account.deposit(amount);
                                if (state.getResult()) {
                                    serverLog.addClientUser(clientID, id, "Deposit successfully");
                                    out.writeBoolean(state.getResult());
                                    out.writeUTF(state.getMessage());
                                } else {
                                    serverLog.addClientUser(clientID, id, "Deposit failed: " + state.getMessage());
                                    out.writeBoolean(state.getResult());
                                    out.writeUTF("\033[31m" + state.getMessage() + "\033[0m");
                                }
                                account.saveToFile();
                            }
                            case 4 -> {
                                int receiveID = in.readInt();
                                amount = in.readDouble();
                                serverLog.addClientUser(clientID, id, "Request: transfer" + " Amount: " + amount + " Receive ID: " + receiveID);
                                state = account.transfer(receiveID, amount);
                                if (state.getResult()) {
                                    serverLog.addClientUser(clientID, id, "Transfer successfully");
                                    out.writeBoolean(state.getResult());
                                    out.writeUTF(state.getMessage());
                                } else {
                                    serverLog.addClientUser(clientID, id, "Transfer failed: " + state.getMessage());
                                    out.writeBoolean(state.getResult());
                                    out.writeUTF("\033[31m" + state.getMessage() + "\033[0m");
                                }
                                account.saveToFile();
                            }
                            case 5 -> {
                                int displayOption = in.readInt();
                                switch (displayOption) {
                                    case 2 -> {
                                        serverLog.addClientUser(clientID, id, "Request: display basic information");
                                        out.writeInt(account.getUserID());
                                        out.writeDouble(account.getBalance());
                                        out.writeUTF(account.getDateCreated().toString());
                                    }
                                    case 3 -> {
                                        serverLog.addClientUser(clientID, id, "Request: display transaction history");
                                        out.writeInt(account.transactions.size());
                                        for (int i = 0; i < account.transactions.size(); i++) {
                                            if (account.transactions.get(i).getType() == 'D') {
                                                String line = String.format("\033[32m%-5c%-7.2f%-8.2f%s %s\033[0m", account.transactions.get(i).getType(), account.transactions.get(i).getAmount(), account.transactions.get(i).getBalance(), account.transactions.get(i).getUpdatedDate().toString(), account.transactions.get(i).getDescription());
                                                out.writeUTF(line);
                                            } else if (account.transactions.get(i).getType() == 'W') {
                                                String line = String.format("\033[31m%-5c%-7.2f%-8.2f%s %s\033[0m", account.transactions.get(i).getType(), account.transactions.get(i).getAmount(), account.transactions.get(i).getBalance(), account.transactions.get(i).getUpdatedDate().toString(), account.transactions.get(i).getDescription());
                                                out.writeUTF(line);
                                            } else if (account.transactions.get(i).getType() == 'T') {
                                                String line = String.format("\033[33m%-5c%-7.2f%-8.2f%s %s\033[0m", account.transactions.get(i).getType(), account.transactions.get(i).getAmount(), account.transactions.get(i).getBalance(), account.transactions.get(i).getUpdatedDate().toString(), account.transactions.get(i).getDescription());
                                                out.writeUTF(line);
                                            }
                                        }
                                    }
                                    case 4 -> {
                                        serverLog.addClientUser(clientID, id, "Request: display proportion chart");
                                        out.writeInt(account.transactions.size());
                                        for (int i = 0; i < account.transactions.size(); i++) {
                                            out.writeChar(account.transactions.get(i).getType());
                                            out.writeDouble(account.transactions.get(i).getAmount());
                                        }
                                    }
                                }
                            }
                            case 6 -> {
                                serverLog.addClientUser(clientID, id, "Request: change password");
                                String newPassword = in.readUTF();
                                serverLog.addClientUser(clientID, id, "New password: " + newPassword);
                                account.setPassword(newPassword);
                                serverLog.addClientUser(clientID, id, "Password changed");
                            }
                            case 7 -> {
                                serverLog.addClientUser(clientID, id, "Request: change question and answer");
                                account.setQuestion(in.readInt());
                                account.setAnswer(in.readUTF());
                                serverLog.addClientUser(clientID, id, "Question and answer changed");
                            }
                            case 8 -> {
                                account.saveToFile();
                                serverLog.addClientUser(clientID, id, "Client logs out");
                            }
                        }
                        if (option == 8) break;
                    }
                } else {
                    serverLog.addClientUser(clientID, id, "Wrong Password");
                    out.writeUTF("Wrong Password");
                    account.addNumOfWrongPassword();
                    serverLog.addClientUser(clientID, id, "Number of wrong password: " + account.getNumOfWrongPassword());
                    account.saveToFile();
                    if (account.getNumOfWrongPassword() == 3) {
                        serverLog.addClientUser(clientID, id, "ID: " + id + " will be frozen");
                        account.resetNumOfWrongPassword();
                        account.setFrozen(true);
                        account.saveToFile();
                    }
                }
            } else {
                serverLog.addClientUser(clientID, id, "User not found");
                out.writeUTF("User not found");
            }
            in.close();
            out.close();
            socket.close();
            Server.numOfUsers--;
            serverLog.addClientUser(clientID, id, "The client is disconnected normally");
        } catch (IOException e) {
            try {
                in.close();
                out.close();
                socket.close();
                Server.numOfUsers--;
                serverLog.addClientUser(clientID, id, "The client was disconnected unexpectedly");
                serverLog.addClientUser(clientID, id, "Error: " + e.getMessage());
            } catch (IOException e1) {
                Server.numOfUsers--;
                serverLog.addClientUser(clientID, id, "Failed to close socket");
                serverLog.addClientUser(clientID, id, "Error: " + e1.getMessage());
            }
        }
    }
}
