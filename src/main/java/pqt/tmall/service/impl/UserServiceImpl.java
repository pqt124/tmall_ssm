package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqt.tmall.mapper.UserMapper;
import pqt.tmall.pojo.User;
import pqt.tmall.pojo.UserExample;
import pqt.tmall.service.UserService;
import sun.invoke.empty.Empty;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> list() {
        UserExample example=new UserExample();
        example.createCriteria();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }
    @Override
    public User get(Integer integer) {
        return userMapper.selectByPrimaryKey(integer);
    }
    @Override
    public List<User> get(String name ,String password) {
        UserExample example =new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        return userMapper.selectByExample(example);
    }

    @Override
    public void add(User User) {
         userMapper.insertSelective(User);
    }

    @Override
    public void delete(Integer id) {
         userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User User) {
         userMapper.updateByPrimaryKeySelective(User);
    }

    @Override
    public boolean isExist(String name){
        boolean staus=false;
        UserExample example=new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users=userMapper.selectByExample(example);
        if (!users.isEmpty()){
          staus=true;
        }
        return staus;
    }
}