package com.example.nhom10_qlhs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DangNhapMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(DangNhapMain.class.getResource("dang-nhap-gui.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(DangNhapMain.class.getResource("man-hinh-chinh-gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 450);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}