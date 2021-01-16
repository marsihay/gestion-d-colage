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
public class Classe {
    
    private Integer Id_Classe;
    private String NomClasse;
    public Classe(){
    }
    public Classe(int id,String libelle){
        this.Id_Classe=id;
        this.NomClasse=libelle;
    }
    
     public int getId_Classe() {
        return Id_Classe;
    }

    public void setId_Classe(int nb) {
        this.Id_Classe = nb;
    }
    
    public String getNomClasse() {
        return NomClasse;
    }

    public void setNomClasse(String categ) {
        this.NomClasse = categ;
    }
    
    public String toString()  {
        return this.NomClasse;
    }
    
}
