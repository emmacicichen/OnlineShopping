package onlineShop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 5186013952828648626L;//serialization version number, when deserization,
    //it needs this one to confirm(jin今天保存了java obj放到数据库里，我有这个带着，下次我来的时候，更新的时候我要检测这个版本号
    //是不是对的上

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "category")
    private String productCategory;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "manufacturer")
    private String productManufacturer;

    @Column(name = "name")
    private String productName;

    @Column(name = "price")
    private double productPrice;

    @Column(name = "unit")
    private String unitStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductManufacturer() {
        return productManufacturer;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getUnitStock() {
        return unitStock;
    }

    public void setUnitStock(String unitStock) {
        this.unitStock = unitStock;
    }
}
