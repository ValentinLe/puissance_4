
package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import game.*;

public class Interface extends JFrame {

    private Board b;

    public Interface(Board b) {
        this.b = b;
        this.setTitle("Puissance 4");
        this.setResizable(true);

        int size = 40;
        View view = new View(this.b,size);
        view.setPreferredSize(new Dimension(this.b.getWidth()*size,this.b.getHeight()*size));
        view.setBackground(Color.white);

        this.add(view);

        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
