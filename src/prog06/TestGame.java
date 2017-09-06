package prog06;
import java.util.*;

public class TestGame {
  public static void main (String[] args) {
    WordPath game = new WordPath();
    try {
      game.loadDictionary("words");
    } catch (Exception e) {
      System.out.println("test: " + e);
    }
    game.solve("fail", "pass");
  }
}
