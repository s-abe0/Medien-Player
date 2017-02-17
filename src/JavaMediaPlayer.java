
//package JavaMediaPlayer;

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
 * player. This is the main body of the program. Everything else is essentially
 * built into this class. ControlPanel, DropDowns, and SeekBar are built into the JavaMediaPlayer.
 *
 * @author Shane
 * @version 1.0
 */


public class JavaMediaPlayer extends Application 
{    
    private Image initImage = new Image("file:../images/medienEmblem.png", 700, 750, true, true);  
    private ImageView imgView = new ImageView(initImage);  
	
    private static Media media;                            // Media, MediaPlayer, and MediaView
    private static MediaPlayer player;                     // are main elements of a JavaFX media player.
    private static MediaView view;              
	
    private static ControlPanel ctrlPanel;                 
    private DropDowns drops = new DropDowns();   
	
    private static boolean initialized = false;            // The player is not initialized (playing) at startup
    
    private static DoubleProperty width;                   // These two values are for the 
    private static DoubleProperty height;                  // full screen option. Currently not being used.
    
    private static BorderPane pane = new BorderPane();     // BorderPane object allows for DropDown object to be set at 
													       // the top of the media player console, the display to 
														   // be set in the center, and Controls at the bottom.
    
	/**
	 *  The start method is needed by all JavaFX applications. It takes a Stage object as its argument
	 *  to create the graphical interface object (in this case, the media player console).
	 *  @param primaryStage The Stage object used to display the graphical interface.
	 */
    @Override
    public void start(Stage primaryStage) 
	{
        pane.setTop(drops.menuBar);                        // Set the dropdown menus at the top of the BorderPane pane object. 
        pane.setCenter(imgView);                           // Set the Medien logo at the center of the BorderPane pane object.
                
        Scene scene = new Scene(pane, 750, 700);           // Initialize the player console to width 750, height 700.
        scene.getStylesheets().add("file:MediaPlayerStyle.css");  // Get the external CSS stylesheet.
        
        primaryStage.setTitle("Medien Player");   
        primaryStage.setScene(scene);             
        //primaryStage.setResizable(false);                  // Uncomment to disable screen resizing.
        primaryStage.show();                      
    }
    
	/**
	 * In JavaFX applications, the main method only has one line, which tells the Application to launch. 
	 * Essentially what this does is run the 'start' method. 
	 */
    public static void main(String[] args)
	{
        Application.launch(args);                 
    }
    
    /*
      Makes the screen bigger. Contains a small algorithm to check the file type extensions
      for any audio files (trying to bind audio files causes problems; they don't contain any display). 
    
    NOTE:  Enlarge screen option is causing more complications than its worth at the moment. 
        Leaving it out for the time being.  Pardon the mess.
        Major problem with enlarging a video screen then switching to an audio file. The controls
        are being sent off of the screen into outer space somewhere.
    */
    
    /*
    public static void bindScreen() 
	{
        String source = media.getSource();     // Retrieve the media source URI
        String type = "";
        
        for(int i = source.length()-3; i < source.length(); i++)  // Get the type of the media file; m4a, mp3, etc.
		{
            type += source.charAt(i);
        }
        
        switch(type)                          // If the user tried to enlarge an audio file, do nothing, because that doesn't make sense. 
		{
            case "m4a":
                
            case "iff":                       // this is for aiff type; had to use iff since 'type' contains only three chars
                
            case "wav":
                
            case "mp3":
                return;
        }
                
        
        width = view.fitWidthProperty();                                     // These four lines are what
        height = view.fitHeightProperty();                                   // causes the screen to go to full screen.
        width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
    }
    */
    
    
    
    /**
     * Change the media. New Media, MediaPlayer, ControlPanel objects are
     * created each time the media source is changed. Each of these objects
     * are essentially bound to one media source and cannot be changed, so new
     * objects are created for a new media source. Due to Java garbage collection, 
     * this should not decrease efficiency (hopefully)
     * 
     * @param source Source of the new media file
     */
    public static void changeMedia(String source) 
	{
        try 
		{
            media = new Media(source);                       
        } 
		catch (Exception e)                                       // If an invalid source was entered, the user must know.
		{                              
            Pane errorPane = new Pane();                     

            Label errorLbl = new Label("Media file type unsupported\n\n"  
                    + "Valid types are\n"
                    + "\tVideo: MP4 and FLV\n"
                    + "\tAudo: MP3, AIFF, WAV, M4A");
					
            errorLbl.setPadding(new Insets(20, 20, 20, 20));      // Set some padding for the message (top, right, bottom, left).
            errorPane.getChildren().add(errorLbl);           
            
            Stage errorPrompt = new Stage();                      // A new Stage must be created to display the error message pane.
            errorPrompt.setTitle("Invalid file type");       
            errorPrompt.setScene(new Scene(errorPane, 250, 150)); // Set the scene, make the window 250 width, 150 height.
            errorPrompt.show();                              
            errorPrompt.setResizable(false);                      // We don't want the user to resize the error pane.
 
            return;
        }
        
        if(!initialized)                                          // If the application has just been started ...
		{                    
            view = new MediaView();                
            player = new MediaPlayer(media);                      // Give the media source to the player.
            initialized = true;                                   // The player has succesfully been initialized.
        } 
		else                                                      // If the player has already been initialized ...
		{ 
            player.dispose();                                     // Dispose the old player 
            player = new MediaPlayer(media);                      // and make a new one. Having multiple players active causes problems. 
        }

        view.setMediaPlayer(player);                              // Give the player to the MediaView view object.
        ctrlPanel = new ControlPanel(player);                     // Create a new Control panel and also give it the player object.
        pane.setCenter(view);                                     // Set the Media view object in the center of the BorderPane.
        pane.setBottom(ctrlPanel.ctrlPane);                       // Set the controls at the bottom of the BorderPane.
    }
}
