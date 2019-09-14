package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends HelperBase{
  private FirefoxDriver wd;

  public NavigationHelper(FirefoxDriver wd) {
   super(wd);
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void gotoGroupPage() {
  click(By.linkText("groups"));
  }


  public void gotoContactPage() {
    click(By.linkText("home"));
  }
}
