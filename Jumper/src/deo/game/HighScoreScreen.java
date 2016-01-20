package deo.game;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import deo.framework.GLScreen;
import deo.gl.Camera2D;
import deo.gl.Sprite;
import deo.math.OverlapTester;
import deo.math.Rectangle;
import deo.math.Vector2;
import deo.skeleton.Game;
import deo.skeleton.Input.TouchEvent;

public class HighScoreScreen extends GLScreen {

	Camera2D guiCamera;
	Sprite sprite;
	Rectangle backBounds;
	Vector2 touchPoint;
	String[] highScores;
	float xOffset = 0;
	
	public HighScoreScreen(Game game) {
		super(game);
		guiCamera = new Camera2D(glGraphics, 320, 480);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector2();
		sprite = new Sprite(glGraphics, 100);
		highScores = new String[5];
		for (int i = 0; i < 5; i++) {
			highScores[i] = (i + 1) + ". " + Settings.highscores[i];
			xOffset = Math.max(highScores[i].length() * Assets.font.gWidth, xOffset);
		}
		xOffset = 160 - xOffset/2;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCamera.touchToWorld(touchPoint);
			if(event.type == TouchEvent.TOUCH_UP){
				if(OverlapTester.pointInRectangle(backBounds, touchPoint)){
					game.setScreen(new MainMenu(game));
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGl();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCamera.setViewPortAndMatrices();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		sprite.beginBatch(Assets.backGround);
		sprite.drawSprite(160, 240, 320, 480, Assets.backGroundRegion);
		sprite.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sprite.beginBatch(Assets.items);
		sprite.drawSprite(160, 360, 300, 33, Assets.highScoresRegion);
		
		float y = 240;
		for(int i = 4; i >= 0; i--){
			Assets.font.drawText(sprite, highScores[i], xOffset, y);
			y += Assets.font.gHeight;
		}
		sprite.drawSprite(32, 32, 64, 64, Assets.arrow);
		sprite.endBatch();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}
