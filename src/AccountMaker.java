import java.io.FileNotFoundException;

public class AccountMaker {

    public static void main(String[] args) throws FileNotFoundException {
        Account[] accounts = new Account[10];
        for(int i = 0; i <= 9; i++) {
            accounts[i] = new Account(i,0);
            accounts[i].saveToFile();
        }
    }
}
