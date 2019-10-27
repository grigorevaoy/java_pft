package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class UserPasswordChangeTests extends TestBase{

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testUserPasswordChange() throws IOException, MessagingException {
    app.user().adminLogin();
    app.user().goToPageManageOverview();
    app.user().goToPageManageUser();

    UserData User = app.db().getUsers().iterator().next();
    String UserEmail = User.getEmail();
    app.user().openUserPageByUsername(User.getUsername());
    app.user().initPasswordChange();

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);

    String ChangePasswordLink = app.user().findLink(mailMessages, UserEmail);

    String newPassword = app.user().changePassword();
    app.user().finish(ChangePasswordLink, newPassword);

    //Проверка на то, что пользователь может войти в систему под новым паролем
    app.user().logout();
    HttpSession session = app.newSession();
    assertTrue(session.login(User.getUsername(), newPassword));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
