
package JavaMediaPlayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 * Since the seek bar is a beast of its own, it gets a class of its own. 
 * It is used by the ControlPanel class.
 * @author shane
 */
public class SeekBar {
    public Slider videoTime;
    
    public SeekBar(MediaPlayer player) {
        videoTime = new Slider();
        
        player.setOnReady(new Runnable() {
            public void run() {
                videoTime.setMaxWidth(680);
                videoTime.setMax(player.getTotalDuration().toSeconds());
            }
        });
        
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                videoTime.setValue(newValue.toSeconds());
            }  
        });
        
        // Allows user to click the slider to move to a different time in the video
        videoTime.setOnMouseClicked(e -> {
            player.seek(Duration.seconds(videoTime.getValue()));
        });
    }
}
