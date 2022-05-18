public interface AccountInterface {
    boolean deposit(double amount);

    boolean withdraw(double amount);

    double getBalance();
}
