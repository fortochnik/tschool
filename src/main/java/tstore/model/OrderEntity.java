package tstore.model;

import tstore.model.enums.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by mipan on 29.09.2016.
 */
@Entity
@Table(name = "consignment")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private UserEntity client;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private AddressEntity address;

    @Column(name = "payment_type", length = 45)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;


    @Column(name = "payment_status", length = 45)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "order_status", length = 45)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "delivery_type", length = 45)
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Column(name = "order_date", length = 45)
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "delivery_date", length = 45)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @Column(name = "state", length = 45)
    @Enumerated(EnumType.STRING)
    private BasketOrderState state;

    @OneToMany(mappedBy = "consignment", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<ProductListEntity> productList;


    public OrderEntity() {
    }

    /**
     * use tis constructor for create new basket with state {@link BasketOrderState#BASKET) and order status {@link OrderStatus#DRAFT)
     */
    public OrderEntity(UserEntity client) {
        this.client = client;
        this.state = BasketOrderState.BASKET;

        this.orderStatus = OrderStatus.DRAFT;
    }

    public OrderEntity(UserEntity client, AddressEntity address, PaymentType paymentType,
                       PaymentStatus paymentStatus, OrderStatus orderStatus, DeliveryType deliveryType,
                       Date orderDate, Date deliveryDate, BasketOrderState state) {
        this.client = client;
        this.address = address;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.deliveryType = deliveryType;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getClient() {
        return client;
    }

    public void setClient(UserEntity clientId) {
        this.client = clientId;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Set<ProductListEntity> getProductList() {
        return productList;
    }

    public void setProductList(Set<ProductListEntity> productList) {
        this.productList = productList;
    }


    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BasketOrderState getState() {
        return state;
    }

    public void setState(BasketOrderState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (!client.equals(that.client)) return false;
        return state == that.state;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + client.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", state=" + state +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
