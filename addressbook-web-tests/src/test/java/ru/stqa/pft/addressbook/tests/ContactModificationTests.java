package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification(){
    app.getNavigationHelper().gotoContactPage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Oksana", "Grigoreva", "Saint-Petersburg", "89112999959", "kiyrina@mail.ru", "the first"), true);
    }
    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Oksana", "Grigoreva", "Saint-Petersburg", "89112999959", "kiyrina@mail.ru", null), false);
    app.getContactHelper().updateContactModification();
    app.getNavigationHelper().returnToHomePage();

  }
}
