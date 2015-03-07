package view.game_object;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Asteroid extends BaseGameObject {

    private final int fragmentCount;
    private final int[][] points;

    public Asteroid(int size, int sizeRange, int fragmentCount, Random r) {
        this.fragmentCount = fragmentCount;
        // generate random points to draw
        this.setPosition(0, 0);
        this.points = new int[fragmentCount][];
        ArrayList<Double> angleList = new ArrayList<>();
        for (int i = 0; i < fragmentCount; i++) {
            angleList.add(r.nextDouble() * Math.PI * 2);
        }
        Collections.sort(angleList);
        for (int i = 0; i < this.points.length; i++) {
            // base point
            int x = r.nextInt(sizeRange) + size - sizeRange / 2;
            // rotate
            this.points[i] = this.rotate(x, 0, angleList.get(i));
        }
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < this.points.length; i++) {
            int nextPoint = i + 1;
            if (nextPoint >= this.points.length) {
                nextPoint = 0;
            }
            g.drawLine(this.getPosx() + this.points[i][0],
                    this.getPosy() + this.points[i][1],
                    this.getPosx() + this.points[nextPoint][0],
                    this.getPosy() + this.points[nextPoint][1]);
        }
    }

    public int getFragmentCount() {
        return fragmentCount;
    }
}
