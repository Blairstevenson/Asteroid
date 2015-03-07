package view.game_object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Asteroid extends BaseGameObject {

    public static final int BASE_SIZE = 10;
    private final int fragmentCount;
    private final int[][] points;
    private final int level;

    public Asteroid(int level, Random r) {
        this.level = level;
        this.setBound(level * Asteroid.BASE_SIZE);
        int sizeRange = (int) (this.getBound() / 2);
        this.fragmentCount = this.level * 6;
        // generate random points to draw
        this.setPosition(0, 0);
        this.points = new int[fragmentCount][2];
        ArrayList<Float> angleList = new ArrayList<>();
        for (int i = 0; i < fragmentCount; i++) {
            angleList.add(r.nextFloat() * (float) (Math.PI * 2));
        }
        Collections.sort(angleList);
        for (int i = 0; i < this.points.length; i++) {
            // base point
            float x = r.nextInt(sizeRange) + this.getBound() - sizeRange / 3;
            // rotate
            float[] point = BaseGameObject.rotate(x, 0, this.getPosx(), this.getPosy(),
                    angleList.get(i));
            this.points[i][0] = (int) point[0];
            this.points[i][1] = (int) point[1];
        }
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        for (int i = 0; i < this.points.length; i++) {
            int nextPoint = i + 1;
            if (nextPoint >= this.points.length) {
                nextPoint = 0;
            }
            g.drawLine(Math.round(this.getPosx()) + this.points[i][0],
                    Math.round(this.getPosy()) + this.points[i][1],
                    Math.round(this.getPosx()) + this.points[nextPoint][0],
                    Math.round(this.getPosy()) + this.points[nextPoint][1]);
        }
    }
}
