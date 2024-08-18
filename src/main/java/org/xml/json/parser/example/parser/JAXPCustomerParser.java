package org.xml.json.parser.example.parser;

import org.xml.json.parser.example.model.Customer;
import org.xml.json.parser.example.model.Order;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JAXPCustomerParser implements Parser {
    @Override
    public List<Customer> parse(File file) {
        List<Customer> customers = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);


            Node firstChild = document.getFirstChild();
            NodeList childNodes = firstChild.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if ("customer".equals(item.getNodeName())) {
                    Customer customer = new Customer();
                    NamedNodeMap customerAttributes = item.getAttributes();
                    Node customerId = customerAttributes.getNamedItem("id");
                    customer.setId(Integer.valueOf(customerId.getTextContent()));

                    NodeList childNodes1 = item.getChildNodes();
                    for (int j = 0; j < childNodes1.getLength(); j++) {
                        Node item1 = childNodes1.item(j);
                        //.println(item1.getNodeName());
                        if (item1.getNodeName().equals("name")) {
                            String customerName = item1.getTextContent();
                            customer.setName(customerName);
                        }

                        if (item1.getNodeName().equals("birth-date")) {
                            String birthDate = item1.getTextContent();

                            customer.setBirthDate(birthDate);
                        }

                        if (item1.getNodeName().equals("orders")) {
                            List<Order> orders = new ArrayList<>();
                            NodeList customerOrders = item1.getChildNodes();
                            for (int customerOrder = 0; customerOrder < customerOrders.getLength(); customerOrder++) {
                                Node customerOrderNode = customerOrders.item(customerOrder);

                                if (customerOrderNode.getNodeName().equals("order")) {
                                    Order order = new Order();

                                    NamedNodeMap orderAttributes = customerOrderNode.getAttributes();
                                    Node orderId = orderAttributes.getNamedItem("id");
                                    order.setId(Integer.valueOf(orderId.getTextContent()));

                                    NodeList orderChildList = customerOrderNode.getChildNodes();

                                    for (int j2 = 0; j2 < orderChildList.getLength(); j2++) {
                                        Node item2 = orderChildList.item(j2);
                                        if (item2.getNodeName().equals("name")) {
                                            String orderName = item2.getTextContent();

                                            order.setName(orderName);
                                        }
                                        if (item2.getNodeName().equals("date")) {
                                            String orderDate = item2.getTextContent();

                                            order.setPurchasedDate(orderDate);
                                        }
                                        if (item2.getNodeName().equals("count")) {
                                            String orderCount = item2.getTextContent();

                                            order.setCount(Integer.valueOf(orderCount));
                                        }
                                    }
                                    orders.add(order);
                                }
                            }
                            customer.setOrders(orders);
                        }
                    }
                    customers.add(customer);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customers;
    }

    @Override
    public void create(List<Customer> customers, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("products");
            document.appendChild(root);

            Element customerList = document.createElement("customers");
            root.appendChild(customerList);

            for (Customer customer : customers) {
                Element book = document.createElement("customer");
                book.setAttribute("id", String.valueOf(customer.getId()));
                customerList.appendChild(book);
                Element book1 = document.createElement("name");
                book1.appendChild(document.createTextNode(String.valueOf(customer.getName())));
                Element book2 = document.createElement("birth-date");
                book2.appendChild(document.createTextNode(String.valueOf(customer.getBirthDate())));
                book.appendChild(book1);
                book.appendChild(book2);
            }

            Element orderList = document.createElement("orders");
            root.appendChild(orderList);

            for (Customer customer : customers) {
                List<Order> orders = customer.getOrders();
                for (Order order : orders) {
                    Element orderElement = document.createElement("order");
                    orderElement.setAttribute("id", String.valueOf(order.getId()));
                    orderList.appendChild(orderElement);

                    String name = order.getName();
                    String purchasedDate = order.getPurchasedDate();
                    Integer count = order.getCount();
                    Element orderName = document.createElement("name");
                    orderName.appendChild(document.createTextNode(name));
                    orderElement.appendChild(orderName);
                    Element orderPurchasedDate = document.createElement("purchase-date");
                    orderPurchasedDate.appendChild(document.createTextNode(purchasedDate));
                    orderElement.appendChild(orderPurchasedDate);
                    Element orderCount = document.createElement("count");
                    orderCount.appendChild(document.createTextNode(String.valueOf(count)));
                    orderElement.appendChild(orderCount);
                    Integer customerId = customer.getId();
                    Element orderCustomerId = document.createElement("customer-id");
                    orderCustomerId.appendChild(document.createTextNode(String.valueOf(customerId)));
                    orderElement.appendChild(orderCustomerId);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(filePath);
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
