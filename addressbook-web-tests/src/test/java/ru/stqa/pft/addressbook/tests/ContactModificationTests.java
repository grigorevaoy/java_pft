package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().ContactPage();
    if (app.contact().all().size()==0){
      app.contact().create(new ContactData().withFirstname("Oksana").withLastname("Grigoreva").withAddress("Saint-Petersburg").withPhonenumber("89112999959").withEmail("kiyrina@mail.ru").withGroup("the first"), true);
    }
  }

  @Test
  public void testContactModification(){
   Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Oksana").withLastname("Grigoreva").withAddress("Saint-Petersburg").withPhonenumber("89112999959").withEmail("kiyrina@mail.ru");
    app.goTo().ContactPage();
    app.contact().modify(contact);
    app.goTo().ContactPage();
   Contacts after = app.contact().all();
    assertEquals(after.size(),before.size());
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

  }


}
