package model;

/**
 * Game component prototype.
 */
public abstract class GameComponent {

    private int posx; // position
    private int posy;
    private int velx; // velocity
    private int vely;

    public abstract boolean isInside(int x, int y);

    /**
     * Move to next position based on velocity.
     */
    public void update() {
        this.posx += this.velx;
        this.posy += this.vely;
    }

    public int getVelx() {
        return velx;
    }

    public void setVelx(int velx) {
        this.velx = velx;
    }

    public int getVely() {
        return vely;
    }

    public void setVely(int vely) {
        this.vely = vely;
    }

    public void setVelocity(int velx, int vely) {
        this.velx = velx;
        this.vely = vely;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public void setPosition(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
    }
}
