package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    /** 员工默认密码 */
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 应用启动时自动将已有员工的空密码设为默认值
     */
    @PostConstruct
    public void initDefaultPasswords() {
        int updated = empMapper.updateEmptyPasswords(DEFAULT_PASSWORD);
        if (updated > 0) {
            System.out.println("已为 " + updated + " 名员工设置默认密码: " + DEFAULT_PASSWORD);
        }
    }

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
        // 密码为空则使用默认密码
        if (emp.getPassword() == null || emp.getPassword().trim().isEmpty()) {
            emp.setPassword(DEFAULT_PASSWORD);
        }
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public void update(Emp emp) {
        // 密码为空则使用默认密码
        if (emp.getPassword() == null || emp.getPassword().trim().isEmpty()) {
            emp.setPassword(DEFAULT_PASSWORD);
        }
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

    @Override
    public String login(String username, String password) {
        // 1. 根据用户名查询员工
        Emp emp = empMapper.findByUsername(username);
        if (emp == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 2. 校验密码（明文比对）
        if (!emp.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 3. 生成并返回JWT令牌
        return jwtUtils.generateToken(emp);
    }
}
