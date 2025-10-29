package tcc.game.engine;

/**
 * FIX: Cockpit is a fixed UI overlay at position (0, 0).
 * It doesn't move with the background - it's the "window frame" of the helicopter.
 */
public class Cockpit extends GameObject {

	public Cockpit() {
		super();
		getSpriteSheet().addImage("assets/images/painel_sprite.png");
		// FIX: Cockpit is fixed at screen origin (0, 0)
		// It's a UI overlay that uses default GameObject drawing
		getPosition().setX(0);
		getPosition().setY(0);
	}

	@Override
	public void update() {
		// Cockpit position never changes - it's fixed UI
		// Only update sprite animations
		super.update();
	}
}
