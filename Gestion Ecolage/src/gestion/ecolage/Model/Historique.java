/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Marsihay
 */
public class Historique {
    private SimpleObjectProperty<Date> Date; 
    private SimpleStringProperty Design;   
    private SimpleDoubleProperty Montant; 
    public Historique(){}
    
    
    public String getDesign() {
        return Design.get();
    }

    public void setDesign(String o) {
        this.Design = new SimpleStringProperty(o);
    }
    
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(Date.get());
	}

	public void setDate(Date day) {
		this.Date=new SimpleObjectProperty<Date>(day);
	}
	
	public SimpleObjectProperty<Date> DateProperty() {
		return Date;
	}
       public double getMontant() {
        return Montant.get();
    }

    public void setMontant(double Ar) {
        this.Montant = new SimpleDoubleProperty(Ar);
    } 
}
