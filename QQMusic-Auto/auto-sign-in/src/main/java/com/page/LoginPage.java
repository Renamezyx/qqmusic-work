package com.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password_input");
    private final By loginButton = By.id("login_button");

    private final WebElement usernameInputElement;
    private final WebElement passwordInputElement;
    private final WebElement loginButtonElement;
    public LoginPage(WebDriver driver,long waitTime){
        super(driver,waitTime);
        driver.get("http://om.tencent.com/");
        this.loginButtonElement = wait.until((webDriver)-> webDriver.findElement(loginButton));
        this.usernameInputElement = driver.findElement(usernameInput);
        this.passwordInputElement = driver.findElement(passwordInput);
    }

    public CheckOutPage login(String username,String password){
        usernameInputElement.sendKeys(username);
        passwordInputElement.sendKeys(password);
        loginButtonElement.click();

        return new CheckOutPage(driver,getTimeOutInSeconds());
    }

}
