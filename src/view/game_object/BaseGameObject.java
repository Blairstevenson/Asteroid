package view.game_object;

import model.GameComponent;
import view.IDrawable;

public abstract class BaseGameObject extends GameComponent
        implements IDrawable {

    private boolean visible;

    private int bound;

    public BaseGameObject() {
        this.visible = true;
    }

    /**
     * Rotate a point around center point.
     *
     * @param x x position relative to center point.
     * @param y y position relative to center point.
     * @param angle angle of rotation.
     * @return point after rotation, in absolute coordinate, not coordinate
     * relative to center point.
     */
    public int[] rotate(int x, int y, double angle) {
        return new int[]{
            (int) Math.round(this.getPosx() + Math.cos(angle) * x
            - Math.sin(angle) * y),
            (int) Math.round(this.getPosy() + Math.sin(angle) * x
            + Math.cos(angle) * y)
        };
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
