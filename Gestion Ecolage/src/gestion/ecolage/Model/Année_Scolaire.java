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
public class Année_Scolaire {
    
    private Integer Id_AS;
    private String Libellé;
    public Année_Scolaire(){
    }
    
    public Année_Scolaire(int id,String libelle){
        this.Id_AS=id;
        this.Libellé=libelle;
    }
     public int getId_AS() {
        return Id_AS;
    }

    public void setId_AS(int nb) {
        this.Id_AS = nb;
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
