package audio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import utilz.LoadSave;

public class AudioPlayer {
	
	public static int MENU = 0;
	public static int PLAY = 1;
	
    public static int HURT = 0;
    public static int RUN_SHOOT = 1;
    public static int SHOOT = 1;
    public static int DEMON_ATTACK = 2;
    public static int FIRE_DEMON_DEAD = 3;
    public static int FROZEN_DEMON_DEAD = 4;
    public static int SHADOW_DEMON_DEAD = 5;
    public static int BOSS_DEAD = 6;
    public static int BOSS_LASER_CASTING = 7;
    public static int BOSS_LASER = 8;
    public static int METEOR = 9;
    public static int BOSS_SHOOT = 10;
    public static int LEVEL_COMPLETE = 11;
    public static int GAME_OVER = 12;
    
	
    private Clip[] songs, effects;
    private int currentSongId;
    private int currentEffectId;
    private float volume = 0.8f;
    private boolean songMute, effectMute;
    
	public AudioPlayer ()
	{
		loadSongs();
		loadEffects();
		playSong(MENU);
	}
	
	private void loadSongs()
	{
		String[] names = {"menu_music", "background"};
		songs = new Clip[names.length];
		for(int i = 0; i < songs.length;i++)
		{
			songs[i] = getClip(names[i]);
		}
	}
	
	private void loadEffects()
	{
		String[] effectNames = {"player_hurt", "player_shoot", "demon_attack", "demon_fire_dead", "demon_frozen_dead",
				"demon_shadow_dead", "boss_dead", "boss_laser_casting", "boss_laser", "meteor_collision", "boss_shoot",
				"level_comp", "game_over"};
		effects = new Clip[effectNames.length];
		for(int i = 0; i < effects.length;i++)
		{
			effects[i] = getClip(effectNames[i]);
		}
		
		updateEffectVolume();
	}
	
	private Clip getClip(String name) {
	    URL url = getClass().getResource("/" + name + ".wav");
	    AudioInputStream audio;

	    if (url == null) {
	        System.err.println("Audio file not found: " + name);
	        return null;
	    }

	    try {
	        audio = AudioSystem.getAudioInputStream(url);
	        Clip c = AudioSystem.getClip();
	        c.open(audio);
	        return c;
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public void playEffect(int effect)
	{
		currentEffectId = effect;
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}
	
	
	public void playSong(int song)
	{
		stopSong();
		currentSongId = song;
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);

	}
	
	public void setVolume(float volume)
	{
		this.volume = volume;
		updateSongVolume();
		updateEffectVolume();
	}
	
	public void stopSong()
	{
		if(songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}
	
	public void stopEffect()
	{
		if(effects[currentEffectId].isActive())
			effects[currentEffectId].stop();
	}
	
	
	public void lvlCompleted()
	{
		stopSong();
		playEffect(LEVEL_COMPLETE);
	}

	
	public void playGameOver()
	{
		stopSong();
		updateSongVolume();
		songs[GAME_OVER].setMicrosecondPosition(0);
		songs[GAME_OVER].start();
	}
	
	public void toggleSongMute()
	{
		this.songMute = !songMute;
		for(Clip c : songs)
		{
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);
		}
	}
	
	public void toggleEffectMute()
	{
		this.effectMute = !effectMute;
		for(Clip c : effects)
		{
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
	}
	
	private void updateSongVolume()
	{
		FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}
	

	private void updateEffectVolume() {
		for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}
	
}
