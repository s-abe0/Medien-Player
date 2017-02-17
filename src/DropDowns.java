//package JavaMediaPlayer;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * Dropdowns class adds drop down menus to the top of the player screen.
 * The contructor creates and initialized all the buttons and menus. The URL menu 
 * has a separate method which opens up a small window that allows the user to 
 * enter a URL to stream from via HTTP.
 *
 * @author shane
 */
public class DropDowns 
{
    private Menu fileMenu = new Menu("File");
    private Menu viewMenu = new Menu("View");
    private Menu aboutMenu = new Menu("About");
	
    private MenuItem urlItem = new MenuItem("Open URL"); 
    private MenuItem fileItem = new MenuItem("Open File");
    private MenuItem enlargeScreen = new MenuItem("Enlarge Screen");    // Currently not implemented.
    private MenuItem aboutItem = new MenuItem("About Medien");
	
    private FileChooser fileChooser = new FileChooser();
	
    public MenuBar menuBar = new MenuBar();                             // Used in JavaMediaPlayer class. Placed in top of BorderPane.

    public DropDowns()
	{
		// Change the text to black.
        urlItem.setStyle("-fx-text-fill: #000000");
        fileItem.setStyle("-fx-text-fill: #000000");
        enlargeScreen.setStyle("-fx-text-fill: #000000");
        aboutItem.setStyle("-fx-text-fill: #000000");

		// Add in items to their corresponding Menus.
        fileMenu.getItems().addAll(urlItem, fileItem);
        viewMenu.getItems().addAll(enlargeScreen);
        aboutMenu.getItems().addAll(aboutItem);
		
		// Put the Menus into the MenuBar, and set the background color.
        menuBar.getMenus().addAll(fileMenu, viewMenu, aboutMenu);
        menuBar.setStyle("-fx-background-color: #383838");

        fileItem.setOnAction(e ->                                      // If 'Open File' was clicked ...
		{
            File file = fileChooser.showOpenDialog(new Stage());       // Open up a file explorer and allow the user to choose a file
            JavaMediaPlayer.changeMedia(file.toURI().toString());      // and attempt to open the media file for playback. 
        });

        urlItem.setOnAction(e ->                                       // If the 'Open URL' was clicked ...
		{
            URLAction();                                               // Run the URLAction method.
        });
        
		// Full screen view is currently not implemented. 
        enlargeScreen.setOnAction(e -> 
		{
            //JavaMediaPlayer.bindScreen();
        });
        
        aboutItem.setOnAction(e ->                                        // If the 'About Medien' was clicked ...
		{
            Pane aboutPane = new Pane();                               
            HBox hbox = new HBox();
            Image emblem = new Image("file:../images/medienEmblem.png", 200, 200, true, true);
            ImageView imgView = new ImageView(emblem);
            Label aboutTitleLabel = new Label("Medien Player");
            Label aboutLabel = new Label("\n\n\nVersion 1.0 All rights reserved\n");
            aboutTitleLabel.setStyle("-fx-font-size: 25; -fx-font-weight: bold");
            aboutTitleLabel.setPadding(new Insets(20,20,20,20));
            aboutLabel.setPadding(new Insets(20, 20, 20, 20));
            
            aboutPane.getChildren().addAll(aboutTitleLabel, aboutLabel);   // aboutPane holds the text in a top to bottom fashion.
            hbox.getChildren().addAll(imgView, aboutPane);                 // hbox holds the Medien logo and aboutPane from left to right, respectively. 

            Stage aboutPrompt = new Stage();
            aboutPrompt.setTitle("About");
            aboutPrompt.setScene(new Scene(hbox, 450, 160));
            aboutPrompt.show();
            aboutPrompt.setResizable(false);                               // User should not be able to resize the window.
        });
    }

	/*
		URLAction method creates a new window that opens up when the user clicks 'Open URL'. 
		The user types in a valid URL into the text box, and then the URL is sent to the JavaMediaPlayer changeMedia method 
		to attempt to open the media source for playback. 
	*/
    private void URLAction() 
	{
        VBox pane = new VBox();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setSpacing(10);

        Button okBt = new Button("OK");

        TextField text = new TextField();
        pane.getChildren().addAll(text, okBt);               // Display the TextField and Button from top to bottom, respectively. 

        Stage stage = new Stage();
        stage.setTitle("Open URL");
        stage.setScene(new Scene(pane, 300, 100));
        stage.setResizable(false);
        stage.show();

        okBt.setOnAction(e ->                                // If 'OK' was clicked ...
		{
            JavaMediaPlayer.changeMedia(text.getText());     // Get the text entered by the user and send it to JavaMediaPlayer to attempt to open the URL. 
            stage.close();
        });
    }
}
