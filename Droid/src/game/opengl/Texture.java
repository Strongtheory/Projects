package game.opengl;

import game.framework.GLGame;
import game.framework.GLGraphics;
import game.skeleton.FileIO;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLUtils;


public class Texture {
	GLGraphics glGraphics;
	FileIO fileIO;
	String fileName;
	int textureId;
	int minFilter;
	int magFilter;
	public int width;
	public int height;
	boolean mapped;
	
	public Texture(GLGame glGame, String fileName, boolean mapped){
		this.glGraphics = glGame.getGlGraphics();
		this.fileIO = glGame.getFileIO();
		this.fileName = fileName;
		this.mapped = mapped;
		load();
	}
	
	public Texture(GLGame glGame, String fileName){
		this(glGame, fileName, false);
	}
	
	private void load(){
		GL10 gl = glGraphics.getGl();
		int[] textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		textureId = textureIds[0];
		InputStream input = null;
		try {
			input = fileIO.readAsset(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            if(mapped){
            	createMap(gl, bitmap);
            } else {
            	gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
                setFilters(GL10.GL_NEAREST, GL10.GL_NEAREST);            
                gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
                width = bitmap.getWidth();
                height = bitmap.getHeight();
                bitmap.recycle();
            }
		} catch (IOException e) {
			throw new RuntimeException("Texture load failed: " + fileName);
		} finally {
			if(input != null)
				try {
					input.close();
				} catch (Exception e2) {
					
				}
		}
	}
	
	private void createMap(GL10 gl, Bitmap bitmap){
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		setFilters(GL10.GL_LINEAR_MIPMAP_NEAREST, GL10.GL_LINEAR);
		int level = 0;
		int nWidth = width;
		int nHeight = height;
		while(true){
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
			nWidth = nWidth/2;
			nHeight = nHeight/2;
			if(nWidth <= 0)
				break;
			Bitmap nBitmap = Bitmap.createBitmap(nWidth, 
					nHeight,bitmap.getConfig());
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(bitmap, 
					new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), 
					new Rect(0, 0, nWidth, nHeight), null);
			bitmap.recycle();
			bitmap = nBitmap;
			level++;
		}
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		bitmap.recycle();
	}
	
	public void reload(){
		load();
		bind();
		setFilters(minFilter, magFilter);
		glGraphics.getGl().glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}
	
	public void bind(){
		GL10 gl = glGraphics.getGl();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	}
	
	
	public void setFilters(int minFilter, int magFilter){
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		GL10 gl = glGraphics.getGl();
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, 
				GL10.GL_TEXTURE_MIN_FILTER, minFilter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, 
				GL10.GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	public void dispose(){
		GL10 gl = glGraphics.getGl();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		int[] textureIds = {textureId};
		gl.glDeleteTextures(1, textureIds, 0);
	}
}
