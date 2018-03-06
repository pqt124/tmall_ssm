package pqt.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pqt.tmall.pojo.Category;
import pqt.tmall.pojo.Product;
import pqt.tmall.pojo.PropertyValue;
import pqt.tmall.service.CategoryService;
import pqt.tmall.service.ProductService;
import pqt.tmall.service.PropertyService;
import pqt.tmall.service.PropertyValueService;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model,Integer pid) {
        Product p = productService.get(pid);
//        propertyValueService.init(p);
        List<PropertyValue> ps = propertyValueService.list(pid);
//        Product p = productService.get(pid);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);
        model.addAttribute("ps", ps);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    public String editDo(PropertyValue propertyValue){
        propertyValueService.update(propertyValue);
        return "redirect:/admin_propertyValue_edit?cid="+propertyValue.getPid();
    }


}
