/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Model;

/**
 *
 * @author Marsihay
 */
public class Mois {
    
    private Integer Id_Mois;
    private String Libellé;
    public Mois(){
    }
    
    public Mois(int id,String libelle){
        this.Id_Mois=id;
        this.Libellé=libelle;
    }
     public int getId_Mois() {
        return Id_Mois;
    }

    public void setId_Mois(int nb) {
        this.Id_Mois = nb;
    }
    
    public String getLibellé() {
        return Libellé;
    }

    public void setLibellé(String categ) {
        this.Libellé = categ;
    }
    
    public String toString()  {
        return this.Libellé;
    }
}
