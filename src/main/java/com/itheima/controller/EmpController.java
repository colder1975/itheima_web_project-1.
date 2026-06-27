package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    // 分页查询（可选部门筛选 + 搜索 + 日期范围）
    @GetMapping
    public Result list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestParam(required = false) Integer deptId,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate) {
        PageBean pb = empService.page(page, pageSize, deptId, keyword, startDate, endDate);
        return Result.success(pb);
    }

    // 根据ID查询员工
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Emp emp = empService.findById(id);
        return Result.success(emp);
    }

    // 新增员工
    @PostMapping
    public Result add(@RequestBody Emp emp) {
        empService.add(emp);
        return Result.success();
    }

    // 更新员工
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        empService.update(emp);
        return Result.success();
    }

    // 根据ID删除员工
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        empService.deleteById(id);
        return Result.success();
    }

    // 批量删除
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        empService.deleteByIds(ids);
        return Result.success();
    }
}
