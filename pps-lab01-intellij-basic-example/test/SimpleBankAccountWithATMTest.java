import lab01.example.model.AccountHolder;
import lab01.example.model.BankAccount;
import lab01.example.model.SimpleBankAccountWithATM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleBankAccountWithATMTest extends AbstractBankAccountTest<SimpleBankAccountWithATM> {
    @Override
    protected SimpleBankAccountWithATM createBankAccountToTest(final AccountHolder accountHolder) {
        return new SimpleBankAccountWithATM(accountHolder, 0);
    }

    @Test
    void testDepositWithATM() {
        getBankAccount().depositWithATM(getAccountHolder().getId(), 100);
        assertEquals(99, getBankAccount().getBalance());
    }

    @Test
    void testWrongDepositWithATM() {
        getBankAccount().depositWithATM(getAccountHolder().getId(), 100);
        getBankAccount().depositWithATM(2, 50);
        assertEquals(99, getBankAccount().getBalance());
    }

    @Test
    void testWithdrawWithATM() {
        getBankAccount().deposit(getAccountHolder().getId(), 100);
        getBankAccount().withdrawWithATM(getAccountHolder().getId(), 70);
        assertEquals(29, getBankAccount().getBalance());
    }

    @Test
    void testWrongWithdrawWithATM() {
        getBankAccount().deposit(getAccountHolder().getId(), 100);
        getBankAccount().withdrawWithATM(2, 70);
        assertEquals(100, getBankAccount().getBalance());
    }
}
