import java.util.Date;

public class Transaction {
    private final char type;
    private final double amount;
    private final double balance;
    private Date dateUpdated;
    private final String description;

    public Transaction(char type, double amount, double balance, String description) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.dateUpdated = new Date();
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