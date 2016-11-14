package model;

/**
 * Game component prototype.
 */
public abstract class GameComponent {

    private float posx; // position
    private float posy;
    private float velx; // velocity
    private float vely;

    /**
     * Check if a point is inside this component.
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if the point is inside, false otherwise.
     */
    public abstract boolean contains(float x, float y);

    /**
     * Move to next position based on velocity.
     */
    public void update() {
        this.posx += this.velx;
        this.posy += this.vely;
    }

    public float getVelx() {
        return velx;
    }

    public void setVelx(float velx) {
        this.velx = velx;
    }

    public float getVely() {
        return vely;
    }

    public void setVely(float vely) {
        this.vely = vely;
    }

    public void setVelocity(float velx, float vely) {
        this.velx = velx;
        this.vely = vely;
    }

    public float getPosx() {
        return posx;
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public float getPosy() {
        return posy;
    }

    public void setPosy(float posy) {
        this.posy = posy;
    }

    public void setPosition(float posx, float posy) {
        this.posx = posx;
        this.posy = posy;
    }
}
