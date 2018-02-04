/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app;

import java.sql.Date;


/**
 *
 * @author Paweł
 */
public class EmployeesModel {
    
    private int idPersonel;
    private String imie;
    private String nazwisko;
    private Date data_urodzenia;
    private String adres;
    private String nr_tel;
    private String stanowisko;
    
    //KONSTRUKTOR
    
     public EmployeesModel(int idPersonel, String imie, String nazwisko, Date data_urodzenia, String adres, String nr_tel, String stanowisko) {
        this.idPersonel = idPersonel;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data_urodzenia = data_urodzenia;
        this.adres = adres;
        this.nr_tel = nr_tel;
        this.stanowisko = stanowisko;
    }

     //GET
     
    public int getIdPersonel() {
        return idPersonel;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public Date getData_urodzenia() {
        return data_urodzenia;
    }

    public String getAdres() {
        return adres;
    }

    public String getNr_tel() {
        return nr_tel;
    }

    public String getStanowisko() {
        return stanowisko;
    }

   
   //SET 
    
    public void setIdPersonel(int idPersonel) {
        this.idPersonel = idPersonel;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setData_urodzenia(Date data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setNr_tel(String nr_tel) {
        this.nr_tel = nr_tel;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }
    
}
