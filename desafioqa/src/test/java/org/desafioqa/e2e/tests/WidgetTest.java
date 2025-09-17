package org.desafioqa.e2e.tests;

import static org.junit.Assert.assertTrue;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.pageObjects.WidgetPO;
import org.junit.Before;
import org.junit.Test;

public class WidgetTest extends BaseTest {
    
    WidgetPO widgetPO = new WidgetPO(driver);

    @Before
    public void setup() {
        widgetPO.clickWidgetTest();
    }

    @Test
    public void whenClickStartAndClickStopThenProgressBarLess25Percent() throws InterruptedException {
        widgetPO.clicarEPararPertoDe(20);
        int valorProgresso = Integer.parseInt(widgetPO.obterValorProgresso());
        assertTrue("Esperava < 25, veio " + valorProgresso, valorProgresso < 25);
    }

    @Test
    public void whenClickStartAndClickStopThenClickResetThenProgressBarZero() throws InterruptedException {
        widgetPO.clicarEPararPertoDe(100);
        widgetPO.clicarReset();
        int valorProgresso = Integer.parseInt(widgetPO.obterValorProgresso());
        assertTrue("Esperava 0, veio " + valorProgresso, valorProgresso == 0);
    }
}
