
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
    this.repaint();
  }

  /**
    * dessine le visuel du jeu
    */
  @Override
  public void paintComponent(Graphics g) {
    //super.paintComponent(g); // a voir pour animation
    int[][] grid = this.b.getGrid();
    int sizeDecal = size + 10;
    Color bg = new Color(0,42,224);
    Color yellow = new Color(255,209,26);
    Color red = new Color(255,51,51);
    Color cEmpty = new Color(222,227,233);
    g.setColor(bg);
    g.fillRect(0, size, this.b.getWidth()*sizeDecal + 10, this.b.getHeight()*sizeDecal + 10);
    if (this.column != -1 && !this.b.getOver()) {
      if (this.b.getPlayerColor() == Board.RED) {
        g.setColor(red);
        g.fillOval(sizeDecal*this.column + 10, 0, size, size);
      } else {
        g.setColor(yellow);
        g.fillOval(sizeDecal*this.column + 10, 0, size, size);
      }
    }
    for (int j = 0; j<this.b.getHeight(); j++) {
      for (int i = 0; i<this.b.getWidth(); i++) {
        if (grid[j][i] == Board.YELLOW) {
          g.setColor(yellow);
          g.fillOval(sizeDecal*i+10, sizeDecal*(j+1), size, size);
        } else if (grid[j][i] == Board.RED) {
          g.setColor(red);
          g.fillOval(sizeDecal*i+10, sizeDecal*(j+1), size, size);
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
