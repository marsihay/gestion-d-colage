/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import static gestion.ecolage.Controller.ClasseController.TableSelectedIndex;
import gestion.ecolage.Model.Niveau;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marsihay
 */
public class Ecolage_By_NiveauController implements Initializable {

    double x, y;
    Statement stmt;
    Connexion maConnexion=new Connexion();
    @FXML
    private JFXDialogLayout ContentDialog;
    @FXML
    private StackPane loginStackPane;
    @FXML
    private Pane signUpPane;
    @FXML
    private TableView<Niveau> droit_tbl;
    @FXML
    private TableColumn<Niveau, String> designColumn;
    @FXML
    private TableColumn<Niveau, Integer> montant;
    @FXML
    private JFXTextField CategTextField;
    @FXML
    private Label Notification;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton editbtn;
    @FXML
    private Label nombreNotif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadDroitTable();
         // Listen for selection changes for Table Client.
            droit_tbl.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if(newValue==null){System.out.println("Cell selected NULL");}
                
                else {TableSelectedIndex=newValue.getId_N();
                    System.out.println("Cell selected "+TableSelectedIndex+"");
                }
            });
    }    

     void LoadDroitTable(){
        int nb;
        designColumn.setCellValueFactory(new PropertyValueFactory<>("Libellé"));
        montant.setCellValueFactory(new PropertyValueFactory<>("Frais"));
        ObservableList<Niveau> list = FXCollections.observableArrayList();        
        list=getDroitData();
        droit_tbl.setItems(list);
        editbtn.setDisable(true);       
        CategTextField.setText("");
        nb=list.size();
        nombreNotif.setText(""+nb+"");   
        Notification.setText("");     
    }
      public ObservableList<Niveau> getDroitData() {
      ObservableList<Niveau> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from niveau ORDER BY Id_Niv;");
         while (resultat.next()) {
            Niveau Droit = createDroit(resultat);
            list.add(Droit);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
      }      
      return list;
   }
     private Niveau createDroit(ResultSet rs) throws SQLException {
      Niveau Droit = new Niveau();
        Droit.setId_N(rs.getInt("Id_Niv"));
        Droit.setLibellé(rs.getString("Label_N"));
          Droit.setFrais(rs.getDouble("Frais"));  
      return Droit;
   }

    @FXML
    private void ActivateSave_DeleteButtounWhenTableClicked(MouseEvent event) {
         editbtn.setDisable(false);
        try{
                int Index=TableSelectedIndex;
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet resultat= stmt1.executeQuery(" SELECT  * from niveau WHERE  Id_Niv='"+Index+"';");
            if(resultat.next()){
                CategTextField.setText(resultat.getString("Frais"));
            }

        }catch(Exception e){

        }
    }

    @FXML
    private void EditClateg(MouseEvent event) {
         String Montant=CategTextField.getText();
         int Index=TableSelectedIndex;
        if (Montant.equals("")){
              Notification.setText("Completez le champ ci-dessus.");
          } else {
          String requete ="UPDATE niveau SET Frais='"+Montant+"' WHERE  Id_Niv='"+Index+"'; ";
        try{
            
            stmt=maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(requete);
             LoadDroitTable();
             CategTextField.setText("");
            Notification.setText("");            
            editbtn.setDisable(true);
            
        }catch(HeadlessException | SQLException e){
            System.out.println("--> Exception : " + e) ;
            }
          }
    }

    @FXML
    private void RefreshTable(MouseEvent event) {
        LoadDroitTable();
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
    
}
