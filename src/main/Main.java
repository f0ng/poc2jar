package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Properties;
//import sun.misc.IOUtils;
import java.lang.System;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        System.out.println(getClass().getResource("/poc2expgui.fxml").toString());
        Parent root = FXMLLoader.load(getClass().getResource("/poc2expgui.fxml"));
        primaryStage.setTitle("poc2jar综合利用工具 v0.55  f0ng");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {

        launch(args);

    }
}