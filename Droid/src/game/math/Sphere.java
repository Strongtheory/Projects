package game.math;

public class Sphere {
	public final Vector3D center = new Vector3D();
	public float radius;
	
	public Sphere(float x, float y, float z, float radius){
		this.center.set(x,y,z);
		this.radius = radius;
	}
}
