public class Driver {

    public static void main(String[] args){

    	double totalPercentage = 0.0;
    	int numberOfRuns = 100;
    	
    	for (int i = 0; i < numberOfRuns ; i++) {
	        Bank bank = new Bank();
	        Customer customer1 = new Customer("ID1","Geaorge McWashiton", "G11 4U");
			Customer customer2 = new Customer("ID2","Wallace William"   , "T54 A1");
	
			bank.addCustomer(customer1);
			bank.addCustomer(customer2);
	
			int accountnumber = bank.openAccount(customer1.getID(), Account.Type.FIXED_INTEREST, "account1");
	
			Account account1 = bank.getAccount(accountnumber);//how to get the account number
			
			Thread customerThread1 = new Thread(new TestRunnable1(account1));
			Thread customerThread2 = new Thread(new TestRunnable1(account1));
			Thread customerThread3 = new Thread(new TestRunnable1(account1));
			
			customerThread1.start();
			customerThread2.start();
			customerThread3.start();
			
			try {
				customerThread1.join();
				customerThread2.join();
				customerThread3.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			totalPercentage += DefUseDataCollector.get().getResults();
    	}
    	
    	System.out.println("Average Percentage: "+totalPercentage/numberOfRuns);
//        UI ui = new UI(bank);
//        ui.loop();

    }

}
