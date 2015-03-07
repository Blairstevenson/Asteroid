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
    public static final int ASTEROID_LEVEL = 3;
    public static final int ASTEROID_VELOCITY = 3;
    public static final int SHIP_SAFE_SPACE = 8;
    public static final float SHIP_ROTATE_SPEED = 0.2f;
    public static final int SHIP_SPEED = 3;
    public static final float BULLET_SPEED = 5f;
    // general
    private final Random random;
    private final Timer timer;
    private final IAsteroidView view;
    private final ArrayList<Integer> keys;
    private final HashMap<Integer, Boolean> keyState;
    // game components
    private SpaceShip ship;
    private Line bullet;
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
        Asteroid a = new Asteroid(level);
        // random position
        do {
            a.setPosition(this.random.nextInt(this.view.getWidth()),
                    this.random.nextInt(this.view.getHeight()));
        } while (safeSpace != null && safeSpace.intersects(a));
        // random velocity
        int[] vel = new int[2];
        do {
            vel[0] = this.random.nextInt(ASTEROID_VELOCITY * 2 + 1)
                    - ASTEROID_VELOCITY;
            vel[1] = this.random.nextInt(ASTEROID_VELOCITY * 2 + 1)
                    - ASTEROID_VELOCITY;
        } while (vel[0] == 0 && vel[1] == 0);
        a.setVelocity(vel[0], vel[1]);
        return a;
    }

    /**
     * Create asteroid with random level.
     *
     * @param safeSpace space in which asteroid is now allowed.
     * @return newly created asteroid.
     */
    private Asteroid createAsteroid(BaseGameObject safeSpace) {
        return this.createAsteroid(this.random.nextInt(ASTEROID_LEVEL) + 1,
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
        this.bullet = new Line(this.view.getWidth(),
                (int) (this.ship.getBound() * 1.5));
        this.asteroids = new ArrayList<>();
        // setup new objects
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
        this.view.showEnemyCount(this.asteroids.size());
        this.view.addGameElement(this.bullet);
        this.view.addGameElement(this.ship);
        this.asteroids.stream().forEach((asteroid) -> {
            this.view.addGameElement(asteroid);
        });
        // resume game
        this.resumeGame();
    }

    public void pauseGame() {
        this.timer.stop();
    }

    public void resumeGame() {
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (this.view.getSynchronizedObject()) {
            //<editor-fold defaultstate="collapsed" desc="win condition">
            if (this.asteroids.isEmpty()) {
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
                this.ship.moveForward(SHIP_SPEED);
            }
            if (this.keyState.get(KeyEvent.VK_S)
                    && !this.keyState.get(KeyEvent.VK_W)) {
                this.ship.moveBackward(SHIP_SPEED);
            }
            if (this.keyState.get(KeyEvent.VK_SPACE)) {
                // fire in the hole!
                this.bullet.setPosition(this.ship.getPosx(),
                        this.ship.getPosy());
                this.bullet.setAngle(this.ship.getFacingAngle());
                this.bullet.setVisible(true);
                ArrayList<Asteroid> fired = new ArrayList<>();
                this.asteroids.stream().forEach((a) -> {
                    if (a.intersects(this.bullet)) {
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
            } else {
                // remove "bullet"
                this.bullet.setVisible(false);
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="update all objects">
            this.ship.update();
            this.asteroids.stream().forEach((a) -> {
                a.update();
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
                            a.setVely(this.random.nextInt(ASTEROID_VELOCITY) + 1);
                            break;
                        case 1: // bottom up
                            a.setPosition(this.view.getWidth() + a.getBound(),
                                    this.random.nextInt(this.view.getWidth()));
                            a.setVely(-this.random.nextInt(ASTEROID_VELOCITY) - 1);
                            break;
                        case 2: // from the left
                            a.setPosition(this.random.nextInt(this.view.getHeight()),
                                    -a.getBound());
                            a.setVelx(this.random.nextInt(ASTEROID_VELOCITY) + 1);
                            break;
                        case 3: // from the right
                            a.setPosition(this.random.nextInt(this.view.getHeight()),
                                    this.view.getHeight() + a.getBound());
                            a.setVelx(-this.random.nextInt(ASTEROID_VELOCITY) - 1);
                            break;
                        default:
                            break;
                    }
                }
            });
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="handling ship if it goes off screen">

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="collision check">
            this.asteroids.stream().forEach((a) -> {
                if (a.intersects(this.ship)) {
                    this.view.showNotification("You lose!");
                    this.pauseGame();
                }
            });
            //</editor-fold>
        }
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
