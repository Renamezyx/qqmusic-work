package com.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private final static Log log = LogFactory.getLog(Config.class);
    static Properties config = new Properties();

    static {
        try {
            config.load(new FileInputStream(new File("config.properties")));
        } catch (IOException e) {
            log.error("load configuration properties error");
            log.error(e);
        }
    }

    public static void setUsername(String username){
        config.setProperty("username",username);
    }
    public static String getUsername(){
        return config.getProperty("username");
    }

    public static void setChromeDrivers(String chromeDrivers){
        config.setProperty("chromeDrivers",chromeDrivers);
    }
    public static String getChromeDrivers(){
        return config.getProperty("chromeDrivers");
    }

    public static void setPassword(String password){
        config.setProperty("password",password);
    }
    public static String getPassword(){
        return config.getProperty("password");
    }

    public static void store(){
        try {
            config.store(new FileOutputStream(new File("config.properties")),"配置文件，请不要随意删除");
        } catch (IOException e) {
            log.error("store properties error");
            log.error(e);
        }
    }

}
