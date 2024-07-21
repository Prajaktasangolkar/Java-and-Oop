import java.util.*;
import java.time.LocalDate;

class OutOfStock extends RuntimeException {
    public OutOfStock(String message) {
        super(message);
    }
}

class OutOfCredits extends RuntimeException {
    public OutOfCredits(String message) {
        super(message);
    }
}

class ProductError extends RuntimeException {
    public ProductError(String message) {
        super(message);
    }
}

class Customer {
    private final String customerId;
    private String customerName;
    private String customerPassword;
    private double credit;
    private String email;
    private List<String> orders;

    Customer(String customerId, String customerName, String customerPassword, double credit, String email) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.credit = credit;
        this.email = email;
        this.orders = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setCredit(double val) {
        credit = val;
    }

    public double getCredit() {
        return credit;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void addOrder(String order) {
        orders.add(order);
    }

    public void myOrders() {
        if (orders.isEmpty()) {
            System.out.println("No previous orders found.");
        } else {
            for (String str : orders) {
                System.out.println(str);
            }
        }
    }
}

class Products {
    private final String productNumber;
    private String productName;
    private final double productPrice;
    private double productStock;

    Products(String productNumber, String productName, double productPrice, double productStock) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductStock(double val) {
        productStock = val;
    }

    public double getProductStock() {
        return productStock;
    }
}

class ProductStore {
    private List<Products> productList;

    ProductStore() {
        productList = new ArrayList<>();
    }

    public void addProduct(Products items) {
        productList.add(items);
    }

    public void getAllProductList() {
        if (productList.isEmpty()) {
            throw new ProductError("Product list is empty.");
        } else {
            for (Products product : productList) {
                System.out.println("Product Id: " + product.getProductNumber() +
                        " Product Name: " + product.getProductName() +
                        " Product Price: " + product.getProductPrice() +
                        " Product stock: " + product.getProductStock());
            }
        }
    }

    public void searchOneProduct(String productName) {
        boolean found = false;
        for (Products product : productList) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                System.out.println("Product Id: " + product.getProductNumber() +
                        " Product Name: " + product.getProductName() +
                        " Product Price: " + product.getProductPrice() +
                        " Product stock: " + product.getProductStock());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    public boolean stockAvailable(String productName, double quantity) {
        for (Products product : productList) {
            if (product.getProductName().equalsIgnoreCase(productName) && product.getProductStock() >= quantity) {
                return true;
            }
        }
        throw new OutOfStock("Product is out of stock.");
    }

    public boolean creditsAvailable(Customer customer, String productName, double quantity) {
        for (Products product : productList) {
            if (product.getProductName().equalsIgnoreCase(productName) && customer.getCredit() >= product.getProductPrice() * quantity) {
                return true;
            }
        }
        throw new OutOfCredits("Insufficient credits.");
    }

    public void getOrderPlaced(Customer customer, String productName, double quantity) {
        try {
            if (stockAvailable(productName, quantity) && creditsAvailable(customer, productName, quantity)) {
                for (Products product : productList) {
                    if (product.getProductName().equalsIgnoreCase(productName)) {
                        product.setProductStock(product.getProductStock() - quantity);
                        customer.setCredit(customer.getCredit() - product.getProductPrice() * quantity);
                        String orderDetails = String.format("Order placed: %s x %.2f at %.2f each. Total: %.2f",
                                productName, quantity, product.getProductPrice(), product.getProductPrice() * quantity);
                        customer.addOrder(orderDetails);
                        System.out.println(orderDetails);
                        return;
                    }
                }
            }
        } catch (OutOfStock | OutOfCredits e) {
            System.err.println(e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ProductStore store = new ProductStore();

        Products p1 = new Products(UUID.randomUUID().toString(), "Laptop", 45000, 140);
        Products p2 = new Products(UUID.randomUUID().toString(), "Cycle", 12000, 400);
        Products p3 = new Products(UUID.randomUUID().toString(), "Dress", 800, 40);
        Products p4 = new Products(UUID.randomUUID().toString(), "Shirts", 567, 890);
        Products p5 = new Products(UUID.randomUUID().toString(), "Books", 7290, 190);

        store.addProduct(p1);
        store.addProduct(p2);
        store.addProduct(p3);
        store.addProduct(p4);
        store.addProduct(p5);

        Map<Customer, String> customers = new HashMap<>();
        Customer currentCustomer = null;
        Scanner sc = new Scanner(System.in);
        int choiceNumber;
        do {
            System.out.println("------------------------------------");
            System.out.println("1. View Available Products");
            System.out.println("2. Purchase a Product");
            System.out.println("3. View Previous Orders");
            System.out.println("4. Search for a Product or Check Stock");
            System.out.println("5. Register New Account");
            System.out.println("6. Log In");
            System.out.println("7. View Account Details");
            System.out.println("8. Checkout");
            System.out.println("Enter 0 to Exit");
            System.out.println("Please enter your choice: ");

            choiceNumber = sc.nextInt();
            sc.nextLine();

            switch (choiceNumber) {
                case 1:
                    try {
                        store.getAllProductList();
                    } catch (ProductError e) {
                        System.err.println(e.getMessage());
                    }
                    break;

                case 2:
                    if (currentCustomer != null) {
                        store.getAllProductList();
                        System.out.println("Please enter Product Name:");
                        String productName = sc.nextLine();
                        System.out.println("Please enter product quantity (quantity >= 0):");
                        double quantity = sc.nextDouble();
                        sc.nextLine();
                        try {
                            store.getOrderPlaced(currentCustomer, productName, quantity);
                        } catch (OutOfStock | OutOfCredits e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Please sign in. You are an unauthorized user!");
                    }
                    break;

                case 3:
                    if (currentCustomer == null) {
                        System.out.println("Please sign in. You are an unauthorized user!");
                    } else {
                        System.out.println("Your Previous Orders are:");
                        currentCustomer.myOrders();
                    }
                    break;

                case 4:
                    System.out.println("Check availability of a specific product");
                    System.out.println("Enter the name of the product:");
                    String productName = sc.nextLine();
                    store.searchOneProduct(productName);
                    break;

                case 5:
                    System.out.println("Enter your name to register:");
                    String customerName = sc.nextLine();
                    boolean isCustomerPresent = false;
                    for (Customer customer : customers.keySet()) {
                        if (customer.getCustomerName().equalsIgnoreCase(customerName)) {
                            isCustomerPresent = true;
                            break;
                        }
                    }
                    if (isCustomerPresent) {
                        System.out.println("This customer already exists. Please log in.");
                    } else {
                        System.out.println("Enter your password:");
                        String password = sc.nextLine();
                        System.out.println("Enter your email address:");
                        String email = sc.nextLine();
                        System.out.println("Enter the initial credit amount:");
                        double initialCredits = sc.nextDouble();
                        sc.nextLine();
                        Customer newCustomer = new Customer(UUID.randomUUID().toString(), customerName, password, initialCredits, email);
                        customers.put(newCustomer, password);
                        currentCustomer = newCustomer;
                        System.out.println("Registration successful! You are now logged in as: " + currentCustomer.getCustomerName());
                    }
                    break;

                case 6:
                    System.out.println("Enter your name to log in:");
                    customerName = sc.nextLine();
                    System.out.println("Enter your password:");
                    String providedPassword = sc.nextLine();
                    boolean isLoggedIn = false;
                    for (Customer customer : customers.keySet()) {
                        if (customer.getCustomerName().equalsIgnoreCase(customerName) &&
                                customers.get(customer).equals(providedPassword)) {
                            currentCustomer = customer;
                            isLoggedIn = true;
                            System.out.println("Logged in successfully as: " + currentCustomer.getCustomerName());
                            break;
                        }
                    }
                    if (!isLoggedIn) {
                        System.out.println("Incorrect name or password. Please try again.");
                    }
                    break;

                case 7:
                    if (currentCustomer == null) {
                        System.out.println("Please log in to view account details.");
                    } else {
                        System.out.println("Customer Name: " + currentCustomer.getCustomerName());
                        System.out.println("Available Credits: " + currentCustomer.getCredit());
                        System.out.println("Email Address: " + currentCustomer.getEmail());
                    }
                    break;

                case 8:
                    // Assuming checkout functionality is similar to viewing orders and then exiting.
                    if (currentCustomer == null) {
                        System.out.println("Please sign in to proceed with checkout.");
                    } else {
                        System.out.println("Generating invoice...");
                        currentCustomer.myOrders();
                        // In a real scenario, you might want to generate a detailed invoice here.
                        System.out.println("Thank you for shopping with us. Have a great day!");
                    }
                    break;

                case 0:
                    System.out.println("Thank you for using our service. Exiting now...");
                    break;

                default:
                    System.out.println("Invalid option selected. Please enter a number between 0 and 8.");
                    break;
            }
        } while (choiceNumber != 0);

        sc.close();
    }
}
