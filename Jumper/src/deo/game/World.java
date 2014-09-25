package deo.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import deo.math.OverlapTester;
import deo.math.Vector2;

public class World {
	public interface WorldListener{
		public void jump();
		public void highJump();
		public void hit();
		public void coin();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final  Vector2 gravity = new Vector2(0, -12);
	
	public final Bob bob;
	public final List<Platform> platForms;
	public final List<Spring> springs;
	public final List<Enemy> enemies;
	public final List<Coin> coins;
	public Castle castle;
	public final WorldListener listener;
	public final Random rand;
	
	public float heightSoFar;
	public int score;
	public int state;
	
	public World(WorldListener listener){
		this.bob = new Bob(5, 1);
		this.platForms = new ArrayList<Platform>();
		this.springs = new ArrayList<Spring>();
		this.enemies = new ArrayList<Enemy>();
		this.coins = new ArrayList<Coin>();
		this.listener = listener;
		rand = new Random();
		generateLevel();
	}
	
	private void generateLevel(){
		float y = Platform.PLATFORM_HEIGHT/2;
		float maxJumpHeight = Bob.BOB_JUMP_VELOCITY * Bob.BOB_JUMP_VELOCITY / 
				(2 * -gravity.y);
		while(y < WORLD_HEIGHT - WORLD_WIDTH/2){
			int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING :
				Platform.PLATFORM_TYPE_STATIC;
			float x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH)
					+ Platform.PLATFORM_WIDTH/2;
			
			Platform platform = new Platform(x, y, type);
			platForms.add(platform);
			
			if(rand.nextFloat() > 0.9f && type 
					!= Platform.PLATFORM_TYPE_MOVING){
				Spring spring = new Spring(platform.position.x, 
						platform.position.y + Platform.PLATFORM_HEIGHT/2
						+ Spring.SPRING_HEIGHT/2);
				springs.add(spring);
			}
			
			if(y > WORLD_HEIGHT/3 && rand.nextFloat() > 0.8f){
				Enemy enemy = new Enemy(platform.position.x
						 + rand.nextFloat(), platform.position.y + 
						 Enemy.ASSHOLE_HEIGHT + rand.nextFloat() * 2);
				enemies.add(enemy);
			}
			
			if(rand.nextFloat() > 0.6f){
				Coin coin = new Coin(platform.position.x + rand.nextFloat(),
						platform.position.y + Coin.COIN_HEIGHT + 
						rand.nextFloat() * 3);
				coins.add(coin);
			}
			
			y += (maxJumpHeight - 0.5f);
			y -= rand.nextFloat() * (maxJumpHeight/2);
		}
		castle = new Castle(WORLD_WIDTH/2, y);
	}
	
	public void update(float deltaTime, float accelX){
		updateBob(deltaTime, accelX);
		updatePlatform(deltaTime);
		updateAsshole(deltaTime);
		updateCoin(deltaTime);
		if(bob.state != Bob.BOB_STATE_HIT)
			collisions();
		gameOver();
	}
	
	private void updateBob(float deltaTime, float accelX){
		if(bob.state != Bob.BOB_STATE_HIT && bob.position.y <= 0.5f)
			bob.hitPlatform();
		if(bob.state != Bob.BOB_STATE_HIT)
			bob.velocity.x = -accelX/10 * Bob.BOB_MOVE_VELOCITY;
		bob.update(deltaTime);
		heightSoFar = Math.max(bob.position.y, heightSoFar);
	}
	
	private void updatePlatform(float deltaTime){
		int length = platForms.size();
		for(int i = 0; i < length; i++){
			Platform platform = platForms.get(i);
			platform.update(deltaTime);
			if(platform.state == Platform.PLATFORM_STATE_BREAKING && 
					platform.stateTime > Platform.PLATFORM_BREAKING_TIME){
				platForms.remove(platform);
				length = platForms.size();
			}
		}
	}
	
	private void updateAsshole(float deltaTime){
		int length = enemies.size();
		for (int i = 0; i < length; i++) {
			Enemy enemy = enemies.get(i);
			enemy.update(deltaTime);
		}
	}
	
	private void updateCoin(float deltaTime){
		int length = coins.size();
		for (int i = 0; i < length; i++) {
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}
	
	private void collisions(){
		platFormCollision();
		assholeCollision();
		itemCollision();
		castleCollision();
	}
	
	private void platFormCollision(){
		if(bob.velocity.y > 0)
			return;
		int length = platForms.size();
		for (int i = 0; i < length; i++) {
			Platform platform = platForms.get(i);
			if(bob.position.y > platform.position.y){
				if(OverlapTester.overlapRectangles(bob.bounds, platform.bounds)){
					bob.hitPlatform();
					listener.jump();
					if(rand.nextFloat() > 0.5f){
						platform.breaking();
					}
					break;
				}
			}
		}
	}
	
	private void assholeCollision(){
		int length = enemies.size();
		for (int i = 0; i < length; i++) {
			Enemy enemy = enemies.get(i);
			if(OverlapTester.overlapRectangles(enemy.bounds, bob.bounds)){
				bob.hitAsshole();
				listener.hit();
			}
		}
	}
	
	private void itemCollision(){
		int length = coins.size();
		for (int i = 0; i < length; i++) {
			Coin coin = coins.get(i);
			if(OverlapTester.overlapRectangles(bob.bounds, coin.bounds)){
				coins.remove(coin);
				length = coins.size();
				listener.coin();
				score += Coin.COIN_SCORE;
			}
		}
		if(bob.velocity.y > 0)
			return;
		length = springs.size();
		for (int i = 0; i < length; i++) {
			Spring spring = springs.get(i);
			if(bob.position.y > spring.position.y){
				if(OverlapTester.overlapRectangles(bob.bounds, spring.bounds)){
					bob.hitSpring();
					listener.highJump();
				}
			}
		}
	}
	
	private void castleCollision(){
		if(OverlapTester.overlapRectangles(castle.bounds, bob.bounds)){
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}
	
	private void gameOver(){
		if(heightSoFar - 7.5f > bob.position.y){
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
