import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    private Scanner userInput;
    private Bank bank;
    private Customer customer;
    private Account account;
    private boolean loop;

    public UI(Bank bank){
        userInput = new Scanner(System.in);
        this.bank = bank;
        loop = false;
        customer = null;
    }

    public void loop() {
        boolean loop = true;
        while (loop) {
            if (customer == null) {
                mainMenu();
            } else {
                if (account == null) {
                    customerMenu();
                } else {
                    accountMenu();
                }
            }
        }
    }

    private void mainMenu () {
        System.out.println("(0) Log in");
        System.out.println("(1) Add Customer");
        System.out.println("(2) Delete Customer");
        System.out.println("(3) Print Bank");
        System.out.println("(4) Exit");
        switch(getUserInputInt()) {
            case 0:
                logIn();
                break;
            case 1:
                addCustomer();
                break;
            case 2:
                deleteCustomer();
                break;
            case 3:
                printBank();
                break;
            case 4:
                exit();
                break;
            default:
                invalidChoice();
                break;
        }
    }

    private void customerMenu () {
        System.out.println("(0) go to Account");
        System.out.println("(1) Add Account");
        System.out.println("(2) Delete Account");
        System.out.println("(3) Print Customer");
        System.out.println("(4) logOut");
        switch(getUserInputInt()) {
            case 0:
                goToAccount();
                break;
            case 1:
                addAccount();
                break;
            case 2:
                deleteAccount();
                break;
            case 3:
                printCustomer();
                break;
            case 4:
                logOut();
                break;
            default:
                invalidChoice();
                break;
        }
    }

    private void accountMenu () {
        System.out.println("(0) Deposit");
        System.out.println("(1) Withdraw");
        System.out.println("(2) Add Owner");
        System.out.println("(3) Remove Owner");
        System.out.println("(4) Print Account");
        System.out.println("(5) go out of this Account");
        switch(getUserInputInt()) {
            case 0:
                deposit();
                break;
            case 1:
                withdraw();
                break;
            case 2:
                addOwner();
                break;
            case 3:
                removeOwner();
                break;
            case 4:
                printAccount();
                break;
            case 5:
                geOutOfThisAccount();
                break;
            default:
                invalidChoice();
                break;
        }
    }

    private void printBank() {
        System.out.println(bank.toString());
    }

    private void printCustomer() {
        System.out.println(customer.toString());
    }

    private void printAccount() {
        System.out.println(account.toString());
    }

    private void logIn() {
        System.out.println("What is your ID?");
        String ID = getUserInputString();
        customer = bank.getCustomer(ID);
        if (customer != null) {
            System.out.println("Logged in!");
        } else {
            System.out.println("Such customer doesnt exist");
        }
    }

    private void logOut() {
        customer = null;
    }

    private void addCustomer() {
        System.out.println("Customer ID: ");
        String ID = getUserInputString();
        if (bank.getCustomer(ID) == null) {
            System.out.println("Customer Name: ");
            String name = getUserInputString();
            System.out.println("Customer Postcode: ");
            String postcode = getUserInputString();
            if (bank.addCustomer(new Customer(ID, name, postcode))) {
                System.out.println("Customer added successfuly");
            } else {
                System.out.println("Something went wrong, customer not added");
            }
        } else {
            System.out.println("Customer with this ID already exists");
        }
    }

    public void deleteCustomer() {
        System.out.println("Customer ID: ");
        String ID = getUserInputString();
        if (bank.getCustomer(ID) == null) {
            System.out.println("Customer with this ID doesnt exist");
        } else {
            if (bank.removeCustomer(ID)) {
                System.out.println("Customer removed successfuly");
            } else {
                System.out.println("Something went wrong, customer not removed");
            }
        }
    }

    public void exit() {
        loop = false;
    }

    public void invalidChoice() {
        System.out.println("Invalid choice");
    }

    public void goToAccount() {
        System.out.println("Account number: ");
        int accountNumer = getUserInputInt();
        Account account = bank.getAccount(accountNumer);
        if (account == null) {
            System.out.println("Account with this ID doesnt exist");
        } else {
            this.account = account;
        }
    }

    public void geOutOfThisAccount() {
        account = null;
    }

    private void deposit() {
        System.out.println("How much do you want to deposit?");
        double amount = getUserInputDouble();
        if (amount > 0) {
            account.deposit(amount);
            System.out.println("Your current balance is: " + account.getBalance());
        } else {
            System.out.println("Only positive values are accepted");
        }
    }

    private void withdraw() {
        System.out.println("How much do you want to withdraw?");
        double amount = getUserInputDouble();
        if (amount > 0) {
            if (account.withdraw(amount)) {
                System.out.println("The withdrawal was successful");
            } else {
                System.out.println("The withdrawal failed");
            }
            System.out.println("Your current balance is: " + account.getBalance());
        } else {
            System.out.println("Only positive values are accepted");
        }
    }

    private void addAccount() {
        System.out.println("Which account you want to add?");
        System.out.println("(0) Checking Account");
        System.out.println("(1) Savings Account");
        System.out.println("(2) Fixed Interest Account");
        Account.Type accountType = null;
        switch (getUserInputInt()) {
            case 0:
                accountType = Account.Type.CHECKING;
                break;
            case 1:
                accountType = Account.Type.SAVINGS;
                break;
            case 2:
                accountType = Account.Type.FIXED_INTEREST;
                break;
            default:
                invalidChoice();
                return;
        }

        System.out.println("Give your account a name: ");
        String accountName = getUserInputString();

        System.out.println("Account number: " + bank.openAccount(customer.getID(), accountType, accountName));
    }

    public void addOwner() {
        System.out.println("Owner ID: ");
        String ID = getUserInputString();
        Customer customer = bank.getCustomer(ID);
        if (customer == null) {
            System.out.println("Customer with this ID doesnt exist");
        } else {
            if (bank.addOwner(ID, account.getAccountNumber())) {
                System.out.println("Owner was added successfuly");
            } else {
                System.out.println("Something went wrong, owner was not added");
            }
        }
    }

    public void removeOwner() {
        System.out.println("Owner ID: ");
        String ID = getUserInputString();
        Customer customer = bank.getCustomer(ID);
        if (customer == null) {
            System.out.println("Customer with this ID doesnt exist");
        } else {
            if (bank.removeOwner(ID, account.getAccountNumber())) {
                System.out.println("Owner removed successfuly");
            } else {
                System.out.println("Something went wrong, owner was not removed");
            }
        }
    }

    private void deleteAccount() {
        System.out.println("Account number: ");
        int accountNumber = getUserInputInt();
        Account account = bank.getAccount(accountNumber);
        if (account == null) {
            System.out.println("Account with this ID doesnt exist");
        } else {
            if (bank.removeAccount(customer.getID(), accountNumber)) {
                System.out.println("Account removed successfuly");
            } else {
                System.out.println("Something went wrong, Account was not removed");
            }
        }
    }

    private int getUserInputInt(){
        int value;
        System.out.println("Input an integer: ");
        String input = userInput.nextLine();
        try {
            value = Integer.parseInt(input);
        } catch(NumberFormatException e){
            System.out.println("This is not an integer: " + input);
            System.out.println("Let's try that again...");
            value = getUserInputInt();
        }
        return value;
    }

    private double getUserInputDouble(){
        double value = 0;
        System.out.println("Input a double: ");
        String input = userInput.nextLine();
        try {
            value = Double.parseDouble(input);
        } catch(NumberFormatException e){
            System.out.println("This is not a double: " + input);
            System.out.println("Let's try that again...");
            value = getUserInputDouble();
        }
        return value;
    }

    private String getUserInputString() {
        return userInput.nextLine();
    }



}
