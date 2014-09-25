package game.opengl;

import game.math.Vector3D;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;


public class Look {
	final Vector3D position;
	final Vector3D up;
	final Vector3D look;
	float fieldOfView;
	float aspectRatio;
	float near;
	float far;
	
	public Look(float fieldOfView, float aspectRatio, float near, float far){
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.near = near;
		this.far = far;
		
		position = new Vector3D();
		up = new Vector3D(0, 1, 0);
		look = new Vector3D(0,0,-1);
	}
	
	public Vector3D getPosition(){
		return position;
	}
	
	public Vector3D up(){
		return up;
	}
	
	public Vector3D look(){
		return look;
	}
	
	public void setMatrices(GL10 gl){
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, fieldOfView, aspectRatio, near, far);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, position.x, position.y, position.z, look.x, 
				look.y, look.z, up.x, up.y, up.z);
	}
}
