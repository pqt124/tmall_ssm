package pqt.tmall.controller;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import pqt.tmall.pojo.*;
import pqt.tmall.service.*;
import tmall.comparator.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;
    //前台主页面
    @RequestMapping("home")
    public String home(Model model) {
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    //注册页面
    @RequestMapping("registerPage")
    public String register(Model model) {
        return "fore/register";
    }

    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if (exist) {
            String m = "用户名已经被使用,不能使用";
            model.addAttribute("msg", m);


            return "fore/register";
        }
        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    @RequestMapping("loginPage")
    public String login(User user, Model model) {
        return "fore/login";
    }

    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        List<User> users = userService.get(name, password);

        if (null == users) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", users.get(0));
        return "redirect:/home";
    }

    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/home";
    }

    @RequestMapping("foreproduct")
    public String foreproduct(int pid, Model model, HttpSession session) {

        Product p = productService.get(pid);
        productService.fill(p);
        List<PropertyValue> ptvs = p.getPropertyValues();
        List<Review> reviews = reviewService.list(pid);
        System.out.println("p.getFirstImage():" + p.getProductDetailImages());
        model.addAttribute("p", p);
        model.addAttribute("ptvs", ptvs);
        model.addAttribute("reviews", reviews);

        return "fore/product";
//        return "include/product/productDetail";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user != null)
            return "success";
        return "fail";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        String Name =HtmlUtils.htmlEscape(name);
        System.out.println(Name);
        List<User> users = userService.get(Name, password);
//        System.out.println(users);
        if (null == users) {
            return "fail";
        }
        session.setAttribute("user", users.get(0));
        return "success";
    }

    @RequestMapping("forecategory")
    public String forecategory(int cid, String sort, Model model) {
        Category c = categoryService.get(cid);
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());

        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;

                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
            }

        }
        model.addAttribute("c", c);
        return "fore/category";
    }
    @RequestMapping("foresearch")
    public String search( String keyword,Model model){

        PageHelper.offsetPage(0,20);
        List<Product> ps= productService.search(keyword);
        productService.fillToProducts(ps);
        productService.setSaleAndReviewNumber(ps);
        model.addAttribute("ps",ps);
        return "fore/searchResult";
    }
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session,Model model) {
        Product p = productService.get(pid);
        float total = 0;
        User user =(User)  session.getAttribute("user");
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.setProduct(oi);
        List<OrderItem> ois=new ArrayList<>();
        ois.add(oi);
        total +=oi.getProduct().getPromotePrice()*oi.getNumber();
        session.setAttribute("oi"+user.getId(), ois.get(0));
        model.addAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buy";
    }


    @RequestMapping("forebuycart")
    public String buy( Model model,String[] oiid,HttpSession session){
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;
        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi= orderItemService.get(id);
            orderItemService.setProduct(oi);
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            ois.add(oi);
        }
        session.setAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buyCart";
    }
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model,HttpSession session) {
        Product p = productService.get(pid);
        User user =(User)session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUidAndNotOid(user.getId());
        for (OrderItem oi : ois) {
            orderItemService.setProduct(oi);
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }

        return "success";
    }
    @RequestMapping("forecart")
    public String forecart(Model model,HttpSession session){
        User user=(User)session.getAttribute("user");
        List<OrderItem> cart=orderItemService.listByUidAndNotOid(user.getId());
        for(OrderItem oi:cart){
            orderItemService.setProduct(oi);
        }
        model.addAttribute("ois",cart);
        return "fore/cart";
    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem( Model model,HttpSession session, int pid, int number) {
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";

        List<OrderItem> ois=orderItemService.listByUidAndNotOid(user.getId());

        for (OrderItem oi : ois) {
            orderItemService.setProduct(oi);
            if(oi.getProduct().getId().intValue()==pid){
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }

        }
        session.setAttribute("ois",ois);
        return "success";
    }
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem( Model model,HttpSession session,int oiid){
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";
        orderItemService.delete(oiid);
        return "success";
    }


    @RequestMapping("forecreateOrder")
    public String createOrder( Model model,Order order,HttpSession session){
        User user =(User)  session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        orderService.add(order);
        Order order1=orderService.getByOrderCode(orderCode);
        OrderItem oi=(OrderItem) session.getAttribute("oi"+user.getId());
        System.out.println("oi:"+oi.getId());
        oi.setOid(order1.getId());
        orderItemService.add(oi);

        return "redirect:forealipay?oid="+order.getId();
    }
    @RequestMapping("forecreateOrderByCart")
    public String forecreateOrderByCart( Model model,Order order,HttpSession session){
        User user =(User)  session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        orderService.add(order);
        Order order1=orderService.getByOrderCode(orderCode);
        List<OrderItem> ois=orderItemService.listByUidAndNotOid(user.getId());
        for(OrderItem oi:ois){
            oi.setOid(order1.getId());
            orderItemService.update(oi);
        }
        return "redirect:forealipay?oid="+order.getId();
    }
    @RequestMapping("forealipay")
    public String forealipay( Model model,String[] oid,HttpSession session){
        float total = 0;
        User user=(User) session.getAttribute("user");
        List<OrderItem> ois =orderItemService.listByUser(user.getId());
        for(OrderItem oi:ois){
            orderItemService.setProduct(oi);
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        model.addAttribute("total", total);
        return "fore/alipay";
    }

    //订单支付
    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }
    @RequestMapping("forebought")
    public String bought(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Order> os = orderService.list(user.getId(),orderService.delete);
        orderItemService.fill(os);
        model.addAttribute("os",os);
        return "fore/bought";
    }
    @RequestMapping("foreconfirmPay")
    public String confirmPay(Model model,int oid){
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        model.addAttribute("o",o);
        return "fore/confirmPay";

    }
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(Model model,int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return "fore/orderConfirmed";
    }
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Model model,int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return "success";
    }
    @RequestMapping("forereview")
    public String review(Model model,int oid){
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("p",p);
        model.addAttribute("o",o);
        model.addAttribute("reviews",reviews);
        return "fore/review";
    }
    @RequestMapping("foredoreview")
    @ResponseBody
    public String doreview(Model model,HttpSession session,@RequestParam("oid") int oid
            ,@RequestParam("pid") int pid,String content){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        Product p = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user = (User) session.getAttribute("user");

        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);

        return "redirect:forereview?oid="+oid+"&showonly=true";
    }

}

