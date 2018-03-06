package pqt.tmall.service;

import pqt.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {
//  int total();
  void add(Property Property);
  void delete(Integer id);
  void update(Property Property);
  Property get(Integer integer);
   List list(int cid);
}