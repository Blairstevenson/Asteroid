package view.game_object;

import java.awt.Color;
import java.awt.Graphics;
import model.GameComponent;
import view.IDrawable;

public abstract class BaseGameObject extends GameComponent
        implements IDrawable {

    private boolean visible;

    private float bound;

    public BaseGameObject() {
        this.visible = true;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(Math.round(this.getPosx() - this.bound),
                Math.round(this.getPosy() - this.bound),
                Math.round(this.bound * 2),
                Math.round(this.bound * 2));
    }

    @Override
    public boolean contains(float x, float y) {
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
    public static float[] rotate(float x, float y,
            float cx, float cy, float angle) {
        return new float[]{
            (float) (cx + Math.cos(angle) * x - Math.sin(angle) * y),
            (float) (cy + Math.sin(angle) * x + Math.cos(angle) * y)
        };
    }

    /**
     * Check if this object intersects with another object.
     *
     * @param other other object to check.
     * @return true if intersect, false otherwise.
     */
    public boolean intersects(BaseGameObject other) {
        return (other.getPosx() - this.getPosx())
                * (other.getPosx() - this.getPosx())
                + (other.getPosy() - this.getPosy())
                * (other.getPosy() - this.getPosy())
                < (other.bound + this.bound) * (other.bound + this.bound);
    }

    /**
     * Check if this object intersects a line.
     *
     * @param l line to check.
     * @return true if intersect, false otherwise.
     */
    public boolean intersects(Line l) {
        float[] endPoint = l.getEndPoint();
        // directional intersection
        float dir = (endPoint[0] - l.getPosx()) * (this.getPosx() - l.getPosx())
                + (endPoint[1] - l.getPosy()) * (this.getPosy() - l.getPosy());
        if (dir <= 0) {
            return false;
        } else {
            float rec = ((endPoint[0] - l.getPosx()) * (l.getPosy() - this.getPosy())
                    - (endPoint[1] - l.getPosy()) * (l.getPosx() - this.getPosx()));
            float length2 = (endPoint[1] - l.getPosy()) * (endPoint[1] - l.getPosy())
                    + (endPoint[0] - l.getPosx()) * (endPoint[0] - l.getPosx());
            float distance2 = (rec * rec) / length2;
            return distance2 < this.getBound() * this.getBound();
        }
    }

    /**
     * Check if this object is visible in the screen or not.
     *
     * @param top top coordinate of the screen.
     * @param left left coordinate of the screen.
     * @param w screen width.
     * @param h screen height.
     * @return true if offscreen, false otherwise.
     */
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

    public float getBound() {
        return bound;
    }

    public void setBound(float bound) {
        this.bound = bound;
    }
}
