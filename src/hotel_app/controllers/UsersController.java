/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.UserModel;
import hotel_app.mysqlconnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paweł
 */
public class UsersController implements Initializable {

    
    @FXML private Button close_button;
    @FXML private Button user_edit;
    @FXML private Button user_update;
    @FXML private Button createUser;
    @FXML private TextField usr_name;
    @FXML private TextField usr_pass;

    Connection connection=mysqlconnect.ConnecrDb();
    ObservableList<UserModel> usr_data=FXCollections.observableArrayList();
    
    PreparedStatement prepareStatement=null;
    ResultSet rs=null;
    
    @FXML TableView<UserModel> usr_table;
        @FXML private TableColumn<? , ?> nu;
        @FXML private TableColumn<? , ?> haslo;
    
        
    
    @FXML
    private void close_action(ActionEvent event) throws IOException {
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
    
    public void show(){
        String query="select * from Uzytkownicy";
        
        try{
            prepareStatement=connection.prepareStatement(query);
            rs=prepareStatement.executeQuery();
            
            while(rs.next())
            {
                usr_data.add(new UserModel(
                        rs.getInt("idUzytkownicy"),
                        rs.getString("Nazwa_uzutkownika"),
                        rs.getString("Haslo")
                ));
                
                usr_table.setItems(usr_data);
            }
            prepareStatement.close();
            rs.close();
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }
    
    //dodawanie usera
    @FXML
    public void AddNewUser() throws SQLException, IOException
	{
            String usr_nazwa = usr_name.getText();           
            String usr_password = usr_pass.getText();
            
           
          if(!usr_nazwa.isEmpty() && !usr_password.isEmpty()){ 
            
            String query="INSERT INTO Uzytkownicy (Nazwa_uzutkownika, Haslo) VALUES(?, ?)";
            prepareStatement=null;
            
            try{
                    prepareStatement=connection.prepareStatement(query);
                    prepareStatement.setString(1, usr_nazwa);
                    prepareStatement.setString(2, usr_password);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                finally{
                    prepareStatement.execute();
                    prepareStatement.close();
                }
                
                usr_name.clear();
		usr_pass.clear();
		
                
                usr_data.clear();
                show();
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
    
    //usuwanie
    public void DeleteUser() throws SQLException, ClassNotFoundException{
    try{
            UserModel user;
            user = (UserModel)usr_table.getSelectionModel().getSelectedItem();
            
            String query="delete from Uzytkownicy where idUzytkownicy = ?";
            prepareStatement=null;
            
            prepareStatement=connection.prepareStatement(query);
            prepareStatement.setInt(1, user.getIdUzytkownicy());
            prepareStatement.executeUpdate();
            
            prepareStatement.close();
            rs.close();
            
            usr_data.clear();
            show();
        }
    catch(Exception e){
    System.err.println(e);
    }
    }
    
    
    //edycja danych
    
     @FXML
    public void editData(ActionEvent ev)throws SQLException, ClassNotFoundException, IOException{
        try{
            if (ev.getSource()==user_edit){
                UserModel user;
                user = (UserModel)usr_table.getSelectionModel().getSelectedItem();
                String query="select * from Uzytkownicy";
                prepareStatement=connection.prepareStatement(query);

                usr_name.setText(user.getNazwa_uzytkownika());
                usr_pass.setText(user.getHaslo());
            

                prepareStatement.close();
                rs.close();
            }
            if (ev.getSource()==user_update){
                String nazwa_add = usr_name.getText();
                String haslo_add = usr_pass.getText();
                
            
                    if(!nazwa_add.isEmpty() && !haslo_add.isEmpty()){
                         UserModel user2;
                         user2 = (UserModel)usr_table.getSelectionModel().getSelectedItem();
                         int ind = user2.getIdUzytkownicy();
                         String s = Integer.toString(ind);
                         String query2="UPDATE Uzytkownicy set Nazwa_uzutkownika=?, Haslo=? WHERE IdUzytkownicy='"+s+"' ";
                         prepareStatement=null;

                             try{
                                 prepareStatement=connection.prepareStatement(query2);
                                 prepareStatement.setString(1, nazwa_add);
                                 prepareStatement.setString(2, haslo_add);
                              
                             }
                             catch(SQLException e)
                             {
                                 System.out.println(e);
                             }
                             finally{
                                 prepareStatement.execute();
                                 prepareStatement.close();
                             }

                             usr_data.clear();
                             show();

                             //info dialog message
                                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/success.fxml"));
                                   Parent root = (Parent) fxmlLoader.load();
                                   Stage stage = new Stage();
                                   stage.setScene(new Scene(root));
                                   stage.show();
                         }
                                 else{
                                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/add_alert.fxml"));
                                   Parent root = (Parent) fxmlLoader.load();
                                   Stage stage = new Stage();
                                   stage.setTitle("Błąd");
                                   stage.setScene(new Scene(root));
                                   stage.show();  
                                      }
        }}
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nu.setCellValueFactory(new PropertyValueFactory<>("Nazwa_uzytkownika"));
        haslo.setCellValueFactory(new PropertyValueFactory<>("Haslo"));
        show();
    }    

    
}
