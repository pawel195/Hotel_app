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
public class RoomsModel {


    private int idPokoje;
    private String typ;
    private int numer_pokoju;
    private String pietro;
    private String ilosc_os;
    private boolean balkon;
    private String opis;
    private String stan;
    private int cena;
    
     public RoomsModel(int idPokoje, String typ, int numer_pokoju, String pietro, String ilosc_os, boolean balkon, String opis, String stan, int cena) {
        this.idPokoje = idPokoje;
        this.typ = typ;
        this.numer_pokoju = numer_pokoju;
        this.pietro = pietro;
        this.ilosc_os = ilosc_os;
        this.balkon = balkon;
        this.opis = opis;
        this.stan = stan;
        this.cena = cena;
    }

    public int getIdPokoje() {
        return idPokoje;
    }

    public String getTyp() {
        return typ;
    }

    public int getNumer_pokoju() {
        return numer_pokoju;
    }

    public String getPietro() {
        return pietro;
    }

    public String getIlosc_os() {
        return ilosc_os;
    }

    public boolean isBalkon() {
        return balkon;
    }

    public String getOpis() {
        return opis;
    }
    public String getStan() {
        return stan;
    }
    
    public int getCena() {
        return cena;
    }

   
    public void setIdPokoje(int idPokoje) {
        this.idPokoje = idPokoje;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setNumer_pokoju(int numer_pokoju) {
        this.numer_pokoju = numer_pokoju;
    }

    public void setPietro(String pietro) {
        this.pietro = pietro;
    }

    public void setIlosc_os(String ilosc_os) {
        this.ilosc_os = ilosc_os;
    }

    public void setBalkon(boolean balkon) {
        this.balkon = balkon;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    public void setStan(String stan) {
        this.stan = stan;
    }
    
    public void setCena(int cena) {
        this.cena = cena;
    }
       
}
