package org.desafioqa.e2e.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePO {

    protected DSL dsl;
    
    @FindBy(xpath = "//*[text()='Forms']/../../..")
    private WebElement formCardButton;

    @FindBy(xpath = "//*[text()='Forms']/../../..//div[contains(@class,'collapse')]")
    private WebElement formSubMenu;

    public BasePO(WebDriver driver) {
        this.dsl = new DSL(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickForm(){
        dsl.clicar(formCardButton);
        
        if(formSubMenu.getAttribute("class").contains("show")){
            dsl.clicar(formSubMenu);
        } else {
            dsl.clicar(formCardButton);
            dsl.clicar(formSubMenu);
        }
    }
}
