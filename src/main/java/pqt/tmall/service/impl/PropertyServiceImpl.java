package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqt.tmall.mapper.PropertyMapper;
import pqt.tmall.pojo.Property;
import pqt.tmall.pojo.PropertyExample;
import pqt.tmall.service.PropertyService;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;
    @Override
    public List list(int cid ) {
        PropertyExample example =new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return propertyMapper.selectByExample(example);
    }
    @Override
    public Property get(Integer integer) {
        return propertyMapper.selectByPrimaryKey(integer);
    }

//    @Override
//    public int total() {
//        return PropertyMapper.total();
//    }

    @Override
    public void add(Property Property) {
         propertyMapper.insertSelective(Property);
    }

    @Override
    public void delete(Integer id) {
         propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property property) {
         propertyMapper.updateByPrimaryKeySelective(property);
    }
}