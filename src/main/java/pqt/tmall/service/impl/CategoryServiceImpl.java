package pqt.tmall.service.impl;
import pqt.tmall.pojo.CategoryExample;
import pqt.tmall.util.Page;
import pqt.tmall.mapper.CategoryMapper;
import pqt.tmall.pojo.Category;
import pqt.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CategoryServiceImpl  implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<Category> list() {
        CategoryExample example =new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }
    @Override
    public Category get(Integer integer) {
        return categoryMapper.selectByPrimaryKey(integer);
    }

//    @Override
//    public int total() {
//        return categoryMapper.total();
//    }

    @Override
    public void add(Category category) {
         categoryMapper.insertSelective(category);
    }

    @Override
    public void delete(Integer id) {
         categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
         categoryMapper.updateByPrimaryKeySelective(category);
    }
}