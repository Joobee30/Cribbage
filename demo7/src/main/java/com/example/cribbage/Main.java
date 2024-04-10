package com.example.cribbage;



import java.io.IOException;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;




public class Main extends Application {

    public static MediaPlayer mediaPlayer;
    public static Scene scene;
    @Override

    public void start(Stage stage) {

        Parent root;

        try {
            Media media = new Media("https://vgmsite.com/soundtracks/danganronpa-trigger-happy-havoc-original-soundtrack/bhorzhytjz/2-06%20Box%2016.mp3");
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.08);
            mediaPlayer.play();

            root = FXMLLoader.load(getClass().getResource("/com/example/cribbage/interface.fxml"));
            root.setId("pane");
            scene = new Scene(root);
            scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            
           System.out.println(e);
        }

    }

    public static void main(String[] args) {
        launch(args);

    }
}
