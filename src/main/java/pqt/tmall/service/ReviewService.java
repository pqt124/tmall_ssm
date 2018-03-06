package pqt.tmall.service;

import pqt.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {
//  int total();
  void add(Review review);
  void delete(Integer id);
  void update(Review review);
  Review get(Integer integer);
  List<Review> list();
  List<Review> list(int pid);
}