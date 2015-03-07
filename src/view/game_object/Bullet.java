package view.game_object;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends BaseGameObject {

    private float angle;

    public Bullet(float angle) {
        this.angle = angle;
    }

    @Override
    public void update() {
        super.update();
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public boolean contains(float x, float y) {
        return x == this.getPosx() && y == this.getPosy();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawLine(Math.round(this.getPosx()), Math.round(this.getPosy()),
                Math.round(this.getPosx()), Math.round(this.getPosy()));
    }
}
