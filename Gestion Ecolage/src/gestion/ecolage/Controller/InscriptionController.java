/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import animatefx.animation.BounceInDown;
import animatefx.animation.BounceInLeft;
import animatefx.animation.BounceInRight;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gestion.ecolage.Model.Année_Scolaire;
import gestion.ecolage.Model.Catégorie;
import gestion.ecolage.Model.Niveau;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import static javafx.beans.property.BooleanProperty.booleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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
public class InscriptionController implements Initializable {

    double x, y;
    double Montant;
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
    private JFXButton minimize;
    @FXML
    private JFXButton close;
    @FXML
    private JFXTextField Num_Matr;
    @FXML
    private JFXToggleButton AncienToggleBtn;
    @FXML
    private JFXComboBox<Niveau> NiveauCombo;
    @FXML
    private JFXComboBox<Catégorie> OBS;
    @FXML
    private JFXTextField Nom;
    @FXML
    private JFXTextField Prenoms;
    @FXML
    private JFXDatePicker Date;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_Combo;
    @FXML
    private TextField NumberMonth;
    @FXML
    private TextField Argent;
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
    private JFXToggleButton AncienToggleBtn1;
    @FXML
    private ComboBox<Niveau> NiveauCombo1;
    @FXML
    private JFXComboBox<Catégorie> OBS1;
    @FXML
    private JFXTextField Nom1;
    @FXML
    private JFXTextField Prenoms1;
    @FXML
    private JFXDatePicker Date1;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_Combo1;
    @FXML
    private TextField Argent1;
    @FXML
    private MaterialDesignIconView arrowLEFT;
    @FXML
    private MaterialDesignIconView arrowRIGHT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        int nb_Matr_Suiv=0;
        Date.setValue(LocalDate.now());      
        Date1.setValue(LocalDate.now());
        loadAll_AS_ComboBox();
        nb_Matr_Suiv=Get_Matr_FIN() +1;
        Num_Matr.setText(""+nb_Matr_Suiv);
    }    
    int Get_Matr_FIN(){
    int Matr=0;
    try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT MAX(Matricule) AS Max from etudiant;;");
         while (resultat.next()) {
             Matr=resultat.getInt("Max");
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
      }  
    return Matr;
    }
    void loadAll_AS_ComboBox(){
    try{
        ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();
        list=getAnneeScolaireData(); 
        ObservableList<Niveau> listN = FXCollections.observableArrayList();
        listN=getNiveauData(); 
        ObservableList<Catégorie> listC = FXCollections.observableArrayList();
        listC=getCategData(); 
        AS_Combo.setItems(list);
        NiveauCombo.setItems(listN);
        OBS.setItems(listC);
        AS_Combo1.setItems(list);
        NiveauCombo1.setItems(listN);
        OBS1.setItems(listC);
        int size=list.size();
        AS_Combo.setValue(list.get(size-1));  
        OBS.setValue(listC.get(0));
        AS_Combo1.setValue(list.get(size-1));  
        OBS1.setValue(listC.get(0));
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
     public ObservableList<Niveau> getNiveauData() {
      ObservableList<Niveau> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from niveau ORDER BY id_Niv;");
         while (resultat.next()) {
            Niveau AS = createNiveau(resultat);
            list.add(AS);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
      }      
      return list;
   }
     private Niveau createNiveau(ResultSet rs) throws SQLException {
      Niveau N = new Niveau();
            N.setId_N(rs.getInt("id_Niv"));
            N.setLibellé(rs.getString("Label_N"));
            N.setFrais(rs.getDouble("Frais"));
      return N;
   }
    
     public ObservableList<Catégorie> getCategData() {
      ObservableList<Catégorie> list = FXCollections.observableArrayList();
            Catégorie AS = new Catégorie(1,"Tsotra");
            Catégorie AS1 = new Catégorie(2,"Zanaka Katekista");
            Catégorie AS2 = new Catégorie(3,"Zanaka mpampianatra");
            list.add(AS);  
            list.add(AS1);
            list.add(AS2);
      return list;
   }
     
     void ExportDoneNotification() throws InterruptedException{  
         String title = "Complète";
        String message ="Le reste a reçu \n";
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
          String title = "Echoué";
        String Message ="Peut-être que le numéro n'existe pas";
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
      
     void ExportDoneNotificationInsc() throws InterruptedException{  
         String title = "Inscription Complète";
        String message ="L'inscription d'un étudiant a réussi\n";
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
      void ExportFailNotificationInsc() throws InterruptedException{  
          String title = "Echoué";
        String Message ="Peut-être que ce N° Matricule a déja fait une inscription\n"
                + "pour ce niveau et cette année scolaire";
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
     
    @FXML
    private void InscrireElève(MouseEvent event) throws InterruptedException {
        if(isInputValid()){
        LocalDate date_Insc=Date.getValue();
        Année_Scolaire AS=AS_Combo.getValue(); 
        int id_AS=AS.getId_AS();
        Boolean Ancien;
        Ancien=AncienToggleBtn.selectedProperty().getValue();
        Niveau Niv=NiveauCombo.getValue(); 
        int id_Niv=Niv.getId_N();
        Catégorie Obs=OBS.getValue(); 
        int id_OBS=Obs.getId();
        String NumMatr=Num_Matr.getText();
        String nom= Nom.getText();
        String prenom=Prenoms.getText();
        int nb_Moi=Integer.parseInt(NumberMonth.getText());
        Double Montant=Double.parseDouble(Argent.getText()); 
        String PaymentText="Droit ";
        System.out.println("Tsy misy diso");
        String requete1="INSERT INTO `inscription`(`Matricule`, `id_AS`, `id_N`, `Date_Insc`, `Ancien`) VALUES ("+NumMatr+","+id_AS+","+id_Niv+",'"+date_Insc+"','"+Ancien+"');";
        String req2="INSERT INTO `etudiant`(`Matricule`, `Nom`, `Prenoms`, `OBS`) VALUES ("+NumMatr+",'"+nom+"','"+prenom+"',"+id_OBS+")";
        try {
         java.sql.Statement InscriptionStatement=maConnexion.ObtenirConnexion().createStatement();      
         InscriptionStatement.executeUpdate(requete1);
         if(!Ancien){
         java.sql.Statement EtudiantStatement=maConnexion.ObtenirConnexion().createStatement();      
         EtudiantStatement.executeUpdate(req2);
         }
         int i=1;
         while(i<=nb_Moi){
         java.sql.Statement PayementStatement=maConnexion.ObtenirConnexion().createStatement(); 
        String req4="INSERT INTO `payer`(`Matricule`, `id_AS`, `id_Eco`, `Date_paiment`) VALUES ("+NumMatr+","+id_AS+","+i+",'"+date_Insc+"')";     
         PayementStatement.executeUpdate(req4);
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
          java.sql.ResultSet titre= stmt1.executeQuery("SELECT Libelle_Eco FROM ecolage WHERE id_Eco="+i+";");
            while(titre.next()){                    
                    PaymentText+=" + "+titre.getString("Libelle_Eco");         
            } 
         i++;
         }
        String req3="INSERT INTO `caisse`(`id_AS`, `Num_Matr`, `Paiment`, `Date`, `Vola`) VALUES ("+id_AS+","+NumMatr+",'"+PaymentText+"','"+date_Insc+"',"+Montant+")";         
         java.sql.Statement CaisseStatement=maConnexion.ObtenirConnexion().createStatement();      
         CaisseStatement.executeUpdate(req3);
         ExportDoneNotificationInsc();
    //Fermer la fenêtre         
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println("--> Exception de la requete 1: " + ex) ;
            ExportFailNotificationInsc();
      }  
        } else 
        System.out.println("Misy diso");
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
        Nom.setText("");
        Prenoms.setText(""); 
        NumberMonth.setText("1"); 
        //AncienToggleBtn.setSelected(false);
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
    private void ToggleBtnChanged(ActionEvent event) {
        Boolean btn;
        btn=AncienToggleBtn.selectedProperty().getValue();
        OBS.setDisable(btn);
        Nom.setEditable(!btn);
        Prenoms.setEditable(!btn);
        if(btn){Num_Matr.setText("");}
        else {Num_Matr.setText(""+(Get_Matr_FIN() +1));
        Nom.setText("");
        Prenoms.setText("");
        }
        LoadMontant();
    }
    void LoadMontant(){
     try {
         Double Coeff;
         Année_Scolaire AS=AS_Combo.getValue(); 
        int id_AS=AS.getId_AS();
        Boolean Ancien;
        Ancien=AncienToggleBtn.selectedProperty().getValue();
        Niveau Niv=NiveauCombo.getValue(); 
        int id_Niv=Niv.getId_N();
        Double Eco=Niv.getFrais();  
        int nb_Moi=Integer.parseInt(NumberMonth.getText());
        Catégorie Obs=OBS.getValue(); 
        int id_OBS=Obs.getId();
        if(id_OBS==1){Coeff=1.0;}
        else if (id_OBS==2){Coeff=0.5;}
        else Coeff=0.0;
         this.Montant=GetMontant(id_Niv,Ancien,id_AS,nb_Moi,Eco,Coeff);
      } catch ( NullPointerException | IndexOutOfBoundsException | NumberFormatException ex) {
      }   
        Argent.setText(""+Montant);
    }
    public double GetMontant(int id_Niv, Boolean Ancien, int id_AS, int nb_Moi,Double Eco,Double Coeff){      
        double AR=0;
    try {
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Montant FROM droit where Id_Niv="+id_Niv+" and Ancien='"+Ancien+"';");
         while (resultat.next()) {
             AR=resultat.getDouble("Montant");
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }  
    AR=AR+(nb_Moi*Eco*Coeff);
    return AR;
    }

    @FXML
    private void NiveauChanged(ActionEvent event) {
        LoadMontant();
    }

    @FXML
    private void ObservationChanged(ActionEvent event) {
        LoadMontant();
    }

    @FXML
    private void Année_ScolaireChanged(ActionEvent event) {
        LoadMontant();
    }

    @FXML
    private void MatriculeEntered(KeyEvent event) {
        String NumMatr=Num_Matr.getText();   
        Boolean Ancien;
        Ancien=AncienToggleBtn.selectedProperty().getValue();
        if(Ancien){
            try {  
                boolean isEmpty=true;
         ObservableList<Catégorie> listC = FXCollections.observableArrayList();
         listC=getCategData();
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("Select * from etudiant where Matricule="+NumMatr+";");
         while (resultat.next()) {
             isEmpty=false;
             Nom.setText(resultat.getString("Nom"));
             Prenoms.setText(resultat.getString("Prenoms"));             
             OBS.setValue(listC.get(resultat.getInt("OBS")-1));
         }
         if(isEmpty){
             Nom.setText("");
             Prenoms.setText("");
             OBS.setValue(listC.get(0));
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }  
        }
    }

    @FXML
    private void NumberChanged(KeyEvent event) {
        LoadMontant();
    }
    
    private boolean isInputValid() {  
        int nb_erreur=0;
        if (Num_Matr.getText() == null || Num_Matr.getText().length() == 0) {
            notification.setText("Complétez le champ Matricule de l'étudiant.");
            ChangeErrorColor(Num_Matr);
            nb_erreur+=1;
        }else
        if (Nom.getText() == null || Nom.getText().length() == 0) {
            notification.setText("Complétez le champ Nom de l'étudiant.");
            ChangeCorrectColor(Num_Matr);
            ChangeErrorColor(Nom);
            nb_erreur+=1;
        }else 
        if (NiveauCombo.getValue() == null ) {
            notification.setText("Selectionnez un Niveau");
            ChangeCorrectColor(Nom);
            ChangeCorrectColor(Num_Matr);
            nb_erreur+=1;
        }else
        if (Prenoms.getText() == null || Prenoms.getText().length() == 0) {
            notification.setText("Complétez le champ Prénoms de l'Etudiant.");
            ChangeCorrectColor(Num_Matr);
            ChangeCorrectColor(Nom);
            ChangeErrorColor(Prenoms);
            nb_erreur+=1;
        } else 
        if (NumberMonth.getText() == null || NumberMonth.getText().length() == 0) {
            ChangeCorrectColor(Num_Matr);
            ChangeCorrectColor(Nom);
            ChangeCorrectColor(Prenoms);
            notification.setText("Complétez le champ  Nombre du Mois.");
            nb_erreur+=1;
        } else{  
            ChangeCorrectColor(Num_Matr);
            ChangeCorrectColor(Nom);
            ChangeCorrectColor(Prenoms);
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
          // verifier le nombre du mois
         if(NumberMonth.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(NumberMonth.getText());
            if (nb<0){
            notification.setText("Nombre du mois Impossible.");
            nb_erreur+=1;
               } else if (nb>10){
            notification.setText("Nombre du mois Impossible.");
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
    private void ShowInscriptionPANEL(MouseEvent event) {
             signUpPane.toFront();
             new BounceInRight(signUpPane).play();
    }

    @FXML
    private void ShowRestePANEL(MouseEvent event) {
             signUpPane1.toFront();
             new BounceInLeft(signUpPane1).play();
    }

    @FXML
    private void MatriculeEntered_1(KeyEvent event) {
        
            try { 
         Année_Scolaire AS=AS_Combo1.getValue(); 
        int id_AS=AS.getId_AS();
         String NumMatr=Num_Matr1.getText();   
        Boolean Ancien;
        Ancien=AncienToggleBtn.selectedProperty().getValue(); 
                boolean isEmpty=true;
         ObservableList<Catégorie> listC = FXCollections.observableArrayList();
         listC=getCategData();          
        ObservableList<Niveau> listN = FXCollections.observableArrayList();
        listN=getNiveauData(); 
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
    
         java.sql.ResultSet resultat= stmt1.executeQuery("Select Nom,Prenoms,OBS,Ancien,Date_Insc,id_N from inscription,etudiant "
                 + "where etudiant.Matricule=inscription.Matricule and etudiant.Matricule="+NumMatr+" "
                 + "and id_AS="+id_AS+";");
         while (resultat.next()) {
             isEmpty=false;
             Nom1.setText(resultat.getString("Nom"));
             Prenoms1.setText(resultat.getString("Prenoms"));             
             OBS1.setValue(listC.get(resultat.getInt("OBS")-1));             
             NiveauCombo1.setValue(listN.get(resultat.getInt("id_N")-1)); 
             AncienToggleBtn1.setSelected(resultat.getBoolean("Ancien"));                                    
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");     
        LocalDate date=ConvertStringtoLocalDate(sdf.format(resultat.getDate("Date_Insc"))); 
             Date1.setValue(date);*/
         }
         if(isEmpty){
             Nom1.setText("");
             Prenoms1.setText("");
             OBS1.setValue(listC.get(0));
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }  
        
    }
public static LocalDate ConvertStringtoLocalDate(String date) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate= LocalDate.parse(date, myFormatObj);
        return localDate;
    } 
    @FXML
    private void InscrireElève_1(MouseEvent event) throws InterruptedException {
        
        try {
        LocalDate date_Insc=Date1.getValue();
        Année_Scolaire AS=AS_Combo1.getValue(); 
        int id_AS=AS.getId_AS();
        String NumMatr=Num_Matr1.getText();
        Double Montant=Double.parseDouble(Argent1.getText()); 
        String PaymentText="Reste du droit";
         
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
    }
    
}
