package com.example.cribbage;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.cribbage.Main.mediaPlayer;
import static com.example.cribbage.Main.scene;
import static com.example.cribbage.MainController.root;
import static com.example.cribbage.MainController.stage;



public class SettingsController implements Initializable {
    private static boolean fullScreen = false;
    @FXML
    Slider volumeSlider;
    @FXML
    CheckBox fullScreenCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (fullScreen){
            fullScreenCheckBox.setSelected(true);
        }
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });
    }
    @FXML
    public void onClickFullScreen(ActionEvent event) throws IOException {
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(!fullScreen);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("Alt F4 for +121 points");
        fullScreen = !fullScreen;
    }
    @FXML
    public void onClickMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        root.setId("pane");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.getScene().setRoot(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
