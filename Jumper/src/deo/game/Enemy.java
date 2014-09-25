package deo.game;

import deo.skeleton.DynamicGameObject;

public class Enemy extends DynamicGameObject {

	public static final float ASSHOLE_WIDTH = 1;
	public static final float ASSHOLE_HEIGHT = 0.6f;
	public static final float ASSHOLE_VELOCITY = 3f;
	float stateTime;
	
	public Enemy(float x, float y) {
		super(x, y, ASSHOLE_WIDTH, ASSHOLE_HEIGHT);
		velocity.set(ASSHOLE_VELOCITY, 0);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(ASSHOLE_WIDTH/2, ASSHOLE_HEIGHT/2);
		
		if(position.x < ASSHOLE_WIDTH/2){
			position.x = ASSHOLE_WIDTH/2;
			velocity.x = ASSHOLE_VELOCITY;
		}
		if(position.x > World.WORLD_WIDTH - ASSHOLE_WIDTH/2){
			position.x = World.WORLD_WIDTH - ASSHOLE_WIDTH/2;
			velocity.x = -ASSHOLE_VELOCITY;
		}
		stateTime += deltaTime;
	}

}
