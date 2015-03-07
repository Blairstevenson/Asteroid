package view;

import java.awt.event.KeyListener;

public interface IAsteroidView {
    public void addGameElement(IDrawable e);
    public void removeGameElement(IDrawable e);
    public void addKeyListener(KeyListener kl);
}
