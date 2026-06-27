package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, Integer deptId, String keyword,
                         String startDate, String endDate) {
        PageHelper.startPage(page, pageSize);
        // 统一使用动态 filter，所有条件可选
        List<Emp> rows = empMapper.filter(deptId,
                keyword != null && !keyword.trim().isEmpty() ? keyword.trim() : null,
                startDate, endDate);
        return new PageBean(((Page<Emp>) rows).getTotal(), rows);
    }

    @Override
    public List<Emp> findAll() {
        return empMapper.findAll();
    }

    @Override
    public Emp findById(Integer id) {
        return empMapper.findById(id);
    }

    @Override
    public void add(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Override
    public void deleteById(Integer id) {
        empMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        empMapper.deleteByIds(ids);
    }

    @Override
    public List<Emp> findByDeptId(Integer deptId) {
        return empMapper.findByDeptId(deptId);
    }
}
