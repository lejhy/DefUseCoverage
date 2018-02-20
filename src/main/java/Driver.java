public class Driver {

    public static void main(String[] args){

        Bank bank = new Bank();
        UI ui = new UI(bank);
        ui.loop();

    }

}
