package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactToGroupAdditionTests  extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){

    ContactData a1 = app.db().contacts().iterator().next();
   // Contacts contactsOutOfgroups = listOfContactsOutOfGroups(app.db().contacts());

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

  /* private Contacts listOfContactsOutOfGroups(Contacts contacts) {
    for (contact: contacts){
      contacts.iterator().next().getGroups();
    }

    return contacts;
  } */

  @Test
  public void TestContactToGroupAddition(){
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    Contacts before = app.db().contacts();
    ContactData addedContact = before.iterator().next();

    //проверка списка групп контакта до добавления в группу
    assertThat(addedContact.getGroups(), equalTo(app.db().contacts().iterator().next().getGroups()));
   Groups groupsBeforeAddition = addedContact.getGroups();

    app.goTo().ContactPage();
    app.contact().addToGroup(addedContact,group);
    app.goTo().SelectedPage(group);

   // assertThat(groupsBeforeAddition, equalTo(app.db().contacts().stream().map((g) -> new ContactData().getId(g.get).collect(Collectors.toSet())));
    //проверка списка групп контакта после добавления в группу
    Groups groupsAfterAddition = app.db().contacts().iterator().next().getGroups();
    assertThat(groupsBeforeAddition, equalTo(app.db().contacts().iterator().next().getGroups()));


  }



}
