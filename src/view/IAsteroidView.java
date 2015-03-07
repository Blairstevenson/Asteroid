package view;

import java.awt.event.KeyListener;

public interface IAsteroidView {
    public void addGameElement(IDrawable e);
    public void removeGameElement(IDrawable e);
    public void clearGameElement();
    public Object getSynchronizedObject();
    //
    public void addKeyListener(KeyListener kl);
    public int getWidth();
    public int getHeight();
}
