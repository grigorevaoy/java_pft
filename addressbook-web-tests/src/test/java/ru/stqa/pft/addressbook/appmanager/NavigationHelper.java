package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.GroupData;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    wd.findElement(By.linkText("home")).click();
  }

  public void groupPage() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {
      return;
    }
    click(By.linkText("groups"));
  }


  public void ContactPage() {
    if (isElementPresent(By.id("maintable"))){
      return;
    }
    click(By.linkText("home"));
  }

  public void SelectedPage(GroupData group) {
    wd.findElement(By.linkText("group page \""+group.getName()+"\"")).click();
  }

  public void AllContactsPage() {
    wd.findElement(By.xpath("//option[2]")).click();
  }

}
