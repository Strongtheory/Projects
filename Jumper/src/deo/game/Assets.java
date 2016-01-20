package deo.game;

import deo.framework.GLGame;
import deo.gl.Font;
import deo.gl.Texture;
import deo.gl.TextureRegions;
import deo.skeleton.Music;
import deo.skeleton.Sound;
import deo.gl.Animation;

public class Assets {
	public static Texture backGround;
	public static TextureRegions backGroundRegion;
	public static Texture items;
	public static TextureRegions mainMenu;
	public static TextureRegions pauseMenu;
	public static TextureRegions ready;
	public static TextureRegions gameOver;
	public static TextureRegions highScoresRegion;
	public static TextureRegions logo;
	public static TextureRegions soundOn;
	public static TextureRegions soundOff;
	public static TextureRegions arrow;
	public static TextureRegions pause;
	public static TextureRegions spring;
	public static TextureRegions castle;
	public static Animation coin;
	public static Animation jump;
	public static Animation fall;
	public static TextureRegions hit;
	public static Animation fly;
	public static TextureRegions platform;
	public static Animation breaking;
	public static Font font;
	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	
	public static void load(GLGame game){
		backGround = new Texture(game, "background.png");
		backGroundRegion = new TextureRegions(backGround, 0, 0, 320, 480);
		items = new Texture(game, "items.png");
		pauseMenu = new TextureRegions(items, 0, 244, 300, 110);
		ready = new TextureRegions(items, 224, 128, 192, 96);
		gameOver = new TextureRegions(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegions(Assets.items, 0, 257, 300, 100/3);
		logo = new TextureRegions(items, 0, 352, 274, 142);
		soundOff = new TextureRegions(items, 0, 0, 64, 64);
		soundOn = new TextureRegions(items, 64, 0, 64, 64);
		arrow = new TextureRegions(items, 0, 64, 64, 64);
		pause = new TextureRegions(items, 64, 64, 64, 64);
		spring = new TextureRegions(items, 128, 0, 32, 32);
		castle = new TextureRegions(items, 128, 64, 64, 64);
		coin = new Animation(0.2f,
				new TextureRegions(items, 128, 32, 32, 32),
				new TextureRegions(items, 160, 32, 32, 32),
				new TextureRegions(items, 192, 32, 32, 32),
				new TextureRegions(items, 160, 32, 32, 32));
		jump = new Animation(0.2f,
				new TextureRegions(items, 64, 128, 32, 32),
				new TextureRegions(items, 96, 128, 32, 32));
		hit = new TextureRegions(items, 128, 128, 32, 32);
		fly = new Animation(0.2f, 
				new TextureRegions(items, 0, 160, 32, 32),
				new TextureRegions(items, 32, 160, 32, 32));
		platform = new TextureRegions(items, 64, 160, 64, 16);
		breaking = new Animation(0.2f, 
				new TextureRegions(items, 64, 160, 64, 16),
				new TextureRegions(items, 64, 176, 64, 16),
				new TextureRegions(items, 64, 192, 64, 16),
				new TextureRegions(items, 64, 208, 64, 16));
		font = new Font(items, 224, 0, 16, 16, 20);
		music = game.getAudio().newMusic("music.mp3");
		music.setLooping(true);
		music.setVolume(0.5f);
		if(Settings.soundEnabled)
			music.play();
		jumpSound = game.getAudio().newSound("jump.ogg");
		highJumpSound = game.getAudio().newSound("highjump.ogg");
        hitSound = game.getAudio().newSound("hit.ogg");
        coinSound = game.getAudio().newSound("coin.ogg");
        clickSound = game.getAudio().newSound("click.ogg"); 
	}
	
	public static void reload(){
		backGround.reload();
		items.reload();
		if(Settings.soundEnabled)
			music.play();
	}
	
	public static void playSound(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}
}
