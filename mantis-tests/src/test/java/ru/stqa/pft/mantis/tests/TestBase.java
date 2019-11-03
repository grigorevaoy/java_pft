package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;


public class TestBase {

  protected static ApplicationManager app;

  static {
    try {
      app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

   @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().unload(new File("src/test/resources/config_inc.php"),"config_inc.php", "config_inc.php.bak");
  }

  //закрыт таск (true) или нет(false)
  public boolean isIssueOpened(int issueId) throws RemoteException, MalformedURLException, ServiceException {
    String status = app.soap().issueStatus(issueId);
    if (!status.equals("closed")) {
      return true;
    }
    return false;
  }

  //пропускаем таск, так как он не закрыт
  public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpened(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId + "was not fixed");
    }
  }

 @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }


}

