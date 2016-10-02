package tstore.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by mipan on 29.09.2016.
 */
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private CategoryEntity category;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "count")
    private int count;

    @Column(name = "company")
    private String company;

    @OneToMany(mappedBy = "product")
    Set<ProductListEntity> productListEntitySet;

    @OneToMany(mappedBy = "product")
    private Set<ImageEntity> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Set<ProductListEntity> getProductListEntitySet() {
        return productListEntitySet;
    }

    public void setProductListEntitySet(Set<ProductListEntity> productListEntitySet) {
        this.productListEntitySet = productListEntitySet;
    }

    public Set<ImageEntity> getImages() {
        return images;
    }

    public void setImages(Set<ImageEntity> images) {
        this.images = images;
    }
}
