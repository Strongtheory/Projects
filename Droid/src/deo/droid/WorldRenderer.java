package deo.droid;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import game.framework.GLGraphics;
import game.math.Vector3D;
import game.opengl.AmbientLight;
import game.opengl.Animation;
import game.opengl.DirectionalLight;
import game.opengl.Look;
import game.opengl.Sprite;
import game.opengl.TextureRegions;

public class WorldRenderer {
	GLGraphics glGraphics;
	Look camera;
	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	Sprite sprite;
	float asswipeAngle = 0;
	
	public WorldRenderer(GLGraphics glGraphics){
		this.glGraphics = glGraphics;
		camera = new Look(67, glGraphics.getWidth() / 
				(float)glGraphics.getHeight(), 0.1f, 100);
		camera.getPosition().set(0, 6, 2);
		camera.look().set(0, 0, -4);
		ambientLight = new AmbientLight();
		ambientLight.setColor(0.2f, 0.2f, 0.2f, 0.1f);
		directionalLight = new DirectionalLight();
		directionalLight.setDirection(-1, -05f, 0);
		sprite = new Sprite(glGraphics, 10);
	}
	
	public void render(World world, float deltaTime){
		GL10 gl = glGraphics.getGl();
		camera.getPosition().x = world.ship.position.x;
		camera.look().x = world.ship.position.x;
		camera.setMatrices(gl);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		ambientLight.enable(gl);
		directionalLight.enable(gl, GL10.GL_LIGHT0);
		renderShip(gl, world.ship);
		renderAsswipes(gl, world.asswipes, deltaTime);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		renderShields(gl, world.shields);
		renderShots(gl, world.shots);
	}
	
	private void renderShip(GL10 gl, Ship ship){
		if(ship.state == Ship.SHIP_EXPLODING){
			gl.glDisable(GL10.GL_LIGHTING);
			renderExplosion(gl, ship.position, ship.stateTime);
			gl.glEnable(GL10.GL_LIGHTING);
		} else {
			Assets.shipTexture.bind();
			Assets.shipModel.bind();
			gl.glPushMatrix();
			gl.glTranslatef(ship.position.x, ship.position.y, ship.position.z);
			gl.glRotatef(ship.velocity.x/Ship.SHIP_VELOCITY * 90, 0, 0, -1);
			Assets.shipModel.draw(GL10.GL_TRIANGLES, 
					0, Assets.shieldModel.getNumVertices());
			gl.glPopMatrix();
			Assets.shipModel.unbind();
		}
	}
	
	private void renderAsswipes(GL10 gl, List<Asswipe> asswipes, float deltaTime){
		asswipeAngle += 45 * deltaTime;
		Assets.asswipeTexture.bind();
		Assets.asswipeModel.bind();
		int length = asswipes.size();
		for (int i = 0; i < length; i++) {
			Asswipe asswipe = asswipes.get(i);
			if(asswipe.state == Asswipe.ASSWIPE_DEAD){
				gl.glDisable(GL10.GL_LIGHTING);
				Assets.asswipeModel.unbind();
				renderExplosion(gl, asswipe.position, asswipe.stateTime);
				Assets.asswipeTexture.bind();
				Assets.asswipeModel.bind();
				gl.glEnable(GL10.GL_LIGHTING);
			} else {
				gl.glPushMatrix();
				gl.glTranslatef(asswipe.position.x, asswipe.position.y, 
						asswipe.position.z);
				gl.glRotatef(asswipeAngle, 0, 1, 0);
				Assets.asswipeModel.draw(GL10.GL_TRIANGLES, 0, 
						Assets.asswipeModel.getNumVertices());
				gl.glPopMatrix();
			}
		}
		Assets.asswipeModel.unbind();
	}
	
	private void renderShields(GL10 gl, List<Shield> shields){
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glColor4f(0, 0, 1, 0.4f);
		Assets.shieldModel.bind();
		int length = shields.size();
		for (int i = 0; i < length; i++) {
			Shield shield = shields.get(i);
			gl.glPushMatrix();
			gl.glTranslatef(shield.position.x, shield.position.y, 
					shield.position.z);
			Assets.shieldModel.draw(GL10.GL_TRIANGLES, 
					0, Assets.shieldModel.getNumVertices());
			gl.glPopMatrix();
		}
		Assets.shieldModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderShots(GL10 gl, List<Shot> shots){
		gl.glColor4f(1, 1, 0, 1);
		Assets.shotModel.bind();
		int length = shots.size();
		for (int i = 0; i < length; i++) {
			Shot shot = shots.get(i);
			gl.glPushMatrix();
			gl.glTranslatef(shot.position.x, 
					shot.position.y, shot.position.z);
			Assets.shotModel.draw(GL10.GL_TRIANGLES, 
					0, Assets.shotModel.getNumVertices());
			gl.glPopMatrix();
			gl.glColor4f(1, 1, 1, 1);
		}
	}
	
	private void renderExplosion(GL10 gl, Vector3D position, float stateTime){
		TextureRegions region = Assets.explosionAnimation.getKeyFrame(stateTime, 
				Animation.NONLOOPING);
		gl.glEnable(GL10.GL_BLEND);
		gl.glPushMatrix();
		gl.glTranslatef(position.x, position.y, position.z);
		sprite.beginBatch(Assets.explosionTexture);
		sprite.drawSprite(0, 0, 2, 2, region);
		sprite.endBatch();
		gl.glPopMatrix();
		gl.glDisable(GL10.GL_BLEND);
	}
}
