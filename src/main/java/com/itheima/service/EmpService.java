package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import java.util.List;

/**
 * 员工服务接口
 */
public interface EmpService {

    // 分页查询
    PageBean page(Integer page, Integer pageSize, Integer deptId, String keyword,
                  String startDate, String endDate);

    // 查询所有员工
    List<Emp> findAll();

    // 根据ID查询员工
    Emp findById(Integer id);

    // 新增员工
    void add(Emp emp);

    // 更新员工
    void update(Emp emp);

    // 根据ID删除员工
    void deleteById(Integer id);

    // 批量删除
    void deleteByIds(List<Integer> ids);

    // 根据部门ID查询员工
    List<Emp> findByDeptId(Integer deptId);
}
