package pqt.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqt.tmall.mapper.ReviewMapper;
import pqt.tmall.pojo.Review;
import pqt.tmall.pojo.ReviewExample;
import pqt.tmall.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Override
    public List<Review> list() {
        ReviewExample example=new ReviewExample();
        example.createCriteria();
        example.setOrderByClause("id desc");
        return reviewMapper.selectByExample(example);
    }
    @Override
    public List<Review> list(int pid) {
        ReviewExample example=new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        return reviewMapper.selectByExample(example);
    }
    @Override
    public Review get(Integer integer) {
        return reviewMapper.selectByPrimaryKey(integer);
    }

    @Override
    public void add(Review Review) {
         reviewMapper.insertSelective(Review);
    }

    @Override
    public void delete(Integer id) {
         reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review Review) {
         reviewMapper.updateByPrimaryKeySelective(Review);
    }

}