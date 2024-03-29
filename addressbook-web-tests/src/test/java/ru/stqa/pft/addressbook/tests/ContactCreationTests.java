package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"));
    String xml = "";
    String line = reader.readLine();
    while (line != null){
      xml +=line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>)xstream.fromXML(xml);
    return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }
  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"));
    String json = "";
    String line = reader.readLine();
    while (line != null){
      json +=line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType()); //List<ContactData>.class
    return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }


  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) throws Exception {
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/stru.png");
    ContactData newContact = new ContactData().withFirstname("Oksana").withLastname("Grigoreva").withAddress("Saint-Petersburg").withPhoto(photo)
            .inGroup(groups.iterator().next());
    Contacts before = app.db().contacts();
    app.contact().create(newContact,true);
    app.goTo().ContactPage();
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.size()+1));
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }


}
