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

public class MainMenu extends GLScreen {

	Camera2D guiCamera;
	Sprite sprite;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoreBounds;
	Rectangle helpBounds;
	Vector2 touchPoint;
	
	public MainMenu(Game game) {
		super(game);
		guiCamera = new Camera2D(glGraphics, 320, 480);
		sprite = new Sprite(glGraphics, 100);
		soundBounds = new Rectangle(0, 0, 64, 64);
		playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
		highscoreBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
		helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);
		touchPoint = new Vector2();
	}
	
	public void update(float deltaTime){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		for(int i = 0; i < touchEvents.size(); i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				touchPoint.set(event.x, event.y);
				guiCamera.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(playBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HighScoreScreen(game));
					return;
				}
				if(OverlapTester.pointInRectangle(helpBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HelpScreen1(game));
					return;
				}
				if(OverlapTester.pointInRectangle(soundBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
						Assets.music.play();
					else
						Assets.music.pause();
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
		
		sprite.drawSprite(160, 480 -10 -71, 274, 142, Assets.logo);
		sprite.drawSprite(160, 200, 300, 110, Assets.mainMenu);
		sprite.drawSprite(32, 32, 64, 64, Settings.soundEnabled ? 
				Assets.soundOn : Assets.soundOff);
		
		sprite.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}
