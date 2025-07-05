public class Customer extends Person {

    public double balance;

    public Customer(String name, String password, double balance) {
        super(name, password);
        this.balance = balance;
    }
}
