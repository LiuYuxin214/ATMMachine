import java.util.Date;

public interface AccountInterface {

    int getUserID();

    void setUserID(int userID);

    double getBalance();

    void setBalance(double balance);

    Date getDateCreated();

    boolean withdraw(double amount);

    boolean deposit(double amount);

    void displayAll();


}
