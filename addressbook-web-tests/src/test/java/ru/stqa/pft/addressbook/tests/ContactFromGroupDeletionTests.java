package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactFromGroupDeletionTests extends TestBase{

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
  public void TestContactFromGroupDeletion(){

    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();

    GroupData group = app.db().getGroupFromDb(returnContactForDeletion(contacts,groups).getId()); //возвращаем группу, в которой есть контакты

    app.contact().selectGroupForDeletion(group);
    Contacts contactsForDeletion = group.getContacts();

    ContactData removeContact = contactsForDeletion.iterator().next();
    app.contact().deleteSelectedContactFromGroup(removeContact,group);
    app.goTo().SelectedPage(group);

    Contacts afterDeletionContacts = app.db().getGroupFromDb(group.getId()).getContacts();
    assertThat(afterDeletionContacts, equalTo(contactsForDeletion.without(removeContact)));

    //старый тест
    /*
    GroupData group = groups.iterator().next();
    app.contact().selectGroupForDeletion(group);
    Contacts uiContacts = app.contact().all();
    ContactData addedContact = before.iterator().next();
    //если в группе нет контактов, то добавляем, а потом удаляем этот же контакт
    if (uiContacts.size() ==0){
      app.goTo().AllContactsPage();
      app.contact().addToGroup(addedContact,group);
      app.goTo().SelectedPage(group);
      app.contact().deleteSelectedContactFromGroup(addedContact,group);
      app.goTo().SelectedPage(group);
    } else { //если в группе есть контакты, то просто удаляем один из них
      ContactData removeContact = uiContacts.iterator().next();
      app.contact().deleteSelectedContactFromGroup(removeContact,group);
      app.goTo().SelectedPage(group);

    }
    Contacts beforeDeletionContacts = group.getContacts();
    Contacts afterDeletionContacts = app.db().getGroupFromDb(group.getId()).getContacts();
    assertThat(afterDeletionContacts, equalTo(beforeDeletionContacts.without(addedContact)));


     */

  }

  private GroupData returnContactForDeletion(Contacts contacts, Groups groups) {
    for (ContactData contact : contacts) { //идем по всем контактам
      if (contact.getGroups().size() > 0) { //смотрим, входят ли они в какие группы
        Groups groupsWithContacts =  contact.getGroups();//если входят, берем список таких групп
        return groupsWithContacts.iterator().next(); // возвращаем из списка следующую групп
      }
    } //если нет контактов в группах
      ContactData addedContact = contacts.iterator().next(); //берем следующий контакт
      GroupData group = groups.iterator().next(); //берем следующую группу
      app.goTo().ContactPage();
      app.contact().addToGroup(addedContact, group); //добавляем контакт в выбранную группу
      app.goTo().SelectedPage(group);
      return group; //возвращаем группу, в которую ранее добавили контакт

  }
}
