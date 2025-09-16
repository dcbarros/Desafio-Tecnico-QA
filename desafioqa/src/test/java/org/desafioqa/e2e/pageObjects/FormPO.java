package org.desafioqa.e2e.pageObjects;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.desafioqa.e2e.core.BasePO;
import org.desafioqa.e2e.models.UserForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class FormPO extends BasePO {

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;


    @FindBy(css = "label[for='gender-radio-1']")
    private WebElement maleRadioLabel;

    @FindBy(id = "userNumber")
    private WebElement mobileField;

    @FindBy(id = "dateOfBirthInput")
    private WebElement dateOfBirthField;

    @FindBy(id = "subjectsInput")
    private WebElement subjectsInput;

    @FindBy(css = "label[for='hobbies-checkbox-1']")
    private WebElement hobbiesSportsLabel;

    @FindBy(id = "uploadPicture")
    private WebElement uploadPictureField;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressField;


    @FindBy(id = "state")
    private WebElement stateContainer;
    @FindBy(id = "react-select-3-input")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityContainer;
    @FindBy(id = "react-select-4-input")
    private WebElement cityInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(xpath = "//*[@role='document']//div[@class='modal-body']//tr")
    private List<WebElement> submitTableRows;

    @FindBy(xpath = "//*[@role='document']//div[@class='modal-header']")
    private WebElement modalHeaderTitle;

    @FindBy(xpath = "//*[@role='document']//div[@class='modal-footer']/button")
    private WebElement closeModalButton;

    public FormPO(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Preenche o formulário completo.
     * @param dateOfBirth esperado na massa como "dd-MM-yyyy" (ex.: 01-01-1991)
     */
    public void preencherFormulario(
            String firstName,
            String lastName,
            String email,
            String mobile,
            String dateOfBirth,
            String subject,
            String currentAddress,
            String state,
            String city,
            String nomeArquivo) {

        dsl.escrever(firstNameField, firstName);
        dsl.escrever(lastNameField, lastName);
        dsl.escrever(emailField, email);

        dsl.clicar(maleRadioLabel);

        dsl.escrever(mobileField, mobile);

        LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        dsl.setDateByTyping(dateOfBirthField, dob, "dd MMM yyyy");
        dsl.typeAndEnter(subjectsInput, subject);

        dsl.clicar(hobbiesSportsLabel);

        dsl.escrever(currentAddressField, currentAddress);

        dsl.clicar(stateContainer);
        dsl.typeAndEnter(stateInput, state);

        dsl.clicar(cityContainer);
        dsl.typeAndEnter(cityInput, city);
            
        String path = Paths.get("src","test","resources","massaDados","e2e", nomeArquivo)
                   .toAbsolutePath().toString();
        dsl.uploadFile(uploadPictureField, path);

        // Se quiser enviar:
        dsl.clicar(submitButton);
    }

    public boolean validarFormularioPreenchido(UserForm user) {

        List<String> dataSubmitting = submitTableRows.stream().map(WebElement::getText).collect(Collectors.toList());

        String fullName = user.getFirstName() + " " + user.getLastName();
        DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("dd MMMM,yyyy").withLocale(java.util.Locale.ENGLISH);
        String expectedDob = LocalDate.parse(user.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                .format(dobFormatter);
        
    
        return dataSubmitting.containsAll(Arrays.asList(
                "Student Name " + fullName,
                "Student Email " + user.getEmail(),
                "Gender Male",
                "Mobile " + user.getMobile(),
                "Date of Birth " + expectedDob,
                "Subjects " + user.getSubject(),
                "Hobbies Sports",
                "Picture form-auto.txt",
                "Address " + user.getAddress(),
                "State and City " + user.getState() + " " + user.getCity()
        ));

    }

    public boolean modalEstaVisivel() {
        return dsl.estaVisivel(modalHeaderTitle) && modalHeaderTitle.getText().equals("Thanks for submitting the form");
    }

    public void fecharModal() {
        dsl.clicar(closeModalButton);
    }
}
