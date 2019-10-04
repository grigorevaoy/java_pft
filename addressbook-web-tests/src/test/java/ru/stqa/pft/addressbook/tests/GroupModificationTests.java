package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Set;

public class GroupModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().all().size()==0){
      app.group().create(new GroupData().withName("the first"));
    }
  }

  @Test
  public void testGroupModification(){
    Set<GroupData> before = app.group().all();
    GroupData modifedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifedGroup.getId()).withName("the first").withHeader("test1").withFooter("test2");
    app.group().modify(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(),before.size());
    before.remove(modifedGroup);
    before.add(group);
    Assert.assertEquals(before,after);
  }

}
