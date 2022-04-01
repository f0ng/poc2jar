package main.javafxtest;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListViewTest  {

    public static void main(String[] args) {
        String dirname = "/Users/f0ngf0ng/JAVA/seeyontest/poc/";
        File file = new File(dirname);
        File[] files = file.listFiles();
        assert files != null;
        for (File file2 : files) {
            if (file2.getAbsolutePath().indexOf(".DS_Store")>0){

            }
            else {
                System.out.println(file2);
            }
        }
    }
}