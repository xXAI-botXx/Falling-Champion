package tobia.ippolito.game.start;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicLoader{
	
	private URL sound;
	private Clip clip;
	private Boolean playing = false;	
	
	public MusicLoader() {

	}

	public void loadWav(String songName) {
		sound = getClass().getClassLoader().getResource("sound/"+songName+".wav");
		
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {	
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			
			playing = true;
			clip.setMicrosecondPosition(0);
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void playWhile() {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			
			playing = true;
			clip.setMicrosecondPosition(0);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		if(playing) {
			clip.stop();
			playing = false;
		}
	}
	
	public void finish() {
		playing = false;
	}
	
	public boolean isPlaying() {
		return clip.isOpen();
	}

}
