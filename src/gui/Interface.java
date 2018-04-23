
package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import game.*;

public class Interface extends JFrame implements ModelListener {

    private Board b;
    private View view;
    private IA ia;
    private int size;

    public Interface(Board b) {
        this.b = b;
        this.b.addListener(this);
        this.size = 80;
        this.ia = new IA(this.b);
        this.setResizable(false);
        this.setTitle("Puissance 4");

        this.view = new View(this.b,size);
        view.setPreferredSize(new Dimension(this.b.getWidth()*(size+10) + 11,(this.b.getHeight() + 1)*(size+10) + 1));

        view.addMouseListener(new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (!Interface.this.b.getOver()) {
              int x = e.getX();
              x -= 5;
              if (x < 0) {
                x = 0;
              }
              x = Math.round(x / (Interface.this.size + 10));
              if (x >= Interface.this.b.getWidth()) {
                x = Interface.this.b.getWidth() - 1;
              }
              if (!Interface.this.b.isColumnFull(x)) {
                Interface.this.b.addPiece(x);
                if (Interface.this.b.playerWin(Interface.this.b.getOtherPlayerColor()) || Interface.this.b.isFull()) {
                  Interface.this.repaint();
                }
                if (!Interface.this.b.getOver()) {
                  ArrayList<Integer> choiceIa = Interface.this.ia.alphabeta(Interface.this.b,Integer.MIN_VALUE,Integer.MAX_VALUE, Board.RED, 5);
                  Interface.this.b.addPiece(choiceIa.get(1));
                  if (Interface.this.b.playerWin(Interface.this.b.getOtherPlayerColor()) || Interface.this.b.isFull()) {
                    Interface.this.repaint();
                  }
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
              x -= 5;
              if (x < 0) {
                x = 0;
              }
              x = Math.round(x / (Interface.this.size + 10));
              if (x >= Interface.this.b.getWidth()) {
                x = Interface.this.b.getWidth() - 1;
              }
              Interface.this.view.setColumn(x);
              Interface.this.repaint();
            }
          }
      });

      JButton bRestart = new JButton("Restart");
      bRestart.setRequestFocusEnabled(false);
      bRestart.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Interface.this.b.restart();
        }
      });

      JButton bQuit = new JButton("Quit");
      bQuit.setRequestFocusEnabled(false);
      bQuit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Interface.this.dispose();
        }
      });

      JPanel zoneButton = new JPanel();
      zoneButton.setLayout(new GridLayout(2,1,20,20));
      zoneButton.add(bRestart);
      zoneButton.add(bQuit);

      this.setLayout(new GridBagLayout());
      GridBagConstraints gc = new GridBagConstraints();

      gc.gridx = 0;
      gc.gridy = 0;

      this.add(view,gc);
      gc.gridx = 1;
      this.add(zoneButton,gc);

      pack();
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
    }

    public void update(Object src) {
      repaint();
    }

}
