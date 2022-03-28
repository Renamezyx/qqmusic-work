package com.testcase;

import com.page.CheckOutPage;
import com.page.LoginPage;
import org.openqa.selenium.WebDriver;

public class CheckOutPageTestCase {

    private final String username;
    private final String password;

    public CheckOutPageTestCase(String username,String password){
        this.username = username;
        this.password = password;
    }

    public void signIn(WebDriver webDriver){
        LoginPage loginPage = new LoginPage(webDriver,16);
        CheckOutPage checkOutPage = loginPage.login(username, password);
        checkOutPage.clickSignIn();
        checkOutPage.okModalButton();
    }

    public void signOut(WebDriver webDriver){
        LoginPage loginPage = new LoginPage(webDriver,16);
        CheckOutPage checkOutPage = loginPage.login(username, password);
        checkOutPage.clickSignOut();
        checkOutPage.okModalButton();
    }

}
