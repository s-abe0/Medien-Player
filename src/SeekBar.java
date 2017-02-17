
//package JavaMediaPlayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 * The SeekBar is a complicated feature to implement, so it gets a class of its own. 
 * The SeekBar allows the user to seek through the media file to a specified time, and also keeps track of where 
 * the user is at in playback of the media file. The ControlPanel class creates an instance of this class for 
 * use in the control panel at the bottom of the player console. 
 * 
 * @author Shane
 */
public class SeekBar 
{
    public Slider videoTime;
    
	/**
	 * The SeekBar constructor takes the MediaPlayer instance that it will be working with, and will be binded together with. 
	 * A Thread is created to keep track of where the media file is in playback time.
	 */
    public SeekBar(MediaPlayer player) 
	{
        videoTime = new Slider();
        
        player.setOnReady(new Runnable()                                    
		{
            public void run() 
			{
                videoTime.setMaxWidth(680);
                videoTime.setMax(player.getTotalDuration().toSeconds());    // Get the total duration of the media file, in seconds. 
            }
        });
        
		// Create a Listener to listen for when the media file time changes. 
        player.currentTimeProperty().addListener(new ChangeListener<Duration>()
		{
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
			{
                videoTime.setValue(newValue.toSeconds());       // If the video time was changed, set the video time to the new value, in seconds.
            }  
        });
        
        
        videoTime.setOnMouseClicked(e ->                           // If the seek bar was clicked on ...
		{
            player.seek(Duration.seconds(videoTime.getValue()));   // Seek to the new playback time. 
        });
    }
}
