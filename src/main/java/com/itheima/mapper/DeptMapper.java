package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 部门数据访问层
 */
@Mapper
public interface DeptMapper {

    List<Dept> findAll();

    Dept findById(@Param("id") Integer id);

    int insert(Dept dept);

    int update(Dept dept);

    int deleteById(@Param("id") Integer id);

    int deleteByIds(@Param("ids") List<Integer> ids);

    // 模糊搜索：按名称或ID（PageHelper 自动分页）
    List<Dept> search(@Param("keyword") String keyword);
}
