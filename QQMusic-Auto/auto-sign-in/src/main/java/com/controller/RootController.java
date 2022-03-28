package com.controller;

import com.RootView;
import com.config.Config;
import com.testcase.CheckOutPageTestCase;
import com.util.ThreadPoolExecutorUtils;
import com.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

public class RootController implements Initializable {

    private final static Log LOG = LogFactory.getLog(RootController.class);

    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    CheckBox signInOrSignOut;
    @FXML
    TextField executeDate;
    @FXML
    TextArea taskInfo;
    @FXML
    TextField driverLocation;

    @FXML
    public void onSaveUsernameAndPassword(ActionEvent actionEvent){
        LOG.debug(String.format("username: [%s] password: [%s] chrome drivers: [%s]",username.getText(),password.getText(),driverLocation.getText()));
        Config.setPassword(password.getText());
        Config.setUsername(username.getText());
        Config.setChromeDrivers(driverLocation.getText());
        Config.store();
    }

    @FXML
    public void onChooseDriver(MouseEvent mouseEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择驱动");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(RootView.PRIMARY_STAGE);
        if (selectedFile != null) {
            driverLocation.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void onAddTask(ActionEvent actionEvent){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date runTime = null;
        try {
            runTime = dateFormat.parse(executeDate.getText());
        } catch (ParseException e) {
            LOG.error("parse date error");
            LOG.error(e);
            return;
        }

        if(Config.getUsername().isEmpty() || Config.getChromeDrivers().isEmpty() || Config.getPassword().isEmpty()){
            LOG.debug("invalid username or chrome drivers or password");
            return;
        }

        if(signInOrSignOut.isSelected()){
            LOG.debug("成功添加一个签入计划");
            TimerTask.add(new TimerTask.Task(runTime,(task)->{
                System.setProperty("webdriver.chrome.driver",Config.getChromeDrivers());
                ChromeDriver chromeDriver = new ChromeDriver();
                CheckOutPageTestCase checkOutPageTestCase = new CheckOutPageTestCase(Config.getUsername(),Config.getPassword());
                checkOutPageTestCase.signIn(chromeDriver);
            },"签入"));
        }else{
            LOG.debug("成功添加一个签出计划");
            TimerTask.add(new TimerTask.Task(runTime,(task)->{
                System.setProperty("webdriver.chrome.driver",Config.getChromeDrivers());
                ChromeDriver chromeDriver = new ChromeDriver();
                CheckOutPageTestCase checkOutPageTestCase = new CheckOutPageTestCase(Config.getUsername(),Config.getPassword());
                checkOutPageTestCase.signOut(chromeDriver);
            },"签出"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Config.getUsername());
        password.setText(Config.getPassword());
        driverLocation.setText(Config.getChromeDrivers());

        ThreadPoolExecutorUtils.execute(()->{
            while (true){
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Vector<TimerTask.Task> tasks = TimerTask.getTasks();
                taskInfo.clear();
                for(int i=0;i<tasks.size();i++){
                    TimerTask.Task task = tasks.get(i);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String message = String.format("任务%d：备注：%s 预定执行时间：%s 剩余执行时间：%d分钟",
                            i,
                            task.getRemark(),
                            dateFormat.format(task.getStartTime()),
                            (task.getStartTime().getTime()-System.currentTimeMillis())/1000/60);
                    taskInfo.appendText(message);
                    taskInfo.appendText("\n");
                }
            }
        });
    }
}
