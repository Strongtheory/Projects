package game.opengl;

import javax.microedition.khronos.opengles.GL10;

public class DirectionalLight {
	float[] ambient = { 0.2f, 0.2f, 0.2f, 1.0f };
    float[] diffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
    float[] specular = { 0.0f, 0.0f, 0.0f, 1.0f };
    float[] direction = { 0, 0, -1, 0 };
    int lastLightId = 0;
    
    public void setAmbient(float red, float green, float blue, float alpha) {
        ambient[0] = red;
        ambient[1] = green;
        ambient[2] = blue;
        ambient[3] = alpha;
    }

    public void setDiffuse(float red, float green, float blue, float alpha) {
        diffuse[0] = red;
        diffuse[1] = green;
        diffuse[2] = blue;
        diffuse[3] = alpha;
    }

    public void setSpecular(float red, float green, float blue, float alpha) {
        specular[0] = red;
        specular[1] = green;
        specular[2] = blue;
        specular[3] = alpha;
    }

    public void setDirection(float x, float y, float z) {
        direction[0] = -x;
        direction[1] = -y;
        direction[2] = -z;
    }

    public void enable(GL10 gl, int lightId) {
        gl.glEnable(lightId);
        gl.glLightfv(lightId, GL10.GL_AMBIENT, ambient, 0);
        gl.glLightfv(lightId, GL10.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(lightId, GL10.GL_SPECULAR, specular, 0);
        gl.glLightfv(lightId, GL10.GL_POSITION, direction, 0);
        lastLightId = lightId;
    }

    public void disable(GL10 gl) {
        gl.glDisable(lastLightId);
    }
}
