package com.activiti.mapper;

import com.activiti.bean.LeaveInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Mirko on 2019/11/15.
 */
@Mapper
public interface LeaveInfoMapper {


    @Select("select * from leave_info")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "status", column = "status"),
            @Result(property = "leaveMsg", column = "leave_msg")
    })
    List<LeaveInfo> getAll();


    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO leave_info (id,status,leave_msg) VALUES(#{id}, #{status}, #{leaveMsg})")
    void insert(LeaveInfo leaveInfo);


    @Select("select * from leave_info where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "status", column = "status"),
            @Result(property = "leaveMsg", column = "leave_msg")
    })
    LeaveInfo getById(String id);

    @Update(" update leave_info set status= #{status} , leave_msg = #{leaveMsg} where id = #{id}")
    void update(LeaveInfo entity);
}
