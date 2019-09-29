package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }


  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getPhonenumber());
    type(By.name("email"), contactData.getEmail());

    /*if (creation){
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    } */

  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void showAlertMessage() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification() {
    click((By.xpath("//img[@alt='Edit']")));
  }

  public void updateContactModification() {
    click((By.name("update")));
  }

  public void createContact(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, creation);
    submitContactCreation();
  }


  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (int i=0; i<elements.size();i++) {
        int tr = i+2;
        String lastname = elements.get(i).findElement(By.xpath("//table[@id='maintable']/tbody/tr["+tr+"]/td[2]")).getText();
        String firstname = elements.get(i).findElement(By.xpath("//table[@id='maintable']/tbody/tr["+tr+"]/td[3]")).getText();
        int id = Integer.parseInt(elements.get(i).findElement(By.tagName("input")).getAttribute("value"));
        ContactData contact = new ContactData(id, lastname, firstname, null, null, null, null);
        contacts.add(contact);
    }
    return contacts;
  }

}
