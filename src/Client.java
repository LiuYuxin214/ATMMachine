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
            System.out.println("\033[31mClient Setting lost!\033[0m");
            System.out.println("Restored to default settings");
            try (PrintWriter resetPrintWriter = new PrintWriter("ClientSetting.txt")) {
                resetPrintWriter.println("localhost");
                resetPrintWriter.println("20000");
                resetPrintWriter.print("0");
            } catch (FileNotFoundException e1) {
                System.out.println("\033[31mCannot create ClientSetting.txt\033[0m");
                System.out.println("Please check the file permission or wait a few seconds and try again.");
                System.out.println("This program will exit now.");
                sleep(5000);
                System.exit(0);
            }
        }
        while (true) {
            System.out.println("\033[31m===================Not connected===================\033[0m");
            System.out.println("              \033[1mWelcome to ATM Machine!\033[0m");
            System.out.println("\033[5mPress enter to connect\033[0m");
            System.out.println("Type \033[31me\033[0m then press enter to exit.");
            System.out.println("Type \033[34ms\033[0m then press enter to setting.");
            Scanner waiter = new Scanner(System.in);
            String function = waiter.nextLine();
            if (function.equals("e")) {
                System.out.println("\033[31mClosing...\033[0m");
                System.exit(0);
            }
            if (function.equals("s")) {
                while (true) {
                    System.out.println("\033[1m=================Setting=================\033[0m");
                    System.out.println("1.Change the server's IP address");
                    System.out.println("2.Change the server's port");
                    System.out.println("3.Change the client's ID");
                    System.out.println("4.Show the current setting");
                    System.out.println("5.Back");
                    System.out.println("\033[1m==========================================\033[0m");
                    System.out.print("\033[1mEnter a choice: \033[0m");
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
                            System.out.println("\033[32mIP address changed!\033[0m");
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
                            System.out.println("\033[32mPort changed!\033[0m");
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
                            System.out.println("\033[32mClient ID changed!\033[0m");
                            sleep(1000);
                            break;
                        case 4:
                            System.out.println("\033[1m=================Current Setting=================\033[0m");
                            System.out.println("Server's IP address: " + ip);
                            System.out.println("Server's port: " + port);
                            System.out.println("Client's ID: " + clientID);
                            System.out.println("\033[1m=================================================\033[0m");
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
            System.out.print("\033[32mConnecting to server\033[0m");
            for (int i = 0; i < 3; i++) {
                sleep(500);
                System.out.print("\033[32m.\033[0m");
            }
            System.out.println();
            try (Socket socket = new Socket(ip, port)) {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                out.writeInt(clientID);
                String dateTime = in.readUTF();
                String announcement = in.readUTF();
                System.out.println("\033[32mConnected to server\033[0m");
                Scanner scanner = new Scanner(System.in);
                System.out.println("\033[33m===================Not logged in===================\033[0m");
                System.out.println("                  \033[5mWelcome to ATM!\033[0m");
                System.out.println("\033[1mServer Time&Date: \033[36m" + dateTime + "\033[0m");
                System.out.println("\033[1mAnnouncement: \033[35m" + announcement + "\033[0m");
                System.out.println("\033[33m===================================================\033[0m");
                System.out.println("If you forget your password, type \033[33mf\033[0m.");
                System.out.println("If you want to disconnect from server, type \033[31md\033[0m.");
                System.out.print("\033[1mEnter an ID: \033[0m");
                String enter = scanner.next();
                int id = 0;
                String password;
                boolean reset = false;
                if (enter.equals("d")) {
                    System.out.print("\033[31mDisconnecting\033[0m");
                    for (int i = 0; i < 3; i++) {
                        sleep(500);
                        System.out.print("\033[31m.\033[0m");
                    }
                    System.out.println();
                    out.writeInt(-1);
                    in.close();
                    out.close();
                    socket.close();
                    System.out.println("\033[31mDisconnected from server\033[0m");
                    sleep(1000);
                    continue;
                } else if (enter.equals("f")) {
                    out.writeInt(-2);
                    Scanner resetPasswordSc = new Scanner(System.in);
                    System.out.print("\033[1mEnter the ID that you forget password: \033[0m");
                    int resetID = resetPasswordSc.nextInt();
                    out.writeInt(resetID);
                    System.out.println("Your password reset question is: ");
                    System.out.println(in.readUTF());
                    System.out.print("\033[1mEnter the answer to your password reset question: \033[0m");
                    String answer = resetPasswordSc.next();
                    out.writeUTF(answer);
                    if (in.readBoolean()) {
                        System.out.print("\033[1mEnter a new password: \033[0m");
                        String newPassword = resetPasswordSc.next();
                        out.writeUTF(newPassword);
                        System.out.print("\033[32mPassword reset successfully, auto login in 3 seconds\033[0m");
                        for (int i = 0; i < 3; i++) {
                            sleep(1000);
                            System.out.print("\033[32m.\033[0m");
                        }
                        System.out.println();
                        id = resetID;
                        reset = true;
                        password = newPassword;
                        out.writeUTF(password);
                    } else {
                        System.out.println("\033[31mIncorrect answer\033[0m");
                        sleep(1000);
                        in.close();
                        out.close();
                        socket.close();
                        continue;
                    }
                }
                if (!reset) {
                    if (!enter.matches("[0-9]*")) {
                        System.out.println("\033[31mInvalid ID, ID must be number.\033[0m");
                        sleep(1000);
                        out.writeInt(-1);
                        in.close();
                        out.close();
                        socket.close();
                        continue;
                    }
                    id = Integer.parseInt(enter);
                    System.out.print("\033[1mEnter your password: \033[0m");
                    password = scanner.next();
                    System.out.println("\033[36mSending ID and password to server...\033[0m");
                    if (id < 0) {
                        out.writeInt(-1);
                        in.close();
                        out.close();
                        socket.close();
                        System.out.println("\033[31mUser not found\033[0m");
                        sleep(1000);
                        continue;
                    }
                    out.writeInt(id);
                    out.writeUTF(password);
                    sleep(1000);
                }
                String result = in.readUTF();
                switch (result) {
                    case "Password Correct" -> {
                        System.out.println("\033[32mPassword Correct\033[0m");
                        dateTime = in.readUTF();
                        while (true) {
                            System.out.println("\033[32m=====================Logged in=====================\033[0m");
                            System.out.println("              \033[1mWelcome to our ATM, " + id + "\033[0m");
                            System.out.println("\033[1mAnnouncement: \033[35m" + announcement + "\033[0m");
                            System.out.println("\033[1mLogin Date&Time: \033[36m" + dateTime + "\033[0m");
                            System.out.println("\033[1m==========Main Menu==========\033[0m");
                            System.out.println("\033[33m-------Basic Functions-------\033[0m");
                            System.out.println("1. Check balance");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Deposit");
                            System.out.println("4. Transfer");
                            System.out.println("\033[34m---------View Details--------\033[0m");
                            System.out.println("5. Display...");
                            System.out.println("\033[32m-----------Security----------\033[0m");
                            System.out.println("6. Change Password");
                            System.out.println("7. Change Question and Answer");
                            System.out.println("\033[31m------------Exit-------------\033[0m");
                            System.out.println("8. Exit");
                            System.out.println("\033[1m=============================\033[0m");
                            System.out.print("\033[1mEnter a choice: \033[0m");
                            Scanner input = new Scanner(System.in);
                            double amount;
                            int option = input.nextInt();
                            switch (option) {
                                case 1 -> {
                                    out.writeInt(1);
                                    double balance = in.readDouble();
                                    System.out.println("\033[1mThe balance is \033[32m$" + balance + "\033[0m");
                                    Waiter.waiter();
                                }
                                case 2 -> {
                                    out.writeInt(2);
                                    System.out.print("\033[1mEnter an amount to withdraw: \033[0m");
                                    amount = input.nextDouble();
                                    out.writeDouble(amount);
                                    if (in.readBoolean()) {
                                        System.out.println("\033[32mWithdrawal successful\033[0m");
                                    } else {
                                        System.out.println("\033[31mWithdrawal failed\033[0m");
                                    }
                                    sleep(1000);
                                }
                                case 3 -> {
                                    out.writeInt(3);
                                    System.out.print("\033[1mEnter an amount to deposit: \033[0m");
                                    amount = input.nextDouble();
                                    out.writeDouble(amount);
                                    if (in.readBoolean()) {
                                        System.out.println("\033[32mDeposit successful\033[0m");
                                    } else {
                                        System.out.println("\033[31mDeposit failed\033[0m");
                                    }
                                    sleep(1000);
                                }
                                case 4 -> {
                                    out.writeInt(4);
                                    System.out.print("\033[1mEnter an account number to transfer to: \033[0m");
                                    int receiveID = input.nextInt();
                                    System.out.print("\033[1mEnter an amount to transfer: \033[0m");
                                    amount = input.nextDouble();
                                    out.writeInt(receiveID);
                                    out.writeDouble(amount);
                                    if (in.readBoolean()) {
                                        System.out.println("\033[32mTransfer successful\033[0m");
                                    } else {
                                        System.out.println("\033[31mTransfer failed\033[0m");
                                    }
                                    sleep(1000);
                                }
                                case 5 -> {
                                    System.out.println("\033[34m+++++++++++Display+++++++++++\033[0m");
                                    System.out.println("1. Display All");
                                    System.out.println("2. Display Basic Information");
                                    System.out.println("3. Display Transaction");
                                    System.out.println("4. Display Revenue and expenditure analysis");
                                    System.out.println("5. Back");
                                    System.out.println("\033[34m+++++++++++++++++++++++++++++\033[0m");
                                    System.out.print("\033[1mEnter a choice: \033[0m");
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
                                            System.out.println("\033[31mInvalid choice\033[0m");
                                            sleep(1000);
                                            break;
                                    }
                                }
                                case 6 -> {
                                    out.writeInt(6);
                                    System.out.print("\033[1mEnter a new password: \033[0m");
                                    out.writeUTF(input.next());
                                    System.out.println("\033[32mPassword changed\033[0m");
                                    sleep(1000);
                                }
                                case 7 -> {
                                    out.writeInt(7);
                                    System.out.println("1. What is your favorite color?");
                                    System.out.println("2. What is your favorite animal?");
                                    System.out.println("3. What is your favorite food?");
                                    System.out.println("4. What is your favorite movie?");
                                    System.out.println("5. What is your favorite sport?");
                                    System.out.print("\033[1mChoice a new question: \033[0m");
                                    int newQuestion = input.nextInt();
                                    System.out.print("\033[1mEnter a new answer: \033[0m");
                                    String newAnswer = input.next();
                                    out.writeInt(newQuestion);
                                    out.writeUTF(newAnswer);
                                    System.out.println("\033[32mQuestion and answer changed\033[0m");
                                    sleep(1000);
                                }
                                case 8 -> {
                                    out.writeInt(8);
                                    System.out.println("\033[5mThank you for using our ATM\033[0m");
                                    sleep(3000);
                                }
                                default -> {
                                    System.out.println("\033[31mInvalid choice\033[0m");
                                    sleep(1000);
                                }
                            }
                            if (option == 8) break;
                        }
                    }
                    case "Wrong Password" -> {
                        System.out.println("\033[31mWrong Password\033[0m");
                        sleep(1000);
                    }
                    case "User not found" -> {
                        System.out.println("\033[31mUser not found\033[0m");
                        sleep(1000);
                    }
                    case "User is frozen" -> {
                        System.out.println("\033[31mUser is frozen, please contact the staff\033[0m");
                        sleep(1000);
                    }
                }
                in.close();
                out.close();
            } catch (IOException e) {
                System.out.println("\033[31mCannot connect to server!\033[0m");
                System.out.println("Please check the Internet connection or wait a few seconds and try again.");
                Waiter.waiter();
            }
        }
    }

    public static void display2() {
        try {
            out.writeInt(5);
            out.writeInt(2);
            System.out.println("\033[34m+++++++++++Basic Information+++++++++++\033[0m");
            System.out.println("User ID: " + in.readInt());
            System.out.println("Balance: $" + in.readDouble());
            System.out.println("Date created: " + in.readUTF());
            System.out.println("\033[34m+++++++++++++++++++++++++++++++++++++++\033[0m");
        } catch (IOException e) {
            System.out.println("\033[31mCannot connect to server!");
            System.out.println("Please check the Internet connection or wait a few seconds and try again.");
            Waiter.waiter();
        }
    }

    public static void display3() {
        try {
            out.writeInt(5);
            out.writeInt(3);
            System.out.println("\033[1m                        Transactions\033[0m");
            System.out.println("\033[1mType Amount Balance Date                         Description\033[0m");
            System.out.println("------------------------------------------------------------");
            int number = in.readInt();
            for (int i = 0; i < number; i++) {
                System.out.println(in.readUTF());
            }
            System.out.println("------------------------------------------------------------");
            System.out.println("(\033[32mD\033[0m = Deposit, \033[31mW\033[0m = Withdraw, \033[33mT\033[0m = Transfer)");
        } catch (IOException e) {
            System.out.println("\033[31mCannot connect to server!");
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
            System.out.println("Total deposits: \033[32m$" + sumOfD + "\033[0m" + "              Total withdrawals: \033[31m$" + sumOfW + "\033[0m");
            double sum = sumOfD + sumOfW;
            System.out.printf("Proportion of deposits: \033[32m%.2f%%\033[0m     Proportion of withdrawal: \033[31m%.2f%%\033[0m\n", (sumOfD / sum) * 100, (sumOfW / sum) * 100);
            int d = (int) ((sumOfD / sum) * 100);
            int w = (int) ((sumOfW / sum) * 100);
            System.out.println("\033[1m 0%       10%       20%       30%       40%       50%       60%       70%       80%       90%     100%\033[0m");
            System.out.println("\033[1m(|    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |)\033[0m");
            System.out.print("\033[1mD|\033[0m");
            while (d > 0) {
                System.out.print("\033[32m#\033[0m");
                d--;
            }
            System.out.println();
            System.out.print("\033[1mW|\033[0m");
            while (w > 0) {
                System.out.print("\033[31m#\033[0m");
                w--;
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("\033[31mCannot connect to server!");
            System.out.println("Please check the Internet connection or wait a few seconds and try again.");
            Waiter.waiter();
        }
    }
}
