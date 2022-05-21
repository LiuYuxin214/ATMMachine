import java.util.Date;

public interface AccountInterface {

    int getUserID();

    void setUserID(int userID);

    double getBalance();

    void setBalance(double balance);

    Date getDateCreated();

    State withdraw(double amount);

    State deposit(double amount);

}
