package org.desafioqa.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.models.UserForm;
import org.desafioqa.e2e.pageObjects.FormPO;
import org.desafioqa.utils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FormTest extends BaseTest{

    FormPO formPage = new FormPO(driver);
    UserForm user = DataLoader.fromResource("/massaDados/e2e/formTest.json", UserForm.class);

    @BeforeEach
    public void setUp(){
        formPage.clickFormTest();
    }

    @Test
    public void givenUser_whenPreencherFormulario_thenDadosDevemConferir(){

        formPage.preencherFormulario(
            user.getFirstName(), 
            user.getLastName(), 
            user.getEmail(), 
            user.getMobile(), 
            user.getDateOfBirth(), 
            user.getSubject(), 
            user.getAddress(), 
            user.getState(), 
            user.getCity(),
            "form-auto.txt"
        );
        
        assertTrue(formPage.modalEstaVisivel());
        assertTrue(formPage.validarFormularioPreenchido(user), "Os dados do formulário não conferem!");

        formPage.fecharModal();
    }
}
