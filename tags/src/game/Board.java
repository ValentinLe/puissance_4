
package game;

public class Board {

  private int[][] grid;
  private int width;
  private int height;
  public final int EMPTY = 0;
  public final int YELLOW = 1;
  public final int RED = 2;

  public Board (int width, int height) {
    this.width = width;
    this.height = height;
    this.initGrid();
  }

  public void setValue(int i, int j, int value) {
    this.grid[j][i] = value;
  }

  public String toString() {
    String ch = "\n";
    for (int j = 0; j<this.height; j++) {
      ch += "| ";
      for (int i = 0; i<this.width; i++) {
        int value = this.grid[j][i];
        String str;
        if (value == YELLOW) {
          str = "O";
        } else if (value == RED) {
          str = "X";
        } else {
          str = " ";
        }
        ch += str + " | ";
      }
      ch += "\n";
    }
    ch += "-";
    for (int k = 0; k<this.width; k ++) {
      ch += "-" + k + "--";
    }
    return ch;
  }

  public void initGrid() {
    this.grid = new int[this.height][this.width];
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        this.grid[j][i] = EMPTY;
      }
    }
  }
}
