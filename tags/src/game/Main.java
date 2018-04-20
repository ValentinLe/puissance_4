
package game;

public class Main {

    public static void main(String[] args) {
      Board b = new Board(7,7);
      b.setValue(4,4,b.YELLOW);
      System.out.println(b.toString());
    }

}
