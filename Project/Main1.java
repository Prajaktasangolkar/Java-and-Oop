import java.util.*;
import java.time.LocalDate;

// Custom exceptions
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

// Customer class
class Customer {
    private final String customerId;
    private String customerName;
    private String customerPassword;
    private double credit;
    private String email;
    private List<Order> orders;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String password) {
        this.customerPassword = password;
    }

    public void setCredit(double val) {
        credit = val;
    }

    public double getCredit() {
        return credit;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}

// Product class
class Product {
    private final String productNumber;
    private String productName;
    private final double productPrice;
    private double productStock;

    Product(String productNumber, String productName, double productPrice, double productStock) {
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

// Order class
class Order {
    private final String orderNumber;
    private final LocalDate orderDate;
    private final Customer customer;
    private final Product product;
    private final double quantity;

    Order(Customer customer, Product product, double quantity) {
        this.orderNumber = UUID.randomUUID().toString();
        this.orderDate = LocalDate.now();
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    // public Customer getCustomer() {
    // return customer;
    // }

    // public Product getProduct() {
    // return product;
    // }

    public double getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return product.getProductPrice() * quantity;
    }

    public void printInvoice() {
        if (quantity > 0) {
            System.out.println("Invoice:");
            System.out.println("Order Number: " + orderNumber);
            System.out.println("Order Date: " + orderDate);
            System.out.println("Customer Name: " + customer.getCustomerName());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Quantity: " + quantity);
            System.out.println("Total Amount: " + getTotalAmount());
        } else {
            System.out.println("No product purchased.");
        }
    }
}

// ProductStore class
class ProductStore {
    private List<Product> productList;

    ProductStore(List<Product> products) {
        this.productList = products;
    }

    public void addProduct(Product items) {
        productList.add(items);
    }

    public void getAllProductList() {
        if (productList.isEmpty()) {
            throw new ProductError("Product list is empty.");
        } else {
            for (Product product : productList) {
                System.out.println("Product Id: " + product.getProductNumber() +
                        " Product Name: " + product.getProductName() +
                        " Product Price: " + product.getProductPrice() +
                        " Product Stock: " + product.getProductStock());
            }
        }
    }

    public void searchOneProduct(String productName) {
        boolean found = false;
        for (Product product : productList) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                System.out.println("Product Id: " + product.getProductNumber() +
                        " Product Name: " + product.getProductName() +
                        " Product Price: " + product.getProductPrice() +
                        " Product Stock: " + product.getProductStock());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    public boolean stockAvailable(String productName, double quantity) {
        for (Product product : productList) {
            if (product.getProductName().equalsIgnoreCase(productName) && product.getProductStock() >= quantity) {
                return true;
            }
        }
        throw new OutOfStock("Product is out of stock.");
    }

    public boolean creditsAvailable(Customer customer, String productName, double quantity) {
        for (Product product : productList) {
            if (product.getProductName().equalsIgnoreCase(productName)
                    && customer.getCredit() >= product.getProductPrice() * quantity) {
                return true;
            }
        }
        throw new OutOfCredits("Insufficient credits.");
    }

    public void placeOrder(Customer customer, String productName, double quantity) {
        try {
            if (stockAvailable(productName, quantity) && creditsAvailable(customer, productName, quantity)) {
                for (Product product : productList) {
                    if (product.getProductName().equalsIgnoreCase(productName)) {
                        product.setProductStock(product.getProductStock() - quantity);
                        customer.setCredit(customer.getCredit() - product.getProductPrice() * quantity);
                        Order order = new Order(customer, product, quantity);
                        customer.addOrder(order);
                        order.printInvoice();
                        return;
                    }
                }
            }
        } catch (OutOfStock | OutOfCredits e) {
            System.err.println(e.getMessage());
        }
    }
}

// Main class
public class Main1 {
    public static void main(String[] args) {
        // Initialize the list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID().toString(), "Laptop", 45000, 140));
        products.add(new Product(UUID.randomUUID().toString(), "Cycle", 12000, 400));
        products.add(new Product(UUID.randomUUID().toString(), "Dress", 800, 40));
        products.add(new Product(UUID.randomUUID().toString(), "Shirts", 567, 890));
        products.add(new Product(UUID.randomUUID().toString(), "Books", 7290, 190));

        ProductStore store = new ProductStore(products);

        Map<String, Customer> customers = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        Customer currentCustomer = null;

        int choiceNumber;
        do {
            System.out.println("------------------------------------");
            System.out.println("1. View Available Products");
            System.out.println("2. Purchase a Product");
            System.out.println("3. View Previous Orders");
            System.out.println("4. Search for a Product");
            System.out.println("5. Register New Account");
            System.out.println("6. Log In");
            System.out.println("7. View Account Details");
            System.out.println("8. Edit User Details");
            System.out.println("9. Invoice");
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
                            store.placeOrder(currentCustomer, productName, quantity);
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
                        List<Order> orders = currentCustomer.getOrders();
                        if (orders.isEmpty()) {
                            System.out.println("No previous orders found.");
                        } else {
                            System.out.println("Your Previous Orders are:");
                            for (Order order : orders) {
                                order.printInvoice();
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.println("Check availability of Product:");
                    System.out.println("Please enter Product Name:");
                    String productName = sc.nextLine();
                    store.searchOneProduct(productName);
                    break;

                case 5:
                    System.out.println("Please enter your name:");
                    String name = sc.nextLine();
                    System.out.println("Please enter your email:");
                    String email = sc.nextLine();
                    System.out.println("Please enter your password:");
                    String password = sc.nextLine();
                    System.out.println("Please enter your credit amount:");
                    double credit = sc.nextDouble();
                    sc.nextLine();

                    String customerId = UUID.randomUUID().toString();
                    Customer newCustomer = new Customer(customerId, name, password, credit, email);
                    customers.put(email, newCustomer);
                    System.out.println("Account created successfully!");
                    break;

                case 6:
                    System.out.println("Please enter your email:");
                    String loginEmail = sc.nextLine();
                    System.out.println("Please enter your password:");
                    String loginPassword = sc.nextLine();

                    Customer customer = customers.get(loginEmail);
                    if (customer != null && customer.getCustomerPassword().equals(loginPassword)) {
                        currentCustomer = customer;
                        System.out.println("Logged in successfully!");
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;

                case 7:
                    if (currentCustomer != null) {
                        System.out.println("Customer Name: " + currentCustomer.getCustomerName());
                        System.out.println("Email: " + currentCustomer.getEmail());
                        System.out.println("Credit: " + currentCustomer.getCredit());
                    } else {
                        System.out.println("Please sign in first.");
                    }
                    break;

                case 8:
                    if (currentCustomer != null) {
                        System.out.println("Enter new name:");
                        String newName = sc.nextLine();
                        currentCustomer.setCustomerName(newName);
                        System.out.println("Enter new password:");
                        String newPassword = sc.nextLine();
                        currentCustomer.setCustomerPassword(newPassword);
                        System.out.println("User details updated successfully!");
                    } else {
                        System.out.println("Please sign in first.");
                    }
                    break;
                case 9:
                    if (currentCustomer == null) {
                        System.out.println("Please sign in. You are an unauthorized user!");
                    } else {
                        System.out.println("Customer Name: " + currentCustomer.getCustomerName());
                        System.out.println("Customer Email: " + currentCustomer.getEmail());
                        List<Order> orders = currentCustomer.getOrders();
                        if (orders.isEmpty()) {
                            System.out.println("No previous orders found.");
                        } else {
                            System.out.println("Your Previous Orders are:");
                            for (Order order : orders) {
                                order.printInvoice();
                            }
                        }
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 8.");
                    break;
            }
        } while (choiceNumber != 0);

        sc.close();
    }
}
