package br.com.brunadelmouro.cursospringboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class RequestItem implements Serializable {
    private static final long serialVersionUID = 1L;

    //composite attribute
    @JsonIgnore
    @EmbeddedId
    private RequestItemPK id = new RequestItemPK();

    private Double discount;
    private Integer amount;
    private Double price;


    public RequestItem() {
    }

    public RequestItem(Request request, Product product, Double discount, Integer amount, Double price) {
        super();
        id.setRequest(request); //auxiliary class - RequestItemPK
        id.setProduct(product); //auxiliary class - RequestItemPK
        this.discount = discount;
        this.amount = amount;
        this.price = price;
    }

    //subtotal
    public double getSubTotal(){
        return (this.price - this.discount) * this.amount;
    }

    @JsonIgnore
    public Request getRequest() {
        return id.getRequest();
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public RequestItemPK getId() {
        return id;
    }

    public void setId(RequestItemPK id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestItem that = (RequestItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
