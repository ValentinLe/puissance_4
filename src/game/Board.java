
package game;

import gui.AbstractModelListener;
import java.util.ArrayList;

/**
  * Classe representant le plateau du jeu
  */
public class Board extends AbstractModelListener {

  private int[][] grid;
  private int width;
  private int height;
  private int playerColor;
  private boolean over;
  public static final int EMPTY = 0;
  public static final int YELLOW = 1;
  public static final int RED = 2;

  /**
    * Constructeur classique, initialise la grille et l'etat de jeu
    * @param width la largeur de la grille
    * @param height la hauteur de la grille
    */
  public Board (int width, int height) {
    this.width = width;
    this.height = height;
    this.playerColor = YELLOW;
    this.over = false;
    this.initGrid();
  }

  /**
    * Constructeur ou on lui passe tout en paramettre (pour l'IA)
    * @param width la largeur de la grille
    * @param height la hauteur de la grille
    * @param grid la grille deja construite
    * @param playerColor la couleur du joueur qui jouera le prochain coup
    */
  public Board(int width, int height, int[][] grid, int playerColor) {
    this.width = width;
    this.height = height;
    this.grid = grid;
    this.playerColor = playerColor;
  }

  /**
    * getteur de width
    * @return la largeur de la grille
    */
  public int getWidth() {
    return this.width;
  }

  /**
    * getteur de height
    * @return la hauteur de la grille
    */
  public int getHeight() {
    return this.height;
  }

  /**
    * getteur de la couleur du joueur qui est en train de jouer
    * @return la couleur du joueur
    */
  public int getPlayerColor() {
    return this.playerColor;
  }

  /**
    * getteur de la couleur du joueur qui n'est pas en train de jouer
    * @return la couleur du joueur qui ne joue pas
    */
  public int getOtherPlayerColor() {
    return this.getOtherPlayerColor(this.getPlayerColor());
  }

  /**
    * recupere la couleur opposee a celle donnee
    * @param color couleur dont on veut l'opposee
    * @return la couleur opposee
    */
  public int getOtherPlayerColor(int color) {
    if (color == RED) {
      return YELLOW;
    } else {
      return RED;
    }
  }

  /**
    * getteur de la grille
    * @return la grille du jeu
    */
  public int[][] getGrid() {
    return this.grid;
  }

  /**
    * fait une copie de la grille du jeu
    * @return la copie de la grille
    */
  public int[][] getCopyGrid() {
    int[][] newGrid = new int[this.height][this.width];
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        newGrid[j][i] = this.grid[j][i];
      }
    }
    return newGrid;
  }

  /**
    * getteur de l'etat de jeu, true si la partie est finie et false sinon
    * @return etat fini ou non du jeu
    */
  public boolean getOver() {
    return this.over;
  }

  /**
    * setteur de l'etat du jeu
    * @param newState nouvel etat de jeu
    */
  public void setOver(boolean newState) {
    this.over = newState;
  }

  /**
    * affichage de la grille en mode console
    * @return la string à afficher
    */
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

  /**
    * initialise la grille selon la taille donnee avec des emplacement EMPTY
    */
  public void initGrid() {
    this.grid = new int[this.height][this.width];
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        this.grid[j][i] = EMPTY;
      }
    }
  }

  /**
    * test si la grille est pleine
    * @return true si la grille est pleine
    */
  public boolean isFull() {
    for (int value : this.grid[0]) {
      if (value == EMPTY) {
        return false;
      }
    }
    this.over = true;
    return true;
  }

  /**
    * test si la colonne est pleine
    * @param column la colonne a tester
    * @return true si la colonne est vide
    */
  public boolean isColumnFull(int column) {
    return this.grid[0][column] != EMPTY;
  }

  /**
    * Ajoute une piece de la couleur du joueur qui est en train de jouer et change le joueur
    * @param column la colonne dans laquelle mettre la piece
    */
  public void addPiece(int column) {
    int j = this.height - 1;
    while (this.grid[j][column] != EMPTY) {
      j -= 1;
    }
    this.grid[j][column] = this.playerColor;
    this.changePlayer();
    this.fireChange();
  }

  /**
    * change le joueur qui est en train de jouer
    */
  public void changePlayer() {
    if (this.playerColor == RED) {
      this.playerColor = YELLOW;
    } else {
      this.playerColor = RED;
    }
  }

  /**
    * compte le nombre de pieces presente les unes a cote des autres selon une direction et une couleur donnee
    * @param i la colonne de la premiere piece
    * @param j la ligne de la premiere piece
    * @param color la couleur avec laquelle il faut tester
    * @param dirX la direction horizontale
    * @param dirY la direction verticale
    * @return le nombre de pieces alignees
    */
  public int countPieceLine(int i, int j, int color, int dirX, int dirY) {
    int c = 0;
    int x = i;
    int y = j;
    while ((0 <= x && x < this.width) && (0 <= y && y < this.height) &&
     this.grid[y][x] == color) {
      c += 1;
      x += dirX;
      y += dirY;
    }
    return c;
  }

  /**
    * test si le joueur donnee a gagne en regardant si il a fait au moins une ligne d'au moins 4 pions
    * @param color la couleur du joueur a tester
    * @return true si le joueur a gagne et false sinon
    */
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

  public boolean isFinish() {
    return this.isFull() || this.playerWin(this.getPlayerColor()) || this.playerWin(this.getOtherPlayerColor());
  }

  /**
    * initialise le jeu afin de recommencer
    */
  public void restart() {
    initGrid();
    this.over = false;
    this.playerColor = YELLOW;
    this.fireChange();
  }

  public int numberPieceOf(int color) {
    int cpt = 0;
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        if (this.grid[j][i] == color) {
          cpt += 1;
        }
      }
    }
    return cpt;
  }

  public boolean alignPossible(int i, int j, int dx, int dy) {
    int c = 0;
    int x = i;
    int y = j;
    while ((0 <= x && x < this.width) && (0 <= y && y < this.height)) {
      c += 1;
      x += dx;
      y += dy;
    }
    return c >= 4;
  }

  private void addPossibleAlign(int[][] matAlignPossible, int i, int j, int dx, int dy) {
    if (this.alignPossible(i, j, dx, dy)) {
      for (int k = 0; k < 4; k++) {
        matAlignPossible[j + k*dy][i + k*dx] += 1;
      }
    }
  }

  public int[][] createTableEvaluation() {
    int[][] matAlignPossible = new int[this.height][this.width];
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        this.addPossibleAlign(matAlignPossible, i, j, 1, -1);
        this.addPossibleAlign(matAlignPossible, i, j, 1, 0);
        this.addPossibleAlign(matAlignPossible, i, j, -1, -1);
        this.addPossibleAlign(matAlignPossible, i, j, 0, 1);
      }
    }
    return matAlignPossible;
  }
  
  public int getValue(int color) {
    int score = 0;
    if (this.isFinish()) {
      if (this.playerWin(color)) {
        return 1000;
      } else if (this.playerWin(this.getOtherPlayerColor(color))) {
        return -1000;
      } else {
        return 0;
      }
    }
    int[][] matAlignPossible = this.createTableEvaluation();
    for (int j = 0; j<this.height; j++) {
      for (int i = 0; i<this.width; i++) {
        if (this.grid[j][i] == color) {
          score += matAlignPossible[j][i];
        } else if (this.grid[j][i] == this.getOtherPlayerColor(color)) {
          score -= matAlignPossible[j][i];
        }
      }
    }
    return score;
  }

  /**
    * renvoie une liste de toutes les colonnes jouables (pas pleines)
    * @return liste des colonnes
    */
  public ArrayList<Integer> getMoves() {
    ArrayList<Integer> moves = new ArrayList<>();
    for (int i = 0; i<this.width; i++) {
      if (!this.isColumnFull(i)) {
        moves.add(i);
      }
    }
    return moves;
  }

  /**
    * fait une copy de l'etat du jeu actuel
    * @return la copy de l'etat du jeu
    */
  public Board copyBoard() {
    return new Board(this.width, this.height, this.getCopyGrid(), this.playerColor);
  }

  /**
    * fait une copy de l'etat de jeu et joue le coup donnee
    * @param column la colonne a jouer
    * @return un nouvel etat de jeu avec le coup joue
    */
  public Board playMove(int column) {
    Board newBoard = this.copyBoard();
    newBoard.addPiece(column);
    return newBoard;
  }
}
