/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.EmployeesModel;
import hotel_app.mysqlconnect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Paweł
 */
public class EmployeesController implements Initializable {

    Connection connection=mysqlconnect.ConnecrDb();
    ObservableList<EmployeesModel> emp_data=FXCollections.observableArrayList();
    
    PreparedStatement prepareStatement=null;
    ResultSet rs=null;
    
    @FXML TableView<EmployeesModel> employee_table;
        @FXML private TableColumn<? , ?> id;
        @FXML private TableColumn<? , ?> imie;
        @FXML private TableColumn<? , ?> nazwisko;
        @FXML private TableColumn<? , ?> data_ur;
        @FXML private TableColumn<? , ?> adres;
        @FXML private TableColumn<? , ?> nr_tel;
        @FXML private TableColumn<? , ?> stanowisko;
        
        @FXML private TextField imie_prac;
        @FXML private TextField naz_prac;
        @FXML private TextField data_prac;
        @FXML private TextField adres_prac;
        @FXML private TextField num_prac;
        @FXML private TextField stanow_prac;
        @FXML private Button employe_edit;
        @FXML private Button employe_update;
        @FXML private Button close_button;
        @FXML private Button clear_btn;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        data_ur.setCellValueFactory(new PropertyValueFactory<>("data_ur"));
        adres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        nr_tel.setCellValueFactory(new PropertyValueFactory<>("nr_tel"));
        stanowisko.setCellValueFactory(new PropertyValueFactory<>("stanowisko"));
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
     //załaduj dane
 
    public void wyswietl(){
        String query="select * from Personel";
        
        try{
            prepareStatement=connection.prepareStatement(query);
            rs=prepareStatement.executeQuery();
            
            while(rs.next())
            {
                emp_data.add(new EmployeesModel(
                        rs.getInt("idPersonel"),
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getDate("data_urodzenia"),
                        rs.getString("adres"),
                        rs.getString("nr_tel"),
                        rs.getString("stanowisko")
                ));
                employee_table.setItems(emp_data);
            }
            prepareStatement.close();
            rs.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    //dodawanie nowgo pracownika do bazy danych
    @FXML
    public void AddNewEmployee() throws SQLException, IOException
	{
            String emp_name = imie_prac.getText();
            String emp_lastname = naz_prac.getText();
            String emp_date = data_prac.getText();
            String emp_adres = adres_prac.getText();
            String emp_num = num_prac.getText();
            String emp_stan = stanow_prac.getText();
           
          if(!emp_name.isEmpty() && !emp_lastname.isEmpty() && !emp_date.isEmpty() && !emp_adres.isEmpty() && !emp_num.isEmpty() && !emp_stan.isEmpty()){ 
            
            String query="INSERT INTO Personel (Imie, Nazwisko, Data_urodzenia, Adres, Nr_tel, Stanowisko) VALUES(?, ?, ?, ?, ?, ?)";
            prepareStatement=null;
            
            try{
                    prepareStatement=connection.prepareStatement(query);
                    prepareStatement.setString(1, emp_name);
                    prepareStatement.setString(2, emp_lastname);
                    prepareStatement.setString(3, emp_date);
                    prepareStatement.setString(4, emp_adres);
                    prepareStatement.setString(5, emp_num);
                    prepareStatement.setString(6, emp_stan);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                finally{
                    prepareStatement.execute();
                    prepareStatement.close();
                }
                
                imie_prac.clear();
		naz_prac.clear();
		data_prac.clear();
                adres_prac.clear();
                num_prac.clear();
                stanow_prac.clear();
                
                emp_data.clear();
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
    //usuwanie pracownika
    public void DeleteEmployee() throws SQLException, ClassNotFoundException{
    try{
            EmployeesModel employee;
            employee = (EmployeesModel)employee_table.getSelectionModel().getSelectedItem();
            
            String query="delete from Personel where idPersonel = ?";
            prepareStatement=null;
            
            prepareStatement=connection.prepareStatement(query);
            prepareStatement.setInt(1, employee.getIdPersonel());
            prepareStatement.executeUpdate();
            
            prepareStatement.close();
            rs.close();
            
            emp_data.clear();
            wyswietl();
        }
    catch(Exception e){
    System.err.println(e);
    }
    }
    
    //edycja danych
     @FXML
    public void editData(ActionEvent ev)throws SQLException, ClassNotFoundException, IOException{
        try{
            if (ev.getSource()==employe_edit){
                EmployeesModel employee;
                employee = (EmployeesModel)employee_table.getSelectionModel().getSelectedItem();
                String query="select * from Personel";
                prepareStatement=connection.prepareStatement(query);

                imie_prac.setText(employee.getImie());
                naz_prac.setText(employee.getNazwisko());
                data_prac.setText(employee.getData_urodzenia().toString());
                adres_prac.setText(employee.getAdres());
                num_prac.setText(employee.getNr_tel());
                stanow_prac.setText(employee.getStanowisko());

                prepareStatement.close();
                rs.close();
            }
            if (ev.getSource()==employe_update){
                String imie_add = imie_prac.getText();
                String nazwisko_add = naz_prac.getText();
                String data_add = data_prac.getText();
                String adres_add = adres_prac.getText();
                String num_add = num_prac.getText();
                String stanow_add = stanow_prac.getText();
            
                    if(!imie_add.isEmpty() && !nazwisko_add.isEmpty() && !data_add.isEmpty() && !adres_add.isEmpty() && !num_add.isEmpty() && !stanow_add.isEmpty()){
                         EmployeesModel employee2;
                         employee2 = (EmployeesModel)employee_table.getSelectionModel().getSelectedItem();
                         int ind = employee2.getIdPersonel();
                         String s = Integer.toString(ind);
                         String query2="UPDATE Personel set Imie=?, Nazwisko=?, Data_urodzenia=?, Adres=?, Nr_tel=?, Stanowisko=? WHERE IdPersonel='"+s+"' ";
                         prepareStatement=null;

                             try{
                                 prepareStatement=connection.prepareStatement(query2);
                                 prepareStatement.setString(1, imie_add);
                                 prepareStatement.setString(2, nazwisko_add);
                                 prepareStatement.setString(3, data_add);
                                 prepareStatement.setString(4, adres_add);
                                 prepareStatement.setString(5, num_add);
                                 prepareStatement.setString(6, stanow_add);
                             }
                             catch(Exception e)
                             {
                                 System.out.println(e);
                             }
                             finally{
                                 prepareStatement.execute();
                                 prepareStatement.close();
                             }

                             emp_data.clear();
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
    public void czysc(ActionEvent event) throws IOException {
        if(event.getSource()==clear_btn){
                imie_prac.setText("");
                naz_prac.setText("");
                data_prac.setText("");
                adres_prac.setText("");
                num_prac.setText("");
                stanow_prac.setText("");
    }
    }
}
    

