package org.desafioqa.e2e.core;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    
    protected WebDriver driver = DriverFactory.getDriver();

    @Before
    public void startTest() {
        driver.get("https://demoqa.com/");
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
