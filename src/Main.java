import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Person>persons=new ArrayList<>();
    public static ArrayList<Customer>customers=new ArrayList<>();
    public static Person current_person=null;
    public  static  Cart cart = new Cart();
    public static Scanner scanner = new Scanner(System.in);
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static void main(String[] args) {
        products.add(new Product("Cheese", 100, 50, false, true, 200));
        products.add(new Product("Biscuits", 150, 30, false, true, 700));
        products.add(new Product("TV", 15000, 20, false, true, 10000));
        products.add(new Product("Mobile", 10000, 40, false, true, 1000));
        products.add(new Product("MobileScratchCard", 50, 100, false, false, 0));
        Customer customer1 = new Customer("amira", "amira123",500);
        persons.add(customer1);
        customers.add(customer1);
        Person admin1=new Person("admin1","admin1");
        persons.add(admin1);

        while (true) {
            if (current_person == null) {
                showLoginMenu();

            } else {
                if (isAdmin(current_person)) {
                    AdminMenu();

                } else {
                    CustomerMenu();

                }
            }
        }

    }

    public static void showLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(PURPLE+"\n=== Welcome ==="+RESET);
        System.out.println("1. Login");
        System.out.println("2. Register (Customer)");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        String choice =  scanner.nextLine();
        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                register();
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println(RED+"Invalid choice"+RESET);
        }
    }
    public static void login() {

        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (Person person : persons) {
            if (person.Name.equals(name) && person.getPassword().equals(password)) {
                current_person = person;
                System.out.println(GREEN+"Login successful"+RESET);
                return;
            }
        }
        System.out.println(RED+"Invalid username or password"+RESET);

    }
    public static void register() {

        System.out.print("\nEnter your name: ");
        String username = scanner.nextLine();

        for (Person person : persons) {
            if (person.Name.equals(username)) {
                System.out.println(YELLOW+"Username already exists"+RESET);
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your balance: ");
        double balance = Double.parseDouble(scanner.nextLine());

        Customer newCustomer = new Customer(username, password, balance);
        persons.add(newCustomer);
        customers.add(newCustomer);
        System.out.println(GREEN+"Registration successful"+RESET);
    }

    private static boolean isAdmin(Person person) {
       if( !customers.contains(person)){
           return true;
       }
        return false;
    }


    //admin
    private static void AdminMenu() {
        while (true) {
            System.out.println("\n---------------------------------");
            System.out.println("1. Add Product");
            System.out.println("2. Add Customer");
            System.out.println("3. Add Admin");
            System.out.println("4. View Products");
            System.out.println("5. Edit Product");
            System.out.println("6. Delete Product");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    addCustomer();
                    break;
                case "3":
                    addAdmin();
                    break;
                case "4":
                    viewProducts();
                    break;
                case "5":
                    editProduct();
                    break;
                case "6":
                    deleteProduct();
                    break;
                case "7":
                    current_person = null;
                    return;
                default:
                    System.out.println(RED + "Invalid choice" + RESET);
            }
        }
    }

    private static void addProduct() {

        System.out.print("\nEnter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter quantity: ");
        double quantity = Double.parseDouble(scanner.nextLine());
        System.out.print("Does it expire? (true/false): ");
        boolean isExpire = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Requires shipping? (true/false): ");
        boolean requireShipping = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Enter weight (in grams): ");
        double weight = Double.parseDouble(scanner.nextLine());

        products.add(new Product(name, price, quantity, isExpire, requireShipping, weight));
        System.out.println(GREEN+"Product added successfully"+RESET);
    }

    private static void addCustomer() {
        register();
    }

    private static void addAdmin() {

        System.out.print("\nEnter admin name: ");
        String name = scanner.nextLine();

        for (Person person : persons) {
            if (person.Name.equals(name)) {
                System.out.println(YELLOW+"name already exists"+RESET);
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        persons.add(new Person(name, password));
        System.out.println(GREEN+"Admin added successfully"+RESET);
    }
    private static void editProduct() {
        if (products.isEmpty()) {
            System.out.println(YELLOW + "No products available to edit" + RESET);
            return;
        }

        viewProducts();
        System.out.print("\nEnter product ID to edit: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine()) - 1;
            if (productId < 0 || productId >= products.size()) {
                System.out.println(RED + "Invalid product ID" + RESET);
                return;
            }

            Product product = products.get(productId);

            System.out.println("\nEditing Product: " + product.Name);
            System.out.println("Dont write anything to keep current value");

            System.out.print("New name (" + product.Name + "): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) product.Name = name;

            System.out.print("New price (" + product.Price + "): ");
            String priceStr = scanner.nextLine();
            if (!priceStr.isEmpty()) product.Price = Double.parseDouble(priceStr);

            System.out.print("New quantity (" + product.Quantity + "): ");
            String quantityStr = scanner.nextLine();
            if (!quantityStr.isEmpty()) product.Quantity = Double.parseDouble(quantityStr);

            System.out.print("IS it expires? (" + product.Expired + ") (true/false): ");
            String expireStr = scanner.nextLine();
            if (!expireStr.isEmpty()) product.Expired = Boolean.parseBoolean(expireStr);

            System.out.print("Requires shipping? (" + product.RequireShipping + ") (true/false): ");
            String shippingStr = scanner.nextLine();
            if (!shippingStr.isEmpty()) product.RequireShipping = Boolean.parseBoolean(shippingStr);

            if (product.RequireShipping) {
                System.out.print("New weight (" + product.Weight + "g): ");
                String weightStr = scanner.nextLine();
                if (!weightStr.isEmpty()) product.Weight = Double.parseDouble(weightStr);
            }

            System.out.println(GREEN + "Product updated successfully!" + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + "Invalid input " + RESET);
        }
    }
    private static void deleteProduct() {
        if (products.isEmpty()) {
            System.out.println(YELLOW + "No products available to delete" + RESET);
            return;
        }

        viewProducts();
        System.out.print("\nEnter product ID to delete: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine()) - 1;
            if (productId < 0 || productId >= products.size()) {
                System.out.println(RED + "Invalid product ID" + RESET);
                return;
            }

            Product product = products.get(productId);
            System.out.print("Are you sure you want to delete " + product.Name + "? (Y/N): ");
            String confirmation = scanner.nextLine();

            if (Objects.equals(confirmation, "y") || Objects.equals(confirmation, "Y")) {
                products.remove(productId);
                System.out.println(GREEN + "Product deleted successfully" + RESET);
            } else {
                System.out.println(YELLOW + "The product has not been deleted" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Invalid product ID" + RESET);
        }
    }
    private static void viewProducts() {
        System.out.println("ID -- Name -- Price  -- Requires Shipping");
        System.out.println("__________________________________________");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println((i + 1) + " -- " + p.Name + " -- " + p.Price + " -- " + p.RequireShipping );
        }

    }


    // customer
    public static void CustomerMenu() {
        Customer customer = (Customer) current_person;

        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. View Products");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. View My Balance");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayProducts();
                    break;
                case "2":
                    viewCart(customer);
                    break;
                case "3":
                    checkout(customer, cart);
                    break;
                case "4":
                    viewBalance(customer);
                    break;
                case "5":
                    current_person = null;
                    cart = new Cart();
                    return;
                default:
                    System.out.println(RED + "Invalid choice" + RESET);
            }
        }
    }

    public static void displayProducts() {

        System.out.println("\nAvailable Products:");
        System.out.println("ID -- Name -- Price  -- Requires Shipping");
        System.out.println("________________________________________");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println((i + 1) + " -- " + p.Name + " -- " + p.Price + " -- " + p.RequireShipping);
        }

        System.out.println("\nEnter product ID to add to cart (or '0' to go back):");
        String input = scanner.nextLine().trim();

        if (input.equals("0")) {
            return;
        }

        try {
            int productId = Integer.parseInt(input)-1;
            if (productId < 0 || productId >= products.size()) {
                System.out.println(RED + "Invalid product ID" + RESET);
                return;
            }

            Product product = products.get(productId);
            if(product.Expired){
                System.out.println(YELLOW + "Product is expired" + RESET);
                return;
            }

            System.out.printf("Enter quantity for " + product.Name +" : ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            if (quantity <= 0) {
                System.out.println(RED + "Quantity must be positive" + RESET);
            }
            else if (quantity > product.Quantity) {
                System.out.println(YELLOW + "Out of stock" + RESET);
            }
            else {
                cart.Add(product, quantity);
                System.out.printf(GREEN + "Added " + quantity + " x " + product.Name + " to your cart" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Please enter a valid number" + RESET);
        }
    }
    private static void viewBalance(Customer customer) {
        System.out.printf("\nYour current balance: " + GREEN + customer.balance + RESET + "\n");

        System.out.println("\nPress enter to go back");
        scanner.nextLine();
    }
    private static void viewCart(Customer customer) {
        if (cart.isEmpty()) {
            System.out.println(YELLOW + "Your cart is empty" + RESET);
            return;
        }

        System.out.println("\n=== Your Cart ===");
        for (CartProduct item : cart.getItems()) {
            System.out.println(item.quantity + " x " + item.product.Name +  "--"+item.product.Price  );
        }

        double subtotal = cart.calculateSubtotal();
        double shippingFees = calculateShippingFees(cart);
        double total = subtotal + shippingFees;

        System.out.println("----------------------");
        System.out.println("Subtotal:"+subtotal);
        System.out.println("Shipping:"+shippingFees);
        System.out.println(GREEN + "Total:"+total + RESET );
        System.out.println("Your balance:"+customer.balance);

        System.out.println("\n1. Checkout");
        System.out.println("2. Continue Shopping");
        System.out.print("Choose an option: ");

        String option = scanner.nextLine();
        if (option.equals("1")) {
            checkout(customer, cart);
        }
    }

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println(YELLOW + "Error: Your cart is empty" + RESET);
            return;
        }

        double subtotal = cart.calculateSubtotal();
        double shippingFees = calculateShippingFees(cart);
        double totalAmount = subtotal + shippingFees;

        System.out.print("\nConfirm checkout? (Y/N): ");
        String confirm = scanner.nextLine();

        if (!(confirm.equals("Y")||confirm.equals("y"))) {
            System.out.println(YELLOW + "cancelled" + RESET);
            return;
        }

        if (customer.balance < totalAmount) {
            System.out.printf(YELLOW + "Error: your balance:" + customer.balance + " and  Required: " + totalAmount + RESET);
            cart.getItems().clear();
            return;
        }


        List<Shippable> shippableItems = new ArrayList<>();
        for (CartProduct item : cart.getItems()) {
            if (item.product.RequireShipping) {
                for (int i = 0; i < item.quantity; i++) {
                    shippableItems.add(item.product);
                }
            }
        }

        if (!shippableItems.isEmpty()) {
            new ShippingService().shipItems(shippableItems);
        }
        System.out.println("\n** Checkout receipt **");
        for (CartProduct item : cart.getItems()) {
            System.out.println(item.quantity + "x " + item.product.Name + " " +
                    (item.product.Price * item.quantity));
        }
        System.out.println("----------------------");
        System.out.println("Subtotal " + subtotal);
        System.out.println("Shipping " + shippingFees);
        System.out.println("Amount " + totalAmount);

        for (CartProduct item : cart.getItems()) {
            item.product.Quantity -= item.quantity;
        }
        customer.balance -= totalAmount;

        System.out.println(GREEN + "\nCheckout successful" + RESET);
        cart.getItems().clear();
    }
    public static double calculateShippingFees(Cart cart) {
        double shippingWeight = 0.0;
        double PriceForOneK = 30;

        for (CartProduct item : cart.getItems()) {
            if (item.product.RequireShipping) {
                shippingWeight += item.product.getWeight() * item.quantity;
            }
        }

        return (shippingWeight / 1000) * PriceForOneK;
    }


}