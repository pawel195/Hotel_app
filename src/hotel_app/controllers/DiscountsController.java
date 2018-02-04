/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.DiscountModel;
import hotel_app.mysqlconnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paweł
 */
public class DiscountsController implements Initializable {

    @FXML
    private TextArea disc_show_desc;
        @FXML
        private TextArea disc_show_desc1;
        
        @FXML
        private TextArea disc_show_val;
    @FXML
    private Button disc_del;
    @FXML
    private Button disc_edit;
    @FXML
    private TextField disc_name;
    @FXML
    private Button disc_new;
    @FXML
    private TextField disc_value;
    @FXML
    private Button close_button;
    
    Connection connection=mysqlconnect.ConnecrDb();
    PreparedStatement prepareStatement=null;
    ResultSet rs=null;
  
   
    final ObservableList<String> options = FXCollections.observableArrayList();
    @FXML private ComboBox<String> disc_combo;
    
 
    @FXML
    private void close_action(ActionEvent event) throws IOException {
        //zamykanie okna
            if (event.getSource()==close_button){
            Stage stage = (Stage) close_button.getScene().getWindow();
            Stage stage2 = new Stage();
            stage.close();
            
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/hotel_app/views/Adminview.fxml"));
                Scene scene1 = new Scene(root);     
                stage2.setScene(scene1);
                stage2.show();
        }
        }
    
    
    //wyswietlenie listy
    @FXML
    public void showDiscount(){
        try {
            String query = "select Rodzaj from Rabaty";
            prepareStatement=connection.prepareStatement(query);
            rs = prepareStatement.executeQuery(query);
            while(rs.next()){
                String napis = rs.getString("Rodzaj");
                options.add(napis);
            }
            disc_combo.setItems(options);
            prepareStatement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DiscountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        //wypisanie danych
        @FXML
        public void showDiscountmore() throws SQLException{
            prepareStatement = null;
            rs = null;   
            String selected = disc_combo.getValue();
            String query2="select * from Rabaty where Rodzaj = '"+selected+"'";
                     try {
                         prepareStatement=connection.prepareStatement(query2);   
                         rs=prepareStatement.executeQuery(query2);
                          while(rs.next()){                             
                                disc_show_desc1.setText(rs.getString("Opis"));
                                disc_show_val.setText(Integer.toString(rs.getInt("Wartosc")));
                            }                         
                         prepareStatement.close();
                         rs.close();
                     } catch(SQLException e)
                        {
                        System.out.println(e);
                        }
        
        }
   

    
    
    //dodawanie rabatu
    @FXML
    public void addnewDiscount() throws SQLException, IOException{
        String dis_name = disc_name.getText();
        String dis_descr = disc_show_desc.getText();
        int val = Integer.valueOf(disc_value.getText());
        
         if(!dis_name.isEmpty() && !dis_descr.isEmpty() && val > 0){
            String query="INSERT INTO Rabaty (Opis, Wartosc, Rodzaj) VALUES(?, ?, ?)";
   
            
            try{
                    prepareStatement=connection.prepareStatement(query);
                    prepareStatement.setString(1, dis_descr);
                    prepareStatement.setInt(2, val);
                    prepareStatement.setString(3, dis_name);
                }
                catch(SQLException e)
                {
                    System.out.println(e);
                }
                finally{
                    prepareStatement.execute();
                    prepareStatement.close();
                }
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/success.fxml"));
                          Parent root = (Parent) fxmlLoader.load();
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root));
                          stage.show();
                options.clear();
                showDiscount();
                disc_name.clear();
		disc_show_desc.clear();
		disc_value.clear();
                disc_show_desc1.clear();
                disc_show_val.clear();
          }
          else{
              FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/add_alert.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Błąd");
                stage.setScene(new Scene(root));
                stage.show();
          }
        
        }
    
        //edycja danych
        @FXML
        public void disc_modify(){
        String dis_descr = disc_show_desc1.getText();
        int val = Integer.parseInt(disc_show_val.getText());
        String selected = disc_combo.getValue();
        
        
         if(!dis_descr.isEmpty() && val > 0){
            try{
                String query="UPDATE Rabaty SET Opis=?,  Wartosc=? WHERE Rodzaj='"+selected+"'";
                
                try{
                    prepareStatement=connection.prepareStatement(query);
                    prepareStatement.setString(1, dis_descr);
                    prepareStatement.setInt(2, val);
                    prepareStatement.execute();
                    prepareStatement.close();
                }
                catch(SQLException e)
                {
                    System.out.println(e);
                }
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/success.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
                catch(IOException ex)
                {
                    Logger.getLogger(DiscountsController.class.getName()).log(Level.SEVERE, null,ex);
                }               
          }
          else{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/add_alert.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Błąd");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(DiscountsController.class.getName()).log(Level.SEVERE, null, ex);
                }
          }
        }
        
        @FXML
        public void disc_delete() throws IOException{
           String selected = disc_combo.getValue();
           
            String query="delete from Rabaty where Rodzaj='"+selected+"'";
            if(!selected.isEmpty()){
            try{
                    prepareStatement=connection.prepareStatement(query);
                     prepareStatement.execute();
                    prepareStatement.close();
                }
                catch(SQLException e)
                {
                    System.out.println(e);
                }               
                options.clear();
                showDiscount();
		disc_show_desc1.clear();
		disc_show_val.clear();
                
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/success.fxml"));
                          Parent root = (Parent) fxmlLoader.load();
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root));
                          stage.show();
            }
          else{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/add_alert.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Błąd");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(DiscountsController.class.getName()).log(Level.SEVERE, null, ex);
                }
          
        }}
      @Override
    public void initialize(URL url, ResourceBundle rb) {
      showDiscount();
    }    
}

    

