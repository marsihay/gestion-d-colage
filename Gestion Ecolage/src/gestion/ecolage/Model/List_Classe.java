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
public class List_Classe {
    
    private SimpleIntegerProperty NumE;
    private SimpleIntegerProperty NumMatrC;
    private SimpleStringProperty NomC;
    private SimpleStringProperty PrenomsC;
    
    public List_Classe(){}
    
     public Integer getNumE() {
        return NumE.get();
    }

    public void setNumE(int id) {
        this.NumE = new SimpleIntegerProperty(id);
    }
    
    public Integer getNumMatrC() {
        return NumMatrC.get();
    }

    public void setNumMatrC(int id) {
        this.NumMatrC = new SimpleIntegerProperty(id);
    }
    
    public String getNomC() {
        return NomC.get();
    }

    public void setNomC(String Nom) {
        this.NomC = new SimpleStringProperty(Nom);
    }
    
    public String getPrenomsC() {
        return PrenomsC.get();
    }

    public void setPrenomsC(String Nom) {
        this.PrenomsC = new SimpleStringProperty(Nom);
    }
}


