package game.opengl;

import javax.microedition.khronos.opengles.GL10;

public class AmbientLight {
	float[] color = {0.2f, 0.2f, 0.2f, 1};
	
	public void setColor(float red, float green, float blue, float alpha){
		color[0] = red;
		color[1] = green;
		color[2] = blue;
		color[3] = alpha;
	}
	
	public void enable(GL10 gl){
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, color, 0);
	}
}
