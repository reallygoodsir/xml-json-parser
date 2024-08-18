package org.xml.json.parser.example.app;

import org.xml.json.parser.example.dao.CustomerDAO;
import org.xml.json.parser.example.model.Customer;
import org.xml.json.parser.example.utils.Resources;
import org.xml.json.parser.example.parser.JAXBCustomerParser;
import org.xml.json.parser.example.parser.Parser;

import java.io.File;
import java.util.List;

public class JAXBApp {
    public static void main(String[] args) throws Exception {
        Resources resources = new Resources();
        File xmlFile = resources.read("customers.xml");
        Parser customerParser = new JAXBCustomerParser();

        List<Customer> customers = customerParser.parse(xmlFile);
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.deleteAll();
        customerDAO.save(customers);

        List<Customer> customersResult = customerDAO.readAll();
        String outputCustomersFilePath = "customers_output.xml";
        customerParser.create(customersResult, outputCustomersFilePath);
    }
}
