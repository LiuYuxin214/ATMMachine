import java.util.Date;

public class Transaction implements TransactionInterface {
    private final char type;
    private final double amount;
    private final double balance;
    private final String description;
    private Date dateUpdated;

    public Transaction(char type, double amount, double balance, String description) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.dateUpdated = new Date();
        this.description = description;
    }

    public Transaction(char type, double amount, double balance, Date dateUpdated, String description) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.dateUpdated = dateUpdated;
        this.description = description;
    }

    public char getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public Date getUpdatedDate() {
        return dateUpdated;
    }

    public void setUpdatedDate(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDescription() {
        return description;
    }
}