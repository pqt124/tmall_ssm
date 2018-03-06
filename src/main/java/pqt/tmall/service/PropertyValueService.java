package pqt.tmall.service;

import pqt.tmall.pojo.Product;
import pqt.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
//  int total();
  void add(PropertyValue propertyValue);
  void delete(Integer id);
  void update(PropertyValue propertyValue);
  PropertyValue get(int ptid, int pid);
   List list(int pid);
   void init(Product product);
}