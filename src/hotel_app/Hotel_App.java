/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Paweł
 */

public class Hotel_App extends Application {

  
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("views/Loginview.fxml"));
        Scene scene1 = new Scene(root);
        primaryStage.setWidth(800);
        primaryStage.setHeight(620);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Aplikacja hotelowa");
        primaryStage.setScene(scene1);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }}
