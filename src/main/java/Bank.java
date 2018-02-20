import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

    private static final String CLERK_PASS = "1234"; //Need some sort of verification for a clerk to 'log in'

    private ConcurrentHashMap<String, Customer> customerDB;
    private ConcurrentHashMap<Integer, Account> accountDB;
    private static Random rand = new Random();
    private Lock lock = new ReentrantLock();

    public Bank(){
        customerDB = new ConcurrentHashMap<String, Customer>();
        accountDB = new ConcurrentHashMap<Integer, Account>();
    }


    public boolean addCustomer(Customer customer){
        System.out.println("Adding new customer");
        lock.lock();
        if (customerDB.containsKey(customer.getID())) {
            lock.unlock();
            System.out.println("This customer already exists!");
            return false;
        } else {
            customerDB.put(customer.getID(), customer);
            lock.unlock();
            System.out.println("You're signed up!");
            return true;
        }
    }


    public boolean removeCustomer(String customerID) {
        System.out.println("Removing customer with ID: " + customerID);
        lock.lock();
        Customer customer = customerDB.get(customerID);
        if (customer != null) {
            for (Account account : customer.getAccounts()) {
                account.removeOwner(customer);
                if (account.owners.size() == 0) {
                    accountDB.remove(account.getAccountNumber());
                }
            }
            customerDB.remove(customerID);
            lock.unlock();
            return true;
        };
        lock.unlock();
        return false;
    }

    public int openAccount(String customerID, Account.Type accountType, String accountName){

        Customer customer = customerDB.get(customerID);
        if (customer == null) return -1; // Customer does not exist
        int accountNumber = generateAccountNumber();
        Account account;
        switch (accountType) {
            case CHECKING:
                account = new CheckingAccount(accountNumber, customer, accountName);
                break;
            case FIXED_INTEREST:
                account = new FixedInterestAccount(accountNumber, customer, accountName);
                break;
            case SAVINGS:
                account = new SavingsAccount(accountNumber, customer, accountName);
                break;
            default:
                System.out.println("Could not create an account, unexpected Account.Type: " + accountType);
                return -1;
        }

        lock.lock();
        customer.addAccount(account);
        accountDB.put(accountNumber, account);
        lock.unlock();

        System.out.println("That's the new Current Account opened for you! - NO OF ACCOUNTS: "+accountDB.size());
        System.out.println("Account Number:\t" + accountNumber);

        return accountNumber;
    }

    public boolean removeAccount (String customerID, int accountNumber) {
        lock.lock();
        Account account = accountDB.get(accountNumber);
        Customer customer = customerDB.get(customerID);
        if (account != null && customer != null) {
            Boolean a =  account.removeOwner(customer);
            lock.unlock();
            return a;
        } else {
            lock.unlock();
            return false;
        }
    }

    public Account getAccount(int accountNumber){
        System.out.println("ACCOUNT DATABASE:");
        System.out.println(accountDB.keySet());
        if(accountDB.keySet().contains(accountNumber)){
            return accountDB.get(accountNumber);
        }
        System.out.println("An account has not been found with the number: "+accountNumber);
        return null;
    }

    public Customer getCustomer(String ID){
        return customerDB.get(ID);
    }

    public int generateAccountNumber(){

        int number = rand.nextInt(1000000);

        for(Integer account : accountDB.keySet()){
            if(number == account){ //If the account number is already taken
                number = generateAccountNumber(); //Try to generate again.
            }
        }

        return number;
    }

    public boolean addOwner(String customerID, int accountNumber) {
        lock.lock();
        Account account = accountDB.get(accountNumber);
        Customer customer = customerDB.get(customerID);
        if (account != null && customer != null) {
            boolean result = account.addOwner(customer) && customer.addAccount(account);
            lock.unlock();
            return result;
        }
        lock.unlock();
        return false;
    }

    public boolean removeOwner(String customerID, int accountNumber) {
        lock.lock();
        Account account = accountDB.get(accountNumber);
        Customer customer = customerDB.get(customerID);
        if (account != null && customer != null) {
            boolean result = account.removeOwner(customer) && customer.removeAccount(account);
            lock.unlock();
            return result;
        }
        lock.unlock();
        return false;
    }

    @Override
    public String toString() {
        String string = "Customers: \n";
        for (Customer customer : customerDB.values()) {
            string += customer.toString();
        }
        return string;
    }

    public String toStringAccounts() {
        String string = "Accounts: \n";
        for (Account account : accountDB.values()) {
            string += account.toString();
        }
        return string;
    }

}
