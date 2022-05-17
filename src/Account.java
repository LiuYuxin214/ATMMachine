import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Account {
    private Date dateCreated;
    private int userID = 0;
    private double balance = 0;
    private String password;
    private int question;
    private String answer;

    private boolean isFreeze = false;

    private int numOfWrongPassword = 0;

    ArrayList<Transaction> transactions = new ArrayList<>();

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

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public Date getDateCreate() {
        return dateCreated;
    }

    public boolean getIsFreeze() {
        return isFreeze;
    }

    public void setFreeze(boolean isFreeze) {
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

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            Waiter.waiter();
        } else if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction('W', amount, balance, ""));
        } else {
            System.out.println("Insufficient funds");
            Waiter.waiter();
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            Waiter.waiter();
        } else {
            balance += amount;
            transactions.add(new Transaction('D', amount, balance, ""));
        }
    }

    public void displayAll() {
        displayBasicInformation();
        displayTransactions();
        displayProportionChart();
    }

    public void displayBasicInformation() {
        System.out.println("User ID: " + userID);
        System.out.println("Balance: " + balance);
        System.out.println("Date created: " + dateCreated);
    }

    public void displayTransactions() {
        System.out.println("                  Transactions");
        System.out.println("Type Amount Balance Date");
        System.out.println("------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-5c%-7.3f%-8.3f%s\n", transaction.getType(), transaction.getAmount(), transaction.getBalance(), transaction.getUpdatedDate().toString());
        }
    }

    public void displayProportionChart() {
        double sumOfD = 0, sumOfW = 0;
        for (Transaction transaction : transactions) {

            if (transaction.getType() == 'D') {
                sumOfD += transaction.getAmount();
            } else if (transaction.getType() == 'W') {
                sumOfW += transaction.getAmount();
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
            writer.println(transaction.getType() + " " + transaction.getAmount() + " " + transaction.getBalance() + " " + transaction.getUpdatedDate().toString());
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
                transactions.add(new Transaction(reader.next().charAt(0), reader.nextDouble(), reader.nextDouble(), reader.nextLine()));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            Account account = new Account(userID, 0, "123456");
            account.setQuestion(1);
            account.setAnswer("red");
            account.saveToFile();
            System.out.println("File not found, initial file created automatically.");
            Waiter.waiter();
        }
    }

}
