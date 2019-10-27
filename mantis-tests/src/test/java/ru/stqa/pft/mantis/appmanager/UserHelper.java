package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.util.List;

public class UserHelper extends HelperBase {

  public UserHelper(ApplicationManager app) {
    super(app);
  }

  public void adminLogin() {
    type(By.name("username"), "administrator");
    click(By.xpath("//input[@value='Войти']"));
    type(By.name("password"), "root");
    click(By.xpath("//input[@value='Войти']"));
  }


  public void goToPageManageOverview() {
    wd.findElement(By.cssSelector("i.menu-icon.fa.fa-gears")).click();
  }

  public void goToPageManageUser() {
    wd.findElement(By.xpath("//a[contains(text(),'Управление пользователями')]")).click();
  }

  public void openUserPageByUsername(String name) {
    click(By.xpath("//a[text() = '" + name + "']"));
  }

  public void initPasswordChange() {
    click(By.xpath("//input[@value='Reset Password']"));
  }

  public String findLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  public String changePassword() {
    int randomValue = (int) (Math.random() * 100000);
    return Integer.toString(randomValue);
  }

  public void finish(String ChangePasswordLink, String password) {
    wd.get(ChangePasswordLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Update User']"));
  }

  public void logout() {
    click(By.xpath("//a[text() = 'Logout']"));
  }

}
