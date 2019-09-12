package ru.stqa.pft.secondtask;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {
  @Test
  public void testArea(){
    Point p1 = new Point(5, 5);
    Point p2 = new Point(4, 4);
    Assert.assertEquals(p1.distance(p2),1.4142135623730951);
  }
}
