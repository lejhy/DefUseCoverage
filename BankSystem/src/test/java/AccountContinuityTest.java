import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountContinuityTest {
	Bank b;
	Customer c;
	Account a;
	@BeforeEach
	public void construct(){
		System.out.println("{");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		b = new Bank();
		c = new Customer("customer", "customer", "customer");
		b.addCustomer(c);
		int accNum = b.openAccount(c.getID(),Account.Type.SAVINGS, "Account name");
		a = b.getAccount(accNum);
		System.out.println("}");
	}

	@Test
	void WW(){
		System.out.println("Withdraw and withdraw");
		withdraw();
		withdraw();
		try {
			Thread.sleep(11000);
		}
		catch (InterruptedException e){

		}
	}

	@Test
	void WD(){
		System.out.println("Withdraw and deposit");
		withdraw();
		deposite();
	}

	@Test
	void DD(){
		System.out.println("Deposit and deposit");
		deposite();
		deposite();
	}

	public  void withdraw(){
		new Thread(()-> {System.out.println("Withdrawing 1000 from account");
			System.out.println("Withdraw successful " + a.withdraw(1000));}).start();
	}

	public  void deposite(){
		new Thread(()-> {System.out.println("Deposit 1000 into account");
			a.deposit(1000);
			System.out.println("Deposit successful");}).start();
	}
}
