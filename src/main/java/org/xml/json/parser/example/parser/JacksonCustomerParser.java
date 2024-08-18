package org.xml.json.parser.example.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.json.parser.example.model.Customer;
import org.xml.json.parser.example.model.Customers;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JacksonCustomerParser implements Parser {
    @Override
    public List<Customer> parse(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Customers customers = mapper.readValue(file, Customers.class);
            return customers.getCustomers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(List<Customer> customers, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            mapper.writeValue(file, customers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
