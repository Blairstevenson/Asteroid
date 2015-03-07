package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;
import view.IAsteroidView;

public class AsteroidController implements ActionListener, KeyListener {

    private final Timer timer;
    private final IAsteroidView view;
    private final ArrayList<Integer> keys;
    private final HashMap<Integer, Boolean> keyState;

    public AsteroidController(IAsteroidView view) {
        // init variable
        this.view = view;
        this.keys = new ArrayList<>();
        this.keyState = new HashMap<>();
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
        this.timer = new Timer(50, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // event handling
        // logic handling
        // updating view
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
