
package game;

import gui.AbstractModelListener;
import java.util.ArrayList;

public class Board extends AbstractModelListener {

  private int[][] grid;
  private int width;
  private int height;
  private int playerColor;
  private boolean over;
  public static final int EMPTY = 0;
  public static final int YELLOW = 1;
  public static final int RED = 2;

  public Board (int width, int height) {
    this.width = width;
    this.height = height;
    this.playerColor = YELLOW;
    this.over = false;
    this.initGrid();
  }

  public Board(int width, int height, int[][] grid, int playerColor) {
    this.width = width;
    this.height = height;
    this.grid = grid;
    this.playerColor = playerColor;
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

  public int getOtherPlayerColor() {
    if (this.playerColor == RED) {
      return YELLOW;
    } else {
      return RED;
    }
  }

  public int getOtherPlayerColor(int color) {
    if (color == RED) {
      return YELLOW;
    } else {
      return RED;
    }
  }

  public int[][] getGrid() {
    return this.grid;
  }

  public int[][] getCopyGrid() {
    int[][] newGrid = new int[this.height][this.width];
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        newGrid[j][i] = this.grid[j][i];
      }
    }
    return newGrid;
  }

  public boolean getOver() {
    return this.over;
  }

  public void setOver(boolean newState) {
    this.over = newState;
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
    this.over = true;
    return true;
  }

  public boolean isColumnFull(int column) {
    return this.grid[0][column] != EMPTY;
  }

  public void addPiece(int column) {
    int j = this.height - 1;
    while (this.grid[j][column] != EMPTY) {
      j -= 1;
    }
    this.grid[j][column] = this.playerColor;
    this.changePlayer();
    this.fireChange();
  }

  public void changePlayer() {
    if (this.playerColor == RED) {
      this.playerColor = YELLOW;
    } else {
      this.playerColor = RED;
    }
  }

  public int countPieceLine(int i, int j, int color, int dirX, int dirY) {
    int c = 0;
    while (0 <= i + c*dirX && i + c*dirX < this.width &&
     0 <= j + c*dirY && j + c*dirY < this.height &&
     this.grid[j + c*dirY][i + c*dirX] == color) {
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
              this.countPieceLine(i,j,color,-1,-1) >= 4 ||
              this.countPieceLine(i,j,color,0,1) >= 4) {
            this.over = true;
            return true;
          }
        }
      }
    }
    return false;
  }
  /*
  public int getValue() {
    int value = 0;
    int color = this.playerColor;
    int otherColor = this.getOtherPlayerColor();
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        if (this.grid[j][i] != EMPTY) {

          if (this.countPieceLine(i,j,color,-1,-1) >= 4 ||
          this.countPieceLine(i,j,color,1,-1) >= 4 ||
          this.countPieceLine(i,j,color,1,0) >= 4 ||
          this.countPieceLine(i,j,color,0,1) >= 4) {
            value = Integer.MAX_VALUE;
          }

          if (this.countPieceLine(i,j,otherColor,-1,-1) >= 4 ||
          this.countPieceLine(i,j,otherColor,1,-1) >= 4 ||
          this.countPieceLine(i,j,otherColor,1,0) >= 4 ||
          this.countPieceLine(i,j,otherColor,0,1) >= 4) {
            value = Integer.MIN_VALUE;
          }
        }
      }
    }
    return value;
  }
  */

  public int getValue(int color) {
    int value = 0;
    int otherColor = this.getOtherPlayerColor(color);
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        if (this.grid[j][i] != EMPTY && this.grid[j][i] == color) {
          int diagoGD = this.countPieceLine(i,j,color,-1,-1);
          int diagoDG = this.countPieceLine(i,j,color,1,-1);
          int horiz = this.countPieceLine(i,j,color,1,0);
          int verti = this.countPieceLine(i,j,color,0,1);

          if (diagoDG >= 4 || diagoGD >= 4 || horiz >= 4 || verti >= 4) {
            value += 1000;
          }

          if (horiz == 1) {
            value += 10;
          } else if (horiz == 2) {
            value += 20;
          } else if (horiz == 3) {
            value += 5;
          }

          if (verti == 1) {
            value += 10;
          } else if (verti == 2) {
            value += 20;
          } else if (verti == 3) {
            value += 5;
          }

          if (diagoGD == 1) {
            value += 10;
          } else if (diagoGD == 2) {
            value += 20;
          } else if (diagoGD == 3) {
            value += 5;
          }

          if (diagoDG == 1) {
            value += 10;
          } else if (diagoDG == 2) {
            value += 20;
          } else if (diagoDG == 3) {
            value += 5;
          }
        } else {
          int diagoGD = this.countPieceLine(i,j,otherColor,-1,-1);
          int diagoDG = this.countPieceLine(i,j,otherColor,1,-1);
          int horiz = this.countPieceLine(i,j,otherColor,1,0);
          int verti = this.countPieceLine(i,j,otherColor,0,1);

          if (horiz == 1) {
            value -= 5;
          } else if (horiz == 2) {
            value -= 50;
          } else if (horiz == 3) {
            value -= 500;
          }

          if (verti == 1) {
            value -= 5;
          } else if (verti == 2) {
            value -= 50;
          } else if (verti == 3) {
            value -= 500;
          }

          if (diagoGD == 1) {
            value -= 5;
          } else if (diagoGD == 2) {
            value -= 50;
          } else if (diagoGD == 3) {
            value -= 500;
          }

          if (diagoDG == 1) {
            value -= 5;
          } else if (diagoDG == 2) {
            value -= 50;
          } else if (diagoDG == 3) {
            value -= 500;
          }
        }
      }
    }
    return value;
  }

  public ArrayList<Integer> getMoves() {
    ArrayList<Integer> moves = new ArrayList<>();
    for (int i = 0; i<this.width; i++) {
      if (!this.isColumnFull(i)) {
        moves.add(i);
      }
    }
    return moves;
  }

  public Board copyBoard() {
    return new Board(this.width, this.height, this.getCopyGrid(), this.playerColor);
  }

  public Board playMove(int column) {
    Board newBoard = this.copyBoard();
    newBoard.addPiece(column);
    return newBoard;
  }
}
