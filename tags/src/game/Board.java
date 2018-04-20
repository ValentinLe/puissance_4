
package game;

public class Board {

  private int[][] grid;
  private int width;
  private int height;
  private int playerColor;
  public static final int EMPTY = 0;
  public static final int YELLOW = 1;
  public static final int RED = 2;

  public Board (int width, int height) {
    this.width = width;
    this.height = height;
    this.playerColor = YELLOW;
    this.initGrid();
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getPlayerColor() {
    return this.playerColor;
  }

  public int[][] getGrid() {
    return this.grid;
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

  public boolean isFull() {
    for (int value : this.grid[0]) {
      if (value == EMPTY) {
        return false;
      }
    }
    return true;
  }

  public boolean isColumnFull(int column) {
    return this.grid[0][column] == EMPTY;
  }

  public void addPiece(int column) {
    int j = this.height - 1;
    while (this.grid[j][column] != EMPTY) {
      j -= 1;
    }
    this.grid[j][column] = this.playerColor;
  }

  public void changePlayer() {
    if (this.playerColor == RED) {
      this.playerColor = YELLOW;
    } else {
      this.playerColor = RED;
    }
  }

  public int countPieceLine(int i, int j, int value, int dirX, int dirY) {
    int c = 1;
    while (i + c*dirX < this.width && 0 <= j + c*dirY && j + c*dirY < this.height && this.grid[j + c*dirY][i + c*dirX] == value) {
      c += 1;
    }
    return c;
  }

  public boolean playerWin(int color) {
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        if (this.grid[j][i] != EMPTY) {
          if (this.countPieceLine(i,j,color,1,-1) >= 4 ||
              this.countPieceLine(i,j,color,1,0) >= 4 ||
              this.countPieceLine(i,j,color,1,1) >= 4 ||
              this.countPieceLine(i,j,color,0,1) >= 4) {

            return true;
          }
        }
      }
    }
    return false;
  }

}
