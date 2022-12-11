package com.example.nhom10_qlhs;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DangNhapMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(DangNhapMain.class.getResource("dang-nhap-gui.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(DangNhapMain.class.getResource("splash-screen.fxml"));

//        FXMLLoader fxmlLoader = new FXMLLoader(DangNhapMain.class.getResource("menu-gui.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 700, 450);
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}