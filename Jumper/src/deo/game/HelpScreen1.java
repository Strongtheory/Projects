package deo.game;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import deo.framework.GLScreen;
import deo.gl.Camera2D;
import deo.gl.Sprite;
import deo.gl.Texture;
import deo.gl.TextureRegions;
import deo.math.OverlapTester;
import deo.math.Rectangle;
import deo.math.Vector2;
import deo.skeleton.Game;
import deo.skeleton.Input.TouchEvent;

public class HelpScreen1 extends GLScreen {

	Camera2D guiCamera;
	Sprite sprite;
	Rectangle nextBounds;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegions helpRegion;
	
	public HelpScreen1(Game game) {
		super(game);
		guiCamera = new Camera2D(glGraphics, 320, 480);
		nextBounds = new Rectangle(320 -64, 64, 64, 64);
		touchPoint = new Vector2();
		sprite = new Sprite(glGraphics, 1);
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
				if(OverlapTester.pointInRectangle(nextBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HelpScreen2(game));
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
		sprite.beginBatch(helpImage);
		sprite.drawSprite(160, 240, 320, 480, helpRegion);
		sprite.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		sprite.beginBatch(Assets.items);
		sprite.drawSprite(320 - 32, 32, -64, 64, Assets.arrow);
		sprite.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		helpImage.dispose();
	}

	@Override
	public void resume() {
		helpImage = new Texture(glGame, "help1.png");
		helpRegion = new TextureRegions(helpImage, 0, 0, 320, 480);
	}

	@Override
	public void dispose() {
		
	}

}
