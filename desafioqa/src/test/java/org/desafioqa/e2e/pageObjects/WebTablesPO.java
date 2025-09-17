package org.desafioqa.e2e.pageObjects;

import java.time.Duration;

import org.desafioqa.e2e.core.BasePO;
import org.desafioqa.e2e.models.WebTables;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class WebTablesPO extends BasePO {
    
    @FindBy(id = "addNewRecordButton")
    public WebElement botaoAdd;

    @FindBy(id = "firstName")
    public WebElement inputFirstName;

    @FindBy(id = "lastName")
    public WebElement inputLastName;

    @FindBy(id = "userEmail")
    public WebElement inputEmail;

    @FindBy(id = "age")
    public WebElement inputAge;

    @FindBy(id = "salary")
    public WebElement inputSalary;

    @FindBy(id = "department")
    public WebElement inputDepartment;

    @FindBy(id = "submit")
    public WebElement botaoSubmit;

    private final WebDriverWait wait;

    public WebTablesPO(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /* ==================== Ações de alto nível ==================== */

    public void criar(WebTables dados) {
        dsl.clicar(botaoAdd);
        preencherFormulario(dados);
        dsl.clicar(botaoSubmit);
        aguardarLinhaPorEmail(dados.getEmail());
    }

    public void editarPorEmail(String emailAlvo, WebTables novosDados) {
        WebElement linha = localizarLinhaPorEmail(emailAlvo);
        WebElement btnEditar = linha.findElement(By.cssSelector("span[title='Edit']"));
        dsl.clicar(btnEditar);
        preencherFormulario(novosDados);
        dsl.clicar(botaoSubmit);
        aguardarLinhaPorEmail(novosDados.getEmail());
    }

    public void deletarPorEmail(String email) {
        WebElement linha = localizarLinhaPorEmail(email);
        WebElement btnDelete = linha.findElement(By.cssSelector("span[title='Delete']"));
        dsl.clicar(btnDelete);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(rowByEmail(email)));
    }

    public boolean existeRegistroPorEmail(String email) {
        return !driver.findElements(rowByEmail(email)).isEmpty();
    }

    /* ==================== Privados/Helpers ==================== */

    private void preencherFormulario(WebTables d) {
        dsl.escrever(inputFirstName, d.getFirstName());
        dsl.escrever(inputLastName,  d.getLastName());
        dsl.escrever(inputEmail,     d.getEmail());
        dsl.escrever(inputAge,       d.getAge());
        dsl.escrever(inputSalary,    d.getSalary());
        dsl.escrever(inputDepartment,d.getDepartment());
    }

    private void aguardarLinhaPorEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(rowByEmail(email)));
    }

    private WebElement localizarLinhaPorEmail(String email) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(rowByEmail(email)));
    }

    private By rowByEmail(String email) {
        return By.xpath("//div[@role='row' and .//div[@role='gridcell' and normalize-space(text())='" + email + "']]");
    }
}
