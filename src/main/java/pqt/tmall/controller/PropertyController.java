package pqt.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pqt.tmall.pojo.Category;
import pqt.tmall.pojo.Property;
import pqt.tmall.service.CategoryService;
import pqt.tmall.service.PropertyService;
import pqt.tmall.util.Page;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("admin_property_list")
    public String list(int cid ,Model model, Page page) {
        Category c = categoryService.get(cid);
        System.out.println(cid);
        List<Property> cs = propertyService.list(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total = (int)new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());
        model.addAttribute("ps", cs);
        model.addAttribute("cid", cid);
        model.addAttribute("page", page);
        return "admin/listProperty1";
    }

    @RequestMapping("admin_property_delete")
    public String delete(Integer id) {
        Property p=propertyService.get(id);
        propertyService.delete(id);

        return "redirect:/admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(Model model,Integer id) {
        Property p= propertyService.get(id);
        model.addAttribute("p", p);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String editDo(Property property){
        propertyService.update(property);
        return "redirect:/admin_property_list?cid="+property.getCid();
    }

    @RequestMapping("admin_property_add")
    public String add(Property p,Model model){
      propertyService.add(p);
      model.addAttribute("cid",p.getCid());
        return "redirect:/admin_property_list";
    }
}
