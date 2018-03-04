import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    static Customer customer;

    @BeforeAll
    static void setUp() {
        customer = new Customer("identification", "firstName lastName", "123 AB");
    }

    @Test
    void getID() {
        assertEquals("identification", customer.getID());
    }

    @Test
    void getName() {
        assertEquals("firstName lastName", customer.getName());
    }

    @Test
    void getPostcode() {
        assertEquals("123 AB",  customer.getPostcode());
    }

    @Test
    void addAccount() {
        Account account = new Account(123456, customer, "accountName");
        assertTrue(customer.addAccount(account));
        assertFalse(customer.addAccount(account));
    }

    @Test
    void removeAccount() {
        Account account = new Account(654321, customer, "accountName");
        assertTrue(customer.addAccount(account));
        assertTrue(customer.removeAccount(account));
        assertFalse(customer.removeAccount(account));
    }

}