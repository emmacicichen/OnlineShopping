
package onlineShop.service;

import onlineShop.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CustomerDao;
import onlineShop.entity.Customer;

@Service//也是spring中特定指业务逻辑的一个component，用@Component也是可以的 用@Service更直观
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;//Dao只跟数据库有关，你让他增删改查数据库

    public void addCustomer(Customer customer) {//创建customer时候 我要给他一个购物车
        customer.getUser().setEnabled(true);//setEnable是springsecurity框架设置用户的时候就给set成true，注销用户的时候就是set false。（soft delete）

        Cart cart = new Cart();
        customer.setCart(cart);

        customerDao.addCustomer(customer);
    }

    public Customer getCustomerByUserName(String userName) {
        return customerDao.getCustomerByUserName(userName);
    }
}


