package com.activiti.service;

import com.activiti.bean.LeaveInfo;

/**
 * Created by Mirko on 2019/11/15.
 */
public interface LeaveInfoService {

    void addLeaveInfo(String msg);

    LeaveInfo getById(String id);

    void update(LeaveInfo entity);
}
