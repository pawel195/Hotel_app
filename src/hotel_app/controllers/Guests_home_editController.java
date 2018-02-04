/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.GuestsModel;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paweł
 */
public class Guests_home_editController implements Initializable {
Connection connection=mysqlconnect.ConnecrDb();
    ObservableList<GuestsModel> data=FXCollections.observableArrayList();
    
    PreparedStatement preparedStatement=null;
    ResultSet rs=null;
            
    @FXML Button confirm_button;
    @FXML Button confirm_button1;
    @FXML Button edit_btn;
    @FXML Button delete_btn;
    
    @FXML TableView<GuestsModel> tableview;
        @FXML private TableColumn<? , ?> Imie;
        @FXML private TableColumn<? , ?> Nazwisko;
        @FXML private TableColumn<? , ?> Nr_tel;
        @FXML private TableColumn<? , ?> Nr_dowodu;
        
        @FXML private TextField nameBox;
        @FXML private TextField lastnameBox;
        @FXML private TextField contactBox;
        @FXML private TextField numberBox;
    @FXML
    private Pane table;
    
    
    
    
//wyswietlenie listy  
    public void loadDatabaseData(){
         
        String query="select * from Goscie";
        
        try{
            preparedStatement=connection.prepareStatement(query);
            rs=preparedStatement.executeQuery();
            
            while(rs.next())
            {
                data.add(new GuestsModel(
                        rs.getInt("idGoscie"),
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getString("Nr_tel"),
                        rs.getString("Nr_dowodu")    
                ));
                tableview.setItems(data);
            }
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    
    
    
    
    
    //usuwanie goscia
    @FXML
    public void showonClick(){
        try{
            GuestsModel user;
            user = (GuestsModel)tableview.getSelectionModel().getSelectedItem();
            String query="select * from Goscie";
            preparedStatement=connection.prepareStatement(query);
            
            nameBox.setEditable(false);
            lastnameBox.setEditable(false);
            contactBox.setEditable(false);
            numberBox.setEditable(false);
            
            nameBox.setText(user.getImie());
            lastnameBox.setText(user.getNazwisko());
            contactBox.setText(user.getNr_tel());
            numberBox.setText(user.getNr_dowodu());
            
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    @FXML
    public void DeleteGuest() throws SQLException, ClassNotFoundException{
	try{
            GuestsModel user;
            user = (GuestsModel)tableview.getSelectionModel().getSelectedItem();
            String query="delete from Goscie where idGoscie = ?";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getidGoscie());
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
            rs.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
         nameBox.clear();
	lastnameBox.clear();
	contactBox.clear();
        numberBox.clear();
        data.clear();
        loadDatabaseData();
        }
    
    
    
    
    
    //edycja gościa(włączenie edycji pól)
    @FXML
    public void EditGuest(){   
            nameBox.setEditable(true);
            lastnameBox.setEditable(true);
            contactBox.setEditable(true);
            numberBox.setEditable(true);
    }
    
    //edycja gościa(zatwierdzenie)
    @FXML
    public void EditGuest_confirm() throws SQLException, ClassNotFoundException, IOException{
            String imie_add = nameBox.getText();
            String nazwisko_add = lastnameBox.getText();
            String numer_add = contactBox.getText();
            String dowod_add = numberBox.getText();
            
           if(!imie_add.isEmpty() && !nazwisko_add.isEmpty() && !numer_add.isEmpty() && !dowod_add.isEmpty()){
                GuestsModel user;
                user = (GuestsModel)tableview.getSelectionModel().getSelectedItem();
                int ind = user.getidGoscie();
                String s = Integer.toString(ind);
                String query="UPDATE Goscie set Imie=?, Nazwisko=?, Nr_tel=?, Nr_dowodu=? WHERE idGoscie='"+s+"' ";
                preparedStatement=null;
            
                    try{
                        preparedStatement=connection.prepareStatement(query);
                        preparedStatement.setString(1, imie_add);
                        preparedStatement.setString(2, nazwisko_add);
                        preparedStatement.setString(3, numer_add);
                        preparedStatement.setString(4, dowod_add);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                    finally{
                        preparedStatement.execute();
                        preparedStatement.close();
                    }


                    data.clear();
                    loadDatabaseData();
                    
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
                }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Imie.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        Nazwisko.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        Nr_tel.setCellValueFactory(new PropertyValueFactory<>("Nr_tel"));
        Nr_dowodu.setCellValueFactory(new PropertyValueFactory<>("Nr_dowodu"));
        loadDatabaseData();
    }    
    
}
