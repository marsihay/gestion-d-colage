/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Marsihay
 */
public class Connexion {
    String urlPilote="com.mysql.jdbc.Driver";
    String urlBaseDonnees="jdbc:Mysql://localhost:3306/db_gs";//Chemin de connexion a la base
    Connection con;
        public Connexion(){
    try{
    Class.forName(urlPilote);
    System.out.println("Chargement du pilote de réussi");
}catch(ClassNotFoundException ex){
    System.out.println(ex);
} 
    try{
        con=DriverManager.getConnection(urlBaseDonnees,"root","");
        System.out.println("Connexion à la base de données réussi");
    
}catch(SQLException ex){
    System.out.println("Exception :" +ex);
}
}
        Connection ObtenirConnexion(){
        return con;
    }

}
