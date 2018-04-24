
package gui;

import game.*;

/**
  * Classe principale de la version graphique
  */
public class Main {

    public static void main(String[] args) {
      Board b = new Board(7,6);
      new Interface(b);
    }

}
