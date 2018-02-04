/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotel_app;

public class GuestsModel {
    private int idGoscie;
    private String imie;
    private String nazwisko;
    private String Nr_tel;
    private String Nr_dowodu;
    
public GuestsModel(int idGoscie, String imie, String nazwisko, String Nr_tel, String Nr_dowodu) {
        this.idGoscie = idGoscie;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.Nr_tel = Nr_tel;
        this.Nr_dowodu = Nr_dowodu;       
    }
  
    public int getidGoscie() {
        return idGoscie;
    }  
    public String getImie() {
        return imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public String getNr_tel() {
        return Nr_tel;
    }
    public String getNr_dowodu() {
        return Nr_dowodu;
    }
    public void setidGoscie(int idGoscie) {
        this.idGoscie = idGoscie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public void setNr_tel(String Nr_kontaktowy) {
        this.Nr_tel = Nr_kontaktowy;
    }
    public void setNr_dowodu(String Nr_dowodu) {
        this.Nr_dowodu = Nr_dowodu;
    }
  }