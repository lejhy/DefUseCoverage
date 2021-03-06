public class Scenarios {
	public void Scenario1(){//two account holders check the balance simultaneously
		Bank bank = new Bank();

		Customer customer1 = new Customer("ID1","Geaorge McWashiton", "G11 4U");
		Customer customer2 = new Customer("ID2","Wallace William"   , "T54 A1");

		bank.addCustomer(customer1);
		bank.addCustomer(customer2);


		int accountnumber = bank.openAccount(customer1.getID(), Account.Type.FIXED_INTEREST,"account1");

		Account account1 = bank.getAccount(accountnumber);//how to get the account number


		bank.addOwner(customer2.getID(),account1.accountNumber);

		Thread customerThread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("***Scenario1 :CustomerThread1，balance: "+account1.getBalance());
			}
		});                                                  //thread is not matching with customer ???

		Thread customerThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("***Scenario1 :CustomerThread2，balance: "+account1.getBalance());
			}
		});
		customerThread1.start();
		customerThread2.start();

	}

	public void Scenario2(){//one account holder check the balance while the other is depositing/withdrawing money.
		Bank bank = new Bank();

		Customer customer1 = new Customer("ID1","Geaorge McWashiton", "G11 4U");
		Customer customer2 = new Customer("ID2","Wallace William"   , "T54 A1");

		bank.addCustomer(customer1);
		bank.addCustomer(customer2);

		int accountnumber = bank.openAccount(customer1.getID(), Account.Type.FIXED_INTEREST,"account1");

		Account account1 = bank.getAccount(accountnumber);

		bank.addOwner(customer2.getID(),account1.accountNumber);

		account1.deposit(10);

		Thread customerThread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("***Scenario2:CustomerThread1 is checking balance :"+account1.getBalance());
			}
		});

		Thread customerThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("***Scenario2:Before modification:CustomerThread2 is checking balance :"+account1.getBalance());
				account1.deposit(100);
				account1.withdraw(25);
				System.out.println("***Scenario2:After modification:CustomerThread2 is checking balance :"+account1.getBalance());
			}
		});
		customerThread1.start();
		customerThread2.start();

	}

	public void Scenario3(){//The two account holders are trying simultaneously to deposit/withdraw money & then check the balance.
		Bank bank = new Bank();

		Customer customer1 = new Customer("ID1","Geaorge McWashiton", "G11 4U");
		Customer customer2 = new Customer("ID2","Wallace William"   , "T54 A1");

		bank.addCustomer(customer1);
		bank.addCustomer(customer2);

		int accountnumber = bank.openAccount(customer1.getID(), Account.Type.FIXED_INTEREST,"account1");

		Account account1 = bank.getAccount(accountnumber);

		bank.addOwner(customer2.getID(),account1.accountNumber);

		account1.deposit(100);
		System.out.println("Before modification : " + account1.getBalance());

		Thread customerThread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("***Scenario3:CustomerThread1 ,before modification : " + account1.getBalance());
				account1.deposit(10);
				account1.withdraw(1);
				System.out.println("***Scenario3:CustomerThread1 ,after modification: " + account1.getBalance());
			}
		});

		Thread customerThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("***Scenario3:CustomerThread2 ,before modification : " + account1.getBalance());
				account1.withdraw(17);
				account1.withdraw(2);
				System.out.println("***Scenario3:CustomerThread2 ,after modification : " + account1.getBalance());
			}
		});
		customerThread1.start();
		customerThread2.start();

	}


	//Withdraw from account with insufficient funds then add in funds later
	public void Scenario4() {
		Bank b = new Bank();
		Customer c1 = new Customer("ID1", "Geaorge McWashiton", "G114u");
		b.addCustomer(c1);
		int accNum = b.openAccount(c1.getID(), Account.Type.SAVINGS, "Saving up for a teddy bear");
		Account a = b.getAccount(accNum);
		Thread t1 = new Thread(() -> a.withdraw(1000));
		Thread t2 = new Thread(() -> a.deposit(1050));
		System.out.println("***Scenario4 : Withdrawing 1000 from the bank account current value £" + a.balance);
		t1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("***Scenario4 : Ohh sleep was disturbed?");
		}
		System.out.println("***Scenario4 : Adding 1050 to the bank account current value £" + a.balance);
		t2.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("***Scenario4 : Ohh sleep was disturbed?");
		}
		System.out.println("***Scenario4 : The threads should have been added by now with account balance of  £" + a.balance);
	}

	//Withdraw from account and do nothing
	public void Scenario5() {
		Bank b = new Bank();
		Customer c1 = new Customer("ID1", "Geaorge McWashiton", "G114u");
		b.addCustomer(c1);
		int accNum = b.openAccount(c1.getID(), Account.Type.SAVINGS, "Saving up for a teddy bear");
		Account a = b.getAccount(accNum);
		Thread t1 = new Thread(() -> a.withdraw(1000));
		System.out.println("***Scenario5 : Withdrawing 1000 from the bank account current value £" + a.balance);
		t1.start();
	}

	public void Scenario6() {
		Bank b = new Bank();
		Customer c1 = new Customer("ID1", "Geaorge McWashiton", "G11 4U");
		Customer c2 = new Customer("ID2", "Wallace William", "T54 A1");
		Customer c3 = new Customer("ID3", "The king of the South", "Summer is Coming");
		Customer c4 = new Customer("ID4", "Pendragon Arthur", "Sword in the stone");
		b.addCustomer(c1);
		b.addCustomer(c2);
		b.addCustomer(c3);
		b.addCustomer(c4);

		int accNum = b.openAccount(c1.getID(), Account.Type.SAVINGS, "Saving up for a teddy bear");
		Account a = b.getAccount(accNum);
		System.out.println("***Scenarios 6 : Adding user to account");
		b.addOwner(c2.getID(), a.accountNumber);
		System.out.println("***Scenarios 6 : Has the " + c2.getAccounts().contains(a));
		System.out.println("***Scenarios 6 : Removing " + c1.getName() + " from account " + a.getAccountName());
		new Thread(() -> b.removeOwner(c1.getID(),a.accountNumber)).start();
		System.out.println("***Scenarios 6 : Removing " + c1.getName() + " from bank");
		new Thread(() -> b.removeCustomer(c1.getID())).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("***Scenarios 6 : Ohh sleep was disturbed?");
		}
		System.out.println("***Scenarios 6 : The owners of the account are : " + a.getOwners());
	}

	public static void main(String[] args){
		int numberOfRuns = 1;
		for (int i = 0; i < numberOfRuns; i++) {
			Scenarios s = new Scenarios();
			System.out.println("!!!Start scenario 1:");
			s.Scenario1();
			System.out.println("!!!Start scenario 2:");
			s.Scenario2();
			System.out.println("!!!Start scenario 3:");
			s.Scenario3();
			System.out.println("!!!Start scenario 4:");
			s.Scenario4();
			System.out.println("!!!Start scenario 5:");
			s.Scenario5();
			System.out.println("!!!Start scenario 6:");
			s.Scenario6();
		}
	}

}
