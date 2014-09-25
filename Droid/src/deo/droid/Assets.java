package deo.droid;

import game.framework.GLGame;
import game.opengl.Font;
import game.opengl.ObjectLoader;
import game.opengl.Texture;
import game.opengl.TextureRegions;
import game.opengl.Vertices3;
import game.skeleton.Music;
import game.skeleton.Sound;
import game.opengl.Animation;

public class Assets {
	public static Texture background;
	public static TextureRegions backgroundRegion;
	public static Texture items;
	public static TextureRegions logoRegion;
	public static TextureRegions menuRegion;
	public static TextureRegions gameOverRegion;
	public static TextureRegions pauseRegion;
	public static TextureRegions settingsRegion;
	public static TextureRegions touchRegion;
	public static TextureRegions accelRegion;
	public static TextureRegions touchEnabledRegion;
	public static TextureRegions accelEnabledRegion;
	public static TextureRegions soundRegion;
	public static TextureRegions soundEnabledRegion;
	public static TextureRegions leftRegion;
	public static TextureRegions rightRegion;
	public static TextureRegions fireRegion;
	public static TextureRegions pauseButtonRegion;
	public static Font font;
	public static Texture explosionTexture;
	public static Animation explosionAnimation;
	public static Vertices3 shipModel;
	public static Texture shipTexture;
	public static Vertices3 asswipeModel;
	public static Texture asswipeTexture;
	public static Vertices3 shotModel;
	public static Vertices3 shieldModel;
	public static Music music;
	public static Sound clickSound;
	public static Sound explosionSound;
	public static Sound shotSound;
	
	public static void load(GLGame game){
		background = new Texture(game, "background.jpg", true);
		backgroundRegion = new TextureRegions(background, 0, 0, 480, 320);
		items = new Texture(game, "items.png", true);
		logoRegion = new TextureRegions(items, 0, 256, 384, 128);
        menuRegion = new TextureRegions(items, 0, 128, 224, 64);
        gameOverRegion = new TextureRegions(items, 224, 128, 128, 64);
        pauseRegion = new TextureRegions(items, 0, 192, 160, 64);
        settingsRegion = new TextureRegions(items, 0, 160, 224, 32);
        touchRegion = new TextureRegions(items, 0, 384, 64, 64);
        accelRegion = new TextureRegions(items, 64, 384, 64, 64);
        touchEnabledRegion = new TextureRegions(items, 0, 448, 64, 64);
        accelEnabledRegion = new TextureRegions(items, 64, 448, 64, 64);
        soundRegion = new TextureRegions(items, 128, 384, 64, 64);
        soundEnabledRegion = new TextureRegions(items, 190, 384, 64, 64);
        leftRegion = new TextureRegions(items, 0, 0, 64, 64);
        rightRegion = new TextureRegions(items, 64, 0, 64, 64);
        fireRegion = new TextureRegions(items, 128, 0, 64, 64);
        pauseButtonRegion = new TextureRegions(items, 0, 64, 64, 64);
        font = new Font(items, 224, 0, 16, 16, 20);
        explosionTexture = new Texture(game, "explode.png", true);
        TextureRegions[] keyFrames = new TextureRegions[16];
        int frame = 0;
        for (int y = 0; y < 256; y += 64) {
			for (int x = 0; x < 256; x += 64) {
				keyFrames[frame++] = new TextureRegions(explosionTexture, 
						x, y, 64, 64);
			}
		}
        explosionAnimation = new Animation(0.1f, keyFrames);
        shipTexture = new Texture(game, "ship.png", true);
        shipModel = ObjectLoader.load(game, "ship.obj");
        asswipeTexture = new Texture(game, "invader.png", true);
        asswipeModel = ObjectLoader.load(game, "invader.obj");
        shieldModel = ObjectLoader.load(game, "shield.obj");
        shotModel = ObjectLoader.load(game, "shot.obj");
        music = game.getAudio().newMusic("music.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        if(Setting.soundEnabled)
        	music.play();
        clickSound = game.getAudio().newSound("click.ogg");
        explosionSound = game.getAudio().newSound("explosion.ogg");
        shotSound = game.getAudio().newSound("shot.ogg");
	}
	
	public static void reload(){
		background.reload();
		items.reload();
		explosionTexture.reload();
		shipTexture.reload();
		asswipeTexture.reload();
		if(Setting.soundEnabled)
			music.play();
	}
	
	public static void playSound(Sound sound){
		if(Setting.soundEnabled)
			sound.play(1);
	}
}
