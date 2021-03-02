package lab01.example.model;

public class SimpleBankAccountWithATM extends SimpleBankAccount {
    private static final int FEE = 1;

    public SimpleBankAccountWithATM(final AccountHolder holder, final double balance) {
        super(holder, balance);
    }

    public void depositWithATM(final int usrID, final double amount) {
        deposit(usrID, amount - FEE);
    }

    public void withdrawWithATM(final int usrID, final double amount) {
        withdraw(usrID, amount + FEE);
    }
}
