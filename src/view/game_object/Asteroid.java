package view.game_object;

public class Asteroid extends BaseGameObject {

    public static final int BASE_SIZE = 10;
    private final int level;

    public Asteroid(int level) {
        this.level = level;
        this.setBound(level * Asteroid.BASE_SIZE);
    }

    public int getLevel() {
        return level;
    }
}
