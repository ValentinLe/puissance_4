
package gui;

import game.*;

public class Main {

    public static void main(String[] args) {
      Board b = new Board(7,7);
      b.addPiece(4);
      b.addPiece(0);
      b.addPiece(2);
      b.changePlayer();
      b.addPiece(4);
      b.addPiece(4);
      new Interface(b);
    }

}
