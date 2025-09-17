package org.desafioqa.e2e.tests;



import static org.junit.jupiter.api.Assertions.assertTrue;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.pageObjects.WidgetPO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WidgetTest extends BaseTest {
    
    WidgetPO widgetPO = new WidgetPO(driver);

    @BeforeEach
    public void setup() {
        widgetPO.clickWidgetTest();
    }

    @Test
    public void whenClickStartAndClickStopThenProgressBarLess25Percent() throws InterruptedException {
        widgetPO.clicarEPararPertoDe(20);
        int valorProgresso = Integer.parseInt(widgetPO.obterValorProgresso());
        assertTrue(valorProgresso < 25, "Esperava < 25, veio " + valorProgresso);
    }

    @Test
    public void whenClickStartAndClickStopThenClickResetThenProgressBarZero() throws InterruptedException {
        widgetPO.clicarEPararPertoDe(100);
        widgetPO.clicarReset();
        int valorProgresso = Integer.parseInt(widgetPO.obterValorProgresso());
        assertTrue(valorProgresso == 0, "Esperava 0, veio " + valorProgresso);
    }
}
