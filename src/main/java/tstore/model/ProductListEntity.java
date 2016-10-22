package tstore.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by mipan on 29.09.2016.
 */
@Entity
@Table(name = "product_list")
public class ProductListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private ProductEntity product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "count", nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private OrderEntity consignment;

    public ProductListEntity() {
    }

    public ProductListEntity(ProductEntity product, int count, OrderEntity consignment)  {
        this.count = count;
        this.consignment = consignment;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
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

    public OrderEntity getConsignment() {
        return consignment;
    }

    public void setConsignment(OrderEntity orderId) {
        this.consignment = orderId;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "id=" + id +
                ", productId=" + product +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
