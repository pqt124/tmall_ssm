package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqt.tmall.mapper.PropertyValueMapper;
import pqt.tmall.pojo.Product;
import pqt.tmall.pojo.Property;
import pqt.tmall.pojo.PropertyValue;
import pqt.tmall.pojo.PropertyValueExample;
import pqt.tmall.service.PropertyService;
import pqt.tmall.service.PropertyValueService;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product p) {

        List<Property> pts = propertyService.list(p.getCid());

        for (Property pt: pts) {
            PropertyValue pv = get(pt.getId(),p.getId());
            if(null==pv){
                pv = new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }

    }
    @Override
    public List list(int Pid ) {
        PropertyValueExample example =new PropertyValueExample();
        example.createCriteria().andPidEqualTo(Pid);
        List<PropertyValue> result = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : result) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
            System.out.println("PropertyValue"+pv);
        }
        return result;
    }
    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pvs= propertyValueMapper.selectByExample(example);
        if (pvs.isEmpty())
            return null;
        return pvs.get(0);
    }

//    @Override
//    public int total() {
//        return PropertyMapper.total();
//    }

    @Override
    public void add(PropertyValue propertyValue) {
         propertyValueMapper.insertSelective(propertyValue);
    }

    @Override
    public void delete(Integer id) {
         propertyValueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(PropertyValue propertyValue) {
         propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }
}