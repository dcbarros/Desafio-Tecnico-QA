package org.desafioqa.e2e.tests;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.models.WebTables;
import org.desafioqa.e2e.pageObjects.WebTablesPO;
import org.desafioqa.utils.DataLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class WebTablesTest extends BaseTest {

    private WebTablesPO webTablesPO = new WebTablesPO(driver);
    private WebTables dados = DataLoader.fromResource("/massaDados/e2e/webTablesTest.json", WebTables.class);
    private WebTables dadosEditado = DataLoader.fromResource("/massaDados/e2e/webTablesEdit.json", WebTables.class);;

    @BeforeEach
    public void setup() {
        webTablesPO.clickElementsTest();
        webTablesPO.criar(dados);
    }

    @Test
    @Order(1)
    public void givenValidData_whenCriarRegistro_thenRegistroDeveSerCriado() {

        assertTrue(webTablesPO.existeRegistroPorEmail(dados.getEmail()), "Registro não localizado após criar");

    }

    @Test
    @Order(2)
    public void givenEditValidData_whenEditarRegistro_thenRegistroDeveSerEditado() {

        webTablesPO.editarPorEmail(dados.getEmail(), dadosEditado);
        assertTrue(webTablesPO.existeRegistroPorEmail(dadosEditado.getEmail()), "Registro editado não localizado");
        assertFalse(webTablesPO.existeRegistroPorEmail(dados.getEmail()), "Registro antigo ainda presente após edição");

    }

    @Test
    @Order(3)
    public void givenValidData_whenDeletarRegistro_thenRegistroDeveSerDeletado() {

        webTablesPO.deletarPorEmail(dados.getEmail());
        assertFalse(webTablesPO.existeRegistroPorEmail(dados.getEmail()), "Registro ainda presente após deletar");
    }

}

