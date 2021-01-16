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
public class Niveau {
    private Integer Id_N;
    private String Libellé;
    private Double Frais;
    public Niveau(){
    }
    
    public Niveau(int id,String libelle){
        this.Id_N=id;
        this.Libellé=libelle;
    }
     public int getId_N() {
        return Id_N;
    }

    public void setId_N(int nb) {
        this.Id_N = nb;
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
     public double getFrais() {
        return Frais;
    }

    public void setFrais(double nb) {
        this.Frais = nb;
    }
}
