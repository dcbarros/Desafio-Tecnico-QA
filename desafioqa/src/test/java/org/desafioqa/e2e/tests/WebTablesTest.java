package org.desafioqa.e2e.tests;

import static org.junit.Assert.*;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.models.WebTables;
import org.desafioqa.e2e.pageObjects.WebTablesPO;
import org.desafioqa.utils.DataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;

public class WebTablesTest extends BaseTest {

    private WebTablesPO webTablesPO = new WebTablesPO(driver);
    private WebTables dados = DataLoader.fromResource("/massaDados/e2e/webTablesTest.json", WebTables.class);
    private WebTables dadosEditado = DataLoader.fromResource("/massaDados/e2e/webTablesEdit.json", WebTables.class);;

    @Before
    public void setup() {
        webTablesPO.clickElementsTest();
        webTablesPO.criar(dados);
    }

    @Test
    @Order(1)
    public void criarRegistro() {

        assertTrue("Registro não localizado após criar", webTablesPO.existeRegistroPorEmail(dados.getEmail()));

    }

    @Test
    @Order(2)
    public void editarRegistro() {
        
        webTablesPO.editarPorEmail(dados.getEmail(), dadosEditado);
        assertTrue("Registro editado não localizado", webTablesPO.existeRegistroPorEmail(dadosEditado.getEmail()));
        assertFalse("Registro antigo ainda presente após edição", webTablesPO.existeRegistroPorEmail(dados.getEmail()));

    }

    @Test
    @Order(3)
    public void deletarRegistro() {

        webTablesPO.deletarPorEmail(dados.getEmail());
        assertFalse("Registro ainda presente após deletar", webTablesPO.existeRegistroPorEmail(dados.getEmail()));
    }

}

