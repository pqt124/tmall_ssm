package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqt.tmall.mapper.OrderItemMapper;
import pqt.tmall.pojo.*;
import pqt.tmall.service.OrderItemService;
import pqt.tmall.service.ProductImageService;
import pqt.tmall.service.ProductService;
import pqt.tmall.service.UserService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Override
    public List<OrderItem> list(OrderItemExample example ) {
        return orderItemMapper.selectByExample(example);
    }
    @Override
    public OrderItem get(Integer integer) {
        return orderItemMapper.selectByPrimaryKey(integer);
    }

//    @Override
//    public int total() {
//        return orderItemMapper.total();
//    }

    @Override
    public void add(OrderItem orderItem) {
         orderItemMapper.insertSelective(orderItem);
    }


    @Override
    public void delete(Integer id) {
         orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
         orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }
    @Override
    public int getSaleCount(int pid){
    OrderItemExample example=new OrderItemExample();
    example.createCriteria().andPidEqualTo(pid);
    List<OrderItem> orderItems=orderItemMapper.selectByExample(example);
    int saleCount=0;
        for (OrderItem oi:orderItems){
            saleCount+=oi.getNumber();
        }
     return  saleCount;
    }
    @Override
    public List<OrderItem> listByUser(Integer uid){
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid);
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems=orderItemMapper.selectByExample(example);
        return orderItems;
    }
    @Override
    public List<OrderItem> listByUidAndNotOid(int uid){

            OrderItemExample example=new OrderItemExample();
            example.createCriteria().andUidEqualTo(uid).andOidIsNull();
            example.setOrderByClause("id desc");
            List<OrderItem> ois=orderItemMapper.selectByExample(example);

        return ois;
    }

    @Override
    public void  fill(List<Order> os){
        for(Order o:os){
            setUser(o);
//            System.out.println("o.getUser().getName()"+o.getUser().getName());
            fill(o);
        }
    }
    @Override
    public void  fill(Order o){
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total=0;//订单总金额
        int totalNumber=0;//订单产品数量
        for (OrderItem oi:ois){
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
            setProductToOderItem(oi);
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);
    }

    public void setProduct(List<OrderItem> ois){
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }
    @Override
    public void setProduct(OrderItem oi) {
        Product p = productService.get(oi.getPid());
        oi.setProduct(p);
        setProductImages(p);
        setFirstImage(p);
    }

    private void setUser(Order o) {
        User u = userService.get(o.getId());
        o.setUser(u);
    }



    private void setProductImages(Product p) {
        String type_single="type_single";
        List<ProductImage> pis=productImageService.list(p.getId(),type_single);
        System.out.println("pis.get(0).getId()"+pis.get(0).getId());
        p.setProductImages(pis);

    }


    public void setProductToOderItem( OrderItem oi){
        Product p=productService.get(oi.getPid());
        oi.setProduct(p);
        setProductImages(p);
        setFirstImage(p);
    }


    public void setFirstImage(Product product){
            Integer firstImage=0;
//            System.out.println("firstImage"+firstImage);
            List<ProductImage> pis = product.getProductImages();
            if (pis!=null){
                firstImage=pis.get(0).getId();
                System.out.println("firstImage:"+firstImage);
                product.setFirstImage(firstImage);
            }
    }
}
