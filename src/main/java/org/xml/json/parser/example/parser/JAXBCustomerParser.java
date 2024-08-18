package org.xml.json.parser.example.parser;

import org.xml.json.parser.example.model.Customer;
import org.xml.json.parser.example.model.Customers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JAXBCustomerParser implements Parser {
    @Override
    public List<Customer> parse(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Customers.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Customers customers = (Customers) unmarshaller.unmarshal(file);
            return customers.getCustomers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(List<Customer> customers, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(Customers.class);
            Marshaller marshaller = context.createMarshaller();
            Customers customersRoot = new Customers();
            customersRoot.setCustomers(customers);
            marshaller.marshal(customersRoot, new File(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
