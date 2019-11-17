package com.activiti.controller;

import com.activiti.act.service.LeaveService;
import com.activiti.bean.LeaveInfo;
import com.activiti.service.LeaveInfoService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Mirko on 2019/11/15.
 */
@RestController
@Slf4j
public class LeaveController {


    @Autowired
    LeaveInfoService leaveInfoService;

    @Autowired
    LeaveService leaveService;

    @RequestMapping("/add")
    public Map<String,String> startLeave(@RequestParam("msg") String msg){
        leaveInfoService.addLeaveInfo(msg);
        Map<String, String> map = new HashMap<>();
        map.put("info", "接收成功");
        return map;
    }

    @RequestMapping("/leaveInfo")
    public LeaveInfo getLeaveInfo(@RequestParam("id") String id) {
        return leaveInfoService.getById(id);
    }


    /**
     * 查询
     * @param name
     * @return
     */
    @RequestMapping("/query")
    public List<Map> queryTaskByUser(@RequestParam("name") String name){
        List<Task> allTask = leaveService.findTaskById(name);
        List<Map> list = getMaps(allTask);
        return list;
    }


    @RequestMapping("/complete")
    public String completeTask(@RequestParam("taskId") String taskId, @RequestParam("userId") String userId,@RequestParam("audit") String audit ){
        try {
            leaveService.completeTask(taskId,userId,audit);
        } catch (Exception e) {
            e.printStackTrace();
            return "操作失败";
        }
        return "操作成功";
    }


    @RequestMapping(value = "/allTask",produces = "application/json")
    public List<Map> queryAll(){
        List<Task> allTask = leaveService.findAllTask();
        List<Map> list = getMaps(allTask);
        return list;
    }

    private List<Map> getMaps(List<Task> allTask) {
        List<Map> list = new ArrayList<>();
        for (Task task : allTask) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("流程定义id", task.getProcessDefinitionId());
            log.info("流程定义id {} ", task.getProcessDefinitionId());
            map.put("任务id——taskId", task.getId());
            log.info("任务id——taskId {} ", task.getId());
            map.put("待办人", task.getAssignee());
            log.info("待办人 {} ", task.getAssignee());
            map.put("创建时间", task.getCreateTime());
            log.info("创建时间 {}", task.getCreateTime());
            map.put("任务名称", task.getName());
            log.info("任务名称 {}", task.getName());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            log.info("taskDefinitionKey {}", task.getTaskDefinitionKey());
            list.add(map);
        }
        return list;
    }


    @RequestMapping("/test")
    public List<String> getList(){
        return Arrays.asList("s1", "s2", "s3");
    }


}
