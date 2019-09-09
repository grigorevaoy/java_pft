package ru.stqa.pft.secondtask;

public class RunPoint {

  public static void main(String[] args) {
    //первая часть задания: считаем расстояние между точками прямо в запускаемом классе
    Point p1 = new Point(5, 5);
    Point p2 = new Point(4, 4);

    System.out.println("Первая часть задания: расстояние между точками (" + p1.x + "," + p1.y + ") и (" + p2.x + "," + p2.y + ")" + " = " + distance(p1, p2));

    //вторая часть задания: считаем расстояние между точками в классе Point
    Point x = new Point(5, 5);
    Point y = new Point(4, 4);
    System.out.println("Вторая часть задания: расстояние между точками (" + x.x + "," + x.y + ") и (" + y.x + "," + y.y + ")" + " = " + Point.distance(x, y));
  }

  public static double distance(Point r1, Point r2){
    double res = Math.sqrt(Math.pow(r2.x - r1.x,2)+Math.pow(r2.y - r1.y,2));
    return res;
  }
}
