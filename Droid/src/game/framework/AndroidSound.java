package game.framework;

import game.skeleton.Sound;
import android.media.SoundPool;

public class AndroidSound implements Sound {

	int soundID;
	SoundPool soundPool;
	
	public AndroidSound(SoundPool soundPool, int soundID){
		this.soundID = soundID;
		this.soundPool = soundPool;
	}
	
	@Override
	public void play(float volume) {
		soundPool.play(soundID, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		soundPool.unload(soundID);
	}

}
