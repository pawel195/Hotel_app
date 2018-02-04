/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.Hotel_App;
import hotel_app.mysqlconnect;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paweł
 */

public class LoginviewController implements Initializable {
    Connection conn = null;
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Label loginresult;
    @FXML private Button close;
    @FXML private Pane login_pane;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
        

    @FXML
    private void logowanie(ActionEvent event) throws IOException{
        
        conn = mysqlconnect.ConnecrDb();
        
        //szyfrowanie podanego tekstu
        String passwordToHash = password.getText();
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
     
        //szyfrowanie loginow i haseł z bazy danych oraz porownanie
        String query = "select Nazwa_uzutkownika, Haslo from Uzytkownicy";
        PreparedStatement prepareStatement=null;
        ResultSet rs=null;
        try{
            prepareStatement=conn.prepareStatement(query);
            rs = prepareStatement.executeQuery(query);
            
            String generatedPassword2 = null;
            
                while(rs.next()){
                    String login = rs.getString("Nazwa_uzutkownika");
                    String passwordToHash2 = rs.getString("Haslo");
                        try {
                                MessageDigest md2 = MessageDigest.getInstance("MD5");
                                md2.update(passwordToHash2.getBytes());
                                byte[] bytes = md2.digest();
                                StringBuilder sb2 = new StringBuilder();
                                for(int i=0; i< bytes.length ;i++)
                                {
                                    sb2.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                                }
                                generatedPassword2 = sb2.toString();
                                
                                // walidacja uzytkownika
                                    if(user.getText().equals("admin") && generatedPassword.equals(generatedPassword2)){
                                        Pane mypane = null;
                                        mypane = FXMLLoader.load(getClass().getResource("/hotel_app/views/Adminview.fxml"));
                                        Scene scene1 = login_pane.getScene();
                                        scene1.setRoot(mypane);

                                        user.setText("");
                                        password.setText("");

                                     }
                                        if(user.getText().equals(login) && user.getText()!= "admin" && generatedPassword.equals(generatedPassword2)){
                                            Pane mypane = null;
                                            mypane = FXMLLoader.load(getClass().getResource("/hotel_app/views/reservations_home.fxml"));
                                            Scene scene1 = login_pane.getScene();
                                            scene1.setRoot(mypane);
           
                                            user.setText("");
                                            password.setText("");
                                            }
                                                else{
                                                    loginresult.setTextFill(Color.RED);
                                                    loginresult.setText("Podano złe dane!!!");
                                                    }
                            } 
                            catch (NoSuchAlgorithmException e) 
                            {
                                e.printStackTrace();
                            }
                }
            prepareStatement.close();
            rs.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DiscountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //zakończenie programu
            @FXML
            public void handleCloseButtonAction(ActionEvent event) {
                Stage stage = (Stage) close.getScene().getWindow();
                Platform.exit(); 
            }
    //czyszczenie label po kliknieciu w pole tekstowe
            @FXML
            public void clearonclick(ActionEvent e){
                if(e.getSource()==user || e.getSource()==password){
                    loginresult.setText("");
                }
            }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    
    
}
