package view.game_object;

import java.awt.Color;
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
        g.setColor(Color.white);
        g.drawOval(this.getPosx() - this.bound, this.getPosy() - this.bound,
                this.bound * 2, this.bound * 2);
    }

    @Override
    public boolean isInside(int x, int y) {
        return (x - this.getPosx()) * (x - this.getPosx())
                + (y - this.getPosy()) * (y - this.getPosy())
                <= this.bound * this.bound;
    }

    /**
     * Rotate a point around a center point.
     *
     * @param x x position relative to center point.
     * @param y y position relative to center point.
     * @param cx center point x
     * @param cy center point y
     * @param angle angle of rotation.
     * @return point after rotation, in absolute coordinate, not coordinate
     * relative to center point.
     */
    public static int[] rotate(int x, int y, int cx, int cy, double angle) {
        return new int[]{
            (int) Math.round(cx + Math.cos(angle) * x
            - Math.sin(angle) * y),
            (int) Math.round(cy + Math.sin(angle) * x
            + Math.cos(angle) * y)
        };
    }

    public boolean collis(BaseGameObject other) {
        return (other.getPosx() - this.getPosx())
                * (other.getPosx() - this.getPosx())
                + (other.getPosy() - this.getPosy())
                * (other.getPosy() - this.getPosy())
                < (other.bound + this.bound) * (other.bound + this.bound);
    }
    
//    public boolean collis(BaseGameObject other) {
//        return (other.getPosx() - this.getPosx())
//                * (other.getPosx() - this.getPosx())
//                + (other.getPosy() - this.getPosy())
//                * (other.getPosy() - this.getPosy())
//                < (other.bound + this.bound) * (other.bound + this.bound);
//    }

    public boolean offscreen(int top, int left, int w, int h) {
        return this.getPosx() + this.bound < left
                || this.getPosx() - this.bound > left + w
                || this.getPosy() + this.bound < top
                || this.getPosy() - this.bound > top + h;
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
