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

public class SettingScreen extends GLScreen {

	Camera2D guiCamera;
	Sprite sprite;
	Vector2D touchPoint;
	Rectangle touchBounds;
	Rectangle accelerationBounds;
	Rectangle soundBounds;
	Rectangle backBounds;
	
	public SettingScreen(Game game) {
		super(game);
		guiCamera = new Camera2D(glGraphics, 480, 320);
		sprite = new Sprite(glGraphics, 10);
		touchPoint = new Vector2D();
		touchBounds = new Rectangle(120 - 32, 160 - 32, 64, 64);
		accelerationBounds = new Rectangle(240 - 32, 160 - 32, 64, 64);
		soundBounds = new Rectangle(360 - 32, 160 - 32, 64, 64);
		backBounds = new Rectangle(32, 32, 64, 64);
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
			if(OverlapTester.pointInRectangle(touchBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				Setting.touchEnabled = true;
				Setting.save(game.getFileIO());
			}
			if(OverlapTester.pointInRectangle(accelerationBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				Setting.soundEnabled = !Setting.soundEnabled;
				if(Setting.soundEnabled){
					Assets.music.play();
				} else {
					Assets.music.pause();
				}
				Setting.save(game.getFileIO());
			}
			if(OverlapTester.pointInRectangle(backBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				game.setScreen(new Main(game));
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
		sprite.drawSprite(240, 280, 224, 32, Assets.settingsRegion);
		sprite.drawSprite(120, 160, 64, 64, 
				Setting.touchEnabled ? Assets.touchEnabledRegion : Assets.touchRegion);
		sprite.drawSprite(240, 160, 64, 64, 
				Setting.touchEnabled ? Assets.accelRegion : Assets.accelEnabledRegion);
		sprite.drawSprite(360, 160, 64, 64, 
				Setting.soundEnabled ? Assets.soundEnabledRegion : Assets.soundRegion);
		sprite.drawSprite(32, 32, 64, 64, Assets.leftRegion);
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
