package deo.droid;

import game.skeleton.DynamicGameObject3D;

public class Asswipe extends DynamicGameObject3D {

	static final int ASSWIPE_ALIVE = 0;
	static final int ASSWIPE_DEAD = 1;
	static final float ASSWIPE_EXPLOSION_TIME = 1.6f;
	static final float ASSWIPE_RADIUS = 0.75f;
	static final float ASSWIPE_VELOCITY = 1;
	static final int MOVE_LEFT = 0;
	static final int MOVE_RIGHT = 2;
	static final int MOVE_DOWN = 1;
	int state = ASSWIPE_ALIVE;
	float stateTime = 0;
	int move = MOVE_LEFT;
	boolean lastStateLeft = true;
	float distanceMoved = World.WORLD_MAX_X/2;
	
	public Asswipe(float x, float y, float z) {
		super(x, y, z, ASSWIPE_RADIUS);
	}

	public void update(float deltaTime, float speedMultiplier){
		if(state == ASSWIPE_ALIVE){
			distanceMoved += deltaTime * ASSWIPE_VELOCITY * speedMultiplier;
			if(move == MOVE_LEFT){
				position.x -= deltaTime * ASSWIPE_VELOCITY * speedMultiplier;
				if(distanceMoved > World.WORLD_MAX_X){
					move = MOVE_DOWN;
					distanceMoved = 0;
					lastStateLeft = true;
				}
			}
			if(move == MOVE_RIGHT){
				position.x += deltaTime * ASSWIPE_VELOCITY * speedMultiplier;
				if(distanceMoved > World.WORLD_MAX_X){
					move = MOVE_DOWN;
					distanceMoved = 0;
					lastStateLeft = false;
				}
			}
			if(move == MOVE_DOWN){
				position.z += deltaTime * ASSWIPE_VELOCITY * speedMultiplier;
				if(distanceMoved > 1){
					if(lastStateLeft)
						move = MOVE_RIGHT;
					else
						move = MOVE_LEFT;
					distanceMoved = 0;
				}
			}
			bounds.center.set(position);
		}
		stateTime += deltaTime;
	}
	
	public void kill(){
		state = ASSWIPE_DEAD;
		stateTime = 0;
	}
}
