package view.game_object;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends BaseGameObject {

    private int offset;
    private int length;
    private double angle;

    public Line(int length, int offset) {
        this.offset = offset;
        this.length = length;
        this.angle = -Math.PI / 2;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        int[] startPoint = BaseGameObject.rotate(this.offset, 0,
                this.getPosx(), this.getPosy(), this.angle);
        int[] endPoint = BaseGameObject.rotate(this.length, 0,
                this.getPosx(), this.getPosy(), this.angle);
        g.drawLine(startPoint[0], startPoint[1], endPoint[0], endPoint[1]);
    }

    public int[] getEndPoint() {
        return BaseGameObject.rotate(this.length, 0,
                this.getPosx(), this.getPosy(), this.angle);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
