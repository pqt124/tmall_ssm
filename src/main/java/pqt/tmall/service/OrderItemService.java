package pqt.tmall.service;

import pqt.tmall.pojo.Order;
import pqt.tmall.pojo.OrderItem;
import pqt.tmall.pojo.OrderItemExample;

import java.util.List;

public interface OrderItemService {
//  int total();
  void add(OrderItem orderItem);
  void delete(Integer id);
  void update(OrderItem orderItem);
  OrderItem get(Integer integer);
  List<OrderItem> list(OrderItemExample example);
  void fill(List<Order> os);
  void fill(Order s);
  int getSaleCount(int id);
  List<OrderItem> listByUser(Integer pid);
  void setProduct(OrderItem orderItem);
   List<OrderItem> listByUidAndNotOid(int uid);
}