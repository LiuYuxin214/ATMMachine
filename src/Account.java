import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Account {
    private Date dateCreated;
    private int userID = 0;
    private double balance = 0;
    ArrayList<Transaction> transactions = new ArrayList<>();
    Scanner waiter = new Scanner(System.in);

    public Account() {
    }

    public Account(int userID, double balance) {
        this.userID = userID;
        this.balance = balance;
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
            Waiter.waiter();
        }
        else if(balance>=amount) {
            balance -= amount;
            transactions.add(new Transaction('W',amount,balance,""));
        }
        else {
            System.out.println("Insufficient funds");
            Waiter.waiter();
        }
    }

    public void deposit(double amount) {
        if(amount<=0) {
            System.out.println("Invalid amount");
            Waiter.waiter();
        }
        else {
        balance += amount;
        transactions.add(new Transaction('D',amount,balance,""));
        }
    }
    public void displayAll() {
        System.out.println("User ID: "+userID);
        System.out.println("Balance: "+balance);
        System.out.println("Date created: "+dateCreated);
        System.out.println("                  Transactions");
        System.out.println("Type Amount Balance Date");
        System.out.println("------------------------------------------------");
        for(int i = 0;i<transactions.size();i++){
            System.out.println(transactions.get(i).getType()+"    "+transactions.get(i).getAmount()+"   "+transactions.get(i).getBalance()+"    "+transactions.get(i).getUpdatedDate().toString());
        }
    }
    public void saveToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Account"+userID+".txt");
        writer.println(userID);
        writer.println(balance);
        writer.println(dateCreated.getTime());
        for(int i = 0;i<transactions.size();i++){
            writer.println(transactions.get(i).getType()+" "+transactions.get(i).getAmount()+" "+transactions.get(i).getBalance()+" "+transactions.get(i).getUpdatedDate().toString());
        }
        writer.close();
    }
    public void getFromFile() throws FileNotFoundException {
        File file = new File("Account"+userID+".txt");
        Scanner reader = new Scanner(file);
        userID=reader.nextInt();
        balance=reader.nextDouble();
        dateCreated = new Date(reader.nextLong());
        while(reader.hasNext()) {
            transactions.add(new Transaction(reader.next().charAt(0),reader.nextDouble(),reader.nextDouble(),reader.nextLine()));
        }
    }

}
