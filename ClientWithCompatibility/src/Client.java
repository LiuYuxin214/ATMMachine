import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Client {

    public static DataInputStream in;
    public static DataOutputStream out;

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        String ip = "localhost";
        int port = 20000;
        int clientID = 0;
        PrintWriter settingPrintWriter;
        try {
            File file = new File("ClientSetting.txt");
            Scanner scanner = new Scanner(file);
            ip = scanner.next();
            port = scanner.nextInt();
            clientID = scanner.nextInt();
        } catch (FileNotFoundException e) {
            System.out.println("Client Setting lost!");
            System.out.println("Restored to default settings");
            try (PrintWriter resetPrintWriter = new PrintWriter("ClientSetting.txt")) {
                resetPrintWriter.println("localhost");
                resetPrintWriter.println("20000");
                resetPrintWriter.print("0");
            } catch (FileNotFoundException e1) {
                System.out.println("Cannot create ClientSetting.txt");
                System.out.println("Please check the file permission or wait a few seconds and try again.");
                System.out.println("This program will exit now.");
                sleep(5000);
                System.exit(0);
            }
        }
        while (true) {
            System.out.println("\033[31m===================Not connected===================\033[0m");
            System.out.println("Welcome to ATM Machine!");
            System.out.println("Press enter to Connect");
            System.out.println("Type \"e\" then press enter to exit.");
            System.out.println("Type \"s\" then press enter to setting.");
            Scanner waiter = new Scanner(System.in);
            String function = waiter.nextLine();
            if (function.equals("e")) {
                System.out.println("Closing...");
                System.exit(0);
            }
            if (function.equals("s")) {
                while (true) {
                    System.out.println("=================Setting=================");
                    System.out.println("1.Change the server's IP address");
                    System.out.println("2.Change the server's port");
                    System.out.println("3.Change the client's ID");
                    System.out.println("4.Show the current setting");
                    System.out.println("5.Back");
                    System.out.println("==========================================");
                    System.out.print("Enter a choice: ");
                    Scanner setting = new Scanner(System.in);
                    int option = setting.nextInt();
                    switch (option) {
                        case 1:
                            System.out.print("Please enter the server's IP address: ");
                            ip = setting.next();
                            settingPrintWriter = new PrintWriter("ClientSetting.txt");
                            settingPrintWriter.println(ip);
                            settingPrintWriter.println(port);
                            settingPrintWriter.print(clientID);
                            settingPrintWriter.close();
                            System.out.println("IP address changed!");
                            sleep(1000);
                            break;
                        case 2:
                            System.out.print("Please enter the server's port: ");
                            port = setting.nextInt();
                            settingPrintWriter = new PrintWriter("ClientSetting.txt");
                            settingPrintWriter.println(ip);
                            settingPrintWriter.println(port);
                            settingPrintWriter.print(clientID);
                            settingPrintWriter.close();
                            System.out.println("Port changed!");
                            sleep(1000);
                            break;
                        case 3:
                            System.out.print("Please enter the client's ID: ");
                            clientID = setting.nextInt();
                            settingPrintWriter = new PrintWriter("ClientSetting.txt");
                            settingPrintWriter.println(ip);
                            settingPrintWriter.println(port);
                            settingPrintWriter.print(clientID);
                            settingPrintWriter.close();
                            System.out.println("Client ID changed!");
                            sleep(1000);
                            break;
                        case 4:
                            System.out.println("=================Current Setting=================");
                            System.out.println("Server's IP address: " + ip);
                            System.out.println("Server's port: " + port);
                            System.out.println("Client's ID: " + clientID);
                            System.out.println("=================================================");
                            Waiter.waiter();
                            break;
                        case 5:
                            break;
                    }
                    if (option == 5) {
                        break;
                    }
                }
                continue;
            }
            System.out.print("Connecting to server");
            for (int i = 0; i < 3; i++) {
                sleep(500);
                System.out.print(".");
            }
            System.out.println();
            try (Socket socket = new Socket(ip, port)) {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                out.writeInt(clientID);
                String dateTime = in.readUTF();
                String announcement = in.readUTF();
                System.out.println("Connected to server");
                Scanner scanner = new Scanner(System.in);
                System.out.println("\033[33m===================Not logged in===================\033[0m");
                System.out.println("Welcome to ATM!");
                System.out.println("Server Time&Date: " + dateTime);
                System.out.println("Announcement: " + announcement);
                System.out.println("If you forget your password, type \"f\".");
                System.out.println("If you want to disconnect from server, type \"d\".");
                System.out.print("Enter an ID: ");
                String enter = scanner.next();
                int id = 0;
                String password;
                boolean reset = false;
                if (enter.equals("d")) {
                    System.out.print("Disconnecting");
                    for (int i = 0; i < 3; i++) {
                        sleep(500);
                        System.out.print(".");
                    }
                    System.out.println();
                    out.writeInt(-1);
                    in.close();
                    out.close();
                    socket.close();
                    System.out.println("Disconnected from server.");
                    sleep(1000);
                    continue;
                } else if (enter.equals("f")) {
                    out.writeInt(-2);
                    Scanner resetPasswordSc = new Scanner(System.in);
                    System.out.print("Enter the ID that you forget password: ");
                    int resetID = resetPasswordSc.nextInt();
                    out.writeInt(resetID);
                    System.out.print("Your password reset question is: ");
                    System.out.println(in.readUTF());
                    System.out.print("Enter the answer to your password reset question: ");
                    String answer = resetPasswordSc.next();
                    out.writeUTF(answer);
                    if (in.readBoolean()) {
                        System.out.print("Enter a new password: ");
                        String newPassword = resetPasswordSc.next();
                        out.writeUTF(newPassword);
                        System.out.print("Password reset successfully, auto login in 3 seconds");
                        for (int i = 0; i < 3; i++) {
                            sleep(1000);
                            System.out.print(".");
                        }
                        System.out.println();
                        id = resetID;
                        reset = true;
                        password = newPassword;
                        out.writeUTF(password);
                    } else {
                        System.out.println("Incorrect answer");
                        sleep(1000);
                        in.close();
                        out.close();
                        socket.close();
                        continue;
                    }
                }
                if (!reset) {
                    if (!enter.matches("[0-9]*")) {
                        System.out.println("Invalid ID, ID must be number.");
                        sleep(1000);
                        out.writeInt(-1);
                        in.close();
                        out.close();
                        socket.close();
                        continue;
                    }
                    id = Integer.parseInt(enter);
                    System.out.print("Enter your password: ");
                    password = scanner.next();
                    System.out.println("Sending ID and password to server...");
                    if (id < 0) {
                        out.writeInt(-1);
                        in.close();
                        out.close();
                        socket.close();
                        System.out.println("User not found");
                        sleep(1000);
                        continue;
                    }
                    out.writeInt(id);
                    out.writeUTF(password);
                    sleep(1000);
                }
                String result = in.readUTF();
                switch (result) {
                    case "Password Correct":
                        System.out.println("Password Correct");
                        dateTime = in.readUTF();
                        while (true) {
                            System.out.println("\033[32m=====================Logged in=====================\033[0m");
                            System.out.println("Welcome to our ATM, " + id);
                            System.out.print("Announcement: ");
                            System.out.println(announcement);
                            System.out.print("Login Date&Time: ");
                            System.out.println(dateTime);
                            System.out.println("==========Main Menu==========");
                            System.out.println("-------Basic Functions-------");
                            System.out.println("1. Check balance");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Deposit");
                            System.out.println("4. Transfer");
                            System.out.println("---------View Details--------");
                            System.out.println("5. Display...");
                            System.out.println("-----------Security----------");
                            System.out.println("6. Change Password");
                            System.out.println("7. Change Question and Answer");
                            System.out.println("------------Exit-------------");
                            System.out.println("8. Exit");
                            System.out.println("=============================");
                            System.out.print("Enter a choice: ");
                            Scanner input = new Scanner(System.in);
                            double amount;
                            int option = input.nextInt();
                            switch (option) {
                                case 1:
                                    out.writeInt(1);
                                    double balance = in.readDouble();
                                    System.out.println("The balance is $" + balance);
                                    Waiter.waiter();
                                    break;
                                case 2:
                                    out.writeInt(2);
                                    System.out.print("Enter an amount to withdraw: ");
                                    amount = input.nextDouble();
                                    out.writeDouble(amount);
                                    if (in.readBoolean()) {
                                        System.out.println("Withdrawal successful");
                                    } else {
                                        System.out.println("Withdrawal failed");
                                    }
                                    sleep(1000);
                                    break;
                                case 3:
                                    out.writeInt(3);
                                    System.out.print("Enter an amount to deposit: ");
                                    amount = input.nextDouble();
                                    out.writeDouble(amount);
                                    if (in.readBoolean()) {
                                        System.out.println("Deposit successful");
                                    } else {
                                        System.out.println("Deposit failed");
                                    }
                                    sleep(1000);
                                    break;
                                case 4:
                                    out.writeInt(4);
                                    System.out.print("Enter an account number to transfer to: ");
                                    int receiveID = input.nextInt();
                                    System.out.print("Enter an amount to transfer: ");
                                    amount = input.nextDouble();
                                    out.writeInt(receiveID);
                                    out.writeDouble(amount);
                                    if (in.readBoolean()) {
                                        System.out.println("Transfer successful");
                                    } else {
                                        System.out.println("Transfer failed");
                                    }
                                    sleep(1000);
                                    break;
                                case 5:
                                    System.out.println("1. Display All");
                                    System.out.println("2. Display Basic Information");
                                    System.out.println("3. Display Transaction");
                                    System.out.println("4. Display Proportion Chart of Deposits and Withdrawals");
                                    System.out.println("5. Back");
                                    System.out.print("Enter a choice: ");
                                    int option2 = input.nextInt();
                                    switch (option2) {
                                        case 1:
                                            display2();
                                            display3();
                                            display4();
                                            Waiter.waiter();
                                            break;
                                        case 2:
                                            display2();
                                            Waiter.waiter();
                                            break;
                                        case 3:
                                            display3();
                                            Waiter.waiter();
                                            break;
                                        case 4:
                                            display4();
                                            Waiter.waiter();
                                            break;
                                        case 5:
                                            break;
                                        default:
                                            System.out.println("Invalid choice");
                                            sleep(1000);
                                            break;
                                    }
                                    break;
                                case 6:
                                    out.writeInt(6);
                                    System.out.print("Enter a new password: ");
                                    out.writeUTF(input.next());
                                    System.out.println("Password changed");
                                    sleep(1000);
                                    break;
                                case 7:
                                    out.writeInt(7);
                                    System.out.println("1. What is your favorite color?");
                                    System.out.println("2. What is your favorite animal?");
                                    System.out.println("3. What is your favorite food?");
                                    System.out.println("4. What is your favorite movie?");
                                    System.out.println("5. What is your favorite sport?");
                                    System.out.print("Enter a new question: ");
                                    int newQuestion = input.nextInt();
                                    System.out.print("Enter a new answer: ");
                                    String newAnswer = input.next();
                                    out.writeInt(newQuestion);
                                    out.writeUTF(newAnswer);
                                    System.out.println("Question and answer changed");
                                    sleep(1000);
                                    break;
                                case 8:
                                    out.writeInt(8);
                                    System.out.println("Thank you for using our ATM");
                                    sleep(1000);
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    sleep(1000);
                                    break;
                            }
                            if (option == 8) break;
                        }
                        break;
                    case "Wrong Password":
                        System.out.println("Wrong Password");
                        sleep(1000);
                        break;
                    case "User not found":
                        System.out.println("User not found");
                        sleep(1000);
                        break;
                    case "User is frozen":
                        System.out.println("User is frozen");
                        sleep(1000);
                        break;
                }
                in.close();
                out.close();
            } catch (IOException e) {
                System.out.println("Cannot connect to server!");
                System.out.println("Please check the Internet connection or wait a few seconds and try again.");
                Waiter.waiter();
            }
        }
    }

    public static void display2() {
        try {
            out.writeInt(5);
            out.writeInt(2);
            System.out.println("User ID: " + in.readInt());
            System.out.println("Balance: $" + in.readDouble());
            System.out.println("Date created: " + in.readUTF());
        } catch (IOException e) {
            System.out.println("Cannot connect to server!");
            System.out.println("Please check the Internet connection or wait a few seconds and try again.");
            Waiter.waiter();
        }
    }

    public static void display3() {
        try {
            out.writeInt(5);
            out.writeInt(3);
            System.out.println("                        Transactions");
            System.out.println("Type Amount Balance Date                         Description");
            System.out.println("------------------------------------------------------------");
            int number = in.readInt();
            for (int i = 0; i < number; i++) {
                System.out.println(in.readUTF());
            }
            System.out.println("------------------------------------------------------------");
            System.out.println("(D = Deposit, W = Withdraw, T = Transfer)");
        } catch (IOException e) {
            System.out.println("Cannot connect to server!");
            System.out.println("Please check the Internet connection or wait a few seconds and try again.");
            Waiter.waiter();
        }
    }

    public static void display4() {
        try {
            out.writeInt(5);
            out.writeInt(4);
            int n = in.readInt();
            double sumOfD = 0, sumOfW = 0;
            for (int i = 0; i < n; i++) {
                char c = in.readChar();
                double a = in.readDouble();
                if (c == 'D') {
                    sumOfD += a;
                } else if (c == 'W' || c == 'T') {
                    sumOfW += a;
                }
            }
            System.out.println("Total deposits: $" + sumOfD + "              Total withdrawals: $" + sumOfW);
            double sum = sumOfD + sumOfW;
            System.out.printf("Proportion of deposits: %.2f%%     Proportion of withdrawal: %.2f%%\n", (sumOfD / sum) * 100, (sumOfW / sum) * 100);
            int d = (int) ((sumOfD / sum) * 100);
            int w = (int) ((sumOfW / sum) * 100);
            System.out.println("Proportion chart of deposits and withdrawals: ");
            System.out.println(" 0%       10%       20%       30%       40%       50%       60%       70%       80%       90%     100%");
            System.out.println("(|    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |)");
            System.out.print("D|");
            while (d > 0) {
                System.out.print("#");
                d--;
            }
            System.out.println();
            System.out.print("W|");
            while (w > 0) {
                System.out.print("#");
                w--;
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Cannot connect to server!");
            System.out.println("Please check the Internet connection or wait a few seconds and try again.");
            Waiter.waiter();
        }
    }

}
