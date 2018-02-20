
class SavingsAccount extends Account {

    public static final int DELAY = 120; //120 Seconds until money can be withdrawn.

    public SavingsAccount(int acNum, Customer owner, String accountName){
        super(acNum, owner, accountName); //sets accountNumber to acNum and balance and interest to def values of 0 and 1.01 respectively.
        System.out.println("SavingsAccount");
        System.out.println("Interest is set to 1.01, no override: "+interest);
        System.out.println("OPENING BALANCE OF: "+balance);
        System.out.println("Account Number: "+accountNumber);

    }

}
