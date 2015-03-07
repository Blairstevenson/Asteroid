package view;

import java.awt.Graphics;

public interface IDrawable {
    public boolean isVisible();
    public void draw(Graphics g);
}
