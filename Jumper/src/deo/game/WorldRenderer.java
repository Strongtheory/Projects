package deo.game;

import javax.microedition.khronos.opengles.GL10;

import deo.framework.GLGraphics;
import deo.gl.Animation;
import deo.gl.Camera2D;
import deo.gl.Sprite;
import deo.gl.TextureRegions;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT =  15;
	GLGraphics glGraphics;
	World world;
	Camera2D camera;
	Sprite sprite;
	
	public WorldRenderer(GLGraphics glGraphics, Sprite sprite, World world){
		this.glGraphics = glGraphics;
		this.world = world;
		this.camera = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.sprite = sprite;
	}
	
	public void render(){
		if(world.bob.position.y > camera.position.y)
			camera.position.y = world.bob.position.y;
		camera.setViewPortAndMatrices();
		renderBackground();
		renderObjects();
	}
	
	public void renderBackground(){
		sprite.beginBatch(Assets.backGround);
		sprite.drawSprite(camera.position.x, camera.position.y, 
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT, 
				Assets.backGroundRegion);
		sprite.endBatch();
	}
	
	public void renderObjects(){
		GL10 gl = glGraphics.getGl();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sprite.beginBatch(Assets.items);
		renderBob();
		renderPlatforms();
		renderItems();
		renderAssholes();
		renderCastle();
		sprite.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderBob(){
		TextureRegions keyFrame;
		switch (world.bob.state) {
		case Bob.BOB_STATE_FALL:
			keyFrame = Assets.fall.getKeyFrame(world.bob.stateTime, 
					Animation.LOOPING);;
			break;
		case Bob.BOB_STATE_JUMP:
			keyFrame = Assets.jump.getKeyFrame(world.bob.stateTime, 
					Animation.NONLOOPING);
			break;
		case Bob.BOB_STATE_HIT:
		default:
			keyFrame = Assets.hit;
		}
		float side = world.bob.velocity.x < 0 ? -1 : 1;
		sprite.drawSprite(world.bob.position.x, world.bob.position.y, 
				side * 1, 1, keyFrame);
	}
	
	private void renderPlatforms(){
		int length = world.platForms.size();
		for (int i = 0; i < length; i++) {
			Platform platform = world.platForms.get(i);
			TextureRegions keyFrame = Assets.platform;
			if(platform.state == Platform.PLATFORM_STATE_BREAKING){
				keyFrame = Assets.breaking.getKeyFrame(platform.stateTime, 
						Animation.NONLOOPING);
			}
			sprite.drawSprite(platform.position.x, platform.position.y, 
					2, 0.5f, keyFrame);
		}
	}
	
	private void renderItems(){
		int length = world.springs.size();
		for (int i = 0; i < length; i++) {
			Spring spring = world.springs.get(i);
			sprite.drawSprite(spring.position.x, spring.position.y, 
					1, 1, Assets.spring);
		}
		length = world.coins.size();
		for (int i = 0; i < length; i++) {
			Coin coin = world.coins.get(i);
			TextureRegions keyFrame = Assets.coin.getKeyFrame(coin.stateTime, 
					Animation.LOOPING);
			sprite.drawSprite(coin.position.x, coin.position.y, 
					1, 1, keyFrame);
		}
	}
	
	private void renderAssholes(){
		int length = world.enemies.size();
		for (int i = 0; i < length; i++) {
			Enemy enemy = world.enemies.get(i);
			TextureRegions keyFrame = Assets.fly.getKeyFrame(enemy.stateTime, 
				Animation.LOOPING);
			float side = enemy.velocity.x < 0 ? -1 : 1;
			sprite.drawSprite(enemy.position.x, enemy.position.y,
					side * 1, 1, keyFrame);
		}
	}
	
	private void renderCastle(){
		Castle castle = world.castle;
		sprite.drawSprite(castle.position.x, castle.position.y, 
				2, 2, Assets.castle);
	}
}
