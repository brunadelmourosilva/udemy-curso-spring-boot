package br.com.brunadelmouro.cursospringboot.domain;

import java.io.Serializable;
import java.util.Objects;

public class RequestItem implements Serializable {
    private static final long serialVersionUID = 1L;

    //composite attribute
    private RequestItemPK id = new RequestItemPK();

    private Double discount;
    private Double amount;
    private Double price;


    public RequestItem() {
    }

    public RequestItem(Request request, Product product, Double discount, Double amount, Double price) {
        super();
        id.setRequest(request); //auxiliary class - RequestItemPK
        id.setProduct(product); //auxiliary class - RequestItemPK
        this.discount = discount;
        this.amount = amount;
        this.price = price;
    }

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
