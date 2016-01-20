package game.skeleton;

import game.math.Sphere;
import game.math.Vector3D;

public class GameObject3D {
	public final Vector3D position;
	public final Sphere bounds;
	
	public GameObject3D(float x, float y, float z, float radius){
		this.position = new Vector3D(x, y, z);
		this.bounds = new Sphere(x, y, z, radius);
	}
}
