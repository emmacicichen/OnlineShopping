
package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.entity.Cart;
import onlineShop.entity.Customer;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;

@Controller
public class CartController {//通过cartid，来拿到customer的购物车，那么cartid在哪：customer有个FK是cart，
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cart/getCartById", method = RequestMethod.GET)
    public ModelAndView getCartId(){
        ModelAndView modelAndView = new ModelAndView("cart");
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        Customer customer = customerService.getCustomerByUserName(username);
        modelAndView.addObject("cartId", customer.getCart().getId());
        return modelAndView;
    }

    @RequestMapping(value = "/cart/getCart/{cartId}", method = RequestMethod.GET)
    @ResponseBody//这个annotation，给一个Cart class，我就给前端返回一个json （可以去前端测试看看）
    public Cart getCartItems(@PathVariable(value="cartId")int cartId){
        return cartService.getCartById(cartId);
    }
}


