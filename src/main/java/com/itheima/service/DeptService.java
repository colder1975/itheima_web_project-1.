package com.itheima.service;

import com.itheima.pojo.Dept;
import com.itheima.pojo.PageBean;
import java.util.List;

/**
 * 部门服务接口
 */
public interface DeptService {

    // 分页查询
    PageBean page(Integer page, Integer pageSize);
    PageBean page(Integer page, Integer pageSize, String keyword);

    // 查询所有部门
    List<Dept> findAll();

    // 根据ID查询部门
    Dept findById(Integer id);

    // 新增部门
    void add(Dept dept);

    // 更新部门
    void update(Dept dept);

    // 根据ID删除部门
    void deleteById(Integer id);

    // 批量删除
    void deleteByIds(List<Integer> ids);
}
