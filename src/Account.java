import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Account {
    private final Date dateCreated;
    private int userID = 0;
    private double balance = 0;
    ArrayList<Transaction> transactions = new ArrayList<>();
    Scanner waiter = new Scanner(System.in);

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
        if(amount<=0) {
            System.out.println("Invalid amount");
            System.out.println("Press enter to continue");
            waiter.nextLine();
        }
        else if(balance>=amount) {
            balance -= amount;
            transactions.add(new Transaction('W',amount,balance,""));
        }
        else {
            System.out.println("Insufficient funds");
            System.out.println("Press enter to continue");
            waiter.nextLine();
        }
    }

    public void deposit(double amount) {
        if(amount<=0) {
            System.out.println("Invalid amount");
            System.out.println("Press enter to continue");
            waiter.nextLine();
        }
        else {
        balance += amount;
        transactions.add(new Transaction('D',amount,balance,""));
        }
    }
    public void displayAll() {
        System.out.println("Trans Type Amount Balance Date");
        System.out.println("-------------------------");
        for(int i = 0;i<transactions.size();i++){
            System.out.println(transactions.get(i).getType()+" "+transactions.get(i).getAmount()+" "+transactions.get(i).getBalance()+" "+transactions.get(i).getUpdatedDate().toString());
        }
    }

}
