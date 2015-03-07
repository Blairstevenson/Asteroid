package view.game_object;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends BaseGameObject {

    private int offset;
    private int length;
    private float angle;

    public Line(int length, int offset) {
        this.offset = offset;
        this.length = length;
        this.angle = (float) (-Math.PI / 2);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        float[] startPoint = BaseGameObject.rotate(this.offset, 0,
                this.getPosx(), this.getPosy(), this.angle);
        float[] endPoint = BaseGameObject.rotate(this.length, 0,
                this.getPosx(), this.getPosy(), this.angle);
        g.drawLine(Math.round(startPoint[0]), Math.round(startPoint[1]),
                Math.round(endPoint[0]), Math.round(endPoint[1]));
    }

    public float[] getEndPoint() {
        return BaseGameObject.rotate(this.length, 0,
                this.getPosx(), this.getPosy(), this.angle);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
