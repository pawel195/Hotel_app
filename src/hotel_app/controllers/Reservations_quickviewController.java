/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.RoomsModel;
import hotel_app.mysqlconnect;
import hotel_app.reservationModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Paweł
 */
public class Reservations_quickviewController implements Initializable {

    @FXML
    private TableView<reservationModel> resq_tableview;
    @FXML 
    private TableView<RoomsModel> tabela_pokoi;
    
    @FXML
    private TableColumn<?, ?> t_gosc;
    @FXML
    private TableColumn<?, ?> t_od;
    @FXML
    private TableColumn<?, ?> t_do;
    @FXML
    private TableColumn<?, ?> t_pokoj;
    @FXML
    private TableColumn<?, ?> t_suma;
    @FXML 
    private TextField rezerwacja_numerpokoju;
    
    Connection connection=mysqlconnect.ConnecrDb();
    ObservableList<reservationModel> dataq=FXCollections.observableArrayList();
    PreparedStatement preparedStatement=null;
    PreparedStatement preparedStatement2=null;
    PreparedStatement preparedStatement3=null;
    ResultSet rs=null;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        t_gosc.setCellValueFactory(new PropertyValueFactory<>("Gosc"));
        t_od.setCellValueFactory(new PropertyValueFactory<>("Od"));
        t_do.setCellValueFactory(new PropertyValueFactory<>("Do"));
        t_pokoj.setCellValueFactory(new PropertyValueFactory<>("Pokoj"));
        t_suma.setCellValueFactory(new PropertyValueFactory<>("Koszt"));
        wyswietl();
    }

@FXML
    private void wyswietl(){
         String query="select * from Rezerwacje";
        
        try{
            preparedStatement=connection.prepareStatement(query);
            rs=preparedStatement.executeQuery();
            
            while(rs.next())
            {
                dataq.add(new reservationModel(
                        rs.getInt("idRezerwacje"),
                        rs.getString("Gosc"),
                        rs.getString("Od"),
                        rs.getString("Do"),       
                        rs.getString("Pokoj"),
                        rs.getDouble("Koszt")
                ));
                resq_tableview.setItems(dataq);
            }
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }

    @FXML
    public void Deleteres() throws SQLException, ClassNotFoundException{
        reservationModel rez;
        rez = (reservationModel)resq_tableview.getSelectionModel().getSelectedItem();
        String idr = Integer.toString(rez.getIdRezerwacje());
        String pokoj = null;
        String query="delete from Rezerwacje where idRezerwacje = ?";
        String query3="select Pokoj from Rezerwacje where idRezerwacje = '"+idr+"'";
        String query2="update Pokoje SET Stan='Wolny' where Numer_pokoju = ?";
	try{
                 
            preparedStatement2=connection.prepareStatement(query3);
            rs=preparedStatement2.executeQuery(query3);
                while(rs.next()){
                    pokoj=rs.getString("Pokoj");
                    preparedStatement3=connection.prepareStatement(query2);
                }
            preparedStatement2.close();
            
            preparedStatement3.setString(1, pokoj); 
            preparedStatement3.execute();         
            preparedStatement3.close();
            
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, rez.getIdRezerwacje());
            preparedStatement.executeUpdate();     
            preparedStatement.close();
 
            rs.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
            dataq.clear();
            wyswietl();
        }    
    
}
