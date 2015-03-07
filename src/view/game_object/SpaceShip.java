package view.game_object;

import java.awt.Color;
import java.awt.Graphics;

public class SpaceShip extends BaseGameObject {

    private static final int radian = 4;
    private double facingAngle;

    public SpaceShip() {
        // default
        this.facingAngle = -Math.PI / 2;
        this.setBound(10);
        this.setPosition(50, 50);
    }

    @Override
    public void draw(Graphics g) {
        // draw inner circle
        g.setColor(Color.yellow);
        g.drawOval(this.getPosx() - SpaceShip.radian,
                this.getPosy() - SpaceShip.radian,
                SpaceShip.radian * 2,
                SpaceShip.radian * 2);
        // draw pointer line
        int[] pointStart = SpaceShip.rotate(radian, 0,
                this.getPosx(), this.getPosy(), this.facingAngle);
        int[] pointEnd = SpaceShip.rotate(this.getBound(), 0,
                this.getPosx(), this.getPosy(), this.facingAngle);
        g.drawLine(pointStart[0], pointStart[1],
                pointEnd[0], pointEnd[1]);
        // draw outer line
        g.setColor(Color.red);
        int[] outerMid = SpaceShip.rotate((int) (this.getBound() * 1.5), 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
        int[] outerLeft = SpaceShip.rotate((int) (-this.getBound() * 0.67),
                -this.getBound(),
                this.getPosx(), this.getPosy(), this.facingAngle);
        int[] outerRight = SpaceShip.rotate((int) (-this.getBound() * 0.67),
                this.getBound(),
                this.getPosx(), this.getPosy(), this.facingAngle);
        g.drawLine(outerMid[0], outerMid[1],
                outerLeft[0], outerLeft[1]);
        g.drawLine(outerMid[0], outerMid[1],
                outerRight[0], outerRight[1]);
        g.drawLine(outerLeft[0], outerLeft[1],
                outerRight[0], outerRight[1]);
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
