# Order Management System (OMS)

A Java-based Order Management System (OMS) designed to streamline the process of managing customer orders. This project includes both console and GUI interfaces, allowing users to add, update, cancel, and export orders with ease. The system is built using Java and Swing for the GUI, with order summaries exported to a text file for record-keeping.

## Features

- **Dual Interface Modes**: Users can operate the OMS through either a console-based interface or a graphical user interface (GUI).
- **Order Management**: Place new orders, update existing orders, cancel orders, and track their statuses.
- **Status Tracking**: Assign statuses such as "Pending," "Shipped," "Delivered," or "Canceled" to orders.
- **Order Summary Export**: Export order details to a CSV file (`OrderSummary.txt`) for record-keeping.
- **Generate Reports**: View a summary report of order statuses within the system.

## Project Structure

- **Order**: Represents individual orders with attributes like order ID, item name, quantity, price, and status.
- **OMS**: Manages the collection of orders and provides functions for adding, displaying, updating, and exporting orders.
- **Main1**: Entry point of the application that supports both console and GUI modes, using Java Swing components for the GUI.

## Technologies Used

- **Java**: Core programming language used for application logic.
- **Swing**: Java framework for creating the graphical user interface.
- **File Handling**: Text-based file export to store order summaries.

## Installation and Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/OrderManagementSystem.git
