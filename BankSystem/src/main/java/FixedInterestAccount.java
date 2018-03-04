class FixedInterestAccount extends Account {

    public FixedInterestAccount(int acNum, Customer owner, String accountName){

        super(acNum, owner, accountName); //sets accountNumber to acNum and balance and interest to def values of 0 and 1.01 respectively.
        System.out.println("FixedInterestAccount");
        interest = 1.05;
        System.out.println("Interest is overridden to 1.05. : "+interest);
        System.out.println("Account Number: "+accountNumber);

    }

}
