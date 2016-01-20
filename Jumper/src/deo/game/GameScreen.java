package deo.game;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import deo.framework.GLScreen;
import deo.game.World.WorldListener;
import deo.gl.Camera2D;
import deo.gl.Sprite;
import deo.math.OverlapTester;
import deo.math.Rectangle;
import deo.math.Vector2;
import deo.skeleton.Game;
import deo.skeleton.Input.TouchEvent;

public class GameScreen extends GLScreen {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSE = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	
	int state;
	Camera2D guiCamera;
	Vector2 touchPoint;
	Sprite sprite;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	int lastScore;
	String scoreString;
	
	
	
	public GameScreen(Game game) {
		super(game);
		state = GAME_READY;
		guiCamera = new Camera2D(glGraphics, 320, 480);
		touchPoint = new Vector2();
		sprite = new Sprite(glGraphics, 1000);
		worldListener = new WorldListener() {
			
			@Override
			public void jump() {
				Assets.playSound(Assets.jumpSound);
			}
			
			@Override
			public void hit() {
				Assets.playSound(Assets.hitSound);
			}
			
			@Override
			public void highJump() {
				Assets.playSound(Assets.highJumpSound);
			}
			
			@Override
			public void coin() {
				Assets.playSound(Assets.coinSound);
			}
		};
		world = new World(worldListener);
		renderer = new WorldRenderer(glGraphics, sprite, world);
		pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
		lastScore = 0;
		scoreString = "score: 0";
	}



	@Override
	public void update(float deltaTime) {
		if(deltaTime > 0.1f)
			deltaTime = 0.1f;
		switch(state){
			case GAME_READY:
				if(game.getInput()
						.getTouchEvents().size() > 0){
					state = GAME_RUNNING;
				}
				break;
			case GAME_RUNNING:
				updateRunning(deltaTime);
				break;
			case GAME_PAUSE:
				updatePause();
				break;
			case GAME_LEVEL_END:
				updateLevelEnd();
				break;
			case GAME_OVER:
				updateGameOver();
				break;
		}
	}
	
	private void updateRunning(float deltaTime){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			touchPoint.set(event.x, event.y);
			guiCamera.touchToWorld(touchPoint);
			if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}
		}
		world.update(deltaTime, game.getInput().getAccelX());
		if(world.score != lastScore){
			lastScore = world.score;
			scoreString = "" + lastScore;
		}
		if(world.state == World.WORLD_STATE_NEXT_LEVEL){
			state = GAME_LEVEL_END;
		}
		if(world.state == World.WORLD_STATE_GAME_OVER){
			state = GAME_OVER;
			if(lastScore >= Settings.highscores[4])
				scoreString = "New HighScore: " + lastScore;
			else
				scoreString = "Score: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save(game.getFileIO());
		}
	}

	private void updatePause(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			touchPoint.set(event.x, event.y);
			guiCamera.touchToWorld(touchPoint);
			if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}
			if(OverlapTester.pointInRectangle(quitBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenu(game));
				return;
			}
		}
	}
	
	private void updateLevelEnd(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			world = new World(worldListener);
			renderer = new WorldRenderer(glGraphics, sprite, world);
			world.score = lastScore;
			state = GAME_READY;
		}
	}
	
	private void updateGameOver(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			game.setScreen(new MainMenu(game));
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGl();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		renderer.render();
		guiCamera.setViewPortAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sprite.beginBatch(Assets.items);
		switch (state) {
			case GAME_READY:
				sprite.drawSprite(160, 240, 192, 32, Assets.ready);
				break;
			case GAME_RUNNING:
				sprite.drawSprite(320-32, 480-32, 64, 64, Assets.pause);
				Assets.font.drawText(sprite, scoreString, 16, 480-20);
				break;
			case GAME_PAUSE:
				sprite.drawSprite(160, 240, 192, 96, Assets.pauseMenu);
				Assets.font.drawText(sprite, scoreString, 16, 480-20);
				break;
			case GAME_LEVEL_END:
				String top = "Your Wife is...";
				String bottom = "in another castle! Cliche!";
				float topWidth = Assets.font.gWidth * top.length();
				float bottomWidth = Assets.font.gWidth * bottom.length();
				Assets.font.drawText(sprite, top, 160 - topWidth/2, 480-20);
				Assets.font.drawText(sprite, bottom, 160 - bottomWidth/2, 40);
				break;
			case GAME_OVER:
				sprite.drawSprite(160, 240, 160, 96, Assets.gameOver);
				float scoreWidth = Assets.font.gWidth * scoreString.length();
				Assets.font.drawText(sprite, scoreString, 160 - scoreWidth/2, 480-20);
				break;
		}
		sprite.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}



	@Override
	public void pause() {
		if(state == GAME_READY)
			state = GAME_PAUSE;
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	
}
