/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Marsihay
 */
public class Inscrit_Table { 
    private SimpleIntegerProperty NumMatr;
    private SimpleStringProperty Nom;
    private SimpleStringProperty Prenoms;
    private SimpleStringProperty OBS;
    private SimpleObjectProperty<Date> Date_Insc; 
    public Inscrit_Table(){}
    
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
    public String getOBS() {
        return OBS.get();
    }

    public void setOBS(String Nom) {
        this.OBS = new SimpleStringProperty(Nom);
    }
    public String getDate_Insc() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(Date_Insc.get());
	}

	public void setDate_Insc(Date day) {
		this.Date_Insc=new SimpleObjectProperty<Date>(day);
	}
	
	public SimpleObjectProperty<Date> Date_EmpruntProperty() {
		return Date_Insc;
	}
      
}
