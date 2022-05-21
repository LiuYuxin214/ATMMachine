import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Account implements AccountInterface {
    ArrayList<Transaction> transactions = new ArrayList<>();
    private Date dateCreated;
    private int userID = 0;
    private double balance = 0;
    private String password;
    private int question;
    private String answer;
    private boolean isFreeze = false;
    private int numOfWrongPassword = 0;

    public Account() {
    }

    public Account(int userID) {
        this.userID = userID;
    }

    public Account(int userID, double balance, String password) {
        this.userID = userID;
        this.balance = balance;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return switch (question) {
            case 1 -> "What is your favorite color?";
            case 2 -> "What is your favorite animal?";
            case 3 -> "What is your favorite food?";
            case 4 -> "What is your favorite movie?";
            case 5 -> "What is your favorite sport?";
            default -> "";
        };
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreate() {
        dateCreated = new Date();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public boolean isFrozen() {
        return isFreeze;
    }

    public void setFrozen(boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public int getNumOfWrongPassword() {
        return numOfWrongPassword;
    }

    public void addNumOfWrongPassword() {
        this.numOfWrongPassword++;
    }

    public void resetNumOfWrongPassword() {
        this.numOfWrongPassword = 0;
    }

    public State withdraw(double amount) {
        if (amount <= 0) {
            return new State(false, "Invalid amount");
        } else if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction('W', amount, balance, "Withdraw"));
            return new State(true, "Now, The balance is \033[32m$" + getBalance() + "");
        } else {
            return new State(false, "Insufficient funds");
        }
    }

    public State deposit(double amount) {
        if (amount <= 0) {
            return new State(false, "Invalid amount");
        } else {
            balance += amount;
            transactions.add(new Transaction('D', amount, balance, "Deposit"));
            return new State(true, "Now, The balance is \033[32m$" + getBalance() + "");
        }
    }

    public State transfer(int userID, double amount) throws FileNotFoundException {
        if (new File("Accounts/" + userID + ".txt").exists()) {
            if (amount <= 0) {
                return new State(false, "Invalid amount");
            } else if (balance >= amount) {
                balance -= amount;
                transactions.add(new Transaction('T', amount, balance, "To" + userID));
                Account target = new Account(userID);
                target.getFromFile();
                target.receiveTransfer(getUserID(), amount);
                target.saveToFile();
                return new State(true, "Now, The balance is \033[32m$" + getBalance() + "");
            } else if (userID == getUserID()) {
                return new State(false, "You can't transfer to yourself");
            } else {
                return new State(false, "Insufficient funds");
            }
        } else {
            return new State(false, "User does not exist");
        }
    }

    public void receiveTransfer(int userID, double amount) {
        balance += amount;
        transactions.add(new Transaction('R', amount, balance, "From" + userID));
    }

    public void saveToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Accounts/" + userID + ".txt");
        writer.println(userID);
        writer.println(password);
        writer.println(balance);
        writer.println(question);
        writer.println(answer);
        writer.println(dateCreated.getTime());
        writer.println(isFreeze);
        writer.println(numOfWrongPassword);
        for (Transaction transaction : transactions) {
            writer.println(transaction.getType() + " " + transaction.getAmount() + " " + transaction.getBalance() + " " + transaction.getUpdatedDate().getTime() + " " + transaction.getDescription());
        }
        writer.close();
    }

    public void getFromFile() throws FileNotFoundException {
        try {
            File file = new File("Accounts/" + userID + ".txt");
            Scanner reader = new Scanner(file);
            userID = reader.nextInt();
            password = reader.next();
            balance = reader.nextDouble();
            question = reader.nextInt();
            answer = reader.next();
            long time = reader.nextLong();
            dateCreated = new Date(time);
            isFreeze = reader.nextBoolean();
            numOfWrongPassword = reader.nextInt();
            while (reader.hasNext()) {
                transactions.add(new Transaction(reader.next().charAt(0), reader.nextDouble(), reader.nextDouble(), new Date(reader.nextLong()), reader.next()));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not accessible");
        }
    }

}
