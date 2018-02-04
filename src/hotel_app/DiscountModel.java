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
public class DiscountModel {

    private int idRabaty;
    private String Opis;
    private int Wartosc;
    private String Rodzaj;

    public int getIdRabaty() {
        return idRabaty;
    }

    public String getOpis() {
        return Opis;
    }

    public int getWartosc() {
        return Wartosc;
    }

    public String getRodzaj() {
        return Rodzaj;
    }

    public void setIdRabaty(int idRabaty) {
        this.idRabaty = idRabaty;
    }

    public void setOpis(String Opis) {
        this.Opis = Opis;
    }

    public void setWartosc(int Wartosc) {
        this.Wartosc = Wartosc;
    }

    public void setRodzaj(String Rodzaj) {
        this.Rodzaj = Rodzaj;
    }
    
    public DiscountModel(int idRabaty, String Opis, int Wartosc, String Rodzaj) {
        this.idRabaty = idRabaty;
        this.Opis = Opis;
        this.Wartosc = Wartosc;
        this.Rodzaj = Rodzaj;
    }
    
    
    
}
