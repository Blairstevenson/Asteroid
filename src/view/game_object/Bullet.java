package view.game_object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Bullet extends BaseGameObject {

    private float angle;

    public Bullet(float angle, float speed) {
        this.angle = angle;
        this.setBound(1);
        // calculate velocity
        float[] velocity = BaseGameObject.rotate(speed, 0, 0, 0, angle);
        this.setVelocity(velocity[0], velocity[1]);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public boolean offscreen(int top, int left, int w, int h) {
        return this.getPosx() < left
                || this.getPosx() > left + w
                || this.getPosy() < top
                || this.getPosy() > top + h;
    }

    @Override
    public boolean contains(float x, float y) {
        return x == this.getPosx() && y == this.getPosy();
    }
Random rand = new Random();
    @Override
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
            g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        g.drawLine(Math.round(this.getPosx()), Math.round(this.getPosy()),
                Math.round(this.getPosx()), Math.round(this.getPosy()));
    }
}
