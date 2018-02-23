public class Driver {

    public static void main(String[] args){

        Bank bank = new Bank();
        Customer customer1 = new Customer("ID1","Geaorge McWashiton", "G11 4U");
		Customer customer2 = new Customer("ID2","Wallace William"   , "T54 A1");

		bank.addCustomer(customer1);
		bank.addCustomer(customer2);

		int accountnumber = bank.openAccount(customer1.getID(), Account.Type.FIXED_INTEREST, "account1");

		Account account1 = bank.getAccount(accountnumber);//how to get the account number
		
		account1.deposit(50.0);
		account1.deposit(50.0);
		account1.getBalance();
		account1.deposit(50.0);
		
		Thread customerThread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				account1.deposit(50.0);
				account1.getBalance();
			}
		});
		
		Thread customerThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				account1.getBalance();
			}
		});
		
		customerThread1.start();
		customerThread2.start();
		
		try {
			customerThread1.join();
			customerThread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DefUseDataCollector.get().getResults();
//        UI ui = new UI(bank);
//        ui.loop();

    }

}
