package org.desafioqa.e2e.core;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DSL {

    private final WebDriver driver;
    protected final WebDriverWait wait;

    public DSL(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /* ========= Ações básicas ========= */

    public void clicar(WebElement elemento){
        scrollToElement(elemento);
        wait.until(ExpectedConditions.elementToBeClickable(elemento)).click();
    }

    public void escrever(WebElement elemento, String texto) {
        scrollToElement(elemento);
        elemento.sendKeys(Keys.CONTROL + "a");
        elemento.sendKeys(Keys.DELETE);
        elemento.sendKeys(texto);
    }

    public void scrollToElement(WebElement elemento) {
        wait.until(ExpectedConditions.visibilityOf(elemento));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", elemento);
    }

    public boolean estaVisivel(WebElement elemento) {
        try {
            wait.until(ExpectedConditions.visibilityOf(elemento));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* ========= Complementos úteis ========= */

    public void uploadFile(WebElement inputFile, String absolutePath) {
        scrollToElement(inputFile);
        inputFile.sendKeys(absolutePath);
    }

    public void escreverEEnter(WebElement input, String texto) {
        scrollToElement(input);
        input.sendKeys(texto);
        input.sendKeys(Keys.ENTER);
    }

    public void setDataPorTipo(WebElement dateInput, LocalDate date, String pattern) {
        String formatted = date.format(DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
        scrollToElement(dateInput);
        dateInput.sendKeys(Keys.CONTROL + "a");
        dateInput.sendKeys(formatted);
        dateInput.sendKeys(Keys.ENTER);
    }

    /* ========= Janela/aba ========= */

    public void alternarParaNovaJanela() {
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
    }


    public String obterTextoNovaJanela() {
        WebElement heading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("sampleHeading"))
        );
        return heading.getText();
    }


    public void fecharJanelaAtualEVoltar(String handleOriginal) {
        driver.close();
        driver.switchTo().window(handleOriginal);
    }

    /* ========= Esperas específicas ========= */
    
    public void esperarAteAtributoNoIntervalo(WebElement el, String attr,
                                            int minIncl, int maxExcl,
                                            long timeoutSeg, long pollingMs) {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutSeg))
            .pollingEvery(java.time.Duration.ofMillis(pollingMs))
            .until(d -> {
                String val = el.getAttribute(attr);
                if (val == null || val.isEmpty()) return false;
                int v = Integer.parseInt(val);
                return v >= minIncl && v < maxExcl;
            });
    }

    public void esperarAtributoEstabilizar(WebElement el, String attr, long quietMillis, long maxSegundos) {
    long fim = System.currentTimeMillis() + maxSegundos * 1000;
    String ultimo = el.getAttribute(attr);
    long desde = System.currentTimeMillis();
    while (System.currentTimeMillis() < fim) {
        String atual = el.getAttribute(attr);
        if (!atual.equals(ultimo)) { ultimo = atual; desde = System.currentTimeMillis(); }
        if (System.currentTimeMillis() - desde >= quietMillis) return;
        try { Thread.sleep(30); } catch (InterruptedException ignored) {}
    }
    throw new org.openqa.selenium.TimeoutException("Atributo não estabilizou em " + maxSegundos + "s");
}
}
