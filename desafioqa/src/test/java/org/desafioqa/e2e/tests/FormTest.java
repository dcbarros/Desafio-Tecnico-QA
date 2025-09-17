package org.desafioqa.e2e.tests;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.models.UserForm;
import org.desafioqa.e2e.pageObjects.FormPO;
import org.desafioqa.utils.DataLoader;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class FormTest extends BaseTest{

    FormPO formPage = new FormPO(driver);
    UserForm user = DataLoader.fromResource("/massaDados/e2e/formTest.json", UserForm.class);

    @Before
    public void setUp(){
        formPage.clickFormTest();
    }

    @Test
    public void testPreencherFormulario(){
        
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
        
        Assert.assertTrue(formPage.modalEstaVisivel());
        Assert.assertTrue("Os dados do formulário não conferem!", formPage.validarFormularioPreenchido(user));

        formPage.fecharModal();
    }
}
