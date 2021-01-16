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
public class Catégorie {
    
    private Integer Id;
    private String Libellé;
    public Catégorie(){
    }
    
    public Catégorie(int id,String libelle){
        this.Id=id;
        this.Libellé=libelle;
    }
     public int getId() {
        return Id;
    }

    public void setId(int nb) {
        this.Id = nb;
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
