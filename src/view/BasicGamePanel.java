package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * Canvas to display the game, has its own refresh interval.
 */
public class BasicGamePanel extends Canvas implements IAsteroidView,
        ActionListener {

    private final Timer timer;
    private final ArrayList<IDrawable> elements;

    public BasicGamePanel() {
        this.setBackground(Color.black);
        this.elements = new ArrayList<>();
        this.timer = new Timer(50, this);
        this.timer.start();
    }

    @Override
    public void addGameElement(IDrawable e) {
        synchronized (this.elements) {
            this.elements.add(e);
        }
    }

    @Override
    public void removeGameElement(IDrawable e) {
        synchronized (this.elements) {
            this.elements.remove(e);
        }
    }

    @Override
    public void clearGameElement() {
        synchronized (this.elements) {
            this.elements.clear();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // draw all elements inside this panel
        synchronized (this.elements) {
            this.elements.stream()
                    .filter((element) -> (element.isVisible()))
                    .forEach((element) -> {
                        element.draw(g);
                    });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    @Override
    public Object getSynchronizedObject() {
        return this.elements;
    }
}
