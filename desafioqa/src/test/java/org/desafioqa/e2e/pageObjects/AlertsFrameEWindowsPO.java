package org.desafioqa.e2e.pageObjects;

import org.desafioqa.e2e.core.BasePO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AlertsFrameEWindowsPO extends BasePO {

    @FindBy(id = "windowButton")
    private WebElement btnNewWindow;

    private String handleOriginal;

    private WebDriver driver;

    public AlertsFrameEWindowsPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clicarNewWindow() {
        handleOriginal = driver.getWindowHandle();
        dsl.clicar(btnNewWindow);
        dsl.alternarParaNovaJanela();
    }

    public String obterTextoNovaJanela() {
        return dsl.obterTextoNovaJanela();
    }

    public void fecharNovaJanela() {
        dsl.fecharJanelaAtualEVoltar(handleOriginal);
    }
}
