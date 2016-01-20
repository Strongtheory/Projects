package game.opengl;

import game.math.Vector3D;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.Matrix;
import android.opengl.GLU;


public class EulerCamera {
	final Vector3D position = new Vector3D();
	float yaw;
	float pitch;
	float fieldOfView;
	float aspectRatio;
	float near;
	float far;
	
	public EulerCamera(float fieldOfView, float aspectRatio, float near, float far){
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.near = near;
		this.far = far;
	}
	
	public Vector3D getPosition(){
		return position;
	}
	
	public float getYaw(){
		return yaw;
	}
	
	public float getPitch(){
		return pitch;
	}
	
	public void setAngles(float yaw, float pitch){
		if(pitch < -90)
			pitch = -90;
		if(pitch > 90)
			pitch = 90;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public void rotate(float yawInc, float pitchInc){
		this.yaw += yawInc;
		this.pitch += pitchInc;
		if(pitch < -90)
			pitch = 90;
		if(pitch > 90)
			pitch = 90;
	}
	
	public void setMatrices(GL10 gl){
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, fieldOfView, aspectRatio, near, far);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-pitch, 1, 0, 0);
		gl.glRotatef(-yaw, 0, 1, 0);
		gl.glTranslatef(-position.x, -position.y, -position.z);
	}
	
	final float[] matrix = new float[8];
	final float[] inVec = {0,0,1,1};
	final float[] outVec = new float[4];
	final Vector3D direction = new Vector3D();
	
	public Vector3D getDirection(){
		Matrix.setIdentityM(matrix, 0);
		Matrix.rotateM(matrix, 0, yaw, 0, 1, 0);
		Matrix.rotateM(matrix, 0, pitch, 1, 0, 0);
		Matrix.multiplyMM(outVec, 0, matrix, 0, inVec, 0);
		direction.set(outVec[0], outVec[1], outVec[2]);
		return direction;
	}
}
