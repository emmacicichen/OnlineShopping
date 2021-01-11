package onlineShop.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 8436097833452420298L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(mappedBy = "cart")//默认会创建FK，但是mappedBy，我不在这里创建FK，通过mappedBy的值 cart，Customer里面
    @JsonIgnore//有个变量名叫cart（Customer里41行）
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch=FetchType.EAGER)//
    private List<CartItem> cartItem;

    private double totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
