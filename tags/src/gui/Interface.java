
package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import game.*;

public class Interface extends JFrame {

    private Board b;
    private View view;
    private IA ia;
    private int size;

    public Interface(Board b) {
        this.b = b;
        this.size = 40;
        this.ia = new IA(this.b);
        this.setTitle("Puissance 4");
        this.setResizable(true);

        this.view = new View(this.b,size);
        view.setPreferredSize(new Dimension(this.b.getWidth()*size + 1,(this.b.getHeight() + 1)*size + 1));
        view.setBackground(Color.white);
        view.addMouseListener(new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (!Interface.this.b.getOver()) {
              int x = e.getX();
              x = Math.round(x/Interface.this.size);
              if (!Interface.this.b.isColumnFull(x)) {
                Interface.this.b.addPiece(x);
                if (Interface.this.b.playerWin(Interface.this.b.getOtherPlayerColor()) || Interface.this.b.isFull()) {
                  Interface.this.repaint();
                }
              }
              if (!Interface.this.b.getOver()) {
                ArrayList<Integer> choiceIa = Interface.this.ia.alphabeta(Interface.this.b,Integer.MIN_VALUE,Integer.MAX_VALUE, Interface.this.b.getPlayerColor(), 10);
                Interface.this.b.addPiece(choiceIa.get(1));
                if (Interface.this.b.playerWin(Interface.this.b.getOtherPlayerColor()) || Interface.this.b.isFull()) {
                  Interface.this.repaint();
                }
              }
            }
          }

          @Override
          public void mousePressed(MouseEvent e) {}

          @Override
          public void mouseReleased(MouseEvent e) {}

          @Override
          public void mouseEntered(MouseEvent e) {}

          @Override
          public void mouseExited(MouseEvent e) {
            Interface.this.view.setColumn(-1);
            Interface.this.view.update(Interface.this.view);
          }
      });
      view.addMouseMotionListener(new MouseMotionListener(){
          @Override
          public void mouseDragged(MouseEvent e) {}

          @Override
          public void mouseMoved(MouseEvent e) {
            if (!Interface.this.b.getOver()) {
              int x = e.getX();
              x = Math.round(x/Interface.this.size);
              Interface.this.view.setColumn(x);
              Interface.this.repaint();
            }
          }
      });

        this.add(view);

        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
