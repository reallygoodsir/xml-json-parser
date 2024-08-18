package org.xml.json.parser.example.app;

import org.xml.json.parser.example.dao.CustomerDAO;
import org.xml.json.parser.example.model.Customer;
import org.xml.json.parser.example.utils.Resources;
import org.xml.json.parser.example.parser.JacksonCustomerParser;
import org.xml.json.parser.example.parser.Parser;

import java.io.File;
import java.util.List;

public class JacksonApp {
    public static void main(String[] args) throws Exception {
        Resources resources = new Resources();
        File jsonFile = resources.read("customers.json");
        Parser customerParser = new JacksonCustomerParser();

        List<Customer> customers = customerParser.parse(jsonFile);
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.deleteAll();
        customerDAO.save(customers);

        List<Customer> customersResult = customerDAO.readAll();
        String outputCustomersFilePath = "customers_output.json";
        customerParser.create(customersResult, outputCustomersFilePath);
    }
}
