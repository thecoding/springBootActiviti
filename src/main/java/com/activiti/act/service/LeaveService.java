package com.activiti.act.service;

import com.activiti.bean.LeaveInfo;
import com.activiti.mapper.LeaveInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Mirko on 2019/11/15.
 */
@Service
@Slf4j
public class LeaveService {


    @Autowired
    private LeaveInfoMapper leaveMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Resource
    TaskService taskService;

//    ${leaveService.findProjectManager(execution)}
//    ${leaveService.findHrManager(execution)}

//    ${leaveService.changeStatus(execution,'ing')}
    /**
     * 查询项目经理
     * @param execution
     * @return
     */
    public List<String> findProjectManager(DelegateExecution execution) {
        log.info("查询ProjectManager {}", Arrays.asList("project1","project2").toString());
        return Arrays.asList("project1","project2");
    }


    /**
     * 查询hr
     * @param execution
     * @return
     */
    public List<String> findHrManager(DelegateExecution execution){
        log.info("查询hr {}", Arrays.asList("hr1","hr2").toString());
        return Arrays.asList("hr1", "hr2");
    }


    /**
     * 修改状态
     * @param execution
     * @param status
     */
    public void changeStatus(DelegateExecution execution, String status) {
        String processBusinessKey = execution.getProcessBusinessKey();
        LeaveInfo entity = leaveMapper.getById(processBusinessKey);
        entity.setStatus(status);
        leaveMapper.update(entity);
        log.info("修改业务表leaveInfo中的status为：{}", status);
    }




    /**
     * 启动流程
     * @param bizKey
     */
    public void startProcess(String bizKey){
        ProcessInstance leaveProcess = runtimeService.startProcessInstanceByKey("myProcess", bizKey);
        log.info("启动myProcess流程 {}", leaveProcess);
    }



    /**
     * 查询待办
     * @param userId
     * @return
     */
    public List<Task> findTaskById(String userId){
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
        if (list != null && list.size() > 0) {
            return list;
        }
        return new ArrayList<>();
    }

    public List<Task> findAllTask(){
        List<Task> list = taskService.createTaskQuery().list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                log.info("taskName : {}" + task.getName());
            }
        }
        return list;
    }

    /**
     * 审批
     * @param taskId
     * @param userId
     * @param audit pass /  reject
     */
    public void completeTask(String taskId,String userId,String audit){
        taskService.claim(taskId, userId);
        Map<String, Object> map = new HashMap<>();
        map.put("audit", audit);
        taskService.complete(taskId, map);
    }
}
