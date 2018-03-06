package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqt.tmall.mapper.ProductImageMapper;
import pqt.tmall.pojo.Product;
import pqt.tmall.pojo.ProductImage;
import pqt.tmall.pojo.ProductImageExample;
import pqt.tmall.service.ProductImageService;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageMapper productImageMapper;
    @Override
    public List list(int pid,String type) {
        ProductImageExample example =new ProductImageExample();
        example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        example.setOrderByClause("id desc");
        return productImageMapper.selectByExample(example);
    }
    @Override
    public ProductImage get(Integer integer) {
        return productImageMapper.selectByPrimaryKey(integer);
    }
 /*   @Override
    public ProductImage get(ProductImage productImage) {
        return productImageMapper.selectByPidAndType(productImage);
    }*/

//    @Override
//    public int total() {
//        return productImageMapper.total();
//    }

    @Override
    public void add(ProductImage productImage) {
         productImageMapper.insertSelective(productImage);
    }

    @Override
    public void delete(Integer id) {
         productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage productImage) {
         productImageMapper.updateByPrimaryKeySelective(productImage);
    }
}