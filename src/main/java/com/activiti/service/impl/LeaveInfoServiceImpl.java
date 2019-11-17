package com.activiti.service.impl;

import com.activiti.act.service.LeaveService;
import com.activiti.bean.LeaveInfo;
import com.activiti.mapper.LeaveInfoMapper;
import com.activiti.service.LeaveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mirko on 2019/11/15.
 */
@Service
public class LeaveInfoServiceImpl implements LeaveInfoService{

    @Autowired
    LeaveInfoMapper leaveInfoMapper;

    @Autowired
    LeaveService leaveService;


    @Override
    public void addLeaveInfo(String msg) {
        LeaveInfo leaveInfo = new LeaveInfo();
        leaveInfo.setStatus("start");
        leaveInfo.setLeaveMsg(msg);
        //新增一条记录至数据库中
        leaveInfoMapper.insert(leaveInfo);
        //启动流程引擎
        leaveService.startProcess(leaveInfo.getId()+"");
    }

    @Override
    public LeaveInfo getById(String id) {
        return leaveInfoMapper.getById(id);
    }

    @Override
    public void update(LeaveInfo entity) {
        leaveInfoMapper.update(entity);
    }


}
