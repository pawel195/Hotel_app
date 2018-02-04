/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app;

/**
 *
 * @author Paweł
 */
public class UserModel {
    
    private int idUzytkownicy;
    private String Nazwa_uzytkownika;
    private String Haslo;
    
    //konstruktor
    public UserModel(int idUzytkownicy, String Nazwa_uzytkownika, String Haslo) {
        this.idUzytkownicy = idUzytkownicy;
        this.Nazwa_uzytkownika = Nazwa_uzytkownika;
        this.Haslo = Haslo;
        }

    //get
    public int getIdUzytkownicy() {
        return idUzytkownicy;
    }

    public String getNazwa_uzytkownika() {
        return Nazwa_uzytkownika;
    }

    public String getHaslo() {
        return Haslo;
    }

    //set
    public void setIdUzytkownicy(int idUzytkownicy) {
        this.idUzytkownicy = idUzytkownicy;
    }

    public void setNazwa_uzytkownika(String Nazwa_uzytkownika) {
        this.Nazwa_uzytkownika = Nazwa_uzytkownika;
    }

    public void setHaslo(String Haslo) {
        this.Haslo = Haslo;
    } 
}
