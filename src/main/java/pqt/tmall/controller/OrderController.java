package pqt.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pqt.tmall.pojo.Order;
import pqt.tmall.pojo.OrderExample;
import pqt.tmall.service.OrderItemService;
import pqt.tmall.service.OrderService;
import pqt.tmall.service.ProductService;
import pqt.tmall.util.Page;
import pqt.tmall.util.UploadedImageFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;
    @RequestMapping("admin_order_list")
    public String list(Model model, Page page,OrderExample example) {
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Order> os = orderService.list(example);
        int total = (int)new PageInfo<>(os).getTotal();
        page.setTotal(total);
        orderItemService.fill(os);
        model.addAttribute("os", os);
        model.addAttribute("page", page);
        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delete")
    public String delete(Integer id ) {
        orderService.delete(id);
        return "redirect:/admin_order_list";
    }

    @RequestMapping("admin_order_edit")
    public String edit(Model model, Integer id) {
        Order c = orderService.get(id);
        model.addAttribute("c", c);
        return "admin/editOrder";
}

    @RequestMapping("admin_order_edit_do")
    public String editDo(Order order)  {
        orderService.update(order);
        return "redirect:/admin_order_list";
    }

    @RequestMapping("admin_order_add")
    public String add(Order c, HttpSession session, UploadedImageFile uploadedImageFile)   {
        orderService.add(c);
        return "redirect:/admin_order_list";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(Order o) throws IOException {
        System.out.println("哈哈撒");
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return "redirect:admin_order_list";
    }
}
