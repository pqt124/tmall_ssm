package pqt.tmall.service;

import pqt.tmall.pojo.Category;

import java.util.List;

public interface CategoryService{
//  int total();
  void add(Category category);
  void delete(Integer id);
  void update(Category category);
  Category get(Integer integer);
  List<Category> list();
}