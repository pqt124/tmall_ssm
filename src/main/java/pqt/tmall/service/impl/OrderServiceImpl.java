package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pqt.tmall.mapper.OrderMapper;
import pqt.tmall.pojo.Order;
import pqt.tmall.pojo.OrderExample;
import pqt.tmall.pojo.OrderItem;
import pqt.tmall.service.OrderItemService;
import pqt.tmall.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemService orderItemService;
    @Override
    public List<Order> list(OrderExample example ) {
        return orderMapper.selectByExample(example);
    }
    @Override
    public List list(int uid) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }
    @Override
    public List<Order> list(int uid,String delete){
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(delete);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }
    @Override
    public Order get(Integer integer) {
        return orderMapper.selectByPrimaryKey(integer);
    }
    @Override
    public Order getByOrderCode(String orderCode){
        OrderExample example = new OrderExample();
        example.createCriteria().andOrderCodeEqualTo(orderCode);
         Order order=orderMapper.selectByExample(example).get(0);
        return order;
    }
//    @Override
//    public int total() {
//        return orderMapper.total();
//    }

    @Override
    public void add(Order order) {
         orderMapper.insertSelective(order);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")

    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);

        if(false)
            throw new RuntimeException();

        for (OrderItem oi: ois) {
            oi.setOid(o.getId());
            orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }
    @Override
    public void delete(Integer id) {
         orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
         orderMapper.updateByPrimaryKeySelective(order);
    }
}