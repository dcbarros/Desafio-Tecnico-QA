package org.desafioqa.e2e.pageObjects;

import java.util.List;

import org.desafioqa.e2e.core.BasePO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InteractionsPO extends BasePO {

    @FindBy(xpath = "//*[@id='demo-tabpane-list']")
    public WebElement elementsContainer;

    @FindBy(xpath = "//*[@id='demo-tabpane-list']/div/div")
    public List<WebElement> elementos;

    public InteractionsPO(WebDriver driver) {
        super(driver);
    }

    public void arrastarElementoESoltar(String textoElementoOrigem, String textoElementoDestino) {

        WebElement elementoOrigem = elementsContainer.findElement(By.xpath(".//*[text()='" + textoElementoOrigem + "']"));
        WebElement elementoDestino = elementsContainer.findElement(By.xpath(".//*[text()='" + textoElementoDestino + "']"));

        dsl.arrastarSoltar(elementoOrigem, elementoDestino);
    }

    public boolean verificarOrdemElementos(List<String> ordemEsperada) {
        
        dsl.scrollToElement(elementsContainer);

        if (elementos.size() != ordemEsperada.size()) {
            return false;
        }

        for (int i = 0; i < elementos.size(); i++) {
            String textoElementoAtual = elementos.get(i).getText().trim();
            if (!textoElementoAtual.equals(ordemEsperada.get(i))) {
                return false;
            }
        }
        return true;
    }
    
}
