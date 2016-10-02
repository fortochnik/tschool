package tstore.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by mipan on 29.09.2016.
 */
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "level")
    private int level;

    @Column(name = "category")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<ProductEntity> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "level=" + level +
                ", name='" + name + '\'' +
                '}';
    }
}
