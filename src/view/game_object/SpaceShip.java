package view.game_object;

import java.awt.Color;
import java.awt.Graphics;

public class SpaceShip extends BaseGameObject {

    private double facingAngle;
    private static final int radian = 4;
    //private static final int bound = 10;

    public SpaceShip() {
        // default
        this.facingAngle = 0.0f;
        this.setBound(10);
        this.setPosition(50, 50);
    }

    @Override
    public void update() {
        super.update();
        this.facingAngle += 0.01;
    }

    @Override
    public void draw(Graphics g) {
        // marker
//        g.setColor(Color.gray);
//        g.drawLine(this.getPosx(), this.getPosy(),
//                this.getPosx(), this.getPosy());
//        g.drawOval(this.getPosx() - this.getBound(),
//                this.getPosy() - this.getBound(),
//                this.getBound() * 2,
//                this.getBound() * 2);
        // actual draw
        g.setColor(Color.white);
        // draw inner circle
        g.drawOval(this.getPosx() - SpaceShip.radian,
                this.getPosy() - SpaceShip.radian,
                SpaceShip.radian * 2,
                SpaceShip.radian * 2);
        // draw outer line
        int[] outerMid = this.rotate((int) (this.getBound() * 1.5), 0,
                this.facingAngle);
        int[] outerLeft = this.rotate((int) (-this.getBound() * 0.67),
                -this.getBound(), this.facingAngle);
        int[] outerRight = this.rotate((int) (-this.getBound() * 0.67),
                this.getBound(), this.facingAngle);
        g.drawLine(outerMid[0], outerMid[1],
                outerLeft[0], outerLeft[1]);
        g.drawLine(outerMid[0], outerMid[1],
                outerRight[0], outerRight[1]);
        g.drawLine(outerLeft[0], outerLeft[1],
                outerRight[0], outerRight[1]);
        // draw pointer line
        int[] pointStart = this.rotate(radian, 0, this.facingAngle);
        int[] pointEnd = this.rotate(this.getBound(), 0, this.facingAngle);
        g.drawLine(pointStart[0], pointStart[1],
                pointEnd[0], pointEnd[1]);
    }

    public double getFacingAngle() {
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
    }
}
