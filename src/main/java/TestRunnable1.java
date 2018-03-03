
public class TestRunnable1 implements Runnable {

	Account a;
	
	public TestRunnable1(Account a) {
		this.a = a;
	}
	
	public void run() {
		a.deposit(50.0);
		a.withdraw(12.3);
		a.getBalance();
	}

}
