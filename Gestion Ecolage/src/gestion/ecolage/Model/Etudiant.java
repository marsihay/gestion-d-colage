/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Marsihay
 */
public class Etudiant {
    private SimpleIntegerProperty NumMatr;
    private SimpleStringProperty Nom;
    private SimpleStringProperty Prenoms;
    
    public Etudiant(){}
    public Integer getNumMatr() {
        return NumMatr.get();
    }

    public void setNumMatr(int id) {
        this.NumMatr = new SimpleIntegerProperty(id);
    }
    
    public String getNom() {
        return Nom.get();
    }

    public void setNom(String Nom) {
        this.Nom = new SimpleStringProperty(Nom);
    }
    
    public String getPrenoms() {
        return Prenoms.get();
    }

    public void setPrenoms(String Nom) {
        this.Prenoms = new SimpleStringProperty(Nom);
    }
}
