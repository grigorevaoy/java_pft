package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactToGroupAdditionTests  extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){

    if(app.db().contacts().size() ==0){
      app.goTo().ContactPage();
      app.contact().create(new ContactData().withFirstname("Oksana").withLastname("Grigoreva").withAddress("Saint-Petersburg").withHomePhone("89112999959").withEmail("kiyrina@mail.ru"), true);
    }

    if(app.db().groups().size()==0){
      app.goTo().groupPage();
      GroupData group = new GroupData().withName("the first'");
      app.group().create(group);
    }
  }

  @Test
  public void TestContactToGroupAddition(){
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    Contacts before = app.db().contacts();

    ContactData addedContact = returnContactForAddition(before);
    //ContactData addedContact = before.iterator().next();

    app.goTo().ContactPage();
    app.contact().addToGroup(addedContact,group);
    app.goTo().SelectedPage(group);


    assertThat(addedContact.getGroups().withAdded(group), equalTo(app.db().getContactFromDb(addedContact.getId()).getGroups()));


  }

  private ContactData returnContactForAddition(Contacts before) {
    for (ContactData contact: before){
      if (contact.getGroups().size() == 0){
        return contact;
      }
    }
    app.contact().create(new ContactData().withFirstname("Oksana").withLastname("Grigoreva").withAddress("Saint-Petersburg").withHomePhone("89112999959").withEmail("kiyrina@mail.ru"), true);
    Contacts newList = app.db().contacts();
    return newList.iterator().next();
  }



}
