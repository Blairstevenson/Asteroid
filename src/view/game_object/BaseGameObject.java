package view.game_object;

import java.awt.Graphics;
import model.GameComponent;
import view.IDrawable;

public abstract class BaseGameObject extends GameComponent
        implements IDrawable {

    private boolean visible;

    private int bound;

    public BaseGameObject() {
        this.visible = true;
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval(this.getPosx() - this.bound, this.getPosy() - this.bound,
                this.bound * 2, this.bound * 2);
    }

    @Override
    public boolean isInside(int x, int y) {
        return (x - this.getPosx()) * (x - this.getPosx())
                + (x - this.getPosy()) * (x - this.getPosy())
                <= this.bound * this.bound;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }
}
