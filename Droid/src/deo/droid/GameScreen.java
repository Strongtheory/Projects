package deo.droid;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import deo.droid.World.WorldListener;
import game.framework.GLScreen;
import game.math.OverlapTester;
import game.math.Rectangle;
import game.math.Vector2D;
import game.opengl.Camera2D;
import game.opengl.FPSCounter;
import game.opengl.Sprite;
import game.skeleton.Game;
import game.skeleton.Input.TouchEvent;

public class GameScreen extends GLScreen {

	static final int GAME_RUNNING = 0;
	static final int GAME_PAUSED = 1;
	static final int GAME_OVER = 2;
	
	int state;
	Camera2D guiCamera;
	Vector2D touchPoint;
	Sprite sprite;
	World world;
	WorldListener listener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle leftBounds;
	Rectangle rightBounds;
	Rectangle shotBounds;
	int lastScore;
	int lastLife;
	int lastWave;
	String score;
	FPSCounter fpsCounter;
	
	public GameScreen(Game game) {
		super(game);
		state = GAME_RUNNING;
		guiCamera = new Camera2D(glGraphics, 480, 320);
		touchPoint = new Vector2D();
		sprite = new Sprite(glGraphics, 100);
		world = new World();
		listener = new WorldListener() {
			
			@Override
			public void shot() {
				Assets.playSound(Assets.shotSound);
			}
			
			@Override
			public void explosion() {
				Assets.playSound(Assets.explosionSound);
			}
		};
		world.setWorldListener(listener);
		renderer = new WorldRenderer(glGraphics);
		pauseBounds = new Rectangle(480 -64, 320 - 64, 64, 64);
		resumeBounds = new Rectangle(240 - 80, 160, 160, 32);
		quitBounds = new Rectangle(240 - 80, 160 - 32,  160, 32);
		shotBounds = new Rectangle(480 - 64, 0, 64, 64);
		leftBounds = new Rectangle(0, 0, 64, 64);
		rightBounds = new Rectangle(64, 0, 64, 64);
		lastScore = 0;
		lastLife = world.ship.lives;
		lastWave = world.waves;
		score = "Lives: " + lastLife + " Waves: " + lastWave
				 + " Score: " + lastScore;
		fpsCounter = new FPSCounter();
	}

	@Override
	public void update(float deltaTime) {
		switch (state) {
			case GAME_PAUSED:
				updatePaused();
				break;
			case GAME_RUNNING:
				updateRunning(deltaTime);
				break;
			case GAME_OVER:
				List<TouchEvent> events = game.getInput().getTouchEvents();
				int length = events.size();
				for (int i = 0; i < length; i++) {
					TouchEvent event = events.get(i);
					if(event.type == TouchEvent.TOUCH_UP){
						Assets.playSound(Assets.clickSound);
						game.setScreen(new Main(game));
					}
				}
				break;
		}
	}
	
	private void updatePaused(){
		List<TouchEvent> events = game.getInput().getTouchEvents();
		for (int i = 0; i < events.size(); i++) {
			TouchEvent event = events.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			guiCamera.touchToWorld(touchPoint.set(event.x,  event.y));
			if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
			}
			if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				game.setScreen(new Main(game));
			}
		}
	}
	
	private void updateRunning(float deltaTime){
		List<TouchEvent> events = game.getInput().getTouchEvents();
		for (int i = 0; i < events.size(); i++) {
			TouchEvent event = events.get(i);
			if(event.type != TouchEvent.TOUCH_DOWN)
				continue;
			guiCamera.touchToWorld(touchPoint.set(event.x, event.y));
			if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
			}
			if(OverlapTester.pointInRectangle(shotBounds, touchPoint)){
				world.shoot();
			}
		}
		world.update(deltaTime, inputAcceleration());
		if(world.ship.lives != lastLife || world.score != lastScore 
				|| world.waves != lastWave){
			lastLife = world.ship.lives;
			lastScore = world.score;
			lastWave = world.waves;
			score = "Lives: " + lastLife + " Waves: " + lastWave + 
					" Score: " + lastScore;
		}
		if(world.gameOver()){
			state = GAME_OVER;
		}
	}
	
	private float inputAcceleration(){
		float accelX = 0;
		if(Setting.touchEnabled){
			for (int i = 0; i < 2; i++) {
				if(game.getInput().isTouchDown(i)){
					guiCamera.touchToWorld(touchPoint.set(game.getInput().getTouchX(i),
							game.getInput().getTouchY(i)));
					if(OverlapTester.pointInRectangle(leftBounds, touchPoint)){
						accelX = -Ship.SHIP_VELOCITY/5;
					}
					if(OverlapTester.pointInRectangle(rightBounds, touchPoint)){
						accelX = Ship.SHIP_VELOCITY/5;
					}
				}
			}
		} else {
			accelX = game.getInput().getAccelY();
		}
		return accelX;
	}
	
	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGl();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		guiCamera.setViewPortAndMatrices();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		sprite.beginBatch(Assets.background);
		sprite.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
		sprite.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		renderer.render(world, deltaTime);
		
		switch (state) {
		case GAME_RUNNING:
			presentPaused();
			break;
		case GAME_PAUSED:
			presentRunning();
			break;
		case GAME_OVER:
			presentGameOver();
		}
		fpsCounter.logFrame();
	}

	private void presentPaused(){
		GL10 gl = glGraphics.getGl();
		guiCamera.setViewPortAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		sprite.beginBatch(Assets.items);
		Assets.font.drawText(sprite, score, 10, 320 - 20);
		sprite.drawSprite(240, 160, 160, 64, Assets.pauseRegion);
		sprite.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentRunning(){
		GL10 gl = glGraphics.getGl();
		guiCamera.setViewPortAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		sprite.beginBatch(Assets.items);
		sprite.drawSprite(480 - 32, 320 - 32,  64, 64, Assets.pauseButtonRegion);
		Assets.font.drawText(sprite, score, 10, 320 - 20);
		if(Setting.touchEnabled){
			sprite.drawSprite(32, 32, 64, 64, Assets.leftRegion);
			sprite.drawSprite(96, 32, 64, 64, Assets.rightRegion);
		}
		sprite.drawSprite(480 - 40, 32, 64, 64, Assets.fireRegion);
		sprite.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentGameOver(){
		GL10 gl = glGraphics.getGl();
		guiCamera.setViewPortAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		sprite.beginBatch(Assets.items);
		sprite.drawSprite(240, 160, 128, 64, Assets.gameOverRegion);
		Assets.font.drawText(sprite, score, 10, 320 - 20);
		sprite.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}
	
	@Override
	public void pause() {
		state = GAME_PAUSED;
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}
