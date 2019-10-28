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
    wd.findElement(By.xpath("//input[2]")).click();
    type(By.name("password"), "root");
    wd.findElement(By.xpath("//input[3]")).click();
  }


  public void goToPageManageOverview() {
    wd.findElement(By.cssSelector("i.menu-icon.fa.fa-gears")).click();
  }

  public void goToPageManageUser() {
    wd.findElement(By.xpath("//a[contains(@href, '/mantisbt-2.22.1/manage_user_page.php')]")).click();
  }

  public void openUserPageByUsername(String name) {
    click(By.xpath("//a[text() = '" + name + "']"));
  }

  public void initPasswordChange() {
    click(By.cssSelector("span > input.btn.btn-primary.btn-white.btn-round"));
  }

  public String findLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  public String changePassword() {
    String newPassword = "new_password";
    return newPassword;
  }

  public void finish(String ChangePasswordLink, String password) {
    wd.get(ChangePasswordLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("//button/span"));
  }

}
