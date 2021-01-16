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
public class Droit {
      private Integer Id_Droit;
    private String Libellé;
    private Double Montant;
    public Droit(){
    }
    
    public Droit(int id,String libelle, double F){
        this.Id_Droit=id;
        this.Libellé=libelle;
        this.Montant=F;
    }
     public int getId_Droit() {
        return Id_Droit;
    }

    public void setId_Droit(int nb) {
        this.Id_Droit = nb;
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
     public double getMontant() {
        return Montant;
    }

    public void setMontant(double nb) {
        this.Montant = nb;
    }
}
