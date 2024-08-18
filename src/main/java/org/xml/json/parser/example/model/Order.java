package org.xml.json.parser.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class Order {
    @XmlAttribute(name = "id")
//    @JsonProperty("id-element")
    private Integer id;
    @XmlElement(name = "name")
//    @JsonProperty("name-element")
    private String name;
    @XmlElement(name = "date")
//    @JsonProperty("purchasedDate-element")
    private String purchasedDate;
    @XmlElement(name = "count")
//    @JsonProperty("count-element")
    private Integer count;

    public Order() {
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

    public String getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(String purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purchasedDate='" + purchasedDate + '\'' +
                ", count=" + count +
                '}';
    }
}
