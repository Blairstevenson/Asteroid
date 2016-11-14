package view.game_object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

Random rand = new Random();
    @Override
    public void draw(Graphics g) {

        
        for (int i = 0; i < this.points.length; i++) {
            int nextPoint = i + 1;
            if (nextPoint >= this.points.length) {
                nextPoint = 0;
            }
            
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(Math.round(this.getPosx()) + this.points[i][0],
                    Math.round(this.getPosy()) + this.points[i][1],
                    Math.round(this.getPosx()) + this.points[nextPoint][0],
                    Math.round(this.getPosy()) + this.points[nextPoint][1]);
        }
    }
}
