package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import view.game_object.SpaceShip;

public class GamePanel extends javax.swing.JPanel implements IAsteroidView,
        ActionListener {

    private final Timer timer;
    private final ArrayList<IDrawable> elements;
    private String notification;
    private int enemy;
    private final ArrayList<SpaceShip> ships;

    /**
     * Creates new form DFPanel
     */
    public GamePanel() {
        initComponents();
        this.setBackground(Color.black);
        this.elements = new ArrayList<>();
        this.ships = new ArrayList<>();
        this.timer = new Timer(50, this);
        this.timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        synchronized (this.elements) {
            // draw all elements inside this panel
            this.elements.stream()
                    .filter((element) -> (element.isVisible()))
                    .forEach((element) -> {
                        element.draw(g);
                    });
            g.setColor(Color.cyan);
            // draw notification
            if (this.notification != null) {
                g.drawString(this.notification,
                        this.getWidth() / 2 - 6 * this.notification.length() / 2,
                        this.getHeight() / 2 - 5);
            }
            // draw enemy count
            g.drawString("Asteroid: " + this.enemy, 0, 10);
            // draw life count
            synchronized (this.ships) {
                this.ships.stream().forEach((ship) -> {
                    ship.draw(g);
                });
            }
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

    @Override
    public void showEnemyCount(int enemy) {
        this.enemy = enemy;
    }

    @Override
    public void showNotification(String notification) {
        this.notification = notification;
    }

    @Override
    public void showLifeCount(int life) {
        synchronized (this.ships) {
            this.ships.clear();
            for (int i = 0; i < life; i++) {
                SpaceShip newShip = new SpaceShip();
                newShip.setFacingAngle((float) (-Math.PI / 2));
                newShip.setPosition(
                        this.getWidth() - newShip.getBound() * (3 * i + 2),
                        newShip.getBound() * 2);
                this.ships.add(newShip);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
