package view.game_object;

import java.awt.Graphics;

public class Bullet extends BaseGameObject {

    @Override
    public boolean isInside(int x, int y) {
        return x == this.getPosx() && y == this.getPosy();
    }

    @Override
    public void draw(Graphics g) {
        g.drawLine(this.getPosx(), this.getPosy(),
                this.getPosx(), this.getPosy());
    }
}
