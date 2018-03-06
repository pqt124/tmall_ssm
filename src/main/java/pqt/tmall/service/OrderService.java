package pqt.tmall.service;

import pqt.tmall.pojo.Order;
import pqt.tmall.pojo.OrderExample;
import pqt.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    //  int total();
  void add(Order order);
  float add(Order order, List<OrderItem> orderItems);
  void delete(Integer id);
  void update(Order order);
  Order get(Integer integer);
  List<Order> list(OrderExample example);
  List<Order> list(int uid);
  List<Order> list(int uid,String delete);

    Order getByOrderCode(String orderCode);
}