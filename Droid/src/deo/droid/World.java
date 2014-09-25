package deo.droid;

import game.math.OverlapTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
	public interface WorldListener{
		public void explosion();
		public void shot();
	}
	final static float WORLD_MIN_X = -14;
	final static float WORLD_MAX_X = 14;
	final static float WORLD_MIN_Z = -15;
	
	WorldListener listener;
	int waves = 1;
	int score = 0;
	float speedMultiplier = 1;
	final List<Shot> shots = new ArrayList<Shot>();
	final List<Asswipe> asswipes = new ArrayList<Asswipe>();
	final List<Shield> shields = new ArrayList<Shield>();
	final Ship ship;
	long lastShot;
	Random random;
	
	public World(){
		ship = new Ship(0, 0, 0);
		generateAsswipes();
		generateShields();
		lastShot = System.nanoTime();
		random = new Random();
	}
	
	private void generateAsswipes(){
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 8; col++) {
				Asswipe asswipe = new Asswipe(-WORLD_MAX_X/2 + col * 2f, 0, 
						WORLD_MIN_Z + row * 2f);
				asswipes.add(asswipe);
			}
		}
	}
	
	private void generateShields(){
		for (int i = 0; i < 5; i++) {
			shields.add(new Shield(-10 + i * 10 - 1, 0, -3));
			shields.add(new Shield(-10 + i * 10 + 0, 0, -3));
			shields.add(new Shield(-10 + i * 10 + 1, 0, -3));
			shields.add(new Shield(-10 + i * 10 - 1, 0, -3));
			shields.add(new Shield(-10 + i * 10 + 1, 0, -2));
		}
	}
	
	public void setWorldListener(WorldListener listener){
		this.listener = listener;
	}
	
	public void update(float deltaTime, float accelX){
		ship.update(deltaTime, accelX);
		updateAsswipes(deltaTime);
		updateShots(deltaTime);
		shotCollision();
		asswipeCollision();
		if(asswipes.size() == 0){
			generateAsswipes();
			waves++;
			speedMultiplier += 0.5f;
		}
	}
	
	private void updateAsswipes(float deltaTime){
		int length = asswipes.size();
		for (int i = 0; i < length; i++) {
			Asswipe asswipe = asswipes.get(i);
			asswipe.update(deltaTime, speedMultiplier);
			if(asswipe.state == Asswipe.ASSWIPE_ALIVE){
				if(random.nextFloat() < 0.001f){
					Shot shot = new Shot(asswipe.position.x, 
							asswipe.position.y, 
							asswipe.position.z, 
							Shot.SHOT_VELOCITY);
					shots.add(shot);
					listener.shot();
				}
			}
			if(asswipe.state == Asswipe.ASSWIPE_DEAD && 
					asswipe.stateTime > Asswipe.ASSWIPE_EXPLOSION_TIME){
				asswipes.remove(i);
				i--;
				length--;
			}
		}
	}
	
	private void updateShots(float deltaTime){
		int length = shots.size();
		for (int i = 0; i < length; i++) {
			Shot shot = shots.get(i);
			shot.update(deltaTime);
			if(shot.position.z < WORLD_MIN_Z || 
					shot.position.z > 0){
				shots.remove(i);
				i--;
				length--;
			}
		}
	}
	
	private void asswipeCollision(){
		if(ship.state == Ship.SHIP_EXPLODING)
			return;
		int length = asswipes.size();
		for (int i = 0; i < length; i++) {
			Asswipe asswipe = asswipes.get(i);
			if(OverlapTester.overlapSpheres(ship.bounds, asswipe.bounds)){
				ship.lives = 1;
				ship.killed();
				return;
			}
		}
	}
	
	private void shotCollision(){
		int length = shots.size();
		for (int i = 0; i < length; i++) {
			Shot shot = shots.get(i);
			boolean removed = false;
			int length2 = shields.size();
			for (int j = 0; j < length2; j++) {
				Shield shield = shields.get(j);
				if(OverlapTester.overlapSpheres(shield.bounds, shot.bounds)){
					shields.remove(j);
					shots.remove(i);
					i--;
					length--;
					removed = true;
					break;
				}
			}
			if(removed)
				continue;
			if(shot.velocity.z < 0){
				length2 = asswipes.size();
				for (int j = 0; j < length2; j++) {
					Asswipe asswipe = asswipes.get(j);
					if(OverlapTester.overlapSpheres(asswipe.bounds, shot.bounds)
							&& asswipe.state == Asswipe.ASSWIPE_ALIVE){
						asswipe.kill();
						listener.explosion();
						score += 10;
						shots.remove(i);
						i--;
						length--;
					}
				}
			}
		}
	}
	
	public boolean gameOver(){
		return ship.lives == 0;
	}
	
	public void shoot(){
		if(ship.state == Ship.SHIP_EXPLODING)
			return;
		int fShots = 0;
		int length = shots.size();
		for (int i = 0; i < length; i++) {
			if(shots.get(i).velocity.z < 0)
				fShots++;
		}
		if(System.nanoTime() - lastShot > 1000000000 || fShots == 0){
			shots.add(new Shot(ship.position.x, 
					ship.position.y, 
					ship.position.z, 
					-Shot.SHOT_VELOCITY));
			lastShot = System.nanoTime();
			listener.shot();
		}
	}
}
