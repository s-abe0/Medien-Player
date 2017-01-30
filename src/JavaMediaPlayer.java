
package JavaMediaPlayer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * JavaMediaPlayer takes advantage of the JavaFX Media library to efficiently 
 * play media files from the users hard drive or stream media from a URL source 
 * via HTTP streaming. Supported video file types are .mp4 and .flv file types. 
 * Certain audio files can also be played. CSS is used to add some styling to the
 * player 
 * @author shane
 * @version 1.0
 */


public class JavaMediaPlayer extends Application {
    
    private Image initImage = new Image("file:images/medienEmblem.png", 700, 750, true, true);
    private ImageView imgView = new ImageView(initImage);
    private static Media media;
    private static MediaPlayer player;
    private static MediaView view;
    private static ControlPanel ctrlPanel;
    private Pane viewPane = new Pane();
    private DropDowns drops = new DropDowns();
    private static boolean initialized = false;
    
    private static DoubleProperty width;
    private static DoubleProperty height;
    
    private static BorderPane pane = new BorderPane();
    
    @Override
    public void start(Stage primaryStage) {
        pane.setTop(drops.menuBar); 
        pane.setCenter(imgView); 
                
        Scene scene = new Scene(pane, 750, 700);
        scene.getStylesheets().add("file:MediaPlayerStyle.css");
        
        primaryStage.setTitle("Medien Player");
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false); // Uncomment to disable screen resizing
        primaryStage.show(); // Display the Media Player
    }
    
    public static void main(String[] args) {
        Application.launch(args); // All JavaFX applications must have this statement in the main method
    }
    
    /*
      Makes the screen bigger. Contains a small algorithm to check the file type extensions
      for any audio files (trying to bind audio files causes problems; they don't contain any display). 
    
    NOTE:  Enlarge screen option is causing more complications than its worth at the moment. 
        Leaving it out for the time being.
        Major problem with enlarging a video screen then switching to an audio file. The controls
        are being sent off of the screen into oblivion somewhere.
    */
    
    /*
    public static void bindScreen() {
        String source = media.getSource();
        String type = "";
        
        for(int i = source.length()-3; i < source.length(); i++) {
            type += source.charAt(i);
        }
        
        switch(type) {
            case "m4a":
                
            case "iff": // this is for aiff type; had to use iff since 'type' contains only three chars
                
            case "wav":
                
            case "mp3":
                return;
        }
                
        
        width = view.fitWidthProperty();
        height = view.fitHeightProperty(); 
        width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
    }
    */
    
    
    
    /**
     * Change the media. New Media, MediaPlayer, ControlPanel objects are
     * created each time the media is played. Each of these objects
     * are essentially bound to one media source and cannot be changed, so new
     * objects are created for a new media source. Due to Java garbage collection, 
     * this should not decrease efficiency. Hopefully.
     * 
     * @param source Source of the new media file
     */
    public static void changeMedia(String source) {

        try {
            media = new Media(source);
        } catch (Exception e) {
            Pane errorPane = new Pane();

            Label errorLbl = new Label("Media file type unsupported\n\n"
                    + "Valid types are\n"
                    + "\tVideo: MP4 and FLV\n"
                    + "\tAudo: MP3, AIFF, WAV, M4A");
            errorLbl.setPadding(new Insets(20, 20, 20, 20));
            errorPane.getChildren().add(errorLbl);
            
            Stage errorPrompt = new Stage();
            errorPrompt.setTitle("Invalid file type");
            errorPrompt.setScene(new Scene(errorPane, 250, 150));
            errorPrompt.show();
            errorPrompt.setResizable(false);
            //System.out.println("Invalid file or file type not supported");
            return;
        }
        
        // if the application has just been started...
        if(!initialized) {
            view = new MediaView();
            player = new MediaPlayer(media);
            initialized = true; // user selected a valid media file; application has properly started
        } else {
            player.dispose(); // dispose the old player and make a new one; having multiple players causes problems
            player = new MediaPlayer(media);
        }

        view.setMediaPlayer(player);
        ctrlPanel = new ControlPanel(player);
        pane.setCenter(view);
        pane.setBottom(ctrlPanel.ctrlPane);
    }
    
}
