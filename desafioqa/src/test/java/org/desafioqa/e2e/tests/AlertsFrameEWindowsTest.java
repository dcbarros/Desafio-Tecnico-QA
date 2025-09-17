package org.desafioqa.e2e.tests;

import static org.junit.Assert.assertEquals;

import org.desafioqa.e2e.core.BaseTest;
import org.desafioqa.e2e.pageObjects.AlertsFrameEWindowsPO;
import org.junit.Before;
import org.junit.Test;

public class AlertsFrameEWindowsTest extends BaseTest{

    AlertsFrameEWindowsPO alertsFrameEWindowsPage = new AlertsFrameEWindowsPO(driver);

    @Before
    public void setup(){
        alertsFrameEWindowsPage.clickAlertsFrameEWindowsTest();
    }

    @Test
    public void acessarNovaJanela() {
        alertsFrameEWindowsPage.clicarNewWindow();
        assertEquals("This is a sample page", alertsFrameEWindowsPage.obterTextoNovaJanela());
        alertsFrameEWindowsPage.fecharNovaJanela();
    }

}
