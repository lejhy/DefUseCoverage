import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    static Account account;
    static Customer customer;

    @BeforeAll
    static void beforeAll() {
        customer = new Customer("customer", "customer", "customer");
        account = new Account(123456, customer, "accountName");
    }

    @Test
    void deposit() {
        double initialBalance = account.getBalance();
        account.deposit(500);

        assertEquals(initialBalance + 500, account.getBalance());
    }

    @Test
    void withdraw() {
        double initialBalance = account.getBalance();
        account.deposit(500);
        account.withdraw(500);
        assertEquals(initialBalance, account.getBalance());
        account.withdraw(initialBalance);
        assertEquals(0, account.getBalance());
        account.withdraw(500);
        assertEquals(0, account.getBalance());
    }

    @Test
    void lockAccount() {
        account.lockAccount();
        assertTrue(account.isLocked());
    }

    @Test
    void unlockAccount() {
        account.unlockAccount();
        assertFalse(account.isLocked());
    }

    @Test
    void getOwners() {
        assertTrue(account.getOwners().contains(customer));
    }

    @Test
    void getInterest() {
        assertEquals(account.DEF_INTEREST, account.getInterest());
    }

    @Test
    void addOwner() {
        assertFalse(account.addOwner(customer));
    }

    @Test
    void removeOwner() {
        Customer customerToREmove = new Customer("customerToREmove", "customerToREmove", "customerToREmove");
        account.addOwner(customerToREmove);
        assertTrue(account.getOwners().contains(customerToREmove));
        account.removeOwner(customerToREmove);
        assertFalse(account.getOwners().contains(customerToREmove));
    }

}