package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoContactPage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Oksana", "Grigoreva", "Saint-Petersburg", "89112999959", "kiyrina@mail.ru", "the first"), false);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().selectContact(before.size()-1);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().showAlertMessage();
    app.getNavigationHelper().gotoContactPage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size()-1);

    before.remove(before.size()-1);
    Assert.assertEquals(before, after);
  }
}
