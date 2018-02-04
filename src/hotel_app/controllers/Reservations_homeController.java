/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app.controllers;

import hotel_app.GuestsModel;
import hotel_app.RoomsModel;
import hotel_app.mysqlconnect;
import hotel_app.reservationModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * FXML Controller class
 *
 * @author Paweł
 */

public class Reservations_homeController implements Initializable {
    Connection connection=mysqlconnect.ConnecrDb();
    
    ObservableList<GuestsModel> data=FXCollections.observableArrayList();
    PreparedStatement preparedStatement=null;
    ResultSet rs=null;
    
    ObservableList<RoomsModel> rom_data=FXCollections.observableArrayList();
    PreparedStatement prepareStatement2=null;
    
@FXML private Pane main_pane;
@FXML private Button close_button;
@FXML private Button aktualne_rezerwacje;
@FXML private Button rezerwuj;
@FXML private Button rabaty_submit;
@FXML private Button edycja_gosci;
@FXML private Button apply_days;
@FXML private Button refresh;
@FXML private Button clear_dis;
@FXML private Label data_lbl;
@FXML private TextField suma;
@FXML private TextField wartosc_rabatu;
@FXML private TextArea rabaty_more;
@FXML private TextField rezerwacja_imieinazwisko;
@FXML private TextField rezerwacja_numerpokoju;
@FXML private TextField numofdays;
@FXML private DatePicker od_kiedy;
@FXML private DatePicker do_kiedy;
@FXML private ComboBox rabaty_combo;

@FXML private TableView<GuestsModel> tabela_gosci;
   @FXML private TableColumn<? , ?> Imie;
   @FXML private TableColumn<? , ?> Nazwisko;
   @FXML private TableColumn<? , ?> Nr_tel;
   @FXML private TableColumn<? , ?> Nr_dowodu;
   
@FXML private TextField nameBox;
@FXML private TextField lastnameBox;
@FXML private TextField contactBox;
@FXML private TextField numberBox;
        
@FXML TableView<RoomsModel> tabela_pokoi;
        @FXML private TableColumn<? , ?> id_pok;
        @FXML private TableColumn<? , ?> typ;
        @FXML private TableColumn<? , ?> numer_pokoju;
        @FXML private TableColumn<? , ?> pietro;
        @FXML private TableColumn<? , ?> ilosc_os;
        @FXML private TableColumn<? , ?> balkon;
        @FXML private TableColumn<? , ?> opis;
        @FXML private TableColumn<? , ?> cena;
        
final ObservableList<String> options = FXCollections.observableArrayList();  
    @FXML
    private void wyswietl_gosci(){
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
                tabela_gosci.setItems(data);
            }
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    @FXML
    private void odswiez(){
        rom_data.clear();
        wyswietl_pokoje();
    }
    
    //wyswietlenie listy w combobox
    @FXML
    public void showDiscount(){
        try {
            String query = "select Rodzaj from Rabaty";
            preparedStatement=connection.prepareStatement(query);
            rs = preparedStatement.executeQuery(query);
            while(rs.next()){
                String napis = rs.getString("Rodzaj");
                options.add(napis);
            }
            rabaty_combo.setItems(options);
            preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DiscountsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showDiscountmore(){
        preparedStatement = null;
            rs = null;   
            String wybrany_rabat = (String) rabaty_combo.getValue();
            String query2="select * from Rabaty where Rodzaj = '"+wybrany_rabat+"'";
                     try {
                         preparedStatement=connection.prepareStatement(query2);   
                         rs=preparedStatement.executeQuery(query2);
                          while(rs.next()){                             
                                rabaty_more.setText(rs.getString("Opis"));
                                wartosc_rabatu.setText(Integer.toString(rs.getInt("Wartosc")));
                            }                         
                         preparedStatement.close();
                         rs.close();
                     } catch(SQLException e)
                        {
                        System.out.println(e);
                        }
    }
    
    @FXML
    private void wyczysc_rabaty(){
        options.clear();
        showDiscount();
        rabaty_more.setText("");
        wartosc_rabatu.setText("0");
    }
    
    
    @FXML
    private void wyswietl_pokoje(){
        String query2="select * from Pokoje where Stan='Wolny'";
        
        try{
            prepareStatement2=connection.prepareStatement(query2);
            rs=prepareStatement2.executeQuery();
            
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
                tabela_pokoi.setItems(rom_data);
            }
            prepareStatement2.close();
            rs.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    //dodawanie gościa
    @FXML
    public void AddNewGuest() throws SQLException, IOException
	{
            String imie_add = nameBox.getText();
            String nazwisko_add = lastnameBox.getText();
            String numer_add = contactBox.getText();
            String dowod_add = numberBox.getText();
         if(!imie_add.isEmpty() && !nazwisko_add.isEmpty() && !numer_add.isEmpty() && !dowod_add.isEmpty()){  
            String query="INSERT INTO Goscie (Imie, Nazwisko, Nr_tel, Nr_dowodu) VALUES(?, ?, ?, ?)";
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
                
                nameBox.clear();
		lastnameBox.clear();
		contactBox.clear();
                numberBox.clear();
                
                data.clear();
                wyswietl_gosci();
                
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
    //nowe okno edycji gosci
    @FXML
    private void edycja_gosci(){
        try {
        Parent root1 = FXMLLoader.load(getClass().getResource("/hotel_app/views/guests_home_edit.fxml"));
        Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
    } catch (IOException ex) {
        Logger.getLogger(Reservations_homeController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    //nowe okno podgladu rezerwacji
    @FXML
    private void show_rezerwacje(){
    try {
        Parent root1 = FXMLLoader.load(getClass().getResource("/hotel_app/views/reservations_quickview.fxml"));
        Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
    } catch (IOException ex) {
        Logger.getLogger(Reservations_homeController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    //pobieranie daty
    @FXML
    private void getdate(){
        Calendar now = Calendar.getInstance();
        data_lbl.setText(Integer.toString(now.get(Calendar.YEAR))+"/"+Integer.toString(now.get(Calendar.MONTH))+1+"/"+Integer.toString(now.get(Calendar.DATE)));
    }
    
     public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }
     
    @FXML
    private void oblicz_dni(){
       long date1 = od_kiedy.getValue().toEpochDay();
       long date2 = do_kiedy.getValue().toEpochDay();  
        if(date2>date1){
        int  days  = (int) Math.abs(date1 - date2); 
        numofdays.setText(Integer.toString(days));
        }
        
                if(date2<date1){
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/date_alert.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Błąd");
                        stage.setScene(new Scene(root));  
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(Reservations_homeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                            if(date1==0 || date2==0){
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/date_alert.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.setTitle("Błąd");
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(Reservations_homeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
    }
    @FXML
    private void zarezerwuj() throws IOException{
         //pobranie daty i konwersja do formatu mysql
         String data_od_kiedy = od_kiedy.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
         String data_do_kiedy = do_kiedy.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
         //pobranie danych z pól oraz ich walidacja
         String l_dni = numofdays.getText();
         String imie_nazwisko = rezerwacja_imieinazwisko.getText();
         String roomnumber = rezerwacja_numerpokoju.getText();
         Double koszt = Double.valueOf(suma.getText());
           if(!data_od_kiedy.isEmpty() && !data_do_kiedy.isEmpty() && !l_dni.isEmpty() && !imie_nazwisko.isEmpty() && !roomnumber.isEmpty() && koszt!=0){
             try {
                 RoomsModel room;
                 reservationModel rez = null;
                 //rezerwacja pokoju
                 String query="INSERT INTO Rezerwacje (Od, Do, Koszt, Pokoj, Gosc) VALUES(?, ?, ?, ?, ?)";
                 preparedStatement=null;
                 try{
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setString(1, data_od_kiedy);
                    preparedStatement.setString(2, data_do_kiedy);
                    preparedStatement.setDouble(3, koszt);
                    preparedStatement.setString(4, roomnumber);
                    preparedStatement.setString(5, imie_nazwisko);
                    preparedStatement.execute();
                    preparedStatement.close();
                    }
                    catch(Exception e)
                    {
                    System.out.println(e);
                    }
                        numofdays.setText("1");
                        rezerwacja_imieinazwisko.clear();
                        rezerwacja_numerpokoju.clear();
                        suma.setText("0");
                        
                    //zmiana statusu zarezerwowanego pokoju
                    preparedStatement=null;
                    String query2="UPDATE Pokoje SET Stan='Zajety' WHERE idPokoje = ?";
                    room = (RoomsModel)tabela_pokoi.getSelectionModel().getSelectedItem();
                    preparedStatement=connection.prepareStatement(query2);
                    preparedStatement.setInt(1, room.getIdPokoje());
                    preparedStatement.execute();
                    preparedStatement.close();
                    
                 //ponowne wyswietlenie wolnych pokoi   
                 rom_data.clear();
                 wyswietl_pokoje();
                 
                          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/success.fxml"));
                          Parent root = (Parent) fxmlLoader.load();
                          Stage stage = new Stage();
                          stage.setScene(new Scene(root));
                          stage.show();
             } catch (SQLException ex) {
                 Logger.getLogger(Reservations_homeController.class.getName()).log(Level.SEVERE, null, ex);
             }
           }
    }
    
    //pobranie danych wybranego goscia
    @FXML
    public void show_guest(){
        try{
            GuestsModel user;
            user = (GuestsModel)tabela_gosci.getSelectionModel().getSelectedItem();
            String query="select Imie, Nazwisko from Goscie where idGoscie = ?";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getidGoscie());
 
            
               rezerwacja_imieinazwisko.setText(user.getImie()+" "+user.getNazwisko());
               
            preparedStatement.close();
 
        }
        catch(SQLException e){
            System.out.println(e);
        }           
    }
    
    //pobranie danych wybranego pokoju
    @FXML
    public void show_price(){
         try{
            RoomsModel room;
            room = (RoomsModel)tabela_pokoi.getSelectionModel().getSelectedItem();
            
            String query="select Numer_pokoju, cena from Pokoje where idPokoje = ?";
            preparedStatement=null;   
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, room.getIdPokoje());

             
            rezerwacja_numerpokoju.setText(Integer.toString(room.getNumer_pokoju()));
            suma.setText(Integer.toString(room.getCena()));
            
            preparedStatement.close();
     
            }  
        catch(Exception e){
            System.err.println(e);
            }
    }
    
    @FXML
    private void przydziel(){
                    //obliczanie ceny z rabatem
                    try{
                    RoomsModel room;
                    room = (RoomsModel)tabela_pokoi.getSelectionModel().getSelectedItem();
                    
                    //wartosc rabatu
                    String wartosc_rabatustr = wartosc_rabatu.getText();
                    int wartosc_rabatuint = Integer.parseInt(wartosc_rabatu.getText());

                    
                    //cena pokoju
                    String rezerwacja_pokoj_cena = Integer.toString(room.getCena());
                    double rezerwacja_pokoj_cenadoub = Double.valueOf(rezerwacja_pokoj_cena.trim());

                    
                    
                    //cena z rabatem
                    String liczba_dnistr = numofdays.getText();
                    int liczbadni = Integer.valueOf(liczba_dnistr);
                    double procent = (wartosc_rabatuint/100.0);
                    double wartosc_rabatu = procent * rezerwacja_pokoj_cenadoub;
                    double cena_z_rabatem = (rezerwacja_pokoj_cenadoub - wartosc_rabatu) * liczbadni;
 
                    
                    //cena z liczba dni bez rabatu
                  
                    if(!liczba_dnistr.isEmpty()){
                    
                            if(wartosc_rabatustr.isEmpty()){
                                double cenabezrabatu = round(rezerwacja_pokoj_cenadoub * liczbadni, 2);
                                suma.setText(Double.toString(cenabezrabatu));
                            }
                            else{
                                    suma.setText(Double.toString(cena_z_rabatem));
                            }
                        }
                    
                    else{
                        try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotel_app/views/date_alert4.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.setTitle("Błąd");
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(Reservations_homeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                    }
                    }
                    catch(NumberFormatException ex){
                        System.err.println(ex);
                    }
    }

    //zamkniecie aplikacji
    @FXML
    private void close(){
         
    try {
        Stage stage = (Stage) close_button.getScene().getWindow();
        Stage stage2 = new Stage();
        stage.close();
        
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/hotel_app/views/Loginview.fxml"));
        Scene scene1 = new Scene(root);
        stage2.setScene(scene1);
        stage2.show();
    } catch (IOException ex) {
        Logger.getLogger(Reservations_homeController.class.getName()).log(Level.SEVERE, null, ex);
    }
        }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Imie.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        Nazwisko.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        Nr_tel.setCellValueFactory(new PropertyValueFactory<>("Nr_tel"));
        Nr_dowodu.setCellValueFactory(new PropertyValueFactory<>("Nr_dowodu"));
        
        typ.setCellValueFactory(new PropertyValueFactory<>("typ"));
        numer_pokoju.setCellValueFactory(new PropertyValueFactory<>("numer_pokoju"));
        pietro.setCellValueFactory(new PropertyValueFactory<>("pietro"));
        ilosc_os.setCellValueFactory(new PropertyValueFactory<>("ilosc_os"));
        balkon.setCellValueFactory(new PropertyValueFactory<>("balkon"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        getdate();
        wyswietl_gosci();
        wyswietl_pokoje();
        od_kiedy.setValue(NOW_LOCAL_DATE());
        showDiscount();
    }    
    
}
