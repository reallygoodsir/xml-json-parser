package org.xml.json.parser.example.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customer")
public class Customer {
    @XmlAttribute(name = "id")
//    @JsonProperty("id-element")
    private Integer id;

    @XmlElement(name = "name")
//    @JsonProperty("name-element")
    private String name;

    @XmlElement(name = "birth-date")
//    @JsonProperty("birthDate-element")
    private String birthDate;

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
//    @JsonProperty("orders-element")
    private List<Order> orders;

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", orders=" + orders +
                '}';
    }
}
