
package onlineShop.controller;

import onlineShop.entity.Product;
import onlineShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller //MVC启动的时候要被create
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public ModelAndView getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ModelAndView("productList", "products", products);//返回一个要展示的页面，（去找jsp文件）
    }//jsp里，&{products}是要返回的data： viewName:jsp的文件名，modelName：页面上的名字

    @RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET)//frontend传进来的url里的productId，map到method里
    public ModelAndView getProductById(@PathVariable(value = "productId") int productId) {
        Product product = productService.getProductById(productId);
        return new ModelAndView("productPage", "product", product);
    }

    @RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET)//GET：得到表单
    public ModelAndView getProductForm() {
        return new ModelAndView("addProduct", "productForm", new Product());
    }
//new Product() 保证下面post请求的conversion中 下面42行 给信息填进这个空页面 （new Product()在这里先返回一个空的product给前端页面，间接的verify一下，有没有错，比后面填数据的时候再错要好
    //返回一个ModelAndView（jsp），jsp名字是"addProduct",到jsp里找
    @RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST)//POST：添加商品
    public String addProduct(@ModelAttribute Product product, BindingResult result) {//BindingResult 为了查error
        if (result.hasErrors()) {
            return "addProduct";
        }
        productService.addProduct(product);
        return "redirect:/getAllProducts"; //添加成功后，跳转到getAllProducts，（在上面呢）
    }

    @RequestMapping(value = "/admin/delete/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") int productId) {
        productService.deleteProduct(productId);
        return "redirect:/getAllProducts";//redirect是一个keyword，告诉dispatchservlet
    }

    @RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.GET)//GET：找到一个表单
    public ModelAndView getEditForm(@PathVariable(value = "productId") int productId) {
        Product product = productService.getProductById(productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editProduct");
        modelAndView.addObject("editProductObj", product);//product本身
        modelAndView.addObject("productId", productId);//product的primary key

        return modelAndView;
    }

    @RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute Product product,
                              @PathVariable(value = "productId") int productId) {
        product.setId(productId);//udate的时候要加上id，告诉他我们update的是一个已经存在的商品，否则他会生成一个新的
        productService.updateProduct(product);
        return "redirect:/getAllProducts";
    }
}


