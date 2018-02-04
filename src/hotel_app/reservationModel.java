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
public class reservationModel {
     public int getIdRezerwacje() {
        return idRezerwacje;
    }

     public String getGosc() {
        return Gosc;
    }

    public String getOd() {
        return Od;
    }

    public String getDo() {
        return Do;
    }

    public Double getKoszt() {
        return Koszt;
    }

    public void setOd_kiedy(String Od) {
        this.Od = Od;
    }

    public void setDo_kiedy(String Do) {
        this.Do = Do;
    }

    public void setKoszt(Double Koszt) {
        this.Koszt = Koszt;
    }

    public String getPokoj() {
        return Pokoj;
    }

    public void setPokoj(String Pokoj) {
        this.Pokoj = Pokoj;
    }
    
    public void setGosc(String Gosc) {
        this.Gosc = Gosc;
    }

    public void setIdRezerwacje(int idRezerwacje) {
        this.idRezerwacje = idRezerwacje;
    }

    public reservationModel(int idRezerwacje, String Gosc, String Od, String Do, String Pokoj, Double Koszt) {
        this.idRezerwacje = idRezerwacje;
        this.Gosc = Gosc;
        this.Od = Od;
        this.Do = Do;
        this.Pokoj = Pokoj;
        this.Koszt = Koszt;
        
    }
    
    private int idRezerwacje;
    private String Gosc;
    private String Od;
    private String Do;
    private Double Koszt;
    private String Pokoj;
    
}
