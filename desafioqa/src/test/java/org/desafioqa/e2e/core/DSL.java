package org.desafioqa.e2e.core;

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
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
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

    public String obterValor(WebElement elemento) {
        wait.until(ExpectedConditions.visibilityOf(elemento));
        scrollToElement(elemento);
        return elemento.getAttribute("value");
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

    public void typeAndEnter(WebElement input, String texto) {
        scrollToElement(input);
        input.sendKeys(texto);
        input.sendKeys(Keys.ENTER);
    }

    public void jsClick(WebElement elemento) {
        scrollToElement(elemento);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
    }

    public void setDateByTyping(WebElement dateInput, LocalDate date, String pattern) {
        String formatted = date.format(DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
        scrollToElement(dateInput);
        dateInput.sendKeys(Keys.CONTROL + "a");
        dateInput.sendKeys(formatted);
        dateInput.sendKeys(Keys.ENTER);
    }
}
