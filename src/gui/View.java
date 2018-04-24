
package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.Image.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import game.*;

/**
  * classe de la vue
  */
public class View extends JPanel implements ModelListener {

  private Board b;
  private Image imBoard;
  private Image imYellow;
  private Image imRed;
  private int size;
  private int column;

  /**
    * constructeur de la vue
    * @param b l'etat de jeu a representer
    * @param size la taille des pions
    */
  public View(Board b,int size) {
    this.b = b;
    this.b.addListener(this);
    this.imYellow = Toolkit.getDefaultToolkit().getImage("../ressources/images/yellow.png");
    this.imRed = Toolkit.getDefaultToolkit().getImage("../ressources/images/red.png");
    this.setOpaque(false);
    this.size = size;
    this.column = -1;
  }

  /**
    * setteur de la colonne qui est survolee
    * @param newColumn la colonne survolee
    */
  public void setColumn(int newColumn) {
    this.column = newColumn;
  }

  /**
    * dessine le visuel du jeu
    */
  @Override
  public void paintComponent(Graphics g) {
    int[][] grid = this.b.getGrid();
    int sizeDecal = size + 10;
    Color bg = new Color(0,42,224);
    Color cEmpty = new Color(222,227,233);
    g.setColor(bg);
    g.fillRect(0, size, this.b.getWidth()*sizeDecal + 10, this.b.getHeight()*sizeDecal + 10);
    if (this.column != -1 && !this.b.getOver()) {
      if (this.b.getPlayerColor() == Board.RED) {
        g.drawImage(this.imRed, sizeDecal*this.column + 10, 0, size, size, null);
      } else {
        g.drawImage(this.imYellow, sizeDecal*this.column + 10, 0, size, size, null);
      }
    }
    for (int j = 0; j<this.b.getHeight(); j++) {
      for (int i = 0; i<this.b.getWidth(); i++) {
        if (grid[j][i] == Board.YELLOW) {
          g.drawImage(this.imYellow, sizeDecal*i+10, sizeDecal*(j+1), size, size, null);
        } else if (grid[j][i] == Board.RED) {
          g.drawImage(this.imRed, sizeDecal*i + 10, sizeDecal*(j+1), size, size, null);
        } else {
          g.setColor(cEmpty);
          g.fillOval(sizeDecal*i + 10, sizeDecal*(j+1), size, size);
        }
      }
    }
  }

  /**
    * actualise le visuel
    */
  @Override
  public void update(Object src) {
    repaint();
  }
}
