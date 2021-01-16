/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import animatefx.animation.BounceInDown;
import animatefx.animation.BounceInUp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gestion.ecolage.Model.Année_Scolaire;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class Cons_SubvController implements Initializable {

    double x, y;
    double Montant=0.0;
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
    private TextField Argent;
    @FXML
    private JFXCheckBox Construction;
    @FXML
    private JFXCheckBox Subvention;
    @FXML
    private JFXCheckBox SiblingsCheckBox;
    @FXML
    private JFXTextField Num_M1;
    @FXML
    private JFXTextField Num_M2;
    @FXML
    private JFXTextField Num_M3;
    @FXML
    private JFXTextField Num_M4;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton close;
    @FXML
    private FontAwesomeIconView Change_ArrowDOWN;
    @FXML
    private Pane signUpPane1;
    @FXML
    private JFXButton InscrireBTN1;
    @FXML
    private JFXButton canceladd1;
    @FXML
    private Label notification1;
    @FXML
    private JFXTextField Num_Matr1;
    @FXML
    private JFXDatePicker Date1;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_Combo1;
    @FXML
    private JFXCheckBox Construction1;
    @FXML
    private JFXCheckBox Subvention1;
    @FXML
    private JFXTextField Num_M11;
    @FXML
    private JFXTextField Num_M21;
    @FXML
    private JFXTextField Num_M31;
    @FXML
    private JFXTextField Num_M41;
    @FXML
    private FontAwesomeIconView Change_ArrowUP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Date.setValue(LocalDate.now());
         Date1.setValue(LocalDate.now());
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
        AS_Combo1.setItems(list);
        AS_Combo1.setValue(list.get(size-1)); 
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
    private void CheckBoxChanged(ActionEvent event) {       
        Boolean btn;
        btn=SiblingsCheckBox.selectedProperty().getValue();
        if(btn){
        Num_M1.setDisable(!btn);
        Num_M2.setDisable(!btn);
        Num_M3.setDisable(!btn);
        Num_M4.setDisable(!btn);
        }else{
        Num_M1.setDisable(!btn);
        Num_M2.setDisable(!btn);
        Num_M3.setDisable(!btn);
        Num_M4.setDisable(!btn);
        }
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
        }else if(event.getSource()== canceladd1){
             Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        }else if(event.getSource()== resetadd){
        Num_Matr.setText("");
        Num_M1.setText(""); 
        Num_M2.setText("");
        Num_M3.setText(""); 
        Num_M4.setText("");
        Argent.setText(""); 
        notification.setText("");
        Construction.setSelected(false);
        Subvention.setSelected(false);
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
    private void PayerC_S(MouseEvent event) throws InterruptedException {
        if( isInputValid()){   
        Boolean CheckB;
        CheckB=SiblingsCheckBox.selectedProperty().getValue();
        String NumMatr1=Num_M1.getText();
        String NumMatr2=Num_M2.getText();
        String NumMatr3=Num_M3.getText();
        String NumMatr4=Num_M4.getText();
        LocalDate date=Date.getValue();
        String NumMatr=Num_Matr.getText();
        Année_Scolaire AS=AS_Combo.getValue();
        int id_AS=AS.getId_AS();        
        Boolean C,S,DisableC,DisableS;
        DisableC=Construction.disableProperty().getValue();
        DisableS=Subvention.disableProperty().getValue();
        C=Construction.selectedProperty().getValue();
        S=Subvention.selectedProperty().getValue();
        String PaymentText="";
    try{
        if(C && !DisableC){    
            PayerConstruction(id_AS,NumMatr, date);  
            if(CheckB){
                if(NumMatr1.length() != 0){PayerConstruction(id_AS,NumMatr1, date);}
                if(NumMatr2.length() != 0){PayerConstruction(id_AS,NumMatr2, date);}
                if(NumMatr3.length() != 0){PayerConstruction(id_AS,NumMatr3, date);}
                if(NumMatr4.length() != 0){PayerConstruction(id_AS,NumMatr4, date);}
            }
            PaymentText+=" Construction";
        }
        if(S && !DisableS){
            PayerSubvention(id_AS,NumMatr, date);
             if(CheckB){
                if(NumMatr1.length() != 0){PayerSubvention(id_AS,NumMatr1, date);}
                if(NumMatr2.length() != 0){PayerSubvention(id_AS,NumMatr2, date);}
                if(NumMatr3.length() != 0){PayerSubvention(id_AS,NumMatr3, date);}
                if(NumMatr4.length() != 0){PayerSubvention(id_AS,NumMatr4, date);}
            }
        PaymentText+=" Subvention";
        }
         if(CheckB){
                if(NumMatr1.length() != 0){UpdateZandry(NumMatr,NumMatr1);}
                if(NumMatr2.length() != 0){UpdateZandry(NumMatr,NumMatr2);}
                if(NumMatr3.length() != 0){UpdateZandry(NumMatr,NumMatr3);}
                if(NumMatr4.length() != 0){UpdateZandry(NumMatr,NumMatr4);}
            }
        String req3="INSERT INTO `caisse`(`id_AS`, `Num_Matr`, `Paiment`, `Date`, `Vola`) VALUES ("+id_AS+","+NumMatr+",'"+PaymentText+"','"+date+"',"+Montant+")";         
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
         
            System.out.println("Tsy Misy diso"); 
        }else System.out.println("Misy diso"); 
    }
    public void PayerConstruction(int id_AS,String NumMatr,LocalDate date){
    try{
        String req1="INSERT INTO `payercs`(`id_Autre`, `id_AS`, `Num_Matr`, `Date_P`) VALUES (1,"+id_AS+","+NumMatr+",'"+date+"')";         
         java.sql.Statement CaisseStatement=maConnexion.ObtenirConnexion().createStatement();      
         CaisseStatement.executeUpdate(req1);  
            System.out.println("--> Payer Construction : " + NumMatr) ;
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println("--> Exception de la requete 1: " + ex) ;
      }
    }
    public void PayerSubvention(int id_AS,String NumMatr,LocalDate date){
      try{
        String req2="INSERT INTO `payercs`(`id_Autre`, `id_AS`, `Num_Matr`, `Date_P`) VALUES (2,"+id_AS+","+NumMatr+",'"+date+"')";         
         java.sql.Statement CaisseStatement=maConnexion.ObtenirConnexion().createStatement();      
         CaisseStatement.executeUpdate(req2);
            System.out.println("--> Payer Subvention : " + NumMatr) ;
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println("--> Exception de la requete 1: " + ex) ;
      }
    }
    
    
    public void UpdateZandry(String Matr_Zoky,String Matr_Zandry){
        try{        
      String query = "UPDATE etudiant SET Matricule_Zoky ="+Matr_Zoky+" WHERE Matricule ="+Matr_Zandry+";";
      stmt=maConnexion.ObtenirConnexion().createStatement();
      stmt.executeUpdate(query);
            System.out.println("--> Manw mise à jour: " + Matr_Zandry) ;
    }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception de la table Emprunter: " + e) ;
     }
    }
    @FXML
    private void Matricule_Changed(KeyEvent event) {
        Load_Montant();
        String NumMatr=Num_Matr.getText();
        Année_Scolaire AS=AS_Combo.getValue();
        int id_AS=AS.getId_AS();
         try {
             Boolean P=false;
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT id_Autre from payercs where id_AS="+id_AS+" and Num_Matr="+NumMatr+";");
         while (resultat.next()) {
            System.out.println("--> Mandeh ny Id Autre: ") ;
             P=true;
             if(resultat.getInt("id_Autre")==1){
                    Construction.setSelected(true);      
                    Construction.setDisable(true);
             }else
             if(resultat.getInt("id_Autre")==2){
                    Subvention.setSelected(true);    
                    Subvention.setDisable(true);           
             }
         }
         if(!P){
                Construction.setSelected(false);
                Subvention.setSelected(false);
                Construction.setDisable(false);
                Subvention.setDisable(false);
             }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
         //Ajoter automatique le Matricule Zandry
          try {
             Boolean M=false;
             ArrayList<String> Matr_Zandry = new ArrayList<String>();
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Matricule from etudiant where Matricule_Zoky="+NumMatr+";");
         while (resultat.next()) {
             M=true;
             Matr_Zandry.add(resultat.getString("Matricule"));
         }
         if(!M){
                SiblingsCheckBox.setSelected(false);
            System.out.println("--> Tsy misy zandry: ") ;
             Num_M1.setText("");
             Num_M2.setText("");
             Num_M3.setText("");
             Num_M4.setText("");
             } else {
             SiblingsCheckBox.setSelected(true);
            System.out.println("--> Misy zandry: ") ;
             Num_M1.setText(Matr_Zandry.get(0));
             Num_M2.setText(Matr_Zandry.get(1));
             Num_M3.setText(Matr_Zandry.get(2));
             Num_M4.setText(Matr_Zandry.get(3));
         }
         Matr_Zandry.clear();
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
    }

    @FXML
    private void Construction_Changed(ActionEvent event) {
        Load_Montant();
    }

    @FXML
    private void Subvention_Changed(ActionEvent event) {
        Load_Montant();
    }
    public void Load_Montant(){
        Boolean C,S,DisableC,DisableS;
        Double Cons=0.0,Subv=0.0;
        DisableC=Construction.disableProperty().getValue();
        DisableS=Subvention.disableProperty().getValue();
        C=Construction.selectedProperty().getValue();
        S=Subvention.selectedProperty().getValue();
        if(C && !DisableC){Cons=GetMontant(1);}
        if(S && !DisableS){Subv=GetMontant(2);}
        Montant=Cons+Subv;
        Argent.setText(""+Montant);
    }
     public double GetMontant(int id){      
        double AR=0;
     try {
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Montant FROM autres where id_Autres="+id+";");
         while (resultat.next()) {
             AR=resultat.getDouble("Montant");
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }     
    return AR;
    }
     
     
    private boolean isInputValid() {        
         boolean foundMatricule = false;
        int nb_erreur=0;
        if (Num_Matr.getText() == null || Num_Matr.getText().length() == 0) {
            notification.setText("Complétez le champ Matricule de l'étudiant.");
            ChangeErrorColor(Num_Matr);
            nb_erreur+=1;
        }else
        if ( !Construction.selectedProperty().getValue() && !Subvention.selectedProperty().getValue()) {
            ChangeCorrectColor(Num_Matr);
            notification.setText("Vous avez oublié de sélectionner au moins l'un de ces choix.");
            nb_erreur+=1;
        }else 
        if ( !Construction.selectedProperty().getValue() && Subvention.disableProperty().getValue()) {
            ChangeCorrectColor(Num_Matr);
            notification.setText("Sélectionnez la case Construction.");
            nb_erreur+=1;
        }  else
         if ( Construction.disableProperty().getValue() && !Subvention.selectedProperty().getValue()) {
            ChangeCorrectColor(Num_Matr);
            notification.setText("Sélectionnez la case Construction.");
            nb_erreur+=1;
        }else 
        {  
            ChangeCorrectColor(Num_Matr);
         // verifier le numéro Matricule de l'Etudiant
         if(Num_Matr.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_Matr.getText());
            if (nb<0){
            notification.setText("Numéro Matricule Impossible.");
            ChangeErrorColor(Num_Matr);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_Matr);
            } catch (NumberFormatException e) {
            notification.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }
        Année_Scolaire AS=AS_Combo.getValue();
        int id_AS=AS.getId_AS();
         if(!FoundMatricule(Num_Matr.getText(),id_AS)){
            notification.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_Matr);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_Matr);
         
        if(SiblingsCheckBox.selectedProperty().getValue()){
            String N1=Num_M1.getText();String N2=Num_M2.getText();
            String N3=Num_M3.getText();String N4=Num_M4.getText();
            /*
            if(N1.equals(N2) || N1.equals(N3) || N1.equals(N4) || N2.equals(N3) || N2.equals(N4) || N3.equals(N4))
            {
                notification.setText("Vous avez entré un même numéro."); 
            ChangeErrorColor(Num_M1);
            ChangeErrorColor(Num_M2);
            ChangeErrorColor(Num_M3);
            ChangeErrorColor(Num_M4); 
                nb_erreur+=1;              
            }*/
              if(Num_M1.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M1.getText());
                ChangeCorrectColor(Num_M1);
              if(!FoundMatricule(Num_M1.getText(),id_AS)){
            notification.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M1);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M1);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M1);
            notification.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
             
        }
              if(Num_M2.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M2.getText());
                ChangeCorrectColor(Num_M2);                
              if(!FoundMatricule(Num_M2.getText(),id_AS)){
            notification.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M2);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M2);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M2);
            notification.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }
              if(Num_M3.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M3.getText());
                ChangeCorrectColor(Num_M3);
              if(!FoundMatricule(Num_M3.getText(),id_AS)){
            notification.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M3);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M3);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M3);
            notification.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }if(Num_M4.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M4.getText());
                ChangeCorrectColor(Num_M4);
              if(!FoundMatricule(Num_M4.getText(),id_AS)){
            notification.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M4);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M4);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M4);
            notification.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
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

    @FXML
    private void ChangePaneDOWN(MouseEvent event) {
             signUpPane1.toFront();
             new BounceInDown(signUpPane1).play();
    }

    @FXML
    private void ChangePaneUP(MouseEvent event) {
             signUpPane.toFront();
             new BounceInUp(signUpPane).play();
    }

    @FXML
    private void Matricule1_Changed(KeyEvent event) {
        String NumMatr=Num_Matr1.getText();
        Année_Scolaire AS=AS_Combo1.getValue();
        int id_AS=AS.getId_AS();
         try {
             Boolean P=false;
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT id_Autre from payercs where id_AS="+id_AS+" and Num_Matr="+NumMatr+";");
         while (resultat.next()) {
            System.out.println("--> Mandeh ny Id Autre: ") ;
             P=true;
             if(resultat.getInt("id_Autre")==1){
                    Construction1.setSelected(true);      
                    Construction1.setDisable(true);
             }else
             if(resultat.getInt("id_Autre")==2){
                    Subvention1.setSelected(true);    
                    Subvention1.setDisable(true);           
             }
         }
         if(!P){
                Construction1.setSelected(false);
                Subvention1.setSelected(false);
                Construction1.setDisable(false);
                Subvention1.setDisable(false);
             }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
         //Ajoter automatique le Matricule Zandry
          try {
             Boolean M=false;
             ArrayList<String> Matr_Zandry = new ArrayList<String>();
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Matricule from etudiant where Matricule_Zoky="+NumMatr+";");
         while (resultat.next()) {
             M=true;
             Matr_Zandry.add(resultat.getString("Matricule"));
         }
         if(!M){
                SiblingsCheckBox.setSelected(false);
            System.out.println("--> Tsy misy zandry: ") ;
             Num_M11.setText("");
             Num_M21.setText("");
             Num_M31.setText("");
             Num_M41.setText("");
             } else {
             SiblingsCheckBox.setSelected(true);
            System.out.println("--> Misy zandry: ") ;
             Num_M11.setText(Matr_Zandry.get(0));
             Num_M21.setText(Matr_Zandry.get(1));
             Num_M31.setText(Matr_Zandry.get(2));
             Num_M41.setText(Matr_Zandry.get(3));
         }
         Matr_Zandry.clear();
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
    }

    @FXML
    private void PayerC_S1(MouseEvent event) throws InterruptedException {
         if( isInputValid1()){   
        Boolean CheckB;
        CheckB=true;
        String NumMatr1=Num_M11.getText();
        String NumMatr2=Num_M21.getText();
        String NumMatr3=Num_M31.getText();
        String NumMatr4=Num_M41.getText();
        LocalDate date=Date1.getValue();
        String NumMatr=Num_Matr1.getText();
        Année_Scolaire AS=AS_Combo1.getValue();
        int id_AS=AS.getId_AS();        
        Boolean C,S,DisableC,DisableS;
        DisableC=Construction1.disableProperty().getValue();
        DisableS=Subvention1.disableProperty().getValue();
        C=Construction1.selectedProperty().getValue();
        S=Subvention1.selectedProperty().getValue();
        String PaymentText="";
    try{
        if(C && DisableC){    
            PayerConstruction(id_AS,NumMatr, date);  
            if(CheckB){
                if(NumMatr1.length() != 0){PayerConstruction(id_AS,NumMatr1, date);}
                if(NumMatr2.length() != 0){PayerConstruction(id_AS,NumMatr2, date);}
                if(NumMatr3.length() != 0){PayerConstruction(id_AS,NumMatr3, date);}
                if(NumMatr4.length() != 0){PayerConstruction(id_AS,NumMatr4, date);}
            }
            PaymentText+=" Construction";
        }
        if(S && DisableS){
            PayerSubvention(id_AS,NumMatr, date);
             if(CheckB){
                if(NumMatr1.length() != 0){PayerSubvention(id_AS,NumMatr1, date);}
                if(NumMatr2.length() != 0){PayerSubvention(id_AS,NumMatr2, date);}
                if(NumMatr3.length() != 0){PayerSubvention(id_AS,NumMatr3, date);}
                if(NumMatr4.length() != 0){PayerSubvention(id_AS,NumMatr4, date);}
            }
        PaymentText+=" Subvention";
        }
         if(CheckB){
                if(NumMatr1.length() != 0){UpdateZandry(NumMatr,NumMatr1);}
                if(NumMatr2.length() != 0){UpdateZandry(NumMatr,NumMatr2);}
                if(NumMatr3.length() != 0){UpdateZandry(NumMatr,NumMatr3);}
                if(NumMatr4.length() != 0){UpdateZandry(NumMatr,NumMatr4);}
            }
    double M=0.0;
        String req3="INSERT INTO `caisse`(`id_AS`, `Num_Matr`, `Paiment`, `Date`, `Vola`) VALUES ("+id_AS+","+NumMatr+",'"+PaymentText+"','"+date+"',"+M+")";         
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
         
            System.out.println("Tsy Misy diso"); 
        }else System.out.println("Misy diso"); 
    }
    
    private boolean isInputValid1() {        
         boolean foundMatricule = false;
        int nb_erreur=0;
        if (Num_Matr1.getText() == null || Num_Matr1.getText().length() == 0) {
            notification1.setText("Complétez le champ Matricule de l'étudiant.");
            ChangeErrorColor(Num_Matr1);
            nb_erreur+=1;
        }else{  
            ChangeCorrectColor(Num_Matr1);
         // verifier le numéro Matricule de l'Etudiant
         if(Num_Matr1.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_Matr1.getText());
            if (nb<0){
            notification1.setText("Numéro Matricule Impossible.");
            ChangeErrorColor(Num_Matr1);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_Matr1);
            } catch (NumberFormatException e) {
            notification1.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }
        Année_Scolaire AS=AS_Combo1.getValue();
        int id_AS=AS.getId_AS();
         if(!FoundMatricule(Num_Matr1.getText(),id_AS)){
            notification1.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_Matr1);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_Matr1);
         
       // if(SiblingsCheckBox.selectedProperty().getValue()){
            String N1=Num_M11.getText();String N2=Num_M21.getText();
            String N3=Num_M31.getText();String N4=Num_M41.getText();
              if(Num_M11.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M11.getText());
                ChangeCorrectColor(Num_M11);
              if(!FoundMatricule(Num_M11.getText(),id_AS)){
            notification1.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M11);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M11);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M11);
            notification1.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
             
        }
              if(Num_M21.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M21.getText());
                ChangeCorrectColor(Num_M21);                
              if(!FoundMatricule(Num_M21.getText(),id_AS)){
            notification1.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M21);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M21);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M21);
            notification1.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }
              if(Num_M31.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M31.getText());
                ChangeCorrectColor(Num_M31);
              if(!FoundMatricule(Num_M31.getText(),id_AS)){
            notification1.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M31);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M31);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M31);
            notification1.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }if(Num_M41.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M41.getText());
                ChangeCorrectColor(Num_M41);
              if(!FoundMatricule(Num_M41.getText(),id_AS)){
            notification1.setText("Le Numéro Matricule n'existe pas");
            ChangeErrorColor(Num_M41);
            nb_erreur+=1;
               } else ChangeCorrectColor(Num_M41);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M41);
            notification1.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
        }
          //}
        } 
        if (nb_erreur == 0) {
            notification.setText("");
            return true;
        } else {
            return false;
        }
}
}
