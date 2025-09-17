package org.desafioqa.e2e;

import org.desafioqa.e2e.tests.AlertsFrameEWindowsTest;
import org.desafioqa.e2e.tests.FormTest;
import org.desafioqa.e2e.tests.InteractionsTest;
import org.desafioqa.e2e.tests.WebTablesTest;
import org.desafioqa.e2e.tests.WidgetTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
  FormTest.class,
  AlertsFrameEWindowsTest.class,
  WebTablesTest.class,
  WidgetTest.class,
  InteractionsTest.class
})
public class SuiteTest {
    
}
