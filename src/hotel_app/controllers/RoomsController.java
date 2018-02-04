/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.RoomsModel;
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
import javafx.scene.control.CheckBox;
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
public class RoomsController implements Initializable {
    Connection connection=mysqlconnect.ConnecrDb();
ObservableList<RoomsModel> rom_data=FXCollections.observableArrayList();
    
    PreparedStatement prepareStatement=null;
    ResultSet rs=null;
    
    @FXML TableView<RoomsModel> rooms_table;
        @FXML private TableColumn<? , ?> id_pok;
        @FXML private TableColumn<? , ?> typ;
        @FXML private TableColumn<? , ?> numer_pokoju;
        @FXML private TableColumn<? , ?> pietro;
        @FXML private TableColumn<? , ?> ilosc_os;
        @FXML private TableColumn<? , ?> balkon;
        @FXML private TableColumn<? , ?> opis;
        @FXML private TableColumn<? , ?> stan;
        @FXML private TableColumn<? , ?> cena;
        
        @FXML private TextField room_type;
        @FXML private TextField room_num;
        @FXML private TextField room_poj;
        @FXML private TextField room_fl;
        @FXML private TextField cena_type;
        @FXML private CheckBox room_bl;
        @FXML private TextField room_more;
        @FXML private Button close_button;
        @FXML private Button room_confirm_update;
        @FXML private Button edit_room;
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typ.setCellValueFactory(new PropertyValueFactory<>("typ"));
        numer_pokoju.setCellValueFactory(new PropertyValueFactory<>("numer_pokoju"));
        pietro.setCellValueFactory(new PropertyValueFactory<>("pietro"));
        ilosc_os.setCellValueFactory(new PropertyValueFactory<>("ilosc_os"));
        balkon.setCellValueFactory(new PropertyValueFactory<>("balkon"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        stan.setCellValueFactory(new PropertyValueFactory<>("stan"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        wyswietl();
    } 
    
    //zamykanie okna
        public void close_action(ActionEvent event) throws IOException{
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
    
     public void wyswietl(){
        String query="select * from Pokoje";
        
        try{
            prepareStatement=connection.prepareStatement(query);
            rs=prepareStatement.executeQuery();
            
            while(rs.next())
            {
                rom_data.add(new RoomsModel(
                        rs.getInt("idPokoje"),
                        rs.getString("typ"),
                        rs.getInt("numer_pokoju"),
                        rs.getString("pietro"),
                        rs.getString("ilosc_os"),
                        rs.getBoolean("balkon"),
                        rs.getString("opis"),
                        rs.getString("stan"),
                        rs.getInt("cena")
                ));
                rooms_table.setItems(rom_data);
            }
            prepareStatement.close();
            rs.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
     
     
     @FXML
    public void AddNewRoom() throws SQLException, IOException
	{
            String typpokoju = room_type.getText();
            String nrpokoju = room_num.getText();
            String pokojpoj = room_poj.getText();
            String pokoj_pietro = room_fl.getText();
            String pokojinfo = room_more.getText();
            String cenazadobe = cena_type.getText();
            
          if(!typpokoju.isEmpty() && !nrpokoju.isEmpty() && !pokojpoj.isEmpty() && !pokoj_pietro.isEmpty() && !pokojinfo.isEmpty() && !cenazadobe.isEmpty()){  
            
             Boolean bal;
            if(room_bl.isSelected()){
                bal = true;
            }
            else
            {
                bal = false;
            }
            
            String query="INSERT INTO Pokoje (Typ, Numer_pokoju, Pietro, Ilosc_os, Balkon, Opis, Stan, cena) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            prepareStatement=null;
            String stan = "Wolny";
            try{
                    prepareStatement=connection.prepareStatement(query);
                    prepareStatement.setString(1, typpokoju);
                    prepareStatement.setString(2, nrpokoju);
                    prepareStatement.setString(3, pokoj_pietro);
                    prepareStatement.setString(4, pokojpoj);
                    prepareStatement.setBoolean(5, bal);
                    prepareStatement.setString(6, pokojinfo);
                    prepareStatement.setString(7, stan);
                    prepareStatement.setString(8, cenazadobe);
                    
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                finally{
                    prepareStatement.execute();
                    prepareStatement.close();
                }
                
                room_type.clear();
		room_num.clear();
		room_poj.clear();
                room_fl.clear();
                room_more.clear();
                cena_type.clear();
                
                rom_data.clear();
                wyswietl();
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
        
    //usuwanie pokoju
    public void DeleteRoom() throws SQLException, ClassNotFoundException{
    try{
            RoomsModel room;
            room = (RoomsModel)rooms_table.getSelectionModel().getSelectedItem();
            
            String query="delete from Pokoje where idPokoje = ?";
            prepareStatement=null;
            
            prepareStatement=connection.prepareStatement(query);
            prepareStatement.setInt(1, room.getIdPokoje());
            prepareStatement.executeUpdate();
            
            prepareStatement.close();
            rs.close();
            
            rom_data.clear();
            wyswietl();
        }
    catch(Exception e){
    System.err.println(e);
    }
    }
    
    
    
    //edycja pokoju
    @FXML
    public void editRoom(ActionEvent ev)throws SQLException, ClassNotFoundException, IOException{
        try{
            if (ev.getSource()==edit_room){
                RoomsModel room;
                room = (RoomsModel)rooms_table.getSelectionModel().getSelectedItem();
                String query="select * from Pokoje";
                prepareStatement=connection.prepareStatement(query);
                 
                room_type.setText(room.getTyp());
                room_num.setText(Integer.toString(room.getNumer_pokoju()));
                room_fl.setText(room.getPietro());
                room_poj.setText(room.getIlosc_os());
                cena_type.setText(Integer.toString(room.getCena()));
                if(room.isBalkon()){
                room_bl.setSelected(true);
                }
                else{
                    room_bl.setSelected(false);
                }
                room_more.setText(room.getOpis());


                prepareStatement.close();
                rs.close();
            }
            if (ev.getSource()==room_confirm_update){
                String rt_add = room_type.getText();
                String rn_add = room_num.getText();
                String rf_add = room_fl.getText();
                String rp_add = room_poj.getText();
                Boolean rb_add = room_bl.isSelected();
                String rm_add = room_more.getText();
                String rc = cena_type.getText();
            
                    if(!rt_add.isEmpty() && !rn_add.isEmpty() && !rf_add.isEmpty() && !rp_add.isEmpty() && !rm_add.isEmpty() && !rc.isEmpty()){
                         RoomsModel room2;
                         room2 = (RoomsModel)rooms_table.getSelectionModel().getSelectedItem();
                         int ind = room2.getIdPokoje();
                         String s = Integer.toString(ind);
                         String query2="UPDATE Pokoje set Typ=?, Numer_pokoju=?, Pietro=?, Ilosc_os=?, Balkon=?, Opis=?, cena=? WHERE IdPokoje='"+s+"' ";
                         prepareStatement=null;

                             try{
                                 prepareStatement=connection.prepareStatement(query2);
                                 prepareStatement.setString(1, rt_add);
                                 prepareStatement.setString(2, rn_add);
                                 prepareStatement.setString(3, rf_add);
                                 prepareStatement.setString(4, rp_add);
                                 prepareStatement.setBoolean(5, rb_add);
                                 prepareStatement.setString(6, rm_add);
                                 prepareStatement.setString(7, rc);
                             }
                             catch(Exception e)
                             {
                                 System.out.println(e);
                             }
                             finally{
                                 prepareStatement.execute();
                                 prepareStatement.close();
                             }

                             rom_data.clear();
                             wyswietl();

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
    //czyszczenie pol
    @FXML
    public void czysc_pola(ActionEvent event) throws IOException {
        
                room_type.setText("");
                room_num.setText("");
                room_fl.setText("");
                room_poj.setText("");
                room_bl.setSelected(false);
                room_more.setText("");
                cena_type.setText("");
    
    }
}