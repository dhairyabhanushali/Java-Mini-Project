import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

class Order {
    public int orderN;
    public String item;
    public int quantity;
    public double price;
    public String status;

    public Order(int orderN, String item, int quantity, double price) {
        this.orderN = orderN;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.status = "Pending";
    }

    public double calculateTotalPrice() {
        return this.quantity * this.price;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Order ID: " + orderN + "\nItem: " + item + "\nQuantity: " + quantity +
               "\nPrice per item: $" + price + "\nTotal: $" + calculateTotalPrice() +
               "\nStatus: " + status + "\n";
    }

    public String toFileString() {
        return orderN + "," + item + "," + quantity + "," + price + "," + status + "," + calculateTotalPrice();
    }
}

class OMS {
    public ArrayList<Order> orders;
    public int orderCount = 1;

    public OMS() {
        orders = new ArrayList<>();
    }

    public void placeOrder(String item, int quantity, double price) {
        Order newOrder = new Order(orderCount++, item, quantity, price);
        orders.add(newOrder);
        System.out.println("Order has been placed successfully.\n");
    }

    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public void cancelOrder(int orderN) {
        boolean found = false;
        for (Order order : orders) {
            if (order.orderN == orderN) {
                order.updateStatus("Canceled");
                System.out.println("Order ID " + orderN + " has been canceled successfully.\n");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Order ID " + orderN + " not found.");
        }
    }

    public void updateOrderStatus(int orderN, String status) {
        boolean found = false;
        for (Order order : orders) {
            if (order.orderN == orderN) {
                order.updateStatus(status);
                System.out.println("Order ID " + orderN + " status updated to " + status + ".\n");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Order ID " + orderN + " not found.");
        }
    }

    public String generateReport() {
        int pendingCount = 0, shippedCount = 0, deliveredCount = 0, canceledCount = 0;

        for (Order order : orders) {
            switch (order.status) {
                case "Pending":
                    pendingCount++;
                    break;
                case "Shipped":
                    shippedCount++;
                    break;
                case "Delivered":
                    deliveredCount++;
                    break;
                case "Canceled":
                    canceledCount++;
                    break;
            }
        }

        return "Order Report:\n" +
               "Pending Orders: " + pendingCount + "\n" +
               "Shipped Orders: " + shippedCount + "\n" +
               "Delivered Orders: " + deliveredCount + "\n" +
               "Canceled Orders: " + canceledCount + "\n";
    }

    public void exportOrderSummary() {
        try (FileWriter writer = new FileWriter("OrderSummary.txt")) {
            writer.write("Order ID,Item,Quantity,Price,Status,Total Price\n");
            for (Order order : orders) {
                writer.write(order.toFileString() + "\n");
            }
            System.out.println("Order summary has been successfully exported to OrderSummary.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while exporting the order summary.");
            e.printStackTrace();
        }
    }
}

public class Main1 extends JFrame {
    private OMS oms;
    private JTextField itemField, quantityField, priceField, orderIdField;
    private JTextArea outputArea;
    private JComboBox<String> statusComboBox;

    public Main1() {
        oms = new OMS();

        setTitle("Order Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Place Order"));

        inputPanel.add(new JLabel("Item Name:"));
        itemField = new JTextField();
        inputPanel.add(itemField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price per Item:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(new PlaceOrderAction());
        inputPanel.add(placeOrderButton);

        JPanel actionPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Order Actions"));

        actionPanel.add(new JLabel("Order ID:"));
        orderIdField = new JTextField();
        actionPanel.add(orderIdField);

        JButton cancelOrderButton = new JButton("Cancel Order");
        cancelOrderButton.addActionListener(new CancelOrderAction());
        actionPanel.add(cancelOrderButton);

        actionPanel.add(new JLabel("New Status:"));
        statusComboBox = new JComboBox<>(new String[]{"Pending", "Shipped", "Delivered", "Canceled"});
        actionPanel.add(statusComboBox);

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(new UpdateStatusAction());
        actionPanel.add(updateStatusButton);

        JButton displayOrdersButton = new JButton("Display Orders");
        displayOrdersButton.addActionListener(new DisplayOrdersAction());
        actionPanel.add(displayOrdersButton);

        JButton exportButton = new JButton("Export Summary");
        exportButton.addActionListener(new ExportAction());
        actionPanel.add(exportButton);

        JButton reportButton = new JButton("Generate Report");
        reportButton.addActionListener(new ReportAction());
        actionPanel.add(reportButton);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private class PlaceOrderAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String item = itemField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            oms.placeOrder(item, quantity, price);
            outputArea.append("Order placed successfully.\n");

            itemField.setText("");
            quantityField.setText("");
            priceField.setText("");
        }
    }

    private class CancelOrderAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int orderId = Integer.parseInt(orderIdField.getText());
            oms.cancelOrder(orderId);
        }
    }

    private class UpdateStatusAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int orderId = Integer.parseInt(orderIdField.getText());
            String status = (String) statusComboBox.getSelectedItem();
            oms.updateOrderStatus(orderId, status);
        }
    }

    private class DisplayOrdersAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            for (Order order : oms.orders) {
                outputArea.append(order.toString() + "\n");
            }
        }
    }

    private class ExportAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            oms.exportOrderSummary();
            outputArea.setText("Exported Order Summary.");
        }
    }

    private class ReportAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            outputArea.setText(oms.generateReport());
            
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select mode: 1 for Console, 2 for GUI");
        int choice = scanner.nextInt();

        if (choice == 1) {
            runConsoleMode();
        } else if (choice == 2) {
            SwingUtilities.invokeLater(() -> {
                Main1 gui = new Main1();
                gui.setVisible(true);
            });
        } else {
            System.out.println("Invalid choice. Exiting program.");
        }
    }

    public static void runConsoleMode() {
        Scanner sc = new Scanner(System.in);
        OMS oms = new OMS();
        System.out.println("Welcome to the Order Management System");

        while (true) {
            System.out.println("\n1. Place an Order");
            System.out.println("2. Display Orders");
            System.out.println("3. Cancel an Order");
            System.out.println("4. Update Order Status");
            System.out.println("5. Generate Order Report");
            System.out.println("6. Export Order Summary to File");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String item = sc.next();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    System.out.print("Enter price per item: ");
                    double price = sc.nextDouble();
                    oms.placeOrder(item, quantity, price);
                    break;
                case 2:
                    oms.displayOrders();
                    break;
                case 3:
                    System.out.print("Enter the Order ID to cancel: ");
                    int orderN = sc.nextInt();
                    oms.cancelOrder(orderN);
                    break;
                case 4:
                    System.out.print("Enter the Order ID to update: ");
                    int updateOrderN = sc.nextInt();
                    System.out.print("Enter new status (Pending/Shipped/Delivered/Canceled): ");
                    String status = sc.next();
                    oms.updateOrderStatus(updateOrderN, status);
                    break;
                case 5:
                    oms.generateReport();
                    break;
                case 6:
                    oms.exportOrderSummary();
                    break;
                case 7:
                    System.out.println("Exiting system.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
