import java.util.Date;

public interface TransactionInterface {
    char getType();

    double getBalance();

    Date getUpdatedDate();

    void setUpdatedDate(Date date);

    double getAmount();

    String getDescription();

}
