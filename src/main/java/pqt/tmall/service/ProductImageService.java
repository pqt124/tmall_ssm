package pqt.tmall.service;

import pqt.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {
//  int total();
  String type_single = "type_single";
  String type_detail = "type_detail";
  void add(ProductImage productImage);
  void delete(Integer id);
  void update(ProductImage productImage);
  ProductImage get(Integer integer);
//  ProductImage get(ProductImage productImage);
  List<ProductImage> list(int pid,String  s);
}