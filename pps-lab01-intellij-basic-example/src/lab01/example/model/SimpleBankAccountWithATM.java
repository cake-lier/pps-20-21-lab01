package lab01.example.model;

public class SimpleBankAccountWithATM extends SimpleBankAccount {
    public SimpleBankAccountWithATM(final AccountHolder holder, final double balance) {
        super(holder, balance);
    }

    public void depositWithATM(final int usrID, final double amount) {
        deposit(usrID, amount - 1);
    }

    public void withdrawWithATM(final int usrID, final double amount) {
        withdraw(usrID, amount + 1);
    }
}
