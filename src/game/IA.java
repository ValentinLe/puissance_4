
package game;

import java.util.ArrayList;

/**
  * Classe de l'IA
  */
public class IA {

  public int getOptimumMove(Board b, int colorIa, int prof) {
    ArrayList<Integer> rec = this.alphabeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, colorIa, prof);
    return rec.get(1);
  }

  /**
    * algorithme alphabeta
    * @param b l'etat du jeu de depart
    * @param alpha valeur minimum du joueur Max
    * @param beta valeur maximum du joueur Min
    * @param colorIa la couleur de l'IA, a maximiser
    * @param prof la profondeur de recherche de l'IA
    * @return la liste de la valeur et du meilleur coup a jouer (colonne)
    */
  public ArrayList<Integer> alphabeta(Board b, int alpha, int beta, int colorIa, int prof) {
    ArrayList<Integer> listReturn = new ArrayList<>();
    if (prof == 0 || b.getOver()) {
      listReturn.add(b.getValue(b.getPlayerColor()));
      listReturn.add(null);
      return listReturn;
    } else {
      if (b.getOtherPlayerColor() == colorIa) {
        Integer valueFinal = Integer.MIN_VALUE;
        Integer move = null;
        for (Integer col : b.getMoves()) {
          ArrayList<Integer> rec = this.alphabeta(b.playMove(col), alpha, beta, b.getPlayerColor(), prof-1);
          Integer value = rec.get(0);
          Integer action = rec.get(1);
          if (value > valueFinal) {
            move = col;
            valueFinal = value;
          }
          alpha = Math.max(alpha,value);
          if (alpha >= beta) {
            listReturn.add(alpha);
            listReturn.add(col);
            return listReturn;
          }
        }
        listReturn = new ArrayList<>();
        listReturn.add(alpha);
        listReturn.add(move);
        return listReturn;
      } else {
        Integer valueFinal = Integer.MAX_VALUE;
        Integer move = null;
        for (Integer col : b.getMoves()) {
          ArrayList<Integer> rec = this.alphabeta(b.playMove(col), alpha, beta, b.getPlayerColor(), prof-1);
          Integer value = rec.get(0);
          Integer action = rec.get(1);
          if (value < valueFinal) {
            move = col;
            valueFinal = value;
          }
          beta = Math.min(beta,value);
          if (alpha >= beta) {
            listReturn.add(beta);
            listReturn.add(col);
            return listReturn;
          }
        }
        listReturn = new ArrayList<>();
        listReturn.add(beta);
        listReturn.add(move);
        return listReturn;
      }
    }
  }

}
