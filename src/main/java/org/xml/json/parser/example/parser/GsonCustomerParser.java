package org.xml.json.parser.example.parser;

import com.google.gson.*;
import org.xml.json.parser.example.model.Customer;
import org.xml.json.parser.example.model.Order;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GsonCustomerParser implements Parser {
    @Override
    public List<Customer> parse(File file) throws IOException {
        List<Customer> customers = new ArrayList<>();
        String fileName = file.toString();
        Path path = Paths.get(fileName);

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonParser parser = new JsonParser();
            JsonElement tree = parser.parse(reader);
            JsonArray arrayCustomers = (JsonArray) tree.getAsJsonObject().get("customers");
            for (JsonElement element : arrayCustomers) {

                if (element.isJsonObject()) {

                    JsonObject jsonCustomer = element.getAsJsonObject();
                    Customer customer = new Customer();
                    customer.setId(jsonCustomer.get("id").getAsInt());
                    customer.setName(String.valueOf(jsonCustomer.get("name")));
                    customer.setBirthDate(String.valueOf(jsonCustomer.get("birthDate")));

                    JsonArray arrayOrders = jsonCustomer.getAsJsonArray("orders");
                    List<Order> listOrders = new ArrayList<>();
                    for (JsonElement element2 : arrayOrders) {
                        if (element2.isJsonObject()) {

                            JsonObject jsonOrder = element2.getAsJsonObject();
                            Order order = new Order();
                            order.setId(jsonOrder.get("id").getAsInt());
                            order.setName(String.valueOf(jsonOrder.get("name")));
                            order.setPurchasedDate(String.valueOf(jsonOrder.get("purchasedDate")));
                            order.setCount(jsonOrder.get("count").getAsInt());
                            listOrders.add(order);
                        }
                    }
                    customer.setOrders(listOrders);
                    customers.add(customer);
                }
            }
            return customers;
        }
    }

    @Override
    public void create(List<Customer> customers, String filePath) {
        Path path = Paths.get(filePath);

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonElement tree = gson.toJsonTree(customers);
            gson.toJson(tree, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
