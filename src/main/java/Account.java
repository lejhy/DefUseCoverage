import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {

    public static final double DEF_BALANCE = 0.0;
    public static final double DEF_INTEREST = 1.01; //Adding interest will be done by amount*interest, so this adds 1% interest.

    protected int accountNumber;
    protected String accountName;
    protected Set<Customer> owners;
    protected double balance;
    protected double interest;
    protected boolean locked = false;

    private Lock lock = new ReentrantLock();
    private Condition balanceIncreased = lock.newCondition();

    public enum Type {
        CHECKING, SAVINGS, FIXED_INTEREST
    }

    protected Account(int accountNumer, Customer owner, String accountName){
        this.accountNumber = accountNumer;
        this.accountName = accountName;
        owners = ConcurrentHashMap.newKeySet();
        owners.add(owner);
        balance = DEF_BALANCE;
        interest = DEF_INTEREST;
    }

    protected void deposit(double amount){
        System.out.println(Thread.currentThread().getName() + "thread with ID: " + Thread.currentThread().getId() + "is attempting to make a deposit...");
        lock.lock();
        this.balance += amount;
        balanceIncreased.signal();
        lock.unlock();
    }

    protected boolean withdraw(double amount) {
        System.out.println(Thread.currentThread().getName() + "thread with ID: " + Thread.currentThread().getId() + "is attempting to make a withdrawal...");
        boolean isWaiting = true;
        boolean success = false;
        lock.lock();
        try {
            while (amount > balance) {
                if (isWaiting == false) {
                    System.out.println("Waited for too long, moving on...");
                    Thread.currentThread().interrupt();
                }
                System.out.println("Insufficient funds waiting for a deposit");
                isWaiting = balanceIncreased.await(10, TimeUnit.SECONDS);
            }
            balance -= amount;
            success = true;
        } catch (InterruptedException e) {
            System.out.println("Withdraw was interrupted...");
        } finally {
            lock.unlock();
        }
        return success;
    }

    public void lockAccount(){
        locked = true;
    }

    public void unlockAccount(){
        locked = false;
    }

    public boolean isLocked () { return locked; }

    public boolean addOwner(Customer customer) {
        return owners.add(customer);
    }

    public boolean removeOwner(Customer customer) {
        return owners.remove(customer);
    }

    public Set<Customer> getOwners(){
        return owners;
    }

    public double getInterest(){
        return interest;
    }

    public double getBalance(){
        return balance;
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public String getAccountName() { return accountName; }

    @Override
    public String toString() {
        String string = "";
        string += "Number: " + accountNumber + "\n"
                + "Name: " + accountName + "\n"
                + "Balance: " + balance + "\n"
                + "Interest: " + interest + "\n"
                + "IsLocked: " + locked + "\n"
                + "Owners: \n";
        for(Customer owner : owners) {
            string += owner.toStringNoAccounts();
        }
        return string;
    }

    public String toStringNoOwners() {
        String string = "";
        string += "Number: " + accountNumber + "\n"
                + "Name: " + accountName + "\n"
                + "Balance: " + balance + "\n"
                + "Interest: " + interest + "\n"
                + "IsLocked: " + locked + "\n";
        return string;
    }

}
