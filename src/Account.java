import java.util.ArrayList;
import java.util.Date;

public class Account {
    private final Date dateCreated;
    private int userID = 0;
    private double balance = 0;
    ArrayList<Transaction> transactions = new ArrayList<>();

    public Account() {
        dateCreated = new Date();
    }

    public Account(int userID, double balance) {
        this.userID = userID;
        this.balance = balance;
        dateCreated = new Date();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public void withdraw(double amount) {
        balance -= amount;
        transactions.add(new Transaction('W',amount,balance,""));
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction('D',amount,balance,""));
    }
    public void displayAll() {
        System.out.println("Trans Type Amount Balance Date");
        System.out.println("-------------------------");
        for(int i = 0;i<transactions.size();i++){
            System.out.println(transactions.get(i).getType()+" "+transactions.get(i).getAmount()+" "+transactions.get(i).getBalance()+" "+transactions.get(i).getUpdatedDate().toString());
        }
    }

}
