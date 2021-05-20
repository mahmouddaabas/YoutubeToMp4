package view;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utility.Converter;
import utility.Downloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GUI extends Application {
    private Stage stage;
    private Parent root;
    private Converter converter;

    @FXML
    private Button btnConvert;
    @FXML
    private TextField txtUrl;
    @FXML
    ImageView viewVideoImage;
    @FXML
    ProgressBar progressBar;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initializeGui();
    }

    /**
     * Constructs the class.
     */
    public GUI() {
        //Sends this class object to Converter.
        this.converter = new Converter(this);
    }

    /**
     * Initializes the GUI.
     */
    public void initializeGui() {
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/gui.fxml"));
            addBackground();
            stage.getIcons().add(new Image(GUI.class.getResourceAsStream("/image/youtubeIcon.png")));
            stage.setTitle("Youtube To MP4 Converter.");
            stage.setResizable(true);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the URL given in the TextField.
     */
    public void convert() {
        if(txtUrl != null || !txtUrl.getText().trim().isEmpty()) {
            try {
                converter.convertVideo(txtUrl.getText());
                setVideoImage(converter.getVideoImage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setProgress(double progress) {
        progressBar.setProgress(progress);
    }

    /**
     * Sets the video image on the GUI.
     * @param videoImage
     */
    public void setVideoImage(String videoImage) {
        try {
            BufferedImage image;
            URL url = new URL(videoImage);
            image = ImageIO.read(url);
            Image img = SwingFXUtils.toFXImage(image, null);
            viewVideoImage.setPreserveRatio(false);
            viewVideoImage.setFitWidth(310);
            viewVideoImage.setFitHeight(192);
            viewVideoImage.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a background to the GUI.
     */
    public void addBackground() {
        //Sets background image and adjusts the size/placement.
        String image = getClass().getResource("/image/background.png").toExternalForm();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: cover;");
    }
}

