package game.skeleton;

import game.math.Vector3D;

public class DynamicGameObject3D extends GameObject3D {
	
	public final Vector3D velocity;
	public final Vector3D acceleration;
	
	public DynamicGameObject3D(float x, float y, float z, float radius) {
		super(x, y, z, radius);
		velocity = new Vector3D();
		acceleration = new Vector3D();
	}
}
