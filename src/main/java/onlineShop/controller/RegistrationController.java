
package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.entity.Customer;
import onlineShop.service.CustomerService;

@Controller
public class RegistrationController {//通过Controller，来spring帮你创建RegistrationController

    @Autowired//把已经创建好的java obj inject到当前的class里头（RegistrationController）
    private CustomerService customerService;

    @RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
    public ModelAndView getRegistrationForm() {
        Customer customer = new Customer();//用于validation：jsp里头的path的attribute必须是Customer里的member field（col）的才行
        return new ModelAndView("register", "customer", customer);//viewName是用户看到的页面的名字，modelName是"customer"，
        // 就是把data 和后端的customer绑定，整个页面返回给前端as a jsp，会在jsp中做一个validataion，到jsp那个path
    }

    @RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
    public ModelAndView registerCustomer(@ModelAttribute Customer customer,
                                         BindingResult result) {//绑定的时候会生成一个对象 @ModelAttribute是convert表单数据到Cusomer这个对象
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("register");//个跳转页面是不是setViewName来实现的, 参数是对应的jsp名字
            return modelAndView;
        }
        customerService.addCustomer(customer);
        modelAndView.setViewName("login");
        modelAndView.addObject("registrationSuccess", "Registered Successfully. Login using username and password");
        return modelAndView;
    }
}


