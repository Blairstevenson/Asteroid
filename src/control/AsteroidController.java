package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.Timer;
import view.IAsteroidView;
import view.game_object.*;

public class AsteroidController implements ActionListener, KeyListener {

    // hard coded constants
    public static final int ASTEROID_COUNT = 10;
    public static final int LIFE_COUNT = 3;
    public static final int ASTEROID_LEVEL_MAX = 4;
    public static final int ASTEROID_VELOCITY_MAX = 3;
    public static final int SHIP_SAFE_SPACE = 8;
    public static final float SHIP_ROTATE_SPEED = 0.15f;
    public static final int SHIP_SPEED = 3;
    public static final int SHIP_ATTACK_DELAY = 5;
    public static final float BULLET_SPEED = 5f;
    // general
    private final Random random;
    private final Timer timer;
    private final IAsteroidView view;
    private final ArrayList<Integer> keys;
    private final HashMap<Integer, Boolean> keyState;
    // game components
    private boolean gameEnded;
    private SpaceShip ship;
    private int lifeCount;
    private int attackDelay;
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;

    public AsteroidController(IAsteroidView view) {
        // init variable
        this.view = view;
        this.keys = new ArrayList<>();
        this.keyState = new HashMap<>();
        this.random = new Random();
        // construct keys and key map
        this.keys.add(KeyEvent.VK_W); // move
        this.keys.add(KeyEvent.VK_D);
        this.keys.add(KeyEvent.VK_A); // rotate
        this.keys.add(KeyEvent.VK_S);
        this.keys.add(KeyEvent.VK_SPACE); // fire
        this.keys.stream().forEach((key) -> {
            this.keyState.put(key, false);
        });
        // start the job
        this.timer = new Timer(40, this);
    }

    /**
     * Create asteroid with specific level.
     *
     * @param level level of asteroid.
     * @param safeSpace space in which asteroid is now allowed.
     * @return newly created asteroid.
     */
    private Asteroid createAsteroid(int level, BaseGameObject safeSpace) {
        // create new asteroid
        Asteroid a = new Asteroid(level, this.random);
        this.randomizeAsteroid(a, safeSpace);
        return a;
    }

    private void randomizeAsteroid(Asteroid a, BaseGameObject safeSpace) {
        // random position
        do {
            a.setPosition(this.random.nextInt(this.view.getWidth()),
                    this.random.nextInt(this.view.getHeight()));
        } while (safeSpace != null && safeSpace.intersects(a));
        // random velocity
        int[] vel = new int[2];
        do {
            vel[0] = this.random.nextInt(ASTEROID_VELOCITY_MAX * 2 + 1)
                    - ASTEROID_VELOCITY_MAX;
            vel[1] = this.random.nextInt(ASTEROID_VELOCITY_MAX * 2 + 1)
                    - ASTEROID_VELOCITY_MAX;
        } while (vel[0] == 0 && vel[1] == 0);
        a.setVelocity(vel[0], vel[1]);
    }

    /**
     * Create asteroid with random level.
     *
     * @param safeSpace space in which asteroid is now allowed.
     * @return newly created asteroid.
     */
    private Asteroid createAsteroid(BaseGameObject safeSpace) {
        return this.createAsteroid(this.random.nextInt(ASTEROID_LEVEL_MAX) + 1,
                safeSpace);
    }

    public void startGame() {
        // pause game
        this.pauseGame();
        // clear view
        this.view.showNotification(null);
        this.view.clearGameElement();
        // create new objects
        this.ship = new SpaceShip();
        this.bullets = new ArrayList<>();
        this.asteroids = new ArrayList<>();
        // setup new objects
        this.lifeCount = LIFE_COUNT;
        this.attackDelay = 0;
        this.gameEnded = false;
        this.ship.setPosition(this.view.getWidth() / 2,
                this.view.getHeight() / 2);
        BaseGameObject safeSpace = new BaseGameObject() {
        };
        safeSpace.setPosition(this.ship.getPosx(), this.ship.getPosy());
        safeSpace.setBound(this.ship.getBound() * SHIP_SAFE_SPACE);
        // add asteroids
        for (int i = 0; i < ASTEROID_COUNT; i++) {
            this.asteroids.add(this.createAsteroid(safeSpace)); // random level
        }
        // update view
        this.view.showLifeCount(this.lifeCount);
        this.view.showEnemyCount(this.asteroids.size());
        this.view.addGameElement(this.ship);
        this.asteroids.stream().forEach((asteroid) -> {
            this.view.addGameElement(asteroid);
        });
        this.view.showNotification("Press \"P\" key to begin!");
    }

    private void newLife() {
        // place ship at the center of screen
        this.ship.setPosition(this.view.getWidth() / 2,
                this.view.getHeight() / 2);
        this.ship.setFacingAngle((float) (-Math.PI / 2));
        // place remaining asteroid(s) away from center
        BaseGameObject safeSpace = new BaseGameObject() {
        };
        safeSpace.setPosition(this.ship.getPosx(), this.ship.getPosy());
        safeSpace.setBound(this.ship.getBound() * SHIP_SAFE_SPACE);
        this.asteroids.stream().forEach((asteroid) -> {
            this.randomizeAsteroid(asteroid, safeSpace);
        });
        // remove all bullet(s)
        this.bullets.stream().forEach((b) -> {
            this.view.removeGameElement(b);
        });
        this.bullets.clear();
    }

    public void pauseGame() {
        this.timer.stop();
        if (!gameEnded) {
            this.view.showNotification("Paused!");
        }
    }

    public void resumeGame() {
        if (!gameEnded) {
            this.timer.start();
            this.view.showNotification(null);
        }
    }

    public boolean isPaused() {
        return !this.timer.isRunning();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (this.view.getSynchronizedObject()) {
            //<editor-fold defaultstate="collapsed" desc="win condition">
            if (this.asteroids.isEmpty()) {
                this.gameEnded = true;
                this.view.showNotification("You win!");
                this.pauseGame();
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="event handling">
            if (this.keyState.get(KeyEvent.VK_A)
                    && !this.keyState.get(KeyEvent.VK_D)) {
                this.ship.setFacingAngle(this.ship.getFacingAngle()
                        - SHIP_ROTATE_SPEED);
            }
            if (this.keyState.get(KeyEvent.VK_D)
                    && !this.keyState.get(KeyEvent.VK_A)) {
                this.ship.setFacingAngle(this.ship.getFacingAngle()
                        + SHIP_ROTATE_SPEED);
            }
            if (this.keyState.get(KeyEvent.VK_W)
                    && !this.keyState.get(KeyEvent.VK_S)) {
                float[] pos = this.ship.checkMoveForward(SHIP_SPEED);
                if (!this.offscreen(pos[0], pos[1],
                        this.ship.getBound(), this.ship.getBound(),
                        this.view.getWidth() - this.ship.getBound() * 2,
                        this.view.getHeight() - this.ship.getBound() * 2)) {
                    this.ship.setPosition(pos[0], pos[1]);
                }
            }
            if (this.keyState.get(KeyEvent.VK_S)
                    && !this.keyState.get(KeyEvent.VK_W)) {
                float[] pos = this.ship.checkMoveBackward(SHIP_SPEED);
                if (!this.offscreen(pos[0], pos[1],
                        this.ship.getBound(), this.ship.getBound(),
                        this.view.getWidth() - this.ship.getBound() * 2,
                        this.view.getHeight() - this.ship.getBound() * 2)) {
                    this.ship.setPosition(pos[0], pos[1]);
                }
            }
            if (this.keyState.get(KeyEvent.VK_SPACE)) {
                if (this.attackDelay == 0) {
                    // fire in the hole!
                    Bullet b = new Bullet(this.ship.getFacingAngle(),
                            BULLET_SPEED);
                    b.setPosition(this.ship.getPosx(), this.ship.getPosy());
                    this.bullets.add(b);
                    this.view.addGameElement(b);
                }
                attackDelay++;
            }
            if (this.attackDelay != 0) {
                this.attackDelay++;
                this.attackDelay %= SHIP_ATTACK_DELAY;
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="update all objects">
            this.ship.update();
            this.bullets.stream().forEach((b) -> {
                b.update();
            });
            this.asteroids.stream().forEach((a) -> {
                a.update();
            });
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="collision check">
            // bullets vs asteroids
            ArrayList<Asteroid> fired = new ArrayList<>();
            this.asteroids.stream().forEach((a) -> {
                boolean boom = false;
                int bullet;
                for (bullet = 0; bullet < this.bullets.size(); bullet++) {
                    Bullet b = this.bullets.get(bullet);
                    if (a.contains(b.getPosx(), b.getPosy())) {
                        boom = true;
                        break;
                    }
                }
                if (boom) {
                    // remove bullet
                    this.view.removeGameElement(this.bullets.get(bullet));
                    this.bullets.remove(bullet);
                    // mark asteroid
                    fired.add(a);
                }
            });
            fired.stream().forEach((a) -> {
                int currentLevel = a.getLevel();
                if (currentLevel > 1) {
                    for (int i = 0; i < currentLevel; i++) {
                        Asteroid smaller = this.createAsteroid(
                                currentLevel - 1, null);
                        smaller.setPosition(a.getPosx(), a.getPosy());
                        this.asteroids.add(smaller);
                        this.view.addGameElement(smaller);
                    }
                }
                this.asteroids.remove(a);
                this.view.removeGameElement(a);
                this.view.showEnemyCount(this.asteroids.size());
            });
            // ship vs asteroids
            this.asteroids.stream().forEach((a) -> {
                if (a.intersects(this.ship)) {
                    this.lifeCount--;
                    this.view.showLifeCount(this.lifeCount);
                    if (this.lifeCount <= 0) {
                        this.gameEnded = true;
                        this.pauseGame();
                        this.view.showNotification("You lose!");
                    } else {
                        this.pauseGame();
                        this.newLife();
                        this.view.showNotification("Get ready! Press \"P\" to continue!");
                    }
                }
            });
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="handling any bullet that goes offscreen">
            ArrayList<Bullet> expired = new ArrayList<>();
            this.bullets.stream().forEach((b) -> {
                if (b.offscreen(0, 0, this.view.getWidth(), this.view.getHeight())) {
                    expired.add(b);
                }
            });
            expired.stream().forEach((b) -> {
                this.bullets.remove(b);
                this.view.removeGameElement(b);
            });
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="handling any asteroid that goes offscreen">
            this.asteroids.forEach((a) -> {
                if (a.offscreen(0, 0, this.view.getWidth(), this.view.getHeight())) {
                    // place it elsewhere randomly (offscreen)
                    // and make it run toward screen
                    int newDir = this.random.nextInt(4);
                    switch (newDir) {
                        case 0: // top down
                            a.setPosition(-a.getBound(),
                                    this.random.nextInt(this.view.getWidth()));
                            a.setVely(this.random.nextInt(ASTEROID_VELOCITY_MAX) + 1);
                            break;
                        case 1: // bottom up
                            a.setPosition(this.view.getWidth() + a.getBound(),
                                    this.random.nextInt(this.view.getWidth()));
                            a.setVely(-this.random.nextInt(ASTEROID_VELOCITY_MAX) - 1);
                            break;
                        case 2: // from the left
                            a.setPosition(this.random.nextInt(this.view.getHeight()),
                                    -a.getBound());
                            a.setVelx(this.random.nextInt(ASTEROID_VELOCITY_MAX) + 1);
                            break;
                        case 3: // from the right
                            a.setPosition(this.random.nextInt(this.view.getHeight()),
                                    this.view.getHeight() + a.getBound());
                            a.setVelx(-this.random.nextInt(ASTEROID_VELOCITY_MAX) - 1);
                            break;
                        default:
                            break;
                    }
                }
            });
            //</editor-fold>
        }
    }

    private boolean offscreen(float posx, float posy,
            float top, float left, float w, float h) {
        return posx < left
                || posx > left + w
                || posy < top
                || posy > top + h;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Integer key = e.getKeyCode();
        if (this.keys.contains(key)) {
            this.keyState.put(key, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Integer key = e.getKeyCode();
        if (this.keys.contains(key)) {
            this.keyState.put(key, false);
        }
    }
}
