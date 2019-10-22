package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    app.contact().selectGroupForDeletion(group);
    Contacts uiContacts = app.contact().all();
    //если в группе нет контактов, то добавляем, а потом удаляем этот же контакт
    if (uiContacts.size() ==0){
      ContactData addedContact = before.iterator().next();
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

  }

}
