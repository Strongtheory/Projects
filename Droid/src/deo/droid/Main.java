package deo.droid;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import game.framework.GLScreen;
import game.math.OverlapTester;
import game.math.Rectangle;
import game.math.Vector2D;
import game.opengl.Camera2D;
import game.opengl.Sprite;
import game.skeleton.Game;
import game.skeleton.Input.TouchEvent;

public class Main extends GLScreen {

	Camera2D guiCamera;
	Sprite sprite;
	Vector2D touchPoint;
	Rectangle playBounds;
	Rectangle settingBounds;
	
	public Main(Game game) {
		super(game);
		guiCamera = new Camera2D(glGraphics, 480, 320);
		sprite = new Sprite(glGraphics, 10);
		playBounds = new Rectangle(240 - 112, 100, 224, 32);
		settingBounds = new Rectangle(240 - 112, 100 - 32, 224, 32);
		touchPoint = new Vector2D();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int length = events.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = events.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			guiCamera.touchToWorld(touchPoint.set(event.x, event.y));
			if(OverlapTester.pointInRectangle(settingBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGl();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCamera.setViewPortAndMatrices();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		sprite.beginBatch(Assets.background);
		sprite.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
		sprite.endBatch();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		sprite.beginBatch(Assets.items);
		sprite.drawSprite(240, 240, 384, 128, Assets.logoRegion);
		sprite.drawSprite(240, 100, 224, 64, Assets.menuRegion);
		sprite.endBatch();
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
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
