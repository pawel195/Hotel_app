/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Paweł
 */
public class AdminviewController implements Initializable {
    
    @FXML private Button lprac;
    @FXML private Button zarz_pok;
    @FXML private Button l_gosci;
    @FXML private Button rabaty;
    @FXML private Button users;
    @FXML private Button reservations;
    @FXML private Button close_button;
    @FXML private Pane Admin_pane;
     /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @FXML
private void action(ActionEvent e) throws IOException{
    if (e.getSource()==lprac){
            Pane mypane2 = null;
            mypane2 = FXMLLoader.load(getClass().getResource("/hotel_app/views/employees.fxml"));
            Scene scene1 = Admin_pane.getScene();
            scene1.setRoot(mypane2);

    }
    if (e.getSource()==zarz_pok){
        Pane mypane4 = null;
            mypane4 = FXMLLoader.load(getClass().getResource("/hotel_app/views/rooms.fxml"));
            Scene scene1 = Admin_pane.getScene();
            scene1.setRoot(mypane4);

    }
    if (e.getSource()==l_gosci){
        Pane mypane3 = null;
            mypane3 = FXMLLoader.load(getClass().getResource("/hotel_app/views/guests.fxml"));
            Scene scene1 = Admin_pane.getScene();
            scene1.setRoot(mypane3);
    }
    if (e.getSource()==rabaty){
        Pane mypane5 = null;
            mypane5 = FXMLLoader.load(getClass().getResource("/hotel_app/views/discounts.fxml"));
            Scene scene1 = Admin_pane.getScene();
            scene1.setRoot(mypane5);
    
    }
    if (e.getSource()==reservations){
        Pane mypane6 = null;
            mypane6 = FXMLLoader.load(getClass().getResource("/hotel_app/views/reservations.fxml"));
            Scene scene1 = Admin_pane.getScene();
            scene1.setRoot(mypane6);
    }     
     if (e.getSource()==users){
         Pane mypane7 = null;
            mypane7 = FXMLLoader.load(getClass().getResource("/hotel_app/views/users.fxml"));
            Scene scene1 = Admin_pane.getScene();
            scene1.setRoot(mypane7);    
    }
     if (e.getSource()==close_button){
         
            Stage stage = (Stage) close_button.getScene().getWindow();
            Stage stage2 = new Stage();
            stage.close();
            
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/hotel_app/views/Loginview.fxml"));
                Scene scene1 = new Scene(root);     
                stage2.setScene(scene1);
                stage2.show();
        }
   
}
 @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}