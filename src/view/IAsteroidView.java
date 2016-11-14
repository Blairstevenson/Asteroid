package view;

import java.awt.event.KeyListener;

public interface IAsteroidView {
    public void addGameElement(IDrawable e);
    public void removeGameElement(IDrawable e);
    public void clearGameElement();
    public Object getSynchronizedObject();
    public void showEnemyCount(int enemy);
    public void showLifeCount(int life);
    public void showNotification(String notification);
    //
    public void addKeyListener(KeyListener kl);
    public int getWidth();
    public int getHeight();
}
