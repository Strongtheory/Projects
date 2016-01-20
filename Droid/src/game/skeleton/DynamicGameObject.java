package game.skeleton;

import game.math.Vector2D;

public class DynamicGameObject extends GameObject {
	public final Vector2D velocity;
	public final Vector2D acceleration;
	
	public DynamicGameObject(float x, float y, float width, float height){
		super(x, y, width, height);
		velocity = new Vector2D();
		acceleration = new Vector2D();
	}
}
