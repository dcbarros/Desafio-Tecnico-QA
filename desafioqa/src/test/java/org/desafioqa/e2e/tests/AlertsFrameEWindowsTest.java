package org.desafioqa.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.pageObjects.AlertsFrameEWindowsPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AlertsFrameEWindowsTest extends BaseTest{

    AlertsFrameEWindowsPO alertsFrameEWindowsPage = new AlertsFrameEWindowsPO(driver);

    @BeforeEach
    public void setup(){
        alertsFrameEWindowsPage.clickAlertsFrameEWindowsTest();
    }

    @Test
    public void whenClicarNewWindow_thenNovaJanelaDeveSerExibida() {
        alertsFrameEWindowsPage.clicarNewWindow();
        assertEquals("This is a sample page", alertsFrameEWindowsPage.obterTextoNovaJanela());
        alertsFrameEWindowsPage.fecharNovaJanela();
    }

}
