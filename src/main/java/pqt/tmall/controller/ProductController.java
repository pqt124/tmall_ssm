package pqt.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pqt.tmall.pojo.Category;
import pqt.tmall.pojo.Product;
import pqt.tmall.service.CategoryService;
import pqt.tmall.service.ProductService;
import pqt.tmall.util.Page;

import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String list(int cid,Model model, Page page) {
//        System.out.println("pp");
        Category c = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> ps = productService.list(cid);
        int total = (int)new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());
        model.addAttribute("ps", ps);
        model.addAttribute("page", page);
        model.addAttribute("c", c);
        return "admin/listProduct";
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product p = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(Model model, Integer id) {
        Product p = productService.get(id);
        model.addAttribute("p", p);
        return "admin/editProduct";
}

    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_add")
    public String add(Product p){
        productService.add(p);
        System.out.println("p.getCid()"+p.getName());
        return "redirect:/admin_product_list?cid="+p.getCid();
    }
}
