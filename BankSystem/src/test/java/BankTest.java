import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    static Bank bank;

    @BeforeAll
    static void beforeAll() {
        bank = new Bank();
    }

    @Test
    void addCustomer() {
        Customer customer1 = new Customer("addCustomerID", "addCustomerName1", "addCustomerPostcode1");
        Customer customer2 = new Customer("addCustomerID", "addCustomerName2", "addCustomerPostcode2");
        assertTrue(bank.addCustomer(customer1));
        assertFalse(bank.addCustomer(customer1));
        assertEquals(bank.getCustomer(customer1.getID()), customer1);
        assertFalse(bank.addCustomer(customer2));
    }

    @Test
    void removeCustomer() {
        Customer customer = new Customer("removeCustomerID", "removeCustomerName", "removeCustomerPostcode");
        assertTrue(bank.addCustomer(customer));
        assertTrue(bank.removeCustomer(customer.getID()));
        assertFalse(bank.removeCustomer(customer.getID()));
    }

    @Test
    void openAccount() {
        Customer customer = new Customer("openAccount", "openAccount", "openAccount");
        bank.addCustomer(customer);
        int checkingAccount = bank.openAccount("openAccount", Account.Type.CHECKING, "openAccountChecking");
        int FixedInterestAccount = bank.openAccount("openAccount", Account.Type.FIXED_INTEREST, "openAccountFixedInterest");
        int savingsAccount = bank.openAccount("openAccount", Account.Type.SAVINGS, "openAccountSavings");

        assertNotNull(bank.getAccount(checkingAccount));
        assertNotNull(bank.getAccount(FixedInterestAccount));
        assertNotNull(bank.getAccount(savingsAccount));
        assertNull(bank.getAccount(123456));
    }

    @Test
    void generateAccountNumber() {
        assertNotEquals(bank.generateAccountNumber(), bank.generateAccountNumber());
    }

}