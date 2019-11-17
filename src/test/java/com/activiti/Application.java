package com.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Application {


	@Resource
	RepositoryService repositoryService;

	@Autowired
	RuntimeService runtimeService;

	@Test
	public void queryProcess(){
		Execution myProcess = runtimeService.createExecutionQuery().processDefinitionKey("myProcess").singleResult();
		System.out.println(myProcess);
	}


	@Test
	public void TestStartProcess() {
		//创建流程
		repositoryService.createDeployment()
				.name("MyProcess")
				.addClasspathResource("processes/MyProcess.bpmn").deploy();
	}


	@Test
	public void engine(){
		ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
		log.info("engine = {}", cfg);
	}

	@Test
	public void createEngine(){
		//创建流程引擎
		ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
		ProcessEngine processEngine = cfg.buildProcessEngine();
		String name = processEngine.getName();
		String version = processEngine.VERSION;
		log.info("流程引擎名称{},版本{}", name, version);


		//创建部署流程文件
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addClasspathResource("MyProcess.xml");
		Deployment deployment = deploymentBuilder.deploy();
		String id = deployment.getId();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(id)
				.singleResult();
		log.info("流程定义文件{},流程id{}", processDefinition.getName(), processDefinition.getId());

		//启动流程
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(id);
		log.info("启动流程{}", processInstance.getProcessDefinitionKey());


		//处理流程
		TaskService taskService = processEngine.getTaskService();
		List<Task> list = taskService.createTaskQuery().list();
		for (Task task : list) {
			log.info("id: {}", task.getId());
			log.info("待处理任务 name: {}", task.getName());
			log.info("assignee: {}", task.getAssignee());
		}
	}



}
