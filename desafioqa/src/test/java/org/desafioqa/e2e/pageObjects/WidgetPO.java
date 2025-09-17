package org.desafioqa.e2e.pageObjects;

import org.desafioqa.e2e.core.BasePO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WidgetPO extends BasePO {

    @FindBy(id = "startStopButton")
    private WebElement startStopButton;

    @FindBy(id = "progressBar")
    private WebElement progressBarContainer;

    @FindBy(id = "resetButton")
    private WebElement resetButton;    
    
    public WidgetPO(WebDriver driver) {
        super(driver);
    }

    public void clicarStartStop() {
        dsl.clicar(startStopButton);
    }

    public void clicarReset() {
        dsl.clicar(resetButton);
    }

    public String obterValorProgresso() {
        dsl.esperarAtributoEstabilizar(progressBarContainer.findElement(By.xpath("./div")), "aria-valuenow", 150, 20);
        return progressBarContainer.findElement(By.xpath("./div")).getAttribute("aria-valuenow");
    }

    public void clicarEPararPertoDe(int alvo) {

        if(alvo < 0 || alvo > 100) {
            throw new IllegalArgumentException("Alvo deve estar entre 0 e 100");
        }
        if(alvo == 0) {
            return;
        }
        if(alvo == 100) {
            clicarStartStop();
            dsl.esperarAtributoEstabilizar(progressBarContainer.findElement(By.xpath("./div")), "aria-valuenow", 150, 20);
            return;
        }

        dsl.clicar(startStopButton); 
        WebElement barra = progressBarContainer.findElement(By.xpath("./div"));
        dsl.esperarAteAtributoNoIntervalo(barra, "aria-valuenow",
                                        alvo, alvo + 5, 5, 50); 
        dsl.clicar(startStopButton);
        dsl.esperarAtributoEstabilizar(barra, "aria-valuenow", 150, 2);
    }
    
}
