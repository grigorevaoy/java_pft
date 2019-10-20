package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactToGroupAdditionTests  extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().contacts().size() ==0){
      app.goTo().ContactPage();
      app.contact().create(new ContactData().withFirstname("Oksana").withLastname("Grigoreva").withAddress("Saint-Petersburg").withHomePhone("89112999959").withEmail("kiyrina@mail.ru"), true);
    }
    //проверка на то, есть ли вообще группы, в которые хотим добавить контакт
  }

  @Test
  public void TestContactToGroupAddition(){
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    Contacts before = app.db().contacts();
    ContactData addedContact = before.iterator().next();
   // ContactData contact = new ContactData().withFirstname("Oksana").withLastname("Grigoreva").withAddress("Saint-Petersburg")
    //       .inGroup(groups.iterator().next());
    app.goTo().ContactPage();
    app.contact().addToGroup(addedContact,group);
    app.goTo().SelectedPage(group);
    //сравнить то, что на экране и то, что в БД
    verifyContactListInUI(addedContact);

  }



}
