package com.epam.productmanagement.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "name")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @ToString.Exclude
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    @Setter(AccessLevel.PRIVATE)
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        product.setCategory(this);
        products.add(product);
    }

    public void removeProduct(Product product) {
        product.setCategory(null);
        products.remove(product);
    }
}
