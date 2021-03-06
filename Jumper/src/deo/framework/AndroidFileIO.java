package deo.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import deo.skeleton.FileIO;

public class AndroidFileIO implements FileIO {
	
	Context context;
	AssetManager manager;
	String externalStoragePath;
	
	public AndroidFileIO(Context context){
		this.context = context;
		this.manager = context.getAssets();
		this.externalStoragePath = Environment.getExternalStorageDirectory().
				getAbsolutePath() + File.separator;
	}
	
	@Override
	public InputStream readAsset(String filename) throws IOException {
		return manager.open(filename);
	}

	@Override
	public InputStream readFile(String filename) throws IOException {
		return new FileInputStream(externalStoragePath + filename);
	}

	@Override
	public OutputStream writeFile(String filename) throws IOException {
		return new FileOutputStream(externalStoragePath + filename);
	}
	
	public SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}
