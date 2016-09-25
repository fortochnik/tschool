package tstore.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by mipan on 25.09.2016.
 */
@Entity
@Table(name = "product", schema = "", catalog = "tstore")
public class ProductEntity {
    private int id;
    private String name;
    private BigDecimal price;
    private int category;
    private String description;
    private double weight;
    private Double volume;
    private int count;
    private Collection<BasketEntity> basketsById;
    private Collection<PictureEntity> picturesById;
    private CategoryEntity categoryByCategory;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "category", nullable = false, insertable = false, updatable = false)
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 16777215)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "weight", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "volume", nullable = true, insertable = true, updatable = true, precision = 0)
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Basic
    @Column(name = "count", nullable = false, insertable = true, updatable = true)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (id != that.id) return false;
        if (category != that.category) return false;
        if (Double.compare(that.weight, weight) != 0) return false;
        if (count != that.count) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + category;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }

    @OneToMany(mappedBy = "productByProduct")
    public Collection<BasketEntity> getBasketsById() {
        return basketsById;
    }

    public void setBasketsById(Collection<BasketEntity> basketsById) {
        this.basketsById = basketsById;
    }

    @OneToMany(mappedBy = "productByProductId")
    public Collection<PictureEntity> getPicturesById() {
        return picturesById;
    }

    public void setPicturesById(Collection<PictureEntity> picturesById) {
        this.picturesById = picturesById;
    }

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    public CategoryEntity getCategoryByCategory() {
        return categoryByCategory;
    }

    public void setCategoryByCategory(CategoryEntity categoryByCategory) {
        this.categoryByCategory = categoryByCategory;
    }
}
