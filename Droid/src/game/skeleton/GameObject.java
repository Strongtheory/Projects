package game.skeleton;

import game.math.Rectangle;
import game.math.Vector2D;

public class GameObject {
	public final Vector2D position;
	public final Rectangle bounds;
	
	public GameObject(float x, float y, float width, float height){
		this.position = new Vector2D(x, y);
		this.bounds = new Rectangle(x - width/2,  y - height/2,  width, height);
	}
}
