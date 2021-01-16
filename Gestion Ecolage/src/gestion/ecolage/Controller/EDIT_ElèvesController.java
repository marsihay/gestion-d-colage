/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import gestion.ecolage.Model.Année_Scolaire;
import gestion.ecolage.Model.Classe;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marsihay
 */
public class EDIT_ElèvesController implements Initializable {

    double x, y;
      Statement stmt;
    Connexion maConnexion=new Connexion();
    static  int Index=HomeController.TableListeSelectedIndex; 
    static  int Id_AS; 
    @FXML
    private JFXDialogLayout ContentDialog;
    @FXML
    private StackPane loginStackPane;
    @FXML
    private Pane signUpPane;
    @FXML
    private JFXButton canceladd;
    @FXML
    private Label notification;
    @FXML
    private JFXTextField Num_Matr;
    @FXML
    private JFXDatePicker Date;
    @FXML
    private ComboBox<Année_Scolaire> AS_Combo;
    @FXML
    private JFXTextField Nom;
    @FXML
    private JFXTextField Prenom;
    @FXML
    private JFXTextField Num;
    @FXML
    private ComboBox<Classe> Classe;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton InscrireBTN;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         loadEmpruntComboBox();
         loadClasseComboBox();
         try{
             Id_AS=HomeController.IdentifiantAS;
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet resultat= stmt1.executeQuery("SELECT etudiant.Nom,etudiant.Prenoms,appartenir.id_Classe,appartenir.Numero,inscription.Date_Insc "
                        + "FROM etudiant,appartenir,inscription WHERE etudiant.Matricule=appartenir.Matricule and appartenir.Matricule=etudiant.Matricule and "
                        + "etudiant.Matricule="+Index+" and appartenir.Matricule="+Index+" and "
                        + "inscription.Matricule="+Index+" and inscription.id_AS="+Id_AS+" and appartenir.id_AS="+Id_AS+";");
            if(resultat.next()){             
                Num_Matr.setText(""+Index);
        Nom.setText(resultat.getString("Nom"));
        Prenom.setText(resultat.getString("Prenoms"));                     
        Num.setText(resultat.getString("Numero"));                  
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");                
        LocalDate date=ConvertStringtoLocalDate(sdf.format(resultat.getDate("Date_Insc")));
        Date.setValue(date); 
                  AddASComboValue();
        int id_Classe=resultat.getInt("id_classe");
                 AddClasseComboValue(id_Classe);
            }
        }catch(Exception e){
        System.out.println("MIsy diso @Voloany------>"+e);
            
        }
    }    
 public void AddASComboValue() { 
     try{
        java.sql.Statement stmt=maConnexion.ObtenirConnexion().createStatement();
               java.sql.ResultSet RS= stmt.executeQuery(" SELECT  * from annee_scolaire WHERE  id_AS="+Id_AS+";");
            if(RS.next()){  
            AS_Combo.setValue(new Année_Scolaire(RS.getInt("id_AS"),RS.getString("design")));
            }
     }catch(SQLException e){
        System.out.println("MIsy diso AS------>"+e);}
 }
 public void AddClasseComboValue(int id_Classe) { 
     try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
        java.sql.ResultSet QueryRS= stmt1.executeQuery(" SELECT  * from classe WHERE  id_classe="+id_Classe+";");
            if(QueryRS.next()){                
            Classe.setValue(new Classe(QueryRS.getInt("id_classe"),QueryRS.getString("Label_C")));
            }  
     }catch(SQLException e){
        System.out.println("MIsy diso Classe------>"+e);}
 }
    public static LocalDate ConvertStringtoLocalDate(String date) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate= LocalDate.parse(date, myFormatObj);
        return localDate;
    } 
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource()== close){
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        } else if(event.getSource()== minimize){
             Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
        }else if(event.getSource()== canceladd){
             Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        }
    }

       @FXML
    private void Dragged(MouseEvent event) {
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    private void Pressed(MouseEvent event) {
        x= event.getSceneX();
        y= event.getSceneY();
    }

    void messageDialog(){
        /*JFXDialogLayout content= new JFXDialogLayout();*/
        ContentDialog.setHeading(new Text("Notification"));
        ContentDialog.setBody(new Text("Votre modification a réussi."));
        JFXDialog dialog=new JFXDialog(loginStackPane, ContentDialog, JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("OK");
        button.setStyle("-fx-background-color :  #0099ff; -fx-text-fill:  white;-fx-cursor: hand;");
        button.setOnAction(new EventHandler<ActionEvent>(){
          @Override
        public void handle(ActionEvent event) {  
            dialog.close();
            Node node=(Node) event.getSource();
            Stage stage=(Stage)node.getScene().getWindow();
            stage.close();
           
            
        }        
        });
        ContentDialog.setActions(button);
        dialog.show();
    }
    @FXML
    private void Enregistrer(MouseEvent event) {
        if (isInputValid()){          
            String nom=Nom.getText();
            String PreNom=Prenom.getText();
            String Numero=Num.getText();
            String NumeroMatr=Num_Matr.getText();
        Année_Scolaire AS=AS_Combo.getValue(); 
        int id_AS=AS.getId_AS();
        Classe cl=Classe.getValue(); 
        int id_Classe=cl.getId_Classe();
        LocalDate date_Insc=Date.getValue(); 
         try{        
      String query = "UPDATE `etudiant` SET `Nom`='"+nom+"',`Prenoms`='"+PreNom+"' WHERE Matricule="+Index+";";
      String requete1="UPDATE `appartenir` SET `id_Classe`='"+id_Classe+"', Numero='"+Numero+"' WHERE Matricule="+Index+" and id_AS="+id_AS+";";
         java.sql.Statement AddEmpStatement=maConnexion.ObtenirConnexion().createStatement();      
          AddEmpStatement.executeUpdate(requete1);
      stmt=maConnexion.ObtenirConnexion().createStatement();
      stmt.executeUpdate(query);
      messageDialog();
      notification.setText("");
    }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception de la table Emprunter: " + e) ;
     }
    }
    }
    void loadEmpruntComboBox(){
        ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();
        list=getAnneeScolaireData();
        AS_Combo.setItems(list);
        int size=list.size();   
        System.out.println("AS:"+size);
        AS_Combo.setValue(list.get(0));
    }
    
    public ObservableList<Année_Scolaire> getAnneeScolaireData() {
        System.out.println("Mandeh Année Scolaire");
      ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from annee_scolaire;");
         while (resultat.next()) {
            Année_Scolaire AS = createA_S(resultat);
            list.add(AS);
         }
        System.out.println("Mandeh Année Scolaire");
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
        System.out.println("MIsy diso Année Scolaire------>"+ex);
      }      
      return list;
   }
     private Année_Scolaire createA_S(ResultSet rs) throws SQLException {
      Année_Scolaire AS = new Année_Scolaire();
          AS.setId_AS(rs.getInt("id_AS"));
          AS.setLibellé(rs.getString("design"));  
      return AS;
   }
     void loadClasseComboBox(){
        ObservableList<Classe> list = FXCollections.observableArrayList();
        list=getClasseData();
        Classe.setItems(list); 
        int size=list.size();  
        System.out.println("Classe:"+size);
    }
     public ObservableList<Classe> getClasseData() {
      ObservableList<Classe> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from classe order by id_classe;");
         while (resultat.next()) {
            Classe Classe = createClasse(resultat);
        System.out.println("Mandeh Classe");
            list.add(Classe);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
        System.out.println("MIsy diso Classe------>"+ex);
      }      
      return list;
   }
     private Classe createClasse(ResultSet rs) throws SQLException {
      Classe Classe = new Classe();
          Classe.setId_Classe(rs.getInt("id_classe"));
          Classe.setNomClasse(rs.getString("Label_C"));  
      return Classe;
   }
     
     
    private boolean isInputValid() {
        boolean foundNum = false;
        int nb_erreur=0;
        if (Num.getText() == null || Num.getText().length() == 0) {
            notification.setText("Complétez le champ Nunéro");
            ChangeErrorColor(Num);
            nb_erreur+=1;
        }else
        if (Nom.getText() == null || Nom.getText().length() == 0) {
            notification.setText("Complétez le champ Nom");
            ChangeCorrectColor(Num);
            ChangeErrorColor(Nom);
            nb_erreur+=1;
        }else
        if (Prenom.getText() == null || Prenom.getText().length() == 0) {
            notification.setText("Complétez le champ Prenoms");
            ChangeCorrectColor(Nom);
            ChangeCorrectColor(Num);
            ChangeErrorColor(Prenom);
            nb_erreur+=1;
        } else{  
            ChangeCorrectColor(Nom);
            ChangeCorrectColor(Num);
            ChangeCorrectColor(Prenom);
             // verifier le numéro de la case
         if(Num.getText().length() != 0){
        try {
             Integer.parseInt(Num.getText());
            ChangeCorrectColor(Num);
                 } catch (NumberFormatException e) {
            notification.setText("Numéro d'un étudiant doit étre un entier");
            ChangeErrorColor(Num);
            nb_erreur+=1;
            }
         try{
        Année_Scolaire Id_AS=AS_Combo.getValue();
        int id_AS=Id_AS.getId_AS();
        Classe categ=Classe.getValue();
        int id_Classe=categ.getId_Classe();
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet numero= stmt1.executeQuery("SELECT Numero from appartenir where id_AS="+id_AS+" and id_Classe="+id_Classe+" ORDER BY Numero;");
               int Numer=Integer.parseInt(Num.getText());
            while(numero.next()){
                if (Numer == numero.getInt("Numero")) {
                foundNum= true;
            ChangeCorrectColor(Num);
                System.out.println("Num Client Trouvée: ");
                break;
                    }  else foundNum= false;               
            } 
         if (foundNum){
             notification.setText("Le numéro que vous avez inséré déja existe");
             ChangeErrorColor(Num);
             nb_erreur+=1;
         };  
        }catch(Exception e){

        }
        }         
        } 
        if (nb_erreur == 0) {
            return true;
        } else {
            return false;
        }
}
    public void ChangeErrorColor(JFXTextField field){
        field.getStylesheets().clear();
        field.getStylesheets().add("/gestion_bibliotheque/CSS/error.css");
    }
    public void ChangeCorrectColor(JFXTextField field){
        field.getStylesheets().clear();
        field.getStylesheets().add("/gestion_bibliotheque/CSS/correct.css");
    } 
}
