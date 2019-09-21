package ru.stqa.pft.addressbook.tests;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Oksana", "Grigoreva", "Saint-Petersburg", "89112999959", "kiyrina@mail.ru", "the first"),true);
    app.getNavigationHelper().returnToHomePage();
  }


}
