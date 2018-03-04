import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Customer{

    private String ID;
    private String name;
    private String postcode;
    private Set<Account> accounts;

    public Customer(String ID, String name, String postcode){
        this.ID = ID;
        this.name = name;
        this.postcode = postcode;
        accounts = ConcurrentHashMap.newKeySet();
    }

    public String getID() { return ID; }

    public String getName() {
        return name;
    }

    public String getPostcode() { return postcode; }

    public Set<Account> getAccounts() { return accounts; }

    public boolean addAccount(Account account) { return accounts.add(account); }

    public boolean removeAccount(Account account) { return accounts.remove(account); }

    @Override
    public String toString() {
        String string = "";
        string += "ID: " + ID + "\n"
                + "name: " + name + "\n"
                + "postcode: " + postcode + "\n"
                + "Accounts: \n";
        for(Account account : accounts) {
            string += account.toStringNoOwners();
        }
        return string;
    }

    public String toStringNoAccounts() {
        String string = "";
        string += "ID: " + ID + "\n"
                + "name: " + name + "\n"
                + "postcode: " + postcode + "\n";
        return string;
    }

}
