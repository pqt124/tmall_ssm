package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqt.tmall.mapper.ProductMapper;
import pqt.tmall.mapper.PropertyValueMapper;
import pqt.tmall.pojo.*;
import pqt.tmall.service.*;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    UserService userService;
    @Autowired
     ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;
    @Override
    public List list(int cid ) {
        ProductExample example =new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return productMapper.selectByExample(example);
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List<Product> result = productMapper.selectByExample(example);
        for(Product p:result){
            setFirstImage(p);
            setCategory(p);
        }
        return result;
    }
   public void setCategory(Product p){
        Category category=categoryService.get(p.getCid());
        p.setCategory(category);
    }
    @Override
    public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category c : cs) {
            List<Product> products =  c.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void fillToProducts(List<Product> ps){
       for(Product p:ps){
           System.out.println("ppp");
           fillImage(p);
        }
    }

    @Override
    public void fillImage(Product p){
        setProductImages(p);
        setFirstImage(p);
    }
    @Override
    public void fill(List<Category> cs){
        for (Category c:cs){
            fill(c);
        }
    }
    @Override
    public void fill(Category c){
        ProductExample example =new ProductExample();
        example.createCriteria().andCidEqualTo(c.getId());
        example.setOrderByClause("id desc");
        List<Product> products=productMapper.selectByExample(example);
        for(Product p:products){
            setProductImages(p);
            setFirstImage(p);
        }
        c.setProducts(products);

    }
    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p:ps){
            setSaleAndReviewNumber(p);
        }
    }
    @Override
    public void setSaleAndReviewNumber(Product p){
        int saleCount = orderItemService.getSaleCount(p.getId());
        p.setSaleCount(saleCount);

        List<Review> reviews=reviewService.list(p.getId());
        for (Review r:reviews){
            setUserToReview(r.getUid(),r);
        }
        p.setReviewCount(reviews.size());
    }
    @Override
    public void fill(Product p){
    Category c=categoryService.get(p.getCid());
    List<Review> reviews=reviewService.list(p.getId());
    for (Review r:reviews){
        setUserToReview(r.getUid(),r);
    }
    p.setReviewCount(reviews.size());

    int saleCount = orderItemService.getSaleCount(p.getId());
    p.setSaleCount(saleCount);

    setProductDetailImages(p);
    setProductImages(p);
    setFirstImage(p);
    p.setCategory(c);
    setPropertyValue(p);
    List<PropertyValue> propertyValues=p.getPropertyValues();
        for (PropertyValue pt:propertyValues){
            setPropertyToPropertyValue(pt);
     }

//    System.out.println("p.getCategory():"+p.getFirstImage());
    }
    public void setUserToReview(int uid,Review review){
        User user=userService.get(uid);
         review.setUser(user);
    }

    @Override
    public void setPropertyToPropertyValue(PropertyValue propertyValue){
        Property pt=propertyService.get(propertyValue.getPtid());
        propertyValue.setProperty(pt);
    }
    @Override
    public void setPropertyValue(Product p){
        PropertyValueExample example=new PropertyValueExample();
        example.createCriteria().andPidEqualTo(p.getId());
        List<PropertyValue> propertyValues=propertyValueMapper.selectByExample(example);
        p.setPropertyValues(propertyValues);
    }
    @Override
    public void setProductImages(Product p) {
        String type_single="type_single";
        List<ProductImage> pis=productImageService.list(p.getId(),type_single);
//        System.out.println("pis.get(0).getId()"+pis.get(0).getId());
        p.setProductImages(pis);

    }

    @Override
    public void setProductDetailImages(Product p) {
        String type_detail="type_detail";
        List<ProductImage> pis=productImageService.list(p.getId(),type_detail);
        System.out.println("pis.get(0).getId()"+pis.get(0).getId());
        p.setProductDetailImages(pis);
    }
    @Override
    public void setFirstImage(Product product){
        Integer firstImage;
        List<ProductImage> pis = product.getProductImages();
        if (pis!=null){
            firstImage=pis.get(0).getId();
            System.out.println("firstImage"+firstImage);
            product.setFirstImage(firstImage);
        }
    }
    @Override
    public Product get(Integer integer) {
        return productMapper.selectByPrimaryKey(integer);
    }

//    @Override
//    public int total() {
//        return ProductMapper.total();
//    }

    @Override
    public void add(Product product) {
         productMapper.insertSelective(product);
    }

    @Override
    public void delete(Integer id) {
         productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
         productMapper.updateByPrimaryKeySelective(product);
    }
}