/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import gestion.ecolage.Model.Classe;
import gestion.ecolage.Model.Mois;
import gestion.ecolage.Model.Niveau;
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
public class ClasseController implements Initializable {
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
    private MenuItem DelContextMenu;
    @FXML
    private JFXButton addClasse;
    @FXML
    private JFXButton editClasse;
    @FXML
    private JFXButton delClasse;
    @FXML
    private Label Notification;
    @FXML
    private TextField search_bar;
    @FXML
    private FontAwesomeIconView searchIcon;
    @FXML
    private Label nombreClasseNotif;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton close;
    @FXML
    private TableView<Classe> Classe_tbl;
    @FXML
    private TableColumn<Classe, String> Classe;
    @FXML
    private JFXTextField ClasseTextField;
    @FXML
    private ContextMenu ContextMenuClasse;
    @FXML
    private JFXComboBox<Niveau> NiveauCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadNiveau_ComboBox();
         LoadClasseTable();
        // Listen for selection changes for Table Client.
            Classe_tbl.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if(newValue==null){System.out.println("Cell selected NULL");}
                
                else {TableSelectedIndex=newValue.getId_Classe();
                    System.out.println("Cell selected "+TableSelectedIndex+"");
                }
            });
        // Context menu
            Classe_tbl.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() { 
            @Override
            public void handle(ContextMenuEvent event) { 
                ContextMenuClasse.show(Classe_tbl, event.getScreenX(), event.getScreenY());
            }
        });
    }    
     void LoadClasseTable(){
        int nb;
        Classe.setCellValueFactory(new PropertyValueFactory<>("NomClasse"));
        ObservableList<Classe> list = FXCollections.observableArrayList();        
        list=getClasseData();
        Classe_tbl.setItems(list);
        editClasse.setDisable(true);
        delClasse.setDisable(true);
        addClasse.setDisable(false);        
        ClasseTextField.setText("");
        search_bar.setText("");
        nb=list.size();
        nombreClasseNotif.setText(""+nb+"");   
        Notification.setText("");     
    }
      public ObservableList<Classe> getClasseData() {
      ObservableList<Classe> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from classe ORDER BY id_classe;");
         while (resultat.next()) {
            Classe Classe = createClasse(resultat);
            list.add(Classe);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
      }      
      return list;
   }
     private Classe createClasse(ResultSet rs) throws SQLException {
      Classe Classe = new Classe();
          Classe.setId_Classe(rs.getInt("id_classe"));
          Classe.setNomClasse(rs.getString("Label_C"));  
      return Classe;
   }
     void loadNiveau_ComboBox(){
    try{
        //Pour Niveau ComboBOX
        ObservableList<Niveau> listN = FXCollections.observableArrayList();
        listN=getNiveauData(); 
        NiveauCombo.setItems(listN);
        NiveauCombo.setValue(listN.get(0));
    }catch(NullPointerException | IndexOutOfBoundsException e){
    }
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
    @FXML
    private void ActivateSave_DeleteButtounWhenTableClicked(MouseEvent event) {
        editClasse.setDisable(false);
        delClasse.setDisable(false);  
        addClasse.setDisable(true); 
        try{
                int Index=TableSelectedIndex;
                ObservableList<Niveau> listN = FXCollections.observableArrayList();
                listN=getNiveauData(); 
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet resultat= stmt1.executeQuery(" SELECT  * from classe WHERE  id_classe='"+Index+"';");
            if(resultat.next()){
                int nb=(resultat.getInt("id_Niv")-1);
                NiveauCombo.setValue(listN.get(nb));
                System.out.println(""+(resultat.getInt("id_Niv")-1));
                ClasseTextField.setText(resultat.getString("Label_C"));
            }

        }catch(Exception e){

        }
    }

    @FXML
    private void RefreshTable(MouseEvent event) {
        LoadClasseTable();
    }

    
void messageDialog(){
        /*JFXDialogLayout content= new JFXDialogLayout();*/
        ContentDialog.setHeading(new Text("Notification"));
        ContentDialog.setBody(new Text("Classe a été supprimer correctement."));
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
    private void AddClasse(MouseEvent event) {
        String classe=ClasseTextField.getText();
        Niveau id=NiveauCombo.getValue();
            int id_N=id.getId_N();   
        if (classe.equals("")){
              Notification.setText("Completez le champ ci-dessus.");
          } if(id_N>3 || id_N<1){
              Notification.setText("Sélectionner un niveau.");
          } else {
          String requete ="INSERT INTO `classe`(`id_Niv`, `Label_C`) VALUES ("+id_N+",'"+classe+"'); ";
        try{
            
            stmt=maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(requete);
             LoadClasseTable();
             ClasseTextField.setText("");
            Notification.setText("");
            
        }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception : " + e) ;
            }
          }
    }

    @FXML
    private void EditClasse(MouseEvent event) {
         String classe=ClasseTextField.getText();
         int Index=TableSelectedIndex;
        Niveau id=NiveauCombo.getValue();
            int id_N=id.getId_N();   
        if (classe.equals("")){
              Notification.setText("Completez le champ ci-dessus.");
          } if(id_N>3 || id_N<1){
              Notification.setText("Sélectionner un niveau.");
          } else {
          String requete ="UPDATE classe SET id_Niv="+id_N+", Label_C='"+classe+"' WHERE  id_classe='"+Index+"'; ";
        try{
            
            stmt=maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(requete);
             LoadClasseTable();
             ClasseTextField.setText("");
            Notification.setText("");            
            editClasse.setDisable(true);
            delClasse.setDisable(true);
            addClasse.setDisable(false);
            
        }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception : " + e) ;
            }
          }
    }

    @FXML
    private void DeleteClasse(MouseEvent event) {
          int Index=TableSelectedIndex;
               try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                /*stmt1.executeUpdate("DELETE from emprunter where id_Emp=ANY (SELECT id_emp from emprunteur where id_Classe='"+Index+"');");
                stmt1.executeUpdate(" DELETE from emprunteur WHERE  id_Classe='"+Index+"';");*/
                stmt1.executeUpdate(" DELETE from classe WHERE  id_classe='"+Index+"';");
                messageDialog();
                }catch(HeadlessException | SQLException e){
                System.out.println("--> Exception : " + e) ;
                }
            editClasse.setDisable(true);
            delClasse.setDisable(true);
            addClasse.setDisable(false);
            ClasseTextField.setText("");
             LoadClasseTable();
    }

    public ObservableList<Classe> getClasseSearchData() {
      ObservableList<Classe> list = FXCollections.observableArrayList();
      String search= search_bar.getText();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT * from classe WHERE  Label_C LIKE '%"+search+"%';");
         
         while (resultat.next()) {
            Classe p = createClasse(resultat);
            list.add(p);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
      }      
      return list;
   }
    @FXML
    private void SearchClasse(KeyEvent event) {
        Classe.setCellValueFactory(new PropertyValueFactory<>("NomClasse"));
      ObservableList<Classe> list = FXCollections.observableArrayList();        
        list=getClasseSearchData();
      Classe_tbl.setItems(list);
                            editClasse.setDisable(true);
                            delClasse.setDisable(true);
                            addClasse.setDisable(false);     
                            ClasseTextField.setText("");
    }
    // Suppression par Context Menu
     @FXML
    private void SupprimerClasse(ActionEvent event) {
        int Index=TableSelectedIndex;
               try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                /*stmt1.executeUpdate("DELETE from emprunter where id_Emp=ANY (SELECT id_emp from emprunteur where id_Classe='"+Index+"');");
                stmt1.executeUpdate(" DELETE from emprunteur WHERE  id_Classe='"+Index+"';");*/
                stmt1.executeUpdate(" DELETE from classe WHERE  id_classe='"+Index+"';");
                messageDialog();
                }catch(HeadlessException | SQLException e){
                System.out.println("--> Exception : " + e) ;
                }
            editClasse.setDisable(true);
            delClasse.setDisable(true);
            addClasse.setDisable(false);
            ClasseTextField.setText("");
             LoadClasseTable();
    }
    
}
