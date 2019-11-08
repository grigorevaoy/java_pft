package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SessionHelper extends HelperBase{
  private final Properties properties;

  public SessionHelper(WebDriver wd) {
    super(wd);
    properties = new Properties();
  }

  public void login(String username, String password) throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    wd.get(properties.getProperty("web.baseUrl"));
    type(By.name("user"),username);
    type(By.name("pass"),password);
    click(By.xpath("//input[@value='Login']"));
  }
}
