package org.xml.json.parser.example.parser;

import org.xml.json.parser.example.model.Customer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser {
    List<Customer> parse(File file) throws IOException;

    void create(List<Customer> customers, String filePath);
}
