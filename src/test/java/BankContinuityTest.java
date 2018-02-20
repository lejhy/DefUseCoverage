import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class BankContinuityTest {

	static Bank b;
	static Customer customer;
	static Customer customer2;
	static int accountNumber;

	//va[23->25]
	//vb[23->30]
	//vc[39->49]
	//vd[39->52]
	//ve[77->80]
	//vf[89->94]
	//vg[89->97]
	//vh[130->135]
	//vi[130->138]
	//vj[143->148]
	//vk[143->151]
	@BeforeEach
	void beforeAll() {
		System.out.println("{");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("There was a problem");
		}
		System.out.println("\n\n\n");
		customer = new Customer("customer", "customer", "customer");
		customer2 = new Customer("customer2", "customer2", "customer2");
		b 		 = new Bank();
		b.addCustomer(customer2);
		accountNumber = b.openAccount(customer2.getID(),Account.Type.SAVINGS, "Account name");
		System.out.println("}");
	}

	@Test
	void aa() {
		System.out.println("*** aa");
		va();
		va();
	}
	@Test
	void ab() {
		System.out.println("*** ab");
		va();
		vb();
	}
	@Test
	void ac() {
		System.out.println("*** ac");
		va();
		vc();
	}
	@Test
	void ad() {
		System.out.println("*** ad");
		va();
		vd();
	}
	@Test
	void ae() {
		System.out.println("*** ae");
		va();
		ve();
	}
	@Test
	void af() {
		System.out.println("*** af");
		va();
		vf();
	}
	@Test
	void ag() {
		System.out.println("*** ag");
		va();
		vg();
	}
	@Test
	void ah() {
		System.out.println("*** ah");
		va();
		vh();
	}
	@Test
	void ai() {
		System.out.println("*** ai");
		va();
		vi();
	}
	@Test
	void aj() {
		System.out.println("*** aj");
		va();
		vj();
	}
	@Test
	void ak() {
		System.out.println("*** ak");
		va();
		vk();
	}
	@Test
	void al() {
		System.out.println("*** al");
		vb();
		vb();
	}
	@Test
	void am() {
		System.out.println("*** am");
		vb();
		vc();
	}
	@Test
	void an() {
		System.out.println("*** an");
		vb();
		vd();
	}
	@Test
	void ao() {
		System.out.println("*** ao");
		vb();
		ve();
	}
	@Test
	void ap() {
		System.out.println("*** ap");
		vb();
		vf();
	}
	@Test
	void aq() {
		System.out.println("*** aq");
		vb();
		vg();
	}
	@Test
	void ar() {
		System.out.println("*** ar");
		vb();
		vh();
	}
	@Test
	void as() {
		System.out.println("*** as");
		vb();
		vi();
	}
	@Test
	void at() {
		System.out.println("*** at");
		vb();
		vj();
	}
	@Test
	void au() {
		System.out.println("*** au");
		vb();
		vk();
	}
	@Test
	void av() {
		System.out.println("*** av");
		vc();
		vc();
	}
	@Test
	void aw() {
		System.out.println("*** aw");
		vc();
		vd();
	}
	@Test
	void ax() {
		System.out.println("*** ax");
		vc();
		ve();
	}
	@Test
	void ay() {
		System.out.println("*** ay");
		vc();
		vf();
	}
	@Test
	void az() {
		System.out.println("*** az");
		vc();
		vg();
	}
	@Test
	void ba() {
		System.out.println("*** ba");
		vc();
		vh();
	}
	@Test
	void bb() {
		System.out.println("*** bb");
		vc();
		vi();
	}
	@Test
	void bc() {
		System.out.println("*** bc");
		vc();
		vj();
	}
	@Test
	void bd() {
		System.out.println("*** bd");
		vc();
		vk();
	}
	@Test
	void be() {
		System.out.println("*** be");
		vd();
		vd();
	}
	@Test
	void bf() {
		System.out.println("*** bf");
		vd();
		ve();
	}
	@Test
	void bg() {
		System.out.println("*** bg");
		vd();
		vf();
	}
	@Test
	void bh() {
		System.out.println("*** bh");
		vd();
		vg();
	}
	@Test
	void bi() {
		System.out.println("*** bi");
		vd();
		vh();
	}
	@Test
	void bj() {
		System.out.println("*** bj");
		vd();
		vi();
	}
	@Test
	void bk() {
		System.out.println("*** bk");
		vd();
		vj();
	}
	@Test
	void bl() {
		System.out.println("*** bl");
		vd();
		vk();
	}
	@Test
	void bm() {
		System.out.println("*** bm");
		ve();
		ve();
	}
	@Test
	void bn() {
		System.out.println("*** bn");
		ve();
		vf();
	}
	@Test
	void bo() {
		System.out.println("*** bo");
		ve();
		vg();
	}
	@Test
	void bp() {
		System.out.println("*** bp");
		ve();
		vh();
	}
	@Test
	void bq() {
		System.out.println("*** bq");
		ve();
		vi();
	}
	@Test
	void br() {
		System.out.println("*** br");
		ve();
		vj();
	}
	@Test
	void bs() {
		System.out.println("*** bs");
		ve();
		vk();
	}
	@Test
	void bt() {
		System.out.println("*** bt");
		vf();
		vf();
	}
	@Test
	void bu() {
		System.out.println("*** bu");
		vf();
		vg();
	}
	@Test
	void bv() {
		System.out.println("*** bv");
		vf();
		vh();
	}
	@Test
	void bw() {
		System.out.println("*** bw");
		vf();
		vi();
	}
	@Test
	void bx() {
		System.out.println("*** bx");
		vf();
		vj();
	}
	@Test
	void by() {
		System.out.println("*** by");
		vf();
		vk();
	}
	@Test
	void bz() {
		System.out.println("*** bz");
		vg();
		vg();
	}
	@Test
	void ca() {
		System.out.println("*** ca");
		vg();
		vh();
	}
	@Test
	void cb() {
		System.out.println("*** cb");
		vg();
		vi();
	}
	@Test
	void cc() {
		System.out.println("*** cc");
		vg();
		vj();
	}
	@Test
	void cd() {
		System.out.println("*** cd");
		vg();
		vk();
	}
	@Test
	void ce() {
		System.out.println("*** ce");
		vh();
		vh();
	}
	@Test
	void cf() {
		System.out.println("*** cf");
		vh();
		vi();
	}
	@Test
	void cg() {
		System.out.println("*** cg");
		vh();
		vj();
	}
	@Test
	void ch() {
		System.out.println("*** ch");
		vh();
		vk();
	}
	@Test
	void ci() {
		System.out.println("*** ci");
		vi();
		vi();
	}
	@Test
	void cj() {
		System.out.println("*** cj");
		vi();
		vj();
	}
	@Test
	void ck() {
		System.out.println("*** ck");
		vi();
		vk();
	}
	@Test
	void cl() {
		System.out.println("*** cl");
		vj();
		vj();
	}
	@Test
	void cm() {
		System.out.println("*** cm");
		vj();
		vk();
	}
	@Test
	void cn() {
		System.out.println("*** cn");
		vk();
		vk();
	}


	//[23->25]
	public static void va() {

		new Thread(() ->System.out.println("Added customer to bank :" + b.addCustomer(customer2))).start();
	}
	//[23->30]
	public static void vb(){

		new Thread(() ->System.out.println("Added customer to bank :" + b.addCustomer(customer))).start();
	}
	//[39->49]
	public static void vc(){
		new Thread(() -> System.out.println("Removed customer from bank :" + b.removeCustomer(customer2.getID()))).start();

	}
	//[39->52]
	public static void vd(){

		new Thread(() -> System.out.println("Removed customer from bank :" +b.removeCustomer("-1"))).start();
	}
	//[77->80]
	public static void ve(){

		new Thread(()->b.openAccount(customer2.getID(), Account.Type.SAVINGS, "New Account")).start();
	}
	//[89->94]
	public static void vf(){

		new Thread(()-> System.out.println("Removed account :" + b.removeAccount(customer2.getID(),accountNumber))).start();
	}
	//[89->97]
	public static void vg(){

		new Thread(()-> System.out.println("Removed account :" + b.removeAccount("",-1))).start();
	}
	//[130->135]
	public static void vh(){

		new Thread(()-> System.out.println("Added owner to account :" +b.addOwner(customer2.getID(), accountNumber))).start();
	}
	//[130->138]
	public static void vi() {

		new Thread(()->  System.out.println("Added owner to account :" +b.addOwner("", -1))).start();
	}
	//[143->148]
	public static void vj(){

		new Thread(()-> System.out.println("Removed owner :" + b.removeOwner(customer2.getID(), accountNumber))).start();
	}
	//[143->151]
	public static void vk(){

		new Thread(()-> System.out.println("Removed owner :" + b.removeOwner("", -1))).start();
	}





}