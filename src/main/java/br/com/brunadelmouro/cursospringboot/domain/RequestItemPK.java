package br.com.brunadelmouro.cursospringboot.domain;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

// auxiliary class
@Embeddable
public class RequestItemPK implements Serializable {
    private static final long serialVersionUID = 1L;

    //between Request and Product
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestItemPK that = (RequestItemPK) o;
        return Objects.equals(product, that.product) && Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, request);
    }
}
