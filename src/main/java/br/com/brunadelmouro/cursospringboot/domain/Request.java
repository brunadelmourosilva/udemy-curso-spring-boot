package br.com.brunadelmouro.cursospringboot.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="request")
    private Payment payment;

    private Customer customer;

    private Address deliveryAddress;

    public Request() {
    }

    public Request(Integer id, Date date, Payment payment, Customer customer, Address deliveryAddress) {
        this.id = id;
        this.date = date;
        this.payment = payment;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
