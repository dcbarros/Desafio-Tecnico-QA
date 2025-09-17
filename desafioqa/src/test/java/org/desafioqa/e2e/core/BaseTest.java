package org.desafioqa.e2e.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    
    protected WebDriver driver = DriverFactory.getDriver();

    @BeforeEach
    public void startTest() {
        driver.get("https://demoqa.com/");
    }

    @AfterEach
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
