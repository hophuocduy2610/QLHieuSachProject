package com.example.nhom10_qlhs.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class SplashViewController implements Initializable {

    @FXML
    private BorderPane splashBorderPane;
    @FXML
    private Label lblPercent;

    @FXML
    private ProgressBar progressBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CompletableFuture.runAsync(() -> {
            for(int i = 0; i <= 11; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int percent = i*10;
                float countProgress = i/10f;
                Platform.runLater(() -> {
                    lblPercent.setText(percent+"%");
                    progressBar.setProgress(countProgress);
                });
            }
            Platform.runLater(() -> {
                    try {
                        URL url = new File("target/classes/com/example/nhom10_qlhs/dang-nhap-gui.fxml").toURI().toURL();

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(url);
                        BorderPane borderPane = null;
                        borderPane = loader.load();
                        Scene scene = new Scene(borderPane);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        splashBorderPane.getScene().getWindow().hide();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            });
        });
    }
}
