package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;
    // 分页查询（可选搜索）
    @GetMapping
    public Result list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestParam(required = false) String keyword) {
        PageBean pb = deptService.page(page, pageSize, keyword);
        return Result.success(pb);
    }

    // 根据ID查询部门
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Dept dept = deptService.findById(id);
        return Result.success(dept);
    }

    // 新增部门
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        deptService.add(dept);
        return Result.success();
    }

    // 更新部门
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        deptService.update(dept);
        return Result.success();
    }

    // 根据ID删除部门
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        deptService.deleteById(id);
        return Result.success();
    }

    // 批量删除
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        deptService.deleteByIds(ids);
        return Result.success();
    }
}
