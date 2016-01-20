package deo.framework;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import deo.skeleton.Audio;
import deo.skeleton.Music;
import deo.skeleton.Sound;

public class AndroidAudio implements Audio {

	AssetManager manager;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity){
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.manager = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	@Override
	public Music newMusic(String filename) {
		try {
			AssetFileDescriptor descriptor = manager.openFd(filename);
			return new AndroidMusic(descriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music: " + filename);
		}
	}

	@Override
	public Sound newSound(String filename) {
		try {
			AssetFileDescriptor descriptor = manager.openFd(filename);
			int soundId = soundPool.load(descriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound: " + filename);
		}
	}

}
