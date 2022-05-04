import java.util.Date;

public class Log {

    private final char type;

    private final int account;

    private final Date date;

    public Log(char type, int account) {
        this.type = type;
        this.account = account;
        this.date = new Date();
    }

    public char getType() {
        return type;
    }

    public int getAccount() {
        return account;
    }

    public Date getDate() {
        return date;
    }

}