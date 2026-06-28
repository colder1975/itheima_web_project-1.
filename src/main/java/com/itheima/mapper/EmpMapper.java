package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 员工数据访问层
 */
@Mapper
public interface EmpMapper {

    List<Emp> findAll();

    Emp findById(@Param("id") Integer id);

    int insert(Emp emp);

    int update(Emp emp);

    int deleteById(@Param("id") Integer id);

    int deleteByIds(@Param("ids") List<Integer> ids);

    List<Emp> findByDeptId(@Param("deptId") Integer deptId);

    // 模糊搜索：按姓名或ID（PageHelper 自动分页）
    List<Emp> search(@Param("keyword") String keyword);

    // 多条件筛选（部门 + 姓名/ID + 日期范围）
    List<Emp> filter(@Param("deptId") Integer deptId,
                     @Param("keyword") String keyword,
                     @Param("startDate") String startDate,
                     @Param("endDate") String endDate);

    /**
     * 将密码为空的员工密码更新为默认值
     * @param defaultPassword 默认密码
     * @return 更新行数
     */
    int updateEmptyPasswords(@Param("defaultPassword") String defaultPassword);
}
