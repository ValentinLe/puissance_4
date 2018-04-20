
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

  public View(Board b,int size) {
    this.b = b;
    this.size = size;
  }

  @Override
  public void paintComponent(Graphics g) {
    int[][] grid = this.b.getGrid();
    for (int j = 0; j<this.b.getHeight(); j++) {
      for (int i = 0; i<this.b.getWidth(); i++) {
        if (grid[j][i] == Board.YELLOW) {
          g.setColor(Color.YELLOW);
          g.fillRect(size*i, size*j,size, size);
        } else if (grid[j][i] == Board.RED) {
          g.setColor(Color.RED);
          g.fillRect(size*i, size*j,size, size);
        }
      }
    }
  }

  @Override
  public void update(Object src) {
    repaint();
  }
}
