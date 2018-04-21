
package game;

import java.util.ArrayList;

public class IA {

  private Board b;

  public IA(Board b) {
    this.b = b;
  }

  public ArrayList<Integer> alphabeta(Board b, int alpha, int beta, int colorIa, int prof) {
    ArrayList<Integer> listReturn = new ArrayList<>();
    if (prof == 0 || b.getOver()) {
      listReturn.add(b.getValue());
      listReturn.add(null);
      return listReturn;
    } else {
      if (b.getPlayerColor() == colorIa) {
        Integer valueFinal = Integer.MIN_VALUE;
        Integer move = null;
        for (Integer col : b.getMoves()) {
          ArrayList<Integer> rec = this.alphabeta(b.playMove(col), alpha, beta, colorIa, prof-1);
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
          ArrayList<Integer> rec = this.alphabeta(b.playMove(col), alpha, beta, colorIa, prof-1);
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
