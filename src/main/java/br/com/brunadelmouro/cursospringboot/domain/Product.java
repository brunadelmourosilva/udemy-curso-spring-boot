package br.com.brunadelmouro.cursospringboot.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;

    @JsonBackReference
    @ManyToMany //n_m
    @JoinTable(name = "PRODUCT_HAS_CATEGORY",
            joinColumns = @JoinColumn(name = "product_id"), //"dominant" side
            inverseJoinColumns = @JoinColumn(name = "category_id") //"dominated" side
    )
    private List<Category> categories = new ArrayList<>();

    private Set<RequestItem> items = new HashSet<>();

    public Product() {
    }

    public Product(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    //wish list - Request and RequestItem
    public List<Request> getRequests(){
        List<Request> list = new ArrayList<>();
        for (RequestItem x : items) {
            list.add(x.getRequest());
        }
        return list;
    }

    public Set<RequestItem> getItems() {
        return items;
    }

    public void setItems(Set<RequestItem> items) {
        this.items = items;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
