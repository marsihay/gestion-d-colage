/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gestion.ecolage.Model.Année_Scolaire;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
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
public class Annee_ScolaireController implements Initializable {
    double x, y;
    Statement stmt;
    Connexion maConnexion=new Connexion();
    public static int TableSelectedIndex;
    @FXML
    private JFXDialogLayout ContentDialog;
    @FXML
    private StackPane loginStackPane;
    @FXML
    private Pane signUpPane;
    @FXML
    private TableView<Année_Scolaire> A_S_tbl;
    @FXML
    private TableColumn<Année_Scolaire, String> Année_ScolaireColumn;
    @FXML
    private MenuItem DelContextMenu;
    @FXML
    private JFXTextField A_STextField;
    @FXML
    private JFXButton addA_S;
    @FXML
    private JFXButton editA_S;
    @FXML
    private JFXButton delA_S;
    @FXML
    private Label Notification;
    @FXML
    private TextField search_bar;
    @FXML
    private FontAwesomeIconView searchIcon;
    private Label nombreClategNotif;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton close;
    @FXML
    private ContextMenu ContextMenuA_S;
    @FXML
    private Label nombreASNotif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // TODO
        LoadAnneeScolaireTable();
        // Listen for selection changes for Table Client.
            A_S_tbl.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if(newValue==null){System.out.println("Cell selected NULL");}
                
                else {TableSelectedIndex=newValue.getId_AS();
                    System.out.println("Cell selected "+TableSelectedIndex+"");
                }
            });
        // Context menu
            A_S_tbl.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() { 
            @Override
            public void handle(ContextMenuEvent event) { 
                ContextMenuA_S.show(A_S_tbl, event.getScreenX(), event.getScreenY());
            }
        });
    }    

    void LoadAnneeScolaireTable(){
        int nb;
        Année_ScolaireColumn.setCellValueFactory(new PropertyValueFactory<>("Libellé"));
        ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();        
        list=getAnneeScolaireData();
        A_S_tbl.setItems(list);
        editA_S.setDisable(true);
        delA_S.setDisable(true);
        addA_S.setDisable(false);        
        A_STextField.setText("");
        search_bar.setText("");
        nb=list.size();
        nombreASNotif.setText(""+nb+"");   
        Notification.setText("");     
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
void messageDialog(){
        /*JFXDialogLayout content= new JFXDialogLayout();*/
        ContentDialog.setHeading(new Text("Notification"));
        ContentDialog.setBody(new Text("Suppression réussi."));
        JFXDialog dialog=new JFXDialog(loginStackPane, ContentDialog, JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("OK");
        button.setStyle("-fx-background-color :  #0099ff; -fx-text-fill:  white;-fx-cursor: hand;");
        button.setOnAction(new EventHandler<ActionEvent>(){
          @Override
        public void handle(ActionEvent event) {  
            dialog.close();
           
            
        }        
        });
        ContentDialog.setActions(button);
        dialog.show();
    }


    @FXML
    private void ActivateSave_DeleteButtounWhenTableClicked(MouseEvent event) {
         editA_S.setDisable(false);
        delA_S.setDisable(false);  
        addA_S.setDisable(true); 
        try{
                int Index=TableSelectedIndex;
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet resultat= stmt1.executeQuery(" SELECT  * from annee_scolaire WHERE  id_AS='"+Index+"';");
            if(resultat.next()){
                A_STextField.setText(resultat.getString("design"));
            }

        }catch(Exception e){

        }
    }


    @FXML
    private void RefreshTable(MouseEvent event) {
        LoadAnneeScolaireTable();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
         if(event.getSource()== close){
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        } else if(event.getSource()== minimize){
             Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
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
    private void AddAS(MouseEvent event) {
         String AS=A_STextField.getText();
        if (AS.equals("")){
              Notification.setText("Completez le champ ci-dessus.");
          } else {
        if(isInputValid(AS)){
          String requete ="INSERT into annee_scolaire(design) values ('"+AS+"'); ";
        try{
            
            stmt=maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(requete);
             LoadAnneeScolaireTable();
             A_STextField.setText("");
            Notification.setText("");
            
        }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception : " + e) ;
            }
          }
        }
    }
    
    @FXML
    private void SupprimerAS(ActionEvent event) {
         int Index=TableSelectedIndex;
               try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                System.out.println("Index io ="+Index);
                stmt1.executeUpdate("DELETE FROM `inscription` WHERE id_AS='"+Index+"');");
                stmt1.executeUpdate(" DELETE FROM `appartenir` WHERE  id_AS='"+Index+"';");
                stmt1.executeUpdate("DELETE FROM `payer` WHERE id_AS='"+Index+"');");
                stmt1.executeUpdate(" DELETE FROM `payercs` WHERE  id_AS='"+Index+"';");
                stmt1.executeUpdate(" DELETE FROM `caisse` WHERE  id_AS='"+Index+"';");
                stmt1.executeUpdate("DELETE from annee_scolaire WHERE  id_AS='"+Index+"';");
                messageDialog();
                }catch(HeadlessException | SQLException e){
                System.out.println("--> Exception : " + e) ;
                }
            editA_S.setDisable(true);
            delA_S.setDisable(true);
            addA_S.setDisable(false);
            A_STextField.setText("");
             LoadAnneeScolaireTable();
    }

    @FXML
    private void EditAS(MouseEvent event) {
         String AS=A_STextField.getText();
         int Index=TableSelectedIndex;
        if (AS.equals("")){
              Notification.setText("Completez le champ ci-dessus.");
          } else {
        if(isInputValid(AS)){
          String requete ="UPDATE annee_scolaire SET design='"+AS+"' WHERE  id_AS='"+Index+"'; ";
        try{
            
            stmt=maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(requete);
             LoadAnneeScolaireTable();
             A_STextField.setText("");
            Notification.setText("");            
            editA_S.setDisable(true);
            delA_S.setDisable(true);
            addA_S.setDisable(false);
            
        }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception : " + e) ;
            }
          }
        }
    }

    @FXML
    private void DeleteAS(MouseEvent event) {
         int Index=TableSelectedIndex;
               try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                System.out.println("Index io ="+Index);
                stmt1.executeUpdate("DELETE FROM `inscription` WHERE id_AS='"+Index+"';");
                stmt1.executeUpdate(" DELETE FROM `appartenir` WHERE  id_AS='"+Index+"';");
                stmt1.executeUpdate("DELETE FROM `payer` WHERE id_AS='"+Index+"';");
                stmt1.executeUpdate(" DELETE FROM `payercs` WHERE  id_AS='"+Index+"';");
                stmt1.executeUpdate(" DELETE FROM `caisse` WHERE  id_AS='"+Index+"';");
                stmt1.executeUpdate("DELETE from annee_scolaire WHERE  id_AS='"+Index+"';");
                messageDialog();
                }catch(HeadlessException | SQLException e){
                System.out.println("--> Exception : " + e) ;
                }
            editA_S.setDisable(true);
            delA_S.setDisable(true);
            addA_S.setDisable(false);
            A_STextField.setText("");
             LoadAnneeScolaireTable();
    }
    
     public ObservableList<Année_Scolaire> getA_SSearchData() {
      ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();
      String search= search_bar.getText();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from annee_scolaire WHERE  design LIKE '%"+search+"%';");
         
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

    @FXML
    private void SearchAS(KeyEvent event) {
        Année_ScolaireColumn.setCellValueFactory(new PropertyValueFactory<>("Libellé"));
      ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();        
        list=getA_SSearchData();
      A_S_tbl.setItems(list);
                            editA_S.setDisable(true);
                            delA_S.setDisable(true);
                            addA_S.setDisable(false);     
                            A_STextField.setText("");
    }
    private boolean isInputValid(String A_S) {
        Notification.setText("");
        int taille=A_S.length();
        if(taille != 9) {Notification.setText("Année Scolaire Invalide!");return false;}
        else if(A_S.indexOf("-") != 4){Notification.setText("Année Scolaire Invalide!");return false;}
        else {
            String an1="";
            String an2="";
            for(int i=0;i<4;i++){
                an1+=A_S.charAt(i);
            }
            for(int i=5;i<9;i++){
                an2+=A_S.charAt(i);
            }
             try {
               int AS1= Integer.parseInt(an1);                
               int AS2= Integer.parseInt(an2);
               if((AS2-AS1)!=1){
                Notification.setText("Cette date n'est pas successive.");return false;
               }else return true;
            } catch (NumberFormatException e) {
                Notification.setText("Année Scolaire doit être en entier!");return false;
            }
        }
    }
}
