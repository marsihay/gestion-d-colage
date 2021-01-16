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
public class SearchSuggest {
    
    
    private Integer Id_Matricule;
    private String Libellé;
    public SearchSuggest(){
    }
     public int getId_Matricule() {
        return Id_Matricule;
    }

    public void setId_Matricule(int nb) {
        this.Id_Matricule = nb;
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
