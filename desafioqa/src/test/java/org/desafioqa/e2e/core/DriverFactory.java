package org.desafioqa.e2e.core;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static WebDriver driver;
    private static ChromeOptions options;

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if(driver == null) {
            WebDriverManager.chromedriver().setup();
            options = new ChromeOptions();
            options.addArguments(Arrays.asList("--start-maximized", "--headless=new", "--disable-gpu", "--window-size=1920,1080"));
            driver = new ChromeDriver(options);            
        }
        return driver;
    }

    public static void quitDriver() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
