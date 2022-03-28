package com.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOutPage extends BasePage{

    private final By signInButton = By.id("checkin_btn");
    private final By signOutButton = By.id("checkout_btn");
    private final By closeSignOutModalButton = By.cssSelector("#tdialog-buttonwrap > a:nth-child(2)");
    private final By okSignOutModalButton = By.cssSelector("#tdialog-buttonwrap > a.btn.btn-primary");

    public CheckOutPage(WebDriver driver, long timeout){
        super(driver,timeout);
    }

    public void clickSignOut(){
        WebElement element = findElementWithWait(signOutButton);
        element.click();
    }

    public void clickSignIn(){
        findElementWithWait(signInButton).click();
    }

    public void closeModalButton(){
        WebElement element = wait.until((webDriver) -> {
            WebElement driverElement = driver.findElement(closeSignOutModalButton);
            if(driverElement.isEnabled() && "btn".equalsIgnoreCase(driverElement.getAttribute("class"))
            && "close_it".equalsIgnoreCase(driverElement.getAttribute("name"))){
                return driverElement;
            }else{
                return null;
            }
        });
        element.click();
    }

    public void okModalButton(){
        WebElement element = wait.until((webDriver) -> {
            WebElement driverElement = driver.findElement(okSignOutModalButton);
            if(driverElement.isEnabled() && "btn btn-primary".equalsIgnoreCase(driverElement.getAttribute("class"))
                    && "check".equalsIgnoreCase(driverElement.getAttribute("name"))){
                return driverElement;
            }else{
                return null;
            }
        });
        element.click();
    }

}
