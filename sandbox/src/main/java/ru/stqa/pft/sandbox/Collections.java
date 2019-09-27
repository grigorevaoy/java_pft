package ru.stqa.pft.sandbox;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

public static void main (String args[]){
  String[] langs = {"java", "c#", "python","php"};
  List<String> languages = Arrays.asList("java", "c#", "python","php");

  for (String l: languages){
    System.out.println("Я хочу выучить "+l);
  }

}
}
