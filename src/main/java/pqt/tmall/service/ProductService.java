package pqt.tmall.service;

import pqt.tmall.pojo.Category;
import pqt.tmall.pojo.Product;
import pqt.tmall.pojo.PropertyValue;

import java.util.List;

public interface ProductService {
//  int total();
  void add(Product product);
  void delete(Integer id);
  void update(Product product);
  Product get(Integer integer);
  List<Product> list(int cid);
  void fill(List<Category> cs);
  void fill(Product p);
  void fill(Category c);
  void fillToProducts(List<Product> ps);
  void fillImage(Product p);
  void fillByRow(List<Category> cs);
  void setProductImages(Product p);
  void setFirstImage(Product p);
  void setProductDetailImages(Product p);
  void setPropertyToPropertyValue(PropertyValue propertyValue);
  void setPropertyValue(Product p);
  void setSaleAndReviewNumber(List<Product> products);
  void setSaleAndReviewNumber(Product p);

  List<Product> search(String keyword);
//  void  setSaleAndReviewNumber(Product p);
}