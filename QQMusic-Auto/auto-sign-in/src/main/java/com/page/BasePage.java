package com.page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    private final static Log LOG = LogFactory.getLog(BasePage.class);

    protected WebDriverWait wait;
    private long timeOutInSeconds;
    protected final WebDriver driver;

    public BasePage(WebDriver driver,long timeout) {
        this(driver,timeout,false);
    }
    public BasePage(WebDriver driver,long timeout,boolean debug) {
        this.timeOutInSeconds = timeout;
        this.driver = driver;
        setTimeOutInSeconds(timeout,debug);
    }

    protected WebElement findElementWithWait(By by){
        return wait.until((webDriver)-> driver.findElement(by));
    }

    public void setTimeOutInSeconds(long timeOutInSeconds,boolean debug){
        LOG.debug(String.format("set time out in seconds: [%d]",timeOutInSeconds));
        this.timeOutInSeconds = timeOutInSeconds;
        if(!debug){
            this.wait = new WebDriverWait(driver,timeOutInSeconds);
        }
    }

    public void setTimeOutInSeconds(long timeOutInSeconds){
        setTimeOutInSeconds(timeOutInSeconds,false);
    }

    public long getTimeOutInSeconds(){
        return timeOutInSeconds;
    }

}
