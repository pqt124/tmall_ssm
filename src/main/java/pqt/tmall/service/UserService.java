package pqt.tmall.service;

import pqt.tmall.pojo.User;

import java.util.List;

public interface UserService {
//  int total();
  void add(User user);
  void delete(Integer id);
  void update(User user);
  User get(Integer integer);
  List<User> get(String name,String password);
  List<User> list();

    boolean isExist(String name);
}