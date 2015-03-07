package view.game_object;

public class SpaceShip extends BaseGameObject {

    private double facingAngle;

    public SpaceShip() {
        // default
        this.facingAngle = 0.0f;
        this.setBound(10);
        this.setPosition(50, 50);
    }

    public double getFacingAngle() {
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
    }
}
