package org.xml.json.parser.example.dao;

import org.xml.json.parser.example.model.Customer;
import org.xml.json.parser.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String QUERY_CUSTOMERS = "INSERT INTO customers VALUES (?, ?, ?)";
    private static final String QUERY_ORDERS = "INSERT INTO orders VALUES (?, ?, ?, ?, ?)";
    private static final String QUERY_READ_CUSTOMERS = "SELECT * FROM customers";
    private static final String QUERY_READ_ORDERS = "SELECT * FROM orders WHERE customer_Id = ?";
    private static final String QUERY_TRUNCATE_CUSTOMERS = "TRUNCATE customers";
    private static final String QUERY_TRUNCATE_ORDERS = "TRUNCATE orders";
    private static final String DB_URL = "jdbc:mysql://localhost/customers_db";
    private static final String USER = "root";
    private static final String PASS = "root";

    public CustomerDAO() {
    }

    public void save(List<Customer> customers) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatementCustomers = connection.prepareStatement(QUERY_CUSTOMERS);
        PreparedStatement preparedStatementOrders = connection.prepareStatement(QUERY_ORDERS);
        connection.setAutoCommit(false);
        try {
            for (Customer customer : customers) {
                preparedStatementCustomers.setInt(1, customer.getId());
                preparedStatementCustomers.setString(2, customer.getName());
                preparedStatementCustomers.setString(3, customer.getBirthDate());
                preparedStatementCustomers.execute();
                List<Order> orders = customer.getOrders();
                for (int j = 0; j < customer.getOrders().size(); j++) {
                    Order order = orders.get(j);
                    preparedStatementOrders.setInt(1, order.getId());
                    preparedStatementOrders.setString(2, order.getName());
                    preparedStatementOrders.setString(3, order.getPurchasedDate());
                    preparedStatementOrders.setInt(4, order.getCount());
                    preparedStatementOrders.setInt(5, customer.getId());
                    preparedStatementOrders.execute();
                }
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.close();
        }
    }

    public List<Customer> readAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatementCustomers = connection.prepareStatement(QUERY_READ_CUSTOMERS);
        ResultSet rsCustomers = preparedStatementCustomers.executeQuery();
        PreparedStatement preparedStatementOrders = connection.prepareStatement(QUERY_READ_ORDERS);
        while (rsCustomers.next()) {
            Customer customer = new Customer();
            customer.setId(Integer.valueOf(rsCustomers.getString(1)));
            customer.setName(rsCustomers.getString(2));
            customer.setBirthDate(rsCustomers.getString(3));
            List<Order> orders = new ArrayList<>();
            preparedStatementOrders.setInt(1, customer.getId());
            ResultSet rsOrders = preparedStatementOrders.executeQuery();
            while (rsOrders.next()) {
                Order order = new Order();
                order.setId(Integer.valueOf(rsOrders.getString(1)));
                order.setName(rsOrders.getString(2));
                order.setPurchasedDate(rsOrders.getString(3));
                order.setCount(Integer.valueOf(rsOrders.getString(4)));
                orders.add(order);
            }
            customer.setOrders(orders);
            customers.add(customer);
        }
        connection.close();
        return customers;
    }

    public void deleteAll() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(false);
        Statement statementTruncateCustomers = connection.createStatement();
        statementTruncateCustomers.execute(QUERY_TRUNCATE_CUSTOMERS);
        statementTruncateCustomers.execute(QUERY_TRUNCATE_ORDERS);
        connection.close();
        statementTruncateCustomers.close();
    }
}
