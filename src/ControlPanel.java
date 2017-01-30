package JavaMediaPlayer;

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
 * Only a constructor is needed to create and initialize all the pieces which are
 * used by the JavaMediaPlayer class
 * @author shane
 */
public class ControlPanel {

    private Image muteImg = new Image("file:images/mute.png", 30, 30, false, false);
    private Image unmuteImg = new Image("file:images/unmute.png", 30, 30, false, false);
    private ImageView playView;
    private ImageView muteView;

    private Button playBt;
    private Button muteBt;
    private Slider volumeSlider;
    private SeekBar skBar;
    
    private HBox btPane;
    public VBox ctrlPane;

    public ControlPanel(MediaPlayer player) {
        skBar = new SeekBar(player);
        
        playView = new ImageView(new Image("file:images/playbt.png", 30, 30, false, false));
        muteView = new ImageView(unmuteImg);

        playBt = new Button(null, playView);
        playBt.setOnAction(e -> {
            if (player.getStatus().equals(Status.PLAYING)) {
                player.pause();
            } else {
                player.play();
            }
        });
        
        muteBt = new Button(null, muteView);
        muteBt.setOnAction(e -> {
            if (player.isMute()) {
                muteView.setImage(unmuteImg);
                player.setMute(false);
            } else {
                muteView.setImage(muteImg);
                player.setMute(true);
            }
        });
        
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(150);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.setValue(50);
        player.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

        btPane = new HBox(10);
        btPane.setAlignment(Pos.CENTER);
        btPane.setPadding(new Insets(10, 0, 15, 0));
        btPane.getChildren().addAll(playBt, new Label("Volume"), volumeSlider, muteBt);
        
        ctrlPane = new VBox(10);
        ctrlPane.setAlignment(Pos.CENTER);
        ctrlPane.setPadding(new Insets(10, 0, 0, 0));
        ctrlPane.getChildren().addAll(skBar.videoTime, btPane);
    }
}
