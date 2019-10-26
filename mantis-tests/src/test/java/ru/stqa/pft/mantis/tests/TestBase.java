package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import java.io.File;


public class TestBase {


  protected final static ApplicationManager app =
          new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
  private WebDriver wd;

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().unload(new File("src/test/resources/config_inc.php"),"config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }


}

