package view.game_object;

import java.awt.Color;
import java.awt.Graphics;

public class SpaceShip extends BaseGameObject {

    private double facingAngle;

    public SpaceShip() {
        // default
        this.facingAngle = -Math.PI / 2;
        this.setBound(10);
        this.setPosition(50, 50);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawOval(this.getPosx() - this.getBound(),
                this.getPosy() - this.getBound(),
                this.getBound() * 2, this.getBound() * 2);
        // draw pointer
        g.drawLine((int) (this.getPosx() + this.getBound() * Math.cos(this.facingAngle)),
                (int) (this.getPosy() + this.getBound() * Math.sin(this.facingAngle)),
                (int) (this.getPosx() + this.getBound() * 1.5 * Math.cos(this.facingAngle)),
                (int) (this.getPosy() + this.getBound() * 1.5 * Math.sin(this.facingAngle)));
    }

    public double getFacingAngle() {
        return facingAngle;
    }

    public void setFacingAngle(double facingAngle) {
        this.facingAngle = facingAngle;
    }
    
    public void moveForward(int step) {
        int[] newPoint = BaseGameObject.rotate(step, 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
        this.setPosition(newPoint[0], newPoint[1]);
    }
    
    public void moveBackward(int step) {
        int[] newPoint = BaseGameObject.rotate(-step, 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
        this.setPosition(newPoint[0], newPoint[1]);
    }
}
