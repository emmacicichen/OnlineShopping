
package onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
    //@RequestMapping是告诉spring，这是一个controller的method，会用来handle前端发来的请求的
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String sayIndex() {
        return "index";//return a string which is the view's name
    }

    @RequestMapping("/login")
    //security的框架会redirect出来以下三种情况：让secuirty给我做验证：
    //-> /login （可以没有？后头的key，也就是required = false的意义
    //-> /login?error 如果没验证成功，会给我一个error的url，然后我加到data里头然后给用户返回来
    //-> /login?logout
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,//for authentication
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");//返回的视图名称是login 去WEB-INFO folder里找这名称的.jsp文件，然后把页面返回给用户

        if (error != null) {
            modelAndView.addObject("error", "Invalid username and Password");
        }

        if (logout != null) {
            modelAndView.addObject("logout", "You have logged out successfully");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public String sayAbout() {
        return "aboutUs";
    }
}
