/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import gestion.ecolage.Model.Année_Scolaire;
import gestion.ecolage.Model.Catégorie;
import gestion.ecolage.Model.Niveau;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Marsihay
 */
public class EcolageController implements Initializable {

    double x, y;
    double Montant;
    int Id_Fin_Eco;
      Statement stmt;
    Connexion maConnexion=new Connexion();
    @FXML
    private Pane signUpPane;
    @FXML
    private JFXButton InscrireBTN;
    private JFXButton resetadd;
    @FXML
    private JFXButton canceladd;
    @FXML
    private Label notification;
    @FXML
    private JFXTextField Num_Matr;
    @FXML
    private JFXDatePicker Date;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_Combo;
    @FXML
    private TextField NumberMonth;
    @FXML
    private TextField Argent;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Date.setValue(LocalDate.now());
        loadAll_AS_ComboBox();
    }    

    
     void ExportDoneNotification() throws InterruptedException{  
         String title = "Inscription Complète";
        String message ="Inscription d'un étudiant a réussi \n";
        Image Img=new Image("/gestion/ecolage/image/icons8_Ok_96px.png");
        NotificationType notification = NotificationType.SUCCESS;        
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(notification);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setImage(Img);        
        tray.setRectangleFill(Paint.valueOf("#4CAF50"));
        tray.showAndDismiss(Duration.seconds(5));      
         playDoneSound();
    }
      void ExportFailNotification() throws InterruptedException{  
          String title = "Inscription Echoué";
        String Message ="Il y a un erreur \n";
        Image Img=new Image("/gestion/ecolage/image/icons8_High_Priority_96px.png");
        NotificationType notification = NotificationType.WARNING;
        
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(Message);
        tray.setNotificationType(notification);
        tray.setAnimationType(AnimationType.POPUP);        
        tray.setRectangleFill(Paint.valueOf("#F44336"));
        tray.setImage(Img);
        tray.showAndDismiss(Duration.seconds(5)); 
        playWarningSound();
    }
      /**********************************************************
     * *******************POUR LE SON -------******************
     */
    private void playWarningSound() throws InterruptedException {
         try {
             int maxVolume=90;
             int sound=65;       
            Synthesizer synthesizer=MidiSystem.getSynthesizer();
            synthesizer.open();
            MidiChannel channel=synthesizer.getChannels()[9];
            for(int i=0;i<2;i++){
                Thread.sleep(100);
                channel.noteOn(sound+i, maxVolume);
                Thread.sleep(100);
                channel.noteOff(sound+i);
            }
        } catch (MidiUnavailableException ex) {
           ex.printStackTrace();
        }
    }
     private void playDoneSound() throws InterruptedException {
        int[] notes=new int[]{80,82,90};
          try {
             int maxVolume=100;
             int sound=65;       
            Synthesizer synthesizer=MidiSystem.getSynthesizer();
            synthesizer.open();
            MidiChannel channel=synthesizer.getChannels()[0];
            for(int note:notes){
                channel.noteOn(note, maxVolume);
                Thread.sleep(200);
                channel.noteOff(note);
            }
        } catch (MidiUnavailableException ex) {
           ex.printStackTrace();
        }
    }
    
    
    void loadAll_AS_ComboBox(){
    try{
        ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();
        list=getAnneeScolaireData(); 
        AS_Combo.setItems(list);
        int size=list.size();
        AS_Combo.setValue(list.get(size-1));  
    }catch(NullPointerException | IndexOutOfBoundsException e){
    }
    } 

    public ObservableList<Année_Scolaire> getAnneeScolaireData() {
      ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from annee_scolaire ORDER BY id_AS;");
         while (resultat.next()) {
            Année_Scolaire AS = createA_S(resultat);
            list.add(AS);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
      }      
      return list;
   }
     private Année_Scolaire createA_S(ResultSet rs) throws SQLException {
      Année_Scolaire AS = new Année_Scolaire();
          AS.setId_AS(rs.getInt("id_AS"));
          AS.setLibellé(rs.getString("design"));  
      return AS;
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
        }else if(event.getSource()== resetadd){
        Num_Matr.setText("");
        NumberMonth.setText(""); 
        Argent.setText(""); 
        notification.setText("");
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

    @FXML
    private void PayerEcolage(MouseEvent event) throws InterruptedException {
         if(isInputValid()){
        LocalDate date_Insc=Date.getValue();
        Année_Scolaire AS=AS_Combo.getValue();
        int id_AS=AS.getId_AS();
        String NumMatr=Num_Matr.getText();
        int nb_Moi=Integer.parseInt(NumberMonth.getText());
        Double Montant=Double.parseDouble(Argent.getText());
        String PaymentText="";
         try {
         int i=1;
         while(i<=nb_Moi){
         java.sql.Statement PayementStatement=maConnexion.ObtenirConnexion().createStatement(); 
        String req4="INSERT INTO `payer`(`Matricule`, `id_AS`, `id_Eco`, `Date_paiment`) VALUES ("+NumMatr+","+id_AS+","+(Id_Fin_Eco+i)+",'"+date_Insc+"')";     
         PayementStatement.executeUpdate(req4);
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
          java.sql.ResultSet titre= stmt1.executeQuery("SELECT Libelle_Eco FROM ecolage WHERE id_Eco="+(Id_Fin_Eco+i)+";");
            while(titre.next()){                    
                    PaymentText+=" + "+titre.getString("Libelle_Eco");         
            } 
         i++;
         }
        String req3="INSERT INTO `caisse`(`id_AS`, `Num_Matr`, `Paiment`, `Date`, `Vola`) VALUES ("+id_AS+","+NumMatr+",'"+PaymentText+"','"+date_Insc+"',"+Montant+")";         
         java.sql.Statement CaisseStatement=maConnexion.ObtenirConnexion().createStatement();      
         CaisseStatement.executeUpdate(req3);
         ExportDoneNotification();
         //Fermer la fenêtre         
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println("--> Exception de la requete 1: " + ex) ;
            ExportFailNotification();
      }  
         }else System.out.println("Misy diso");         
    }
    
     public void Get_Id_Fin_Eco(int Num,int id_AS){
     try {
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT MAX(id_Eco) AS id_Mois from payer where Matricule="+Num+" and id_AS="+id_AS+";");
         while (resultat.next()) {
             Id_Fin_Eco=resultat.getInt("id_Mois");
         }
         resultat.close();
         stmt1.close();
         System.out.println("--> Faran'ny ecolage naloa: " + Id_Fin_Eco) ;
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
    }
     
     void LoadMontant(){
     try {
         Double Coeff;
         Année_Scolaire AS=AS_Combo.getValue(); 
        int id_AS=AS.getId_AS();
        Boolean Ancien; 
        int NumMatr=Integer.parseInt(Num_Matr.getText());
        int nb_Moi=Integer.parseInt(NumberMonth.getText());
        Get_Id_Fin_Eco(NumMatr,id_AS);
         this.Montant=GetMontant(id_AS,NumMatr)*nb_Moi;
      } catch ( NullPointerException | IndexOutOfBoundsException | NumberFormatException ex) {
      }   
        Argent.setText(""+Montant);
    }
    public double GetMontant(int id_AS,int Num){      
        double AR=0;int id_OBS=0;double Coeff;
    try {
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Frais,OBS from etudiant,niveau,inscription where niveau.Id_Niv=inscription.id_N and etudiant.Matricule=inscription.Matricule and inscription.id_AS="+id_AS+" and inscription.Matricule="+Num+";");
         while (resultat.next()) {
             AR=resultat.getDouble("Frais");
             id_OBS=resultat.getInt("OBS");
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }      
        if(id_OBS==1){Coeff=1.0;}
        else if (id_OBS==2){Coeff=0.5;}
        else Coeff=0.0;
    AR=AR*Coeff;
    return AR;
    }

    private boolean isInputValid() {  
        int nb_erreur=0;
        if (Num_Matr.getText() == null || Num_Matr.getText().length() == 0) {
            notification.setText("Complétez le champ Matricule de l'étudiant.");
            ChangeErrorColor(Num_Matr);
            nb_erreur+=1;
        }else
        if (NumberMonth.getText() == null || NumberMonth.getText().length() == 0) {
            ChangeCorrectColor(Num_Matr);
            notification.setText("Complétez le champ  Nombre du Mois.");
            nb_erreur+=1;
        } else{  
            ChangeCorrectColor(Num_Matr);
        Année_Scolaire AS=AS_Combo.getValue();
        int id_AS=AS.getId_AS();
         // verifier le numéro Matricule de l'Etudiant
         if(Num_Matr.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_Matr.getText());
            if (nb<0){
            notification.setText("Numéro Matricule Impossible.");
            ChangeErrorColor(Num_Matr);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_Matr);
            //vérifier le numéro matricule
              if(!FoundMatricule(Num_Matr.getText(),id_AS)){
            notification.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_Matr);
            nb_erreur+=1;
              }
            } catch (NumberFormatException e) {
            notification.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }
          // verifier le nombre du mois
          int Mois=0;
          int Id_NIV=Get_Id_NIVEAU(Num_Matr.getText(),id_AS);  
          if(Id_NIV==3){Mois=11;}else Mois=10;
         if(NumberMonth.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(NumberMonth.getText());
            if (nb<0){
            notification.setText("Nombre du mois Impossible.");
            nb_erreur+=1;
               } else if (nb>Mois){
            notification.setText("Nombre du mois Impossible.");
            nb_erreur+=1;
               }else if (nb+Id_Fin_Eco>Mois){
            notification.setText("Nombre du mois dépasse la limite.");
            nb_erreur+=1;
               }
            } catch (NumberFormatException e) {
            notification.setText("Le champ  Nombre du Mois doit être un entier");
            nb_erreur+=1;
            }
        } 
        } 
        if (nb_erreur == 0) {
            notification.setText("");
            return true;
        } else {
            return false;
        }
}
     public void ChangeErrorColor(JFXTextField field){
        field.getStylesheets().clear();
        field.getStylesheets().add("/gestion/ecolage/CSS/error.css");
    }
    public void ChangeCorrectColor(JFXTextField field){
        field.getStylesheets().clear();
        field.getStylesheets().add("/gestion/ecolage/CSS/correct.css");
    }

    @FXML
    private void Num_Changed(KeyEvent event) {
        LoadMontant();
    }

    @FXML
    private void Number_Changed(KeyEvent event) {
        LoadMontant();
    }
    
    public Boolean FoundMatricule(String Matr,int Id_AS){
           // verifier si la matricule existe   
         boolean foundMatricule = false;
         try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet titre= stmt1.executeQuery("SELECT DISTINCT Matricule from inscription where id_AS="+Id_AS+";");
            while(titre.next()){
                if (Matr.equals(titre.getString("Matricule"))) {
                foundMatricule= true;
                break;
                    }          
            } 
        }catch(Exception e){
            System.out.println("--> Exception Found Titre: " + e) ;     
        }  
         return foundMatricule;
    }
    
     public int Get_Id_NIVEAU(String Matr,int Id_AS){
           // verifier si la matricule existe   
         int Id_Niv=0;
         try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet Id= stmt1.executeQuery("SELECT niveau.Id_Niv FROM niveau,classe,appartenir WHERE niveau.Id_Niv=classe.id_Niv and"
                        + " classe.id_classe=appartenir.id_Classe and appartenir.Matricule="+Matr+" and id_AS="+Id_AS+";");
            while(Id.next()){
                 Id_Niv=Id.getInt("Id_Niv");
            }
        }catch(Exception e){
            System.out.println("--> Exception Found ID_NIV: " + e) ;     
        }  
            System.out.println("--> ID_NIV: " + Id_Niv) ;   
         return Id_Niv;
    }

    @FXML
    private void AS_Changed(ActionEvent event) {
        LoadMontant();
    }
}
