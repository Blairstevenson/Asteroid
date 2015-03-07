package view.game_object;

import java.awt.Color;
import java.awt.Graphics;

public class SpaceShip extends BaseGameObject {

    private static final int radian = 4;
    private float facingAngle;

    public SpaceShip() {
        // default
        this.facingAngle = (float) (-Math.PI / 2);
        this.setBound(10);
        this.setPosition(50, 50);
    }

    @Override
    public void draw(Graphics g) {
        // draw inner circle
        g.setColor(Color.yellow);
        g.drawOval(Math.round(this.getPosx() - SpaceShip.radian),
                Math.round(this.getPosy() - SpaceShip.radian),
                SpaceShip.radian * 2,
                SpaceShip.radian * 2);
        // draw pointer line
        float[] pointStart = SpaceShip.rotate(radian, 0,
                this.getPosx(), this.getPosy(), this.facingAngle);
        float[] pointEnd = SpaceShip.rotate(this.getBound(), 0,
                this.getPosx(), this.getPosy(), this.facingAngle);
        g.drawLine(Math.round(pointStart[0]), Math.round(pointStart[1]),
                Math.round(pointEnd[0]), Math.round(pointEnd[1]));
        // draw outer line
        g.setColor(Color.red);
        float[] outerMid = SpaceShip.rotate(this.getBound() * 1.5f, 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
        float[] outerLeft = SpaceShip.rotate(-this.getBound() * 0.67f,
                -this.getBound(),
                this.getPosx(), this.getPosy(), this.facingAngle);
        float[] outerRight = SpaceShip.rotate(-this.getBound() * 0.67f,
                this.getBound(),
                this.getPosx(), this.getPosy(), this.facingAngle);
        g.drawLine(Math.round(outerMid[0]), Math.round(outerMid[1]),
                Math.round(outerLeft[0]), Math.round(outerLeft[1]));
        g.drawLine(Math.round(outerMid[0]), Math.round(outerMid[1]),
                Math.round(outerRight[0]), Math.round(outerRight[1]));
        g.drawLine(Math.round(outerLeft[0]), Math.round(outerLeft[1]),
                Math.round(outerRight[0]), Math.round(outerRight[1]));
    }

    public float getFacingAngle() {
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
    }

    public float[] checkMoveForward(float step) {
        return BaseGameObject.rotate(step, 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
    }

    public float[] checkMoveBackward(float step) {
        return BaseGameObject.rotate(-step, 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
    }

    public void moveForward(float step) {
        float[] newPoint = BaseGameObject.rotate(step, 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
        this.setPosition(newPoint[0], newPoint[1]);
    }

    public void moveBackward(float step) {
        float[] newPoint = BaseGameObject.rotate(-step, 0,
                this.getPosx(), this.getPosy(),
                this.facingAngle);
        this.setPosition(newPoint[0], newPoint[1]);
    }
}
