package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.pojo.PageBean;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        return page(page, pageSize, null);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String keyword) {
        // PageHelper.startPage 必须在 Mapper 查询之前调用
        PageHelper.startPage(page, pageSize);
        List<Dept> rows;
        if (keyword != null && !keyword.trim().isEmpty()) {
            rows = deptMapper.search(keyword.trim());
        } else {
            rows = deptMapper.findAll();
        }
        // PageHelper 拦截器自动设置了 total 和 rows
        return new PageBean(((Page<Dept>) rows).getTotal(), rows);
    }

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public Dept findById(Integer id) {
        return deptMapper.findById(id);
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        deptMapper.deleteByIds(ids);
    }
}
