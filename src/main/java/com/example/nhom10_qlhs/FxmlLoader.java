package com.example.nhom10_qlhs;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.net.URL;

public class FxmlLoader {
    private static BorderPane view;
    public BorderPane getBorderPane(String filename){
        try {
            URL fileUrl = DangNhapMain.class.getResource(filename + ".fxml");
            if (fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("No page " + filename + " please check FxmlLoader");
        }
        return view;
    }
}
