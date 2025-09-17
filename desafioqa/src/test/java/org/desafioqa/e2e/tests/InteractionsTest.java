package org.desafioqa.e2e.tests;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.pageObjects.InteractionsPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InteractionsTest extends BaseTest {
    
    private InteractionsPO interactionsPO = new InteractionsPO(driver);

    private final List<String> ordemEsperada = Arrays.asList("Six","Five", "Four", "Three", "Two", "One");

    @BeforeEach
    public void setUp() {
        interactionsPO.clickInteractionsTest();
    }

    @Test
    public void givenElementosInterativos_whenOrdenarDecrescente_thenElementosOrdenados() {
        interactionsPO.arrastarElementoESoltar("Six", "One");
        interactionsPO.arrastarElementoESoltar("Five", "One");
        interactionsPO.arrastarElementoESoltar("Four", "One");
        interactionsPO.arrastarElementoESoltar("Three", "One");
        interactionsPO.arrastarElementoESoltar("Two", "One");

        assertTrue(interactionsPO.verificarOrdemElementos(ordemEsperada));
    }


}
