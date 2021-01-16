/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FlipInX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author Marsihay
 */
public class LoginController implements Initializable {
    double x, y;
    public static String Username;
      Statement stmt;
    Connexion maConnexion=new Connexion();
     @FXML
    private JFXTextField userN;

    @FXML
    private JFXPasswordField pass1;

    @FXML
    private JFXButton save;

    @FXML
    private Label Notif_save;

    @FXML
    private JFXPasswordField pass2;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;


    @FXML
    private Label Notif_login;

    @FXML
    private Label createLbl;
    @FXML
    private JFXButton info;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton minimize;
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private StackPane loginStackPane;
    @FXML
    private JFXDialogLayout DialogLayout;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private Pane AboutThisAppPanel;

    /**
     * Initializes the controller class.
     */
      void messageDialog(){
        /*JFXDialogLayout content= new JFXDialogLayout();*/
        DialogLayout.setHeading(new Text("Message"));
        DialogLayout.setBody(new Text("Enregistrement réussi."));
        JFXDialog dialog=new JFXDialog(loginStackPane, DialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("OK");
        button.setStyle("-fx-background-color :  #0099ff; -fx-text-fill:  white;-fx-cursor: hand;");
        button.setOnAction(new EventHandler<ActionEvent>(){
          @Override
        public void handle(ActionEvent event) {  
            dialog.close();
            
            
            Node node=(Node) event.getSource();
            Stage stage=(Stage)node.getScene().getWindow();
            stage.close();
            
            Parent home;
              try {
                  home = FXMLLoader.load(getClass().getResource("/gestion/ecolage/View/Home.fxml"));
                  Scene scene = new Scene(home);
                    stage.setScene(scene);
                    new FlipInX(home).play();
                    stage.show();
              } catch (IOException ex) {
                  Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
              }
            
        }        
        });
        DialogLayout.setActions(button);
        dialog.show();
    }
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
         if(event.getSource()== close){
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        } else if(event.getSource()== minimize){
             Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
        } else if(event.getSource()== info){
          // ************* PopOver Info APP ************************       
        PopOver popOver = new PopOver(AboutThisAppPanel);
        (AboutThisAppPanel).setVisible(true);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.show(info);
        }else if(event.getSource()== loginBtn){
           String userlog=username.getText();
           String passW=password.getText();
            if (userlog.equals("")){
              Notif_login.setText("Remplir le champ Nom d'utilisateur.");
          } else if (passW.equals("")){
              Notif_login.setText("Remplir le champ Mot de passe.");
          } else {
             String requete ="select * from login where username ='"+userlog+"' and password ='"+DigestUtils.md5Hex(passW)+"' ";
        try{
            Notif_login.setText("");
            stmt=maConnexion.ObtenirConnexion().createStatement();
            ResultSet resultat= stmt.executeQuery(requete);
            
        if(!resultat.next()){
            Notif_login.setText("Vérifier le Mot de passe/Nom d'utilisateur");
            username.setText(userlog);
            password.setText(null);
        }else{
             LoginController.Username = resultat.getString("username");
            
            Node node=(Node) event.getSource();
            Stage stage=(Stage)node.getScene().getWindow();
            stage.close();
            
            Parent home = FXMLLoader.load(getClass().getResource("/gestion/ecolage/View/Home.fxml"));
            Scene scene = new Scene(home);
            stage.setScene(scene);
            new FlipInX(home).play();
            stage.show();             
        }   
            
        }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception : " + e) ;
        }catch(NullPointerException e){
            System.out.println("--> NullPointerException : " + e) ;
            WampServerNotification();
        }
        }
      }else if(event.getSource()== save){
          String user= userN.getText();
          String pass_1= pass1.getText();
          String pass_2= pass2.getText();
          if (user.equals("")){
              Notif_save.setText("Remplir le champ nom d'utilisateur.");
          } else if (pass_1.equals("")){
              Notif_save.setText("Remplir le champ mot de passe.");
          } else if (pass_2.equals("")){
              Notif_save.setText("Re-insérer votre mot de passe.");
          }else
              if(!pass_1.equals(""+pass_2+"")){
              Notif_save.setText("Les mot de passe sont différent.");
          }else {
                  Notif_save.setText("");
          String passWord= DigestUtils.md5Hex(pass_1);
          String requete ="Insert into login (username, password) values ('"+user+"','"+passWord+"') ";
        try{
            
            stmt=maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(requete);
            
             messageDialog();
            /*JOptionPane.showMessageDialog(null,"Enregistrement réussi!");*/
            LoginController.Username = user;
             
          
            
        }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception : " + e) ;
         }catch(NullPointerException e){
            System.out.println("--> NullPointerException : " + e) ;
            WampServerNotification();
        }
          }
        } 
    }
    void WampServerNotification(){
        DialogLayout.setHeading(new Text("Notification"));
        DialogLayout.setBody(new Text("Veuillez démarrer le Wamp Server, s'il vous plait!!! \n Puis Redémarrer cette application"));
        JFXDialog dialog=new JFXDialog(loginStackPane, DialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("OK");
        button.setStyle("-fx-background-color :   #0099ff; -fx-text-fill:  white;-fx-cursor: hand;");
        button.setOnAction(new EventHandler<ActionEvent>(){
          @Override
        public void handle(ActionEvent event) {  
            dialog.close();            
        }        
        });
        DialogLayout.setActions(button);
        dialog.show();
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
    private void SignUp(MouseEvent event) {
        signUpPane.toFront();
        new FadeIn(signUpPane).play();
    }

    @FXML
    private void retour(MouseEvent event) {
        loginPane.toFront();
        new FadeIn(loginPane).play();
    }
}
