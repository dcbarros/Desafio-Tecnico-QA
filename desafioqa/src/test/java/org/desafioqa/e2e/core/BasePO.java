package org.desafioqa.e2e.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePO {

    protected DSL dsl;
    protected WebDriver driver;

    @FindBy(xpath = "//*[text()='Forms']/../../..")
    private WebElement formCardButton;

    @FindBy(xpath = "//*[text()='Alerts, Frame & Windows']/../../..")
    private WebElement alertsFrameEWindowsCardButton;

    @FindBy(xpath = "//*[text()='Elements']/../../..")
    private WebElement elementsCardButton;

    @FindBy(xpath = "//*[text()='Forms']/../../..//div[contains(@class,'collapse')]")
    private WebElement formSubMenu;

    @FindBy(xpath = "//*[text()='Alerts, Frame & Windows']/../../..//div[contains(@class,'collapse')]")
    private WebElement alertsFrameEWindowsSubMenu;

    @FindBy(xpath = "//*[text()='Elements']/../../..//div[contains(@class,'collapse')]")
    private WebElement elementsSubMenu;

    @FindBy(xpath = "//*[text()='Web Tables']/..")
    private WebElement btnWebTables;

    @FindBy(xpath = "//*[text()='Browser Windows']/..")
    private WebElement btnBrowserWindows;


    public BasePO(WebDriver driver) {
        this.dsl = new DSL(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickFormTest(){
        dsl.clicar(formCardButton);
        
        if(formSubMenu.getAttribute("class").contains("show")){
            dsl.clicar(formSubMenu);
        } else {
            dsl.clicar(formCardButton);
            dsl.clicar(formSubMenu);
        }
    }

    public void clickAlertsFrameEWindowsTest(){
        dsl.clicar(alertsFrameEWindowsCardButton);
        if(alertsFrameEWindowsSubMenu.getAttribute("class").contains("show")){
            dsl.clicar(btnBrowserWindows);
        } 
    }

    public void clickElementsTest(){
        dsl.clicar(elementsCardButton);
        if(elementsSubMenu.getAttribute("class").contains("show")){
            dsl.clicar(btnWebTables);
        }
    }
}
