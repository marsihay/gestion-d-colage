/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Marsihay
 */
public class PaimentsTable {
    private SimpleIntegerProperty Num_Matr;
    private SimpleStringProperty Nom_Prenoms;
    private SimpleStringProperty Paye;
    private SimpleDoubleProperty Montant;
    
    public PaimentsTable(){
    }
      
    public int getNum_Matr() {
        return Num_Matr.get();
    }

    public void setNum_Matr(int nb) {
        this.Num_Matr = new SimpleIntegerProperty(nb);
    }
    
    public String getNom_Prenoms() {
        return Nom_Prenoms.get();
    }
    
     public void setNom_Prenoms(String nom) {
        this.Nom_Prenoms = new SimpleStringProperty(nom);
    }
     
      public String getPaye() {
        return Paye.get();
    }
    
     public void setPaye(String s) {
        this.Paye = new SimpleStringProperty(s);
    }
     
     public double getMontant() {
        return Montant.get();
    }

    public void setMontant(double Ar) {
        this.Montant = new SimpleDoubleProperty(Ar);
    }
}
