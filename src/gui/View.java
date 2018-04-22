
package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import game.*;

public class View extends JPanel implements ModelListener {

  private Board b;
  private int size;
  private int column;

  public View(Board b,int size) {
    this.b = b;
    this.b.addListener(this);
    this.size = size;
    this.column = -1;
  }

  public void setColumn(int newColumn) {
    this.column = newColumn;
  }

  @Override
  public void paintComponent(Graphics g) {
    int[][] grid = this.b.getGrid();
    if (this.column != -1 && !this.b.getOver()) {
      if (this.b.getPlayerColor() == Board.RED) {
        g.setColor(Color.RED);
      } else {
        g.setColor(Color.YELLOW);
      }
      g.fillOval(size*this.column, 0, size, size);
    }

    for (int j = 0; j<this.b.getHeight(); j++) {
      for (int i = 0; i<this.b.getWidth(); i++) {
        if (grid[j][i] == Board.YELLOW) {
          g.setColor(Color.YELLOW);
          g.fillOval(size*i, size*(j+1), size, size);
        } else if (grid[j][i] == Board.RED) {
          g.setColor(Color.RED);
          g.fillOval(size*i, size*(j+1), size, size);
        }
        g.setColor(Color.black);
        g.drawRect(size*i, size*(j+1), size, size);
      }
    }
  }

  @Override
  public void update(Object src) {
    repaint();
  }
}
