package ru.stqa.pft.mantis.appmanager;

import org.subethamail.wiser.Wiser;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

public class MailHelper {

  private ApplicationManager app;
  private final Wiser wiser;

  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser();
  }

  public List<MailMessage> waitForMail (int count, int timeout) throws MessagingException, IOException{
    long start = System.currentTimeMillis();
    while (System.currentTimeMillis() < start + timeout){
      if (wiser.getMessages().size() >= count) {
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  //преобразование реальных почтовых сообщений в модельные
  public static MailMessage toModelMail(WiserMessage m) {
    try {
      MimeMessage mm = m.getMimeMessage(); //берем реальный объект
      //берем список получателей и оставляем только первого из них (так как он там точно один)
      //и преобразуем письмо в строку, значение которой и попадает в модельный объект
      return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  //запустить почтовый сервер
  public void start() {
    wiser.start();
  }

  //остановить почтовый сервер
  public void stop() {
    wiser.stop();
  }
}
