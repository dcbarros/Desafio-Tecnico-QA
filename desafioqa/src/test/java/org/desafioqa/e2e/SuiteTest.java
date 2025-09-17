package org.desafioqa.e2e;

import org.desafioqa.e2e.tests.AlertsFrameEWindowsTest;
import org.desafioqa.e2e.tests.FormTest;
import org.desafioqa.e2e.tests.WebTablesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  FormTest.class,
  AlertsFrameEWindowsTest.class,
  WebTablesTest.class
})
public class SuiteTest {
    
}
