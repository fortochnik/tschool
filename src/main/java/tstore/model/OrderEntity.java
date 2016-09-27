package tstore.model;

import javax.persistence.*;

/**
 * Created by mipan on 25.09.2016.
 */
@Entity
@Table(name = "order", schema = "", catalog = "tstore")
public class OrderEntity {
    private int id;
    private int adress;
    private int payment;
    private int delivery;
    private int basket;
    private int paymentStatus;
    private int orderStatus;
    private int client;
    private AddressEntity adressByAdress;
    private BasketEntity basketByBasket;
    private DeliveryEntity deliveryByDelivery;
    private OrderStatusEntity orderStatusByOrderStatus;
    private PaymentEntity paymentByPayment;
    private PaymentStatusEntity paymentStatusByPaymentStatus;
    private UsersEntity usersByClient;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "adress", nullable = false, insertable = false, updatable = false)
    public int getAdress() {
        return adress;
    }

    public void setAdress(int adress) {
        this.adress = adress;
    }

    @Basic
    @Column(name = "payment", nullable = false, insertable = false, updatable = false)
    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "delivery", nullable = false, insertable = false, updatable = false)
    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    @Basic
    @Column(name = "basket", nullable = false, insertable = false, updatable = false)
    public int getBasket() {
        return basket;
    }

    public void setBasket(int basket) {
        this.basket = basket;
    }

    @Basic
    @Column(name = "payment_status", nullable = false, insertable = false, updatable = false)
    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Basic
    @Column(name = "order_status", nullable = false, insertable = false, updatable = false)
    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic
    @Column(name = "client", nullable = false, insertable = false, updatable = false)
    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (adress != that.adress) return false;
        if (payment != that.payment) return false;
        if (delivery != that.delivery) return false;
        if (basket != that.basket) return false;
        if (paymentStatus != that.paymentStatus) return false;
        if (orderStatus != that.orderStatus) return false;
        if (client != that.client) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + adress;
        result = 31 * result + payment;
        result = 31 * result + delivery;
        result = 31 * result + basket;
        result = 31 * result + paymentStatus;
        result = 31 * result + orderStatus;
        result = 31 * result + client;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "adress", referencedColumnName = "id", nullable = false)
    public AddressEntity getAdressByAdress() {
        return adressByAdress;
    }

    public void setAdressByAdress(AddressEntity adressByAdress) {
        this.adressByAdress = adressByAdress;
    }

    @ManyToOne
    @JoinColumn(name = "basket", referencedColumnName = "id", nullable = false)
    public BasketEntity getBasketByBasket() {
        return basketByBasket;
    }

    public void setBasketByBasket(BasketEntity basketByBasket) {
        this.basketByBasket = basketByBasket;
    }


    @ManyToOne
    @JoinColumn(name = "delivery", referencedColumnName = "id", nullable = false)
    public DeliveryEntity getDeliveryByDelivery() {
        return deliveryByDelivery;
    }

    public void setDeliveryByDelivery(DeliveryEntity deliveryByDelivery) {
        this.deliveryByDelivery = deliveryByDelivery;
    }

    @ManyToOne
    @JoinColumn(name = "order_status", referencedColumnName = "id", nullable = false)
    public OrderStatusEntity getOrderStatusByOrderStatus() {
        return orderStatusByOrderStatus;
    }

    public void setOrderStatusByOrderStatus(OrderStatusEntity orderStatusByOrderStatus) {
        this.orderStatusByOrderStatus = orderStatusByOrderStatus;
    }

    @ManyToOne
    @JoinColumn(name = "payment", referencedColumnName = "id", nullable = false)
    public PaymentEntity getPaymentByPayment() {
        return paymentByPayment;
    }

    public void setPaymentByPayment(PaymentEntity paymentByPayment) {
        this.paymentByPayment = paymentByPayment;
    }

    @ManyToOne
    @JoinColumn(name = "payment_status", referencedColumnName = "id", nullable = false)
    public PaymentStatusEntity getPaymentStatusByPaymentStatus() {
        return paymentStatusByPaymentStatus;
    }

    public void setPaymentStatusByPaymentStatus(PaymentStatusEntity paymentStatusByPaymentStatus) {
        this.paymentStatusByPaymentStatus = paymentStatusByPaymentStatus;
    }

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id", nullable = false)
    public UsersEntity getUsersByClient() {
        return usersByClient;
    }

    public void setUsersByClient(UsersEntity usersByClient) {
        this.usersByClient = usersByClient;
    }
}
