package com;

import com.config.Config;
import com.controller.RootController;
import com.testcase.CheckOutPageTestCase;
import com.util.TimerTask;
import com.vo.PlanVO;
import com.vo.ResultVO;
import com.vo.TaskVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class Application {

    private final static Log LOG = LogFactory.getLog(Application.class);

    @DeleteMapping("/tasks/{uuid}")
    public ResultVO deleteTasksByUuid(@PathVariable("uuid")String uuid){
        boolean removeIf = TimerTask.getTasks().removeIf((task -> task.getUuid().equals(uuid)));
        ResultVO resultVO = new ResultVO();
        if(removeIf){
            resultVO.setMessage("删除计划成功");
        }else {
            resultVO.setMessage("删除的计划不存在");
        }
        return resultVO;
    }

    @GetMapping("/tasks")
    public List<TaskVO> info(){
        Vector<TimerTask.Task> tasks = TimerTask.getTasks();
        return tasks.stream().map((item)->{
            TaskVO vo = new TaskVO();
            BeanUtils.copyProperties(item,vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @PostMapping("/plans")
    public ResultVO insertPlan(@RequestBody  PlanVO plan){
        LOG.info("insert plan: ["+plan+"]");
        ResultVO resultVO = new ResultVO();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date runTime = null;
        try {
            runTime = dateFormat.parse(plan.getDate());
        } catch (ParseException e) {
            LOG.error("parse date error");
            LOG.error(e);
            resultVO.setMessage("您输入的时间格式必须是yyyy-MM-dd HH:mm");
            return resultVO;
        }

        if(plan.getUsername().isEmpty() || plan.getPassword().isEmpty()){
            LOG.debug("invalid username or chrome drivers or password");
            resultVO.setMessage("用户名或者密码为空");
            return resultVO;
        }

        if("signIn".equalsIgnoreCase(plan.getType())){
            LOG.debug("成功添加一个签入计划");
            TimerTask.add(new TimerTask.Task(runTime,(task)->{
                System.setProperty("webdriver.chrome.driver",plan.getChromeDriverLocation());
                ChromeDriver chromeDriver = new ChromeDriver();
                CheckOutPageTestCase checkOutPageTestCase = new CheckOutPageTestCase(plan.getUsername(),plan.getPassword());
                checkOutPageTestCase.signIn(chromeDriver);
            },"签入"));
        }else{
            LOG.debug("成功添加一个签出计划");
            TimerTask.add(new TimerTask.Task(runTime,(task)->{
                System.setProperty("webdriver.chrome.driver",plan.getChromeDriverLocation());
                ChromeDriver chromeDriver = new ChromeDriver();
                CheckOutPageTestCase checkOutPageTestCase = new CheckOutPageTestCase(plan.getUsername(),plan.getPassword());
                checkOutPageTestCase.signOut(chromeDriver);
            },"签出"));
        }

        resultVO.setMessage("添加计划成功");
        return resultVO;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
