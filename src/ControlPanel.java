
//package JavaMediaPlayer;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The ControlPanel class holds all the user controls that appear towards the bottom
 * of the player (Play/Pause button, volume, mute, etc.)
 * Only a constructor is needed to create and initialize all the pieces which are then
 * used by the JavaMediaPlayer class.
 *
 * @author Shane
 */
public class ControlPanel 
{
    private Image muteImg = new Image("file:../images/mute.png", 30, 30, false, false);      
    private Image unmuteImg = new Image("file:../images/unmute.png", 30, 30, false, false);  
    private ImageView playView;           
    private ImageView muteView;			 

    private Button playBt;               
    private Button muteBt;               
    private Slider volumeSlider;         
    private SeekBar skBar;               
    
    private HBox btPane;                 // A horizontal layout object to display the buttons horizontally. 
    public VBox ctrlPane;                // A vertical layout object that will hold the btPane for proper display.

	/**
	 * The ControlPanel constructor will create all the control buttons (play, pause, mute, etc.) 
	 * in a nice displayed format that will be placed at the bottom of the media player console (the BorderPane). 
	 * The actions of the controls are also set. 
	 * @param player The ControlPanel needs a reference to the player it is being used in. 
	 */
    public ControlPanel(MediaPlayer player) 
	{
        skBar = new SeekBar(player);                        // Give the seekBar the player reference it's working with.
        
        playView = new ImageView(new Image("file:../images/playbt.png", 30, 30, false, false)); 
        muteView = new ImageView(unmuteImg);                // Mute button is set to the non-muted image initially. 

        playBt = new Button(null, playView);                
        playBt.setOnAction(e ->                             // If the play button is clicked ...
		{
            if (player.getStatus().equals(Status.PLAYING))  // If the player is currently playing
			{
                player.pause();                             // pause the video. 
            } 
			else                                            // If the player is currently paused
			{
                player.play();                              // play the video. 
            }
        });
        
        muteBt = new Button(null, muteView);                
        muteBt.setOnAction(e ->                             // If the mute button is clicked ...
		{
            if (player.isMute())                            // If the player is currently muted
			{
                muteView.setImage(unmuteImg);               // swap the image to the non-muted image
                player.setMute(false);                      // and unmute the player.
            } 
			else                                            // If the player is currently not muted
			{
                muteView.setImage(muteImg);                 // swap the image to the muted image
                player.setMute(true);                       // and mute the player. 
            }
        });
        
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(150);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.setValue(50);
        player.volumeProperty().bind(volumeSlider.valueProperty().divide(100));  // Bind the volumeSlider and the player together.

        btPane = new HBox(10);
        btPane.setAlignment(Pos.CENTER);
        btPane.setPadding(new Insets(10, 0, 15, 0));
        btPane.getChildren().addAll(playBt, new Label("Volume"), volumeSlider, muteBt);  
        
        ctrlPane = new VBox(10);
        ctrlPane.setAlignment(Pos.CENTER);
        ctrlPane.setPadding(new Insets(10, 0, 0, 0));
        ctrlPane.getChildren().addAll(skBar.videoTime, btPane);                 // seekBar displayed above the button pane.
    }
}
