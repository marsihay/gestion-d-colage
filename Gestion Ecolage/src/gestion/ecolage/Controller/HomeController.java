/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FlipInX;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import static gestion.ecolage.Controller.StyleWindow.makeBlur;
import static gestion.ecolage.Controller.StyleWindow.EjectBlur;
import gestion.ecolage.Model.Année_Scolaire;
import gestion.ecolage.Model.Catégorie;
import gestion.ecolage.Model.Classe;
import gestion.ecolage.Model.Etudiant;
import gestion.ecolage.Model.Historique;
import gestion.ecolage.Model.Inscrit_Table;
import gestion.ecolage.Model.List_Classe;
import gestion.ecolage.Model.Mois;
import gestion.ecolage.Model.Niveau;
import gestion.ecolage.Model.PaimentsTable;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import static java.sql.JDBCType.NULL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.management.Notification;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.TextFields;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Marsihay
 */
public class HomeController implements Initializable {

    double x, y;
    int Id_Fin_Eco;
    public static int TableListeSelectedIndex;
    public static int IdentifiantAS;
    public String Username=LoginController.Username;Statement stmt;
    Connexion maConnexion=new Connexion();
    @FXML
    private Pane header;
    @FXML
    private JFXButton info;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton BTN1;
    @FXML
    private JFXButton BTN2;
    @FXML
    private JFXButton BTN3;
    @FXML
    private JFXButton BTN4;
    @FXML
    private JFXButton BTN5;
    @FXML
    private Line lineBtn1;
    @FXML
    private Line lineBtn2;
    @FXML
    private Line lineBtn3;
    @FXML
    private Line lineBtn4;
    @FXML
    private Line lineBtn5;
    @FXML
    private Pane home;
    @FXML
    private Pane autre_pnl;
    @FXML
    private AnchorPane HomeAnchorPane;
    @FXML
    private AnchorPane PANEL;
    @FXML
    private Pane Categ_pnl;
    @FXML
    private Pane place_pnl;
    @FXML
    private Pane anneeS_pnl;
    @FXML
    private Pane Classe_pnl;
    @FXML
    private Pane AboutThisAppPanel;
    @FXML
    private FontAwesomeIconView powerIcon;
    @FXML
    private JFXButton LogOut;
    @FXML
    private Pane LivreSummaryPnl;
    @FXML
    private Pane EmpruntSummaryPnl;
    @FXML
    private Pane BilanSummaryPnl;
    @FXML
    private TableView<PaimentsTable> Paiment_Table;
    @FXML
    private JFXButton Inscription_BTN;
    @FXML
    private JFXButton Ecolage_BTN;
    @FXML
    private JFXButton Cons_Subv_BTN;
    @FXML
    private JFXDatePicker Paiment_DateP;
    @FXML
    private JFXComboBox<Année_Scolaire> Paiment_Combo;
    @FXML
    private TableColumn<PaimentsTable, Integer> Num_Matr;
    @FXML
    private TableColumn<PaimentsTable, String> Nom_Prenom;
    @FXML
    private TableColumn<PaimentsTable, String> paye;
    @FXML
    private TableColumn<PaimentsTable, Double> Montant;
    @FXML
    private Label Montant_Total;
    @FXML
    private Label montant_To_Text;
    @FXML
    private Pane Paiments;
    @FXML
    private Pane Controles;
    @FXML
    private Pane Repartition;
    @FXML
    private Pane Listes_ParClasse;
    @FXML
    private Pane Liste_Inscript;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnPricing;
    @FXML
    private JFXButton btnControls;
    @FXML
    private JFXComboBox<Niveau> NiveauComboBOX;
    @FXML
    private Label NbTotLabel;
    @FXML
    private TableView<Inscrit_Table> RepartionTable;
    @FXML
    private TableColumn<Inscrit_Table, Integer> Matr_RepartTable;
    @FXML
    private TableColumn<Inscrit_Table, String> nom_RepartTable;
    @FXML
    private TableColumn<Inscrit_Table, String> prenom_RepartTable;
    @FXML
    private TableColumn<Inscrit_Table, String> Obs_repartTbl;
    @FXML
    private TableColumn<Inscrit_Table, Date> DateInsc_RepartTable;
    @FXML
    private JFXComboBox<Année_Scolaire> Année_ScoRepart;
    @FXML
    private JFXComboBox<Classe> ClasseComboBOX;
    @FXML
    private JFXComboBox<Année_Scolaire> Année_ScoRepart_Classe;
    @FXML
    private Label NbClasse;
    @FXML
    private TableView<List_Classe> RepartionTable1;
    @FXML
    private TableColumn<List_Classe, Integer> Num_Tbl;
    @FXML
    private TableColumn<List_Classe, Integer> Matr_RepartTableClasse;
    @FXML
    private TableColumn<List_Classe, String> nom_RepartTableClasse;
    @FXML
    private TableColumn<List_Classe, String> prenom_RepartTableCasse;
    @FXML
    private Pane RepartitionElèves;
    @FXML
    private JFXComboBox<Classe> ClasseRepart;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_Repart;
    @FXML
    private JFXButton InscrireBTN;
    @FXML
    private JFXButton canceladd;
    @FXML
    private JFXTextField N1;
    @FXML
    private JFXTextField M1;
    @FXML
    private JFXTextField N2;
    @FXML
    private JFXTextField M2;
    @FXML
    private JFXTextField N3;
    @FXML
    private JFXTextField M3;
    @FXML
    private JFXTextField N4;
    @FXML
    private JFXTextField M4;
    @FXML
    private JFXTextField N5;
    @FXML
    private JFXTextField M5;
    @FXML
    private JFXTextField N6;
    @FXML
    private JFXTextField M6;
    @FXML
    private JFXTextField N7;
    @FXML
    private JFXTextField M7;
    @FXML
    private JFXTextField N8;
    @FXML
    private JFXTextField M8;
    @FXML
    private JFXTextField N9;
    @FXML
    private JFXTextField M9;
    @FXML
    private JFXTextField N10;
    @FXML
    private JFXTextField M10;
    @FXML
    private JFXTextField N11;
    @FXML
    private JFXTextField M11;
    @FXML
    private JFXTextField N12;
    @FXML
    private JFXTextField M12;
    @FXML
    private JFXTextField N13;
    @FXML
    private JFXTextField M13;
    @FXML
    private JFXTextField N14;
    @FXML
    private JFXTextField M14;
    @FXML
    private JFXTextField N15;
    @FXML
    private JFXTextField M15;
    @FXML
    private JFXTextField N16;
    @FXML
    private JFXTextField M16;
    @FXML
    private JFXTextField N17;
    @FXML
    private JFXTextField M17;
    @FXML
    private JFXTextField N18;
    @FXML
    private JFXTextField M18;
    @FXML
    private JFXTextField N19;
    @FXML
    private JFXTextField M19;
    @FXML
    private JFXTextField N20;
    @FXML
    private JFXTextField M20;
    @FXML
    private JFXTextField N21;
    @FXML
    private JFXTextField M21;
    @FXML
    private JFXTextField N22;
    @FXML
    private JFXTextField M22;
    @FXML
    private JFXTextField N23;
    @FXML
    private JFXTextField M23;
    @FXML
    private JFXTextField N24;
    @FXML
    private JFXTextField M24;
    @FXML
    private JFXTextField N25;
    @FXML
    private JFXTextField M25;
    @FXML
    private JFXTextField N26;
    @FXML
    private JFXTextField M26;
    @FXML
    private JFXTextField N27;
    @FXML
    private JFXTextField M27;
    @FXML
    private JFXTextField N28;
    @FXML
    private JFXTextField M28;
    @FXML
    private JFXTextField N29;
    @FXML
    private JFXTextField M29;
    @FXML
    private JFXTextField N30;
    @FXML
    private JFXTextField M30;
    @FXML
    private JFXTextField N31;
    @FXML
    private JFXTextField M31;
    @FXML
    private JFXTextField N32;
    @FXML
    private JFXTextField M32;
    @FXML
    private JFXTextField N33;
    @FXML
    private JFXTextField M33;
    @FXML
    private JFXTextField N34;
    @FXML
    private JFXTextField M34;
    @FXML
    private JFXTextField N35;
    @FXML
    private JFXTextField N36;
    @FXML
    private JFXTextField M36;
    @FXML
    private JFXTextField N37;
    @FXML
    private JFXTextField M37;
    @FXML
    private JFXTextField N38;
    @FXML
    private JFXTextField M38;
    @FXML
    private JFXTextField N39;
    @FXML
    private JFXTextField M39;
    @FXML
    private JFXTextField N40;
    @FXML
    private JFXTextField M40;
    @FXML
    private Label notification;
    @FXML
    private JFXTextField M35;
    @FXML
    private Pane Liste_Pas_PayéECOLAGE;
    @FXML
    private JFXComboBox<Mois> MoisCombo_Box;
    @FXML
    private JFXComboBox<Classe> ClasseCombo_Box1;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_Combo_Ecolage;
    @FXML
    private Label nb_EcoLbl;
    @FXML
    private TableView<List_Classe> NonPayéEcolageTable;
    @FXML
    private TableColumn<List_Classe, Integer> Num_Eco;
    @FXML
    private TableColumn<List_Classe, Integer> Matr_EcolageTable1;
    @FXML
    private TableColumn<List_Classe, String> nom_ClasseTable1;
    @FXML
    private TableColumn<List_Classe, String> prenom_ClasseTable1;
    @FXML
    private Pane Listes_PasPayéCS;
    @FXML
    private Label C_S_Lbl;
    @FXML
    private TableView<List_Classe> NonPayéCS_Table;
    @FXML
    private TableColumn<List_Classe, Integer> Num_TblCS;
    @FXML
    private TableColumn<List_Classe, Integer> Matr_RepartTableCS;
    @FXML
    private TableColumn<List_Classe, String> nom_RepartTableCS;
    @FXML
    private TableColumn<List_Classe, String> prenom_RepartTableCS;
    @FXML
    private Pane RechercheElève;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_Search;
    @FXML
    private Label notificationSearch;
    @FXML
    private JFXButton Ecolage_Contrôle;
    @FXML
    private JFXButton Cons_Subv;
    @FXML
    private JFXButton SearchBTN;
    @FXML
    private JFXComboBox<Classe> Cons_SubvClasseComboBox;
    @FXML
    private JFXComboBox<Année_Scolaire> Année_ScoCons_Subv;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label NomLbl;
    @FXML
    private Label PrenomsLbl;
    @FXML
    private Label NbLbl;
    @FXML
    private Label EtatLbl;
    @FXML
    private Label ClasseLabel;
    @FXML
    private Label NuméroLbl;
    @FXML
    private Label Date_InscLbl;
    @FXML
    private Label EcolagePayéLbl;
    @FXML
    private Label EcolageRestantLbl;
    @FXML
    private Label ConstructionLbl;
    @FXML
    private Label SubvLbl;
    @FXML
    private Pane panelResult;
    @FXML
    private Pane Construction_Subv;
    @FXML
    private JFXButton Exporter;
    @FXML
    private Pane ExportRECAP;
    @FXML
    private JFXComboBox<Année_Scolaire> AS_EXPORT;
    @FXML
    private JFXButton FraisSco_BTN;
    @FXML
    private JFXButton CahierSco_BTN1;
    @FXML
    private MenuItem EditByContext;
    @FXML
    private ContextMenu ListebyClasse_Tbl;
    @FXML
    private MenuItem DeleteByContext1;
    @FXML
    private Pane Siblings_Panel;
    @FXML
    private Label NbTotLabel1;
    @FXML
    private Label Zandry1;
    @FXML
    private Label Zandry2;
    @FXML
    private Label Zandry3;
    @FXML
    private Label Zandry4;
    @FXML
    private TextField SearchBar1;
    @FXML
    private JFXButton SearchBTN1;
    @FXML
    private JFXButton ZokyBTN;
    @FXML
    private TableView<Historique> History_Tbl;
    @FXML
    private TableColumn<Historique, Date> Date_C;
    @FXML
    private TableColumn<Historique, String> Design_C;
    @FXML
    private TableColumn<Historique, Double> Vola_C;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LogOut.setText(Username);
        Paiment_DateP.setValue(LocalDate.now());
        loadAll_AS_ComboBox();
        loadClasseComboBox();
        // TODO
         ChangeLine(lineBtn1,lineBtn2,lineBtn3,lineBtn4,lineBtn5);
         ChangerCouleur(BTN1,BTN2,BTN3,BTN4,BTN5);
         loadPaimentTable();
          // Listen for selection changes for Table Liste par Classe.
            RepartionTable1.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if(newValue==null){System.out.println("Cell selected NULL");TableListeSelectedIndex=0;}
                
                else {TableListeSelectedIndex=newValue.getNumMatrC();
                    System.out.println("Cell selected "+TableListeSelectedIndex+"");
                }
            });
            RepartionTable1.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
                ListebyClasse_Tbl.show(RepartionTable1, event.getScreenX(), event.getScreenY());
            }
        });
    }    
        
     //Rafraichir tous les tables
    public void Rafraichir_tous_les_tables(){ 
        loadPaimentTable();
        loadListe_PasEncorePayéEcolage_Table();
        loadListe_PasEncorePayéCons_Subv_Table();
        loadINSCRIT_Table();
        loadListe_Par_Classe_Table();
    }
     // Fonctiion qui affiche les MODAL
     private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/gestion/ecolage/image/LOGOGB M.png"));
            stage.setTitle("Gestion d'une Bibliothèque");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("primaryStage focused : "+newValue);
            if(!newValue){Rafraichir_tous_les_tables();}
        });
            stage.show();
            new FlipInX(root).play();
            makeBlur(HomeAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      // Charger tous les ANNEE SCOLAIRE ComboBox
void loadAll_AS_ComboBox(){
    try{
        ObservableList<Année_Scolaire> list = FXCollections.observableArrayList();
        list=getAnneeScolaireData(); 
        Paiment_Combo.setItems(list);
        Année_ScoRepart.setItems(list);
        AS_Repart.setItems(list);
        Année_ScoRepart_Classe.setItems(list);
        AS_Combo_Ecolage.setItems(list);
        Année_ScoCons_Subv.setItems(list);
        AS_Search.setItems(list);
        AS_EXPORT.setItems(list);
        int size=list.size();
        Paiment_Combo.setValue(list.get(size-1));   
        Année_ScoRepart.setValue(list.get(size-1));
        AS_Repart.setValue(list.get(size-1));
        Année_ScoRepart_Classe.setValue(list.get(size-1));
        AS_Combo_Ecolage.setValue(list.get(size-1));
        Année_ScoCons_Subv.setValue(list.get(size-1));
        AS_Search.setValue(list.get(size-1));
        AS_EXPORT.setValue(list.get(size-1));
        //Pour Niveau ComboBOX
        ObservableList<Niveau> listN = FXCollections.observableArrayList();
        listN=getNiveauData(); 
        NiveauComboBOX.setItems(listN);
        NiveauComboBOX.setValue(listN.get(0));
        //Pour Mois ComboBox
        ObservableList<Mois> listM = FXCollections.observableArrayList();
        listM=getMoisData(); 
        MoisCombo_Box.setItems(listM);
        MoisCombo_Box.setValue(listM.get(0));
        //TOKONY hasiana Switch momban'ny Mois
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
     
    public ObservableList<Mois> getMoisData() {
      ObservableList<Mois> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from ecolage ORDER BY id_Eco;");
         while (resultat.next()) {
            Mois AS = createMois(resultat);
            list.add(AS);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException ex) {
      }      
      return list;
   }
     private Mois createMois(ResultSet rs) throws SQLException {
      Mois AS = new Mois();
          AS.setId_Mois(rs.getInt("id_Eco"));
          AS.setLibellé(rs.getString("Libelle_Eco"));  
      return AS;
   }
     // Pour Classe COMBOBOX
     void loadClasseComboBox(){
         try{
        ObservableList<Classe> list = FXCollections.observableArrayList();
        list=getClasseData();
        ClasseRepart.setItems(list);  
        ClasseComboBOX.setItems(list);
        ClasseComboBOX.setValue(list.get(0));
        ClasseCombo_Box1.setItems(list);
        ClasseCombo_Box1.setValue(list.get(0));
        Cons_SubvClasseComboBox.setItems(list);
        Cons_SubvClasseComboBox.setValue(list.get(0));
         }catch(NullPointerException ex){ System.out.println("MISY NULL ao");}
    }
     public ObservableList<Classe> getClasseData() {
      ObservableList<Classe> list = FXCollections.observableArrayList();
      try {
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from classe order by id_classe;");
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
     
    @FXML
    private void handleButtonAction(ActionEvent event) {
         if(event.getSource()== close){
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        } else if(event.getSource()== minimize){
             Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
        }else if(event.getSource()== BTN1){
             home.toFront();
            new FadeIn(home).play();
            ChangeLine(lineBtn1,lineBtn2,lineBtn3,lineBtn4,lineBtn5);
            ChangerCouleur(BTN1,BTN2,BTN3,BTN4,BTN5);
        }else if(event.getSource()== BTN2){
             Paiments.toFront();
            new FadeIn(Paiments).play();
            ChangeLine(lineBtn2,lineBtn1,lineBtn3,lineBtn4,lineBtn5);
            ChangerCouleur(BTN2,BTN1,BTN3,BTN4,BTN5);            
            loadAll_AS_ComboBox(); 
            loadClasseComboBox();
            Rafraichir_tous_les_tables();
        }else if(event.getSource()== BTN3){
             Repartition.toFront();
            new FadeIn(Repartition).play();
            ChangeLine(lineBtn3,lineBtn2,lineBtn1,lineBtn4,lineBtn5);
            ChangerCouleur(BTN3,BTN2,BTN1,BTN4,BTN5);
            //loadINSCRIT_Table();
            loadAll_AS_ComboBox(); 
            loadClasseComboBox();
            Rafraichir_tous_les_tables();
        }else if(event.getSource()== BTN4){
             Controles.toFront();
            new FadeIn(Controles).play();
            ChangeLine(lineBtn4,lineBtn2,lineBtn3,lineBtn1,lineBtn5);
            ChangerCouleur(BTN4,BTN2,BTN3,BTN1,BTN5);            
            loadAll_AS_ComboBox(); 
            loadClasseComboBox();
            Rafraichir_tous_les_tables();
        }else if(event.getSource()== BTN5){
             autre_pnl.toFront();
            new FadeIn(autre_pnl).play();
            ChangeLine(lineBtn5,lineBtn2,lineBtn3,lineBtn4,lineBtn1);
            ChangerCouleur(BTN5,BTN2,BTN3,BTN4,BTN1);
        }else if(event.getSource()== info){
          // ************* PopOver Info APP ************************       
        PopOver popOver = new PopOver(AboutThisAppPanel);
        AboutThisAppPanel.setVisible(true);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.show(info);
        }
    }

      public void ChangerCouleur(JFXButton btn1,JFXButton btn2,JFXButton btn3,JFXButton btn4,JFXButton btn5){
   btn1.setStyle(" -fx-cursor: hand;\n" +
"    -fx-border-color: #e6e600;\n" +
"    -fx-background-color:white;"+
"    -fx-border-width: 2px 2px 5px 2px;");
    btn2.setStyle(
" \n" +
"    -fx-text-fill: black;\n" +
"    -fx-border-style: solid;\n" +
"    -fx-border-color:  #e6e600 ;\n" +
"    -fx-border-radius: 100px 100px 10px 10px;\n" +
"    -fx-border-width: 2px;\n" +
"    -fx-background-radius:100px 100px 10px 10px;");
    btn3.setStyle(
"\n" +
"    -fx-text-fill: black;\n" +
"    -fx-border-style: solid;\n" +
"    -fx-border-color:  #e6e600 ;\n" +
"    -fx-border-radius: 100px 100px 10px 10px;\n" +
"    -fx-border-width: 2px;\n" +
"    -fx-background-radius:100px 100px 10px 10px;");
    btn4.setStyle(
"    -fx-text-fill: black;\n" +
"    -fx-border-style: solid;\n" +
"    -fx-border-color:  #e6e600 ;\n" +
"    -fx-border-radius: 100px 100px 10px 10px;\n" +
"    -fx-border-width: 2px;\n" +
"    -fx-background-radius:100px 100px 10px 10px;");
    btn5.setStyle(
"    -fx-text-fill: black;\n" +
"    -fx-border-style: solid;\n" +
"    -fx-border-color:  #e6e600 ;\n" +
"    -fx-border-radius: 100px 100px 10px 10px;\n" +
"    -fx-border-width: 2px;\n" +
"    -fx-background-radius:100px 100px 10px 10px;");
   }
      
        public void ChangerCouleurControle(JFXButton btn1,JFXButton btn2,JFXButton btn3,JFXButton btn4){
   btn1.setStyle("-fx-background-color: black;    \n" +
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
"    -fx-border-color: #e6e600;\n" +
           "-fx-font-weight: bold;"+
       " -fx-font-size: 14px;"+
"    -fx-border-width: 0px 0px 0px 4px;");
    btn2.setStyle(
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
           "-fx-font-weight: normal;"+
       " -fx-font-size: 14px;"+
"    -fx-border-color: white;\n" +
"    -fx-border-width: 0px 0px 0px 0px;");
    btn3.setStyle(
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
           "-fx-font-weight: normal;"+
       " -fx-font-size: 14px;"+
"    -fx-border-color: white;\n" +
"    -fx-border-width: 0px 0px 0px 0px;");
    btn4.setStyle(
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
           "-fx-font-weight: normal;"+
       " -fx-font-size: 14px;"+
"    -fx-border-color: white;\n" +
"    -fx-border-width: 0px 0px 0px 0px;");
   }
        public void ChangerCouleurRepartition(JFXButton btn1,JFXButton btn2,JFXButton btn3,JFXButton btn4){
   btn1.setStyle("-fx-background-color: black;    \n" +
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
"    -fx-border-color:  #2E75B6;\n" +
           "-fx-font-weight: bold;"+
       " -fx-font-size: 14px;"+
"    -fx-border-width: 0px 0px 0px 4px;");
    btn2.setStyle(
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
           "-fx-font-weight: normal;"+
       " -fx-font-size: 14px;"+
"    -fx-border-color: white;\n" +
"    -fx-border-width: 0px 0px 0px 0px;");
    btn3.setStyle(
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
           "-fx-font-weight: normal;"+
       " -fx-font-size: 14px;"+
"    -fx-border-color: white;\n" +
"    -fx-border-width: 0px 0px 0px 0px;");
    btn4.setStyle(
"    -fx-text-fill:#ffffff;\n" +
"    -fx-cursor: hand;\n" +
           "-fx-font-weight: normal;"+
       " -fx-font-size: 14px;"+
"    -fx-border-color: white;\n" +
"    -fx-border-width: 0px 0px 0px 0px;");
   }
     public void ChangeLine(Line l1,Line l2,Line l3,Line l4,Line l5){
         Color c=Color.web("0xe6e600"); Color T=Color.web("0x0099ff");
     l1.setStroke(c);
     l2.setStroke(T);
     l3.setStroke(T);
     l4.setStroke(T);
     l5.setStroke(T);
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
    // Tray NOTIFICATION
    void messageBookRenduDone(LocalDate date) throws InterruptedException{
       String title = "Rendu Complète";
        String message ="Le livre a été rendu le "+ date;
        Image Img=new Image("/gestion_bibliotheque/image/icons8_Ok_96px.png");
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
     void messageBookRenduFail(LocalDate date) throws InterruptedException{
       String title = "Un erreur";
        String message = "La "+ date+" est inférieur à la date d'emprunt.";
        Image Img=new Image("/gestion_bibliotheque/image/icons8_High_Priority_96px.png");
        NotificationType notification = NotificationType.WARNING;
        
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(notification);
        tray.setAnimationType(AnimationType.POPUP);        
        tray.setRectangleFill(Paint.valueOf("#F44336"));
        tray.setImage(Img);
        tray.showAndDismiss(Duration.seconds(5)); 
        playWarningSound();
    }
     public static LocalDate ConvertStringtoLocalDate(String date) {
        LocalDate localDate= LocalDate.parse(date);
        return localDate;
    }   
    @FXML
    private void Déconnecter(MouseEvent event) throws IOException {
         Node node=(Node) event.getSource();
            Stage stage=(Stage)node.getScene().getWindow();
            stage.close();            
            Parent home = FXMLLoader.load(getClass().getResource("/gestion/ecolage/View/login.fxml"));
            Scene scene = new Scene(home);
            stage.setScene(scene);
            new FlipInX(home).play();
            stage.show();  
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

    private PaimentsTable createPaimentTable(ResultSet rs) {
      PaimentsTable T = new PaimentsTable();
      try {
          T.setNum_Matr(rs.getInt("Matricule"));
          T.setNom_Prenoms(rs.getString("Nom")+" "+rs.getString("Prenoms"));
          T.setPaye(rs.getString("Paiment"));
          T.setMontant(rs.getDouble("Vola"));
      } catch (SQLException ex) {
      }
      return T;
   }
    
     void loadPaimentTable()
    {  
        try{
        Num_Matr.setCellValueFactory(new PropertyValueFactory<>("Num_Matr"));
        Nom_Prenom.setCellValueFactory(new PropertyValueFactory<>("Nom_Prenoms"));
        paye.setCellValueFactory(new PropertyValueFactory<>("Paye"));
        Montant.setCellValueFactory(new PropertyValueFactory<>("Montant"));
      ObservableList<PaimentsTable> list = FXCollections.observableArrayList();        
        list=getPaimentT();
      Paiment_Table.setItems(list);
        } catch( NullPointerException ex){}
    }
     
      public ObservableList<PaimentsTable> getPaimentT() {
      ObservableList<PaimentsTable> list = FXCollections.observableArrayList();
      try {
          Année_Scolaire AS=Paiment_Combo.getValue();
            int id_AS=AS.getId_AS();            
        LocalDate date=Paiment_DateP.getValue(); 
        LoadMontantTotal(date, id_AS);
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT etudiant.Matricule,etudiant.Nom,etudiant.Prenoms,caisse.Paiment,caisse.Vola from caisse,etudiant "
                 + "WHERE caisse.Num_Matr=etudiant.Matricule and caisse.id_AS="+id_AS+" and caisse.Date='"+date+"' ORDER BY caisse.id_HS");
         
         while (resultat.next()) {
            PaimentsTable p = createPaimentTable(resultat);
            list.add(p);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException ex) {
      }      
      return list;
   }
      
    public void LoadMontantTotal(LocalDate d,int id){
        String requete ="SELECT SUM(Vola) AS Montant From caisse where Date='"+d+"' and id_AS="+id+";";
            try {
                Double Total = 0.00;
                stmt=maConnexion.ObtenirConnexion().createStatement();
                ResultSet result= stmt.executeQuery(requete); 
                while (result.next()) {    
                    Total=result.getDouble("Montant");
                    Montant_Total.setText(""+Total+" Ar"); 
                }
                
      RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT);
 	montant_To_Text.setText(""+rbnf.format(Total)+" Ariary");
            }catch (SQLException ex) {
      } 
      }
    @FXML
    private void FaireInscription(MouseEvent event) {
        loadStage("/gestion/ecolage/View/Inscription.fxml");     
    }

    @FXML
    private void ejectBlur(MouseEvent event) {
        EjectBlur(HomeAnchorPane);
    }
    
    @FXML
    private void PayerEcolage(MouseEvent event) {        
        loadStage("/gestion/ecolage/View/Ecolage.fxml");  
    }

    @FXML
    private void PayerCons_Subv(MouseEvent event) {
        loadStage("/gestion/ecolage/View/Cons_Subv.fxml");  
    }

    @FXML
    private void Date_Paiment_Changed(ActionEvent event) {
        loadPaimentTable();
    }

    @FXML
    private void Année_ScolaireChanged(ActionEvent event) {
        loadPaimentTable();
    }

    @FXML
    private void Inscrit_Listes(ActionEvent event) {
        ChangerCouleurRepartition(btnHome,btnControls,btnPricing,ZokyBTN);
             Liste_Inscript.toFront();
            new FadeIn(Liste_Inscript).play();
    }

    @FXML
    private void Repartitions(ActionEvent event) {
        loadClasseComboBox();
        ChangerCouleurRepartition(btnControls,btnHome,btnPricing,ZokyBTN);
             RepartitionElèves.toFront();
            new FadeIn(RepartitionElèves).play();
    }
    
    @FXML
    private void Liste_Classe(ActionEvent event) {
        ChangerCouleurRepartition(btnPricing,btnControls,btnHome,ZokyBTN);
        loadClasseComboBox();
        loadListe_Par_Classe_Table();
             Listes_ParClasse.toFront();
            new FadeIn(Listes_ParClasse).play();
    }
    public void LoadNameSuggest(){
        ArrayList<String> NumM = new ArrayList<String>();
         NumM=getSuggest();
        TextFields.bindAutoCompletion(SearchBar1, NumM);
    }
    @FXML
    private void ShowZoky_Panel(ActionEvent event) {
         LoadNameSuggest();
        ChangerCouleurRepartition(ZokyBTN,btnPricing,btnControls,btnHome);        
             Siblings_Panel.toFront();
            new FadeIn(Siblings_Panel).play();
    }
    
    @FXML
    private void ShowListeZandry(ActionEvent event) {
            NbTotLabel1.setText("0");            
             Zandry1.setText("");Zandry1.setVisible(false);
             Zandry2.setText("");Zandry2.setVisible(false);
             Zandry3.setText("");Zandry3.setVisible(false);
             Zandry4.setText("");Zandry4.setVisible(false);
         //Ajoter automatique le Matricule Zandry
          try {
              
             Boolean M=false;
            String NumMatr=Get_Num_Matricule(SearchBar1.getText());
             ArrayList<String> Matr_Zandry = new ArrayList<String>();
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Matricule,Nom, Prenoms from etudiant where Matricule_Zoky="+NumMatr+";");
         while (resultat.next()) {
             M=true;
             Matr_Zandry.add(resultat.getString("Matricule")+"- "+resultat.getString("Nom")+" "+resultat.getString("Prenoms"));
         }
         if(!M){
            System.out.println("--> Tsy misy zandry: ") ;
             Zandry1.setText("");Zandry1.setVisible(false);
             Zandry2.setText("");Zandry2.setVisible(false);
             Zandry3.setText("");Zandry3.setVisible(false);
             Zandry4.setText("");Zandry4.setVisible(false);
             } else {
            String vide="";
            NbTotLabel1.setText(Matr_Zandry.size()+"");
            if(Matr_Zandry.get(0)!= null || Matr_Zandry.get(0).length() != 0){
             Zandry1.setVisible(true);Zandry1.setText(Matr_Zandry.get(0));
            }else {
             Zandry1.setText("");Zandry1.setVisible(false);
            }
             if(Matr_Zandry.get(1)!= vide){
             Zandry2.setText(Matr_Zandry.get(1));Zandry2.setVisible(true);
            }else {
             Zandry2.setText("");Zandry2.setVisible(false);
            }
              if(Matr_Zandry.get(2)!= vide){
             Zandry3.setText(Matr_Zandry.get(2));Zandry3.setVisible(true);
            }else {
             Zandry3.setText("");Zandry3.setVisible(false);
            }
               if(Matr_Zandry.get(3)!= vide){
             Zandry4.setText(Matr_Zandry.get(3));Zandry4.setVisible(true);
            }else {
             Zandry4.setText("");Zandry4.setVisible(false);
            }
         }
         Matr_Zandry.clear();
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
    }
    /*
     POUR LA REPARTITION DES ELEVES
    */    
    
     void loadINSCRIT_Table()
    {  
        try{
        Matr_RepartTable.setCellValueFactory(new PropertyValueFactory<>("NumMatr"));
        nom_RepartTable.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenom_RepartTable.setCellValueFactory(new PropertyValueFactory<>("Prenoms"));
        Obs_repartTbl.setCellValueFactory(new PropertyValueFactory<>("OBS"));
        DateInsc_RepartTable.setCellValueFactory(new PropertyValueFactory<>("Date_Insc"));
      ObservableList<Inscrit_Table> list = FXCollections.observableArrayList();        
        list=getInsripTByNIV();
      RepartionTable.setItems(list);      
      NbTotLabel.setText(""+list.size());
        } catch (NullPointerException ex){
        }
    }
     
      public ObservableList<Inscrit_Table> getInsripTByNIV() {
      ObservableList<Inscrit_Table> list = FXCollections.observableArrayList();
      try {
          Année_Scolaire AS=Année_ScoRepart.getValue();
            int id_AS=AS.getId_AS();  
        Niveau Niv=NiveauComboBOX.getValue(); 
        int id_Niv=Niv.getId_N();
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT etudiant.Matricule,etudiant.Nom,etudiant.Prenoms,etudiant.OBS,inscription.Date_Insc "
                 + "FROM etudiant,inscription where etudiant.Matricule=inscription.Matricule and inscription.id_AS="+id_AS+" "
                 + "and inscription.id_N="+id_Niv+" ORDER BY etudiant.Matricule;");
         
         while (resultat.next()) {
            Inscrit_Table p = createInscritTable(resultat);
            list.add(p);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException ex) {
      }      
      return list;
   }
       private Inscrit_Table createInscritTable(ResultSet rs) {
      Inscrit_Table T = new Inscrit_Table();
      try {
          T.setNumMatr(rs.getInt("Matricule"));
          T.setNom(rs.getString("Nom"));
          T.setPrenoms(rs.getString("Prenoms"));          
         String query2 ="SELECT label from obs where id_Obs="+rs.getString("OBS")+";";
            try {                
         int nb_liv_Emprunt;
                stmt=maConnexion.ObtenirConnexion().createStatement(); 
                ResultSet result= stmt.executeQuery(query2); 
                while(result.next()){
                    String OBS=result.getString("label");
            T.setOBS(OBS);                  
                }
            }catch (SQLException ex) {
      } 
          T.setDate_Insc(rs.getDate("Date_Insc"));
      } catch (SQLException ex) {
      }
      return T;
   }

    @FXML
    private void NiveauComboChanged(ActionEvent event) {
        loadINSCRIT_Table();
    }

    @FXML
    private void Annee_ScolaireChanged(ActionEvent event) {
        loadINSCRIT_Table();
    }

    @FXML
    private void ClasseComboChanged(ActionEvent event) {
        loadListe_Par_Classe_Table();
    }

    @FXML
    private void Annee_ScolaireChanged_Classe(ActionEvent event) {
        loadListe_Par_Classe_Table();
    }

    @FXML
    private void Classer_Les_Elèves(MouseEvent event) throws InterruptedException {
        if( isInputValid()){  
            try{
        Année_Scolaire AS=AS_Repart.getValue();
        int id_AS=AS.getId_AS();
        Classe Cl= ClasseRepart.getValue();
        int id_Classe=Cl.getId_Classe();
         String Num1=N1.getText();String NumM1=M1.getText();     String Num11=N11.getText();String NumM11=M11.getText();      String Num21=N21.getText();String NumM21=M21.getText();      String Num31=N31.getText();String NumM31=M31.getText();
         String Num2=N2.getText();String NumM2=M2.getText();     String Num12=N12.getText();String NumM12=M12.getText();      String Num22=N22.getText();String NumM22=M22.getText();      String Num32=N32.getText();String NumM32=M32.getText();
         String Num3=N3.getText();String NumM3=M3.getText();     String Num13=N13.getText();String NumM13=M13.getText();      String Num23=N23.getText();String NumM23=M23.getText();      String Num33=N33.getText();String NumM33=M33.getText();
         String Num4=N4.getText();String NumM4=M4.getText();     String Num14=N14.getText();String NumM14=M14.getText();      String Num24=N24.getText();String NumM24=M24.getText();      String Num34=N34.getText();String NumM34=M34.getText();
         String Num5=N5.getText();String NumM5=M5.getText();     String Num15=N15.getText();String NumM15=M15.getText();      String Num25=N25.getText();String NumM25=M25.getText();      String Num35=N35.getText();String NumM35=M35.getText();
         String Num6=N6.getText();String NumM6=M6.getText();     String Num16=N16.getText();String NumM16=M16.getText();      String Num26=N26.getText();String NumM26=M26.getText();      String Num36=N36.getText();String NumM36=M36.getText();
         String Num7=N7.getText();String NumM7=M7.getText();     String Num17=N17.getText();String NumM17=M17.getText();      String Num27=N27.getText();String NumM27=M27.getText();      String Num37=N37.getText();String NumM37=M37.getText();
         String Num8=N8.getText();String NumM8=M8.getText();     String Num18=N18.getText();String NumM18=M18.getText();      String Num28=N28.getText();String NumM28=M28.getText();      String Num38=N38.getText();String NumM38=M38.getText();
         String Num9=N9.getText();String NumM9=M9.getText();     String Num19=N19.getText();String NumM19=M19.getText();      String Num29=N29.getText();String NumM29=M29.getText();      String Num39=N39.getText();String NumM39=M39.getText();
         String Num10=N10.getText();String NumM10=M10.getText();     String Num20=N20.getText();String NumM20=M20.getText();      String Num30=N30.getText();String NumM30=M30.getText();      String Num40=N40.getText();String NumM40=M40.getText();
         
              if(Num1.length() != 0 && NumM1.length() != 0){AjoutNUMERO(NumM1,id_AS,id_Classe,Num1);}
              if(Num2.length() != 0 && NumM2.length() != 0){AjoutNUMERO(NumM2,id_AS,id_Classe,Num2);}
              if(Num3.length() != 0 && NumM3.length() != 0){AjoutNUMERO(NumM3,id_AS,id_Classe,Num3);}
              if(Num4.length() != 0 && NumM4.length() != 0){AjoutNUMERO(NumM4,id_AS,id_Classe,Num4);}
              if(Num5.length() != 0 && NumM5.length() != 0){AjoutNUMERO(NumM5,id_AS,id_Classe,Num5);}
              if(Num6.length() != 0 && NumM6.length() != 0){AjoutNUMERO(NumM6,id_AS,id_Classe,Num6);}
              if(Num7.length() != 0 && NumM7.length() != 0){AjoutNUMERO(NumM7,id_AS,id_Classe,Num7);}
              if(Num8.length() != 0 && NumM8.length() != 0){AjoutNUMERO(NumM8,id_AS,id_Classe,Num8);}
              if(Num9.length() != 0 && NumM9.length() != 0){AjoutNUMERO(NumM9,id_AS,id_Classe,Num9);}
              if(Num10.length() != 0 && NumM10.length() != 0){AjoutNUMERO(NumM10,id_AS,id_Classe,Num10);}
              if(Num11.length() != 0 && NumM11.length() != 0){AjoutNUMERO(NumM11,id_AS,id_Classe,Num11);}
              if(Num12.length() != 0 && NumM12.length() != 0){AjoutNUMERO(NumM12,id_AS,id_Classe,Num12);}
              if(Num13.length() != 0 && NumM13.length() != 0){AjoutNUMERO(NumM13,id_AS,id_Classe,Num13);}
              if(Num14.length() != 0 && NumM14.length() != 0){AjoutNUMERO(NumM14,id_AS,id_Classe,Num14);}
              if(Num15.length() != 0 && NumM15.length() != 0){AjoutNUMERO(NumM15,id_AS,id_Classe,Num15);}
              if(Num16.length() != 0 && NumM16.length() != 0){AjoutNUMERO(NumM16,id_AS,id_Classe,Num16);}
              if(Num17.length() != 0 && NumM17.length() != 0){AjoutNUMERO(NumM17,id_AS,id_Classe,Num17);}
              if(Num18.length() != 0 && NumM18.length() != 0){AjoutNUMERO(NumM18,id_AS,id_Classe,Num18);}
              if(Num19.length() != 0 && NumM19.length() != 0){AjoutNUMERO(NumM19,id_AS,id_Classe,Num19);}
              if(Num20.length() != 0 && NumM20.length() != 0){AjoutNUMERO(NumM20,id_AS,id_Classe,Num20);}
              if(Num21.length() != 0 && NumM21.length() != 0){AjoutNUMERO(NumM21,id_AS,id_Classe,Num21);}
              if(Num22.length() != 0 && NumM22.length() != 0){AjoutNUMERO(NumM22,id_AS,id_Classe,Num22);}
              if(Num23.length() != 0 && NumM23.length() != 0){AjoutNUMERO(NumM23,id_AS,id_Classe,Num23);}
              if(Num24.length() != 0 && NumM24.length() != 0){AjoutNUMERO(NumM24,id_AS,id_Classe,Num24);}
              if(Num25.length() != 0 && NumM25.length() != 0){AjoutNUMERO(NumM25,id_AS,id_Classe,Num25);}
              if(Num26.length() != 0 && NumM26.length() != 0){AjoutNUMERO(NumM26,id_AS,id_Classe,Num26);}
              if(Num27.length() != 0 && NumM27.length() != 0){AjoutNUMERO(NumM27,id_AS,id_Classe,Num27);}
              if(Num28.length() != 0 && NumM28.length() != 0){AjoutNUMERO(NumM28,id_AS,id_Classe,Num28);}
              if(Num29.length() != 0 && NumM29.length() != 0){AjoutNUMERO(NumM29,id_AS,id_Classe,Num29);}
              if(Num30.length() != 0 && NumM30.length() != 0){AjoutNUMERO(NumM30,id_AS,id_Classe,Num30);}
              if(Num31.length() != 0 && NumM31.length() != 0){AjoutNUMERO(NumM31,id_AS,id_Classe,Num31);}
              if(Num32.length() != 0 && NumM32.length() != 0){AjoutNUMERO(NumM32,id_AS,id_Classe,Num32);}
              if(Num33.length() != 0 && NumM33.length() != 0){AjoutNUMERO(NumM33,id_AS,id_Classe,Num33);}
              if(Num34.length() != 0 && NumM34.length() != 0){AjoutNUMERO(NumM34,id_AS,id_Classe,Num34);}
              if(Num35.length() != 0 && NumM35.length() != 0){AjoutNUMERO(NumM35,id_AS,id_Classe,Num35);}
              if(Num36.length() != 0 && NumM36.length() != 0){AjoutNUMERO(NumM36,id_AS,id_Classe,Num36);}
              if(Num37.length() != 0 && NumM37.length() != 0){AjoutNUMERO(NumM37,id_AS,id_Classe,Num37);}
              if(Num38.length() != 0 && NumM38.length() != 0){AjoutNUMERO(NumM38,id_AS,id_Classe,Num38);}
              if(Num39.length() != 0 && NumM39.length() != 0){AjoutNUMERO(NumM39,id_AS,id_Classe,Num39);}
              if(Num40.length() != 0 && NumM40.length() != 0){AjoutNUMERO(NumM40,id_AS,id_Classe,Num40);}
             
         }catch(NullPointerException ex){
            notification.setText("Selectionner un Classe");}
        }
    }
     public void AjoutNUMERO(String NumMatr,int id_AS,int id_Classe,String Num) throws InterruptedException{
    try{
        String req1="INSERT INTO `appartenir`(`Matricule`, `id_AS`, `id_Classe`, `Numero`) VALUES ("+NumMatr+","+id_AS+","+id_Classe+","+Num+")";         
         java.sql.Statement CaisseStatement=maConnexion.ObtenirConnexion().createStatement();      
         CaisseStatement.executeUpdate(req1);  
            System.out.println("--> Classer l'élèves : " + NumMatr) ;            
         ExportDoneNotification();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println("--> Exception de la requete 1: " + ex) ;
            ExportFailNotification();
      }
    }
    private boolean isInputValid() { 
        try{            
        Classe Cl= ClasseRepart.getValue();
        int id_Classe=Cl.getId_Classe();
        }catch(NullPointerException ex){
            notification.setText("Selectionner un Classe");
        }
        int nb=0;
        nb+=VérifierNum(N1);nb+=VérifierNumMatr(M1);    nb+=VérifierNum(N11);nb+=VérifierNumMatr(M11);    nb+=VérifierNum(N21);nb+=VérifierNumMatr(M21);    nb+=VérifierNum(N31);nb+=VérifierNumMatr(M31);
        nb+=VérifierNum(N2);nb+=VérifierNumMatr(M2);    nb+=VérifierNum(N12);nb+=VérifierNumMatr(M12);    nb+=VérifierNum(N22);nb+=VérifierNumMatr(M22);    nb+=VérifierNum(N32);nb+=VérifierNumMatr(M32);
        nb+=VérifierNum(N3);nb+=VérifierNumMatr(M3);    nb+=VérifierNum(N13);nb+=VérifierNumMatr(M13);    nb+=VérifierNum(N23);nb+=VérifierNumMatr(M23);    nb+=VérifierNum(N33);nb+=VérifierNumMatr(M33);
        nb+=VérifierNum(N4);nb+=VérifierNumMatr(M4);    nb+=VérifierNum(N14);nb+=VérifierNumMatr(M14);    nb+=VérifierNum(N24);nb+=VérifierNumMatr(M24);    nb+=VérifierNum(N34);nb+=VérifierNumMatr(M34);
        nb+=VérifierNum(N5);nb+=VérifierNumMatr(M5);    nb+=VérifierNum(N15);nb+=VérifierNumMatr(M15);    nb+=VérifierNum(N25);nb+=VérifierNumMatr(M25);    nb+=VérifierNum(N35);nb+=VérifierNumMatr(M35);
        nb+=VérifierNum(N6);nb+=VérifierNumMatr(M6);    nb+=VérifierNum(N16);nb+=VérifierNumMatr(M16);    nb+=VérifierNum(N26);nb+=VérifierNumMatr(M26);    nb+=VérifierNum(N36);nb+=VérifierNumMatr(M36);
        nb+=VérifierNum(N7);nb+=VérifierNumMatr(M7);    nb+=VérifierNum(N17);nb+=VérifierNumMatr(M17);    nb+=VérifierNum(N27);nb+=VérifierNumMatr(M27);    nb+=VérifierNum(N37);nb+=VérifierNumMatr(M37);
        nb+=VérifierNum(N8);nb+=VérifierNumMatr(M8);    nb+=VérifierNum(N18);nb+=VérifierNumMatr(M18);    nb+=VérifierNum(N28);nb+=VérifierNumMatr(M28);    nb+=VérifierNum(N38);nb+=VérifierNumMatr(M38);
        nb+=VérifierNum(N9);nb+=VérifierNumMatr(M9);    nb+=VérifierNum(N19);nb+=VérifierNumMatr(M19);    nb+=VérifierNum(N29);nb+=VérifierNumMatr(M29);    nb+=VérifierNum(N39);nb+=VérifierNumMatr(M39);
        nb+=VérifierNum(N10);nb+=VérifierNumMatr(M10);    nb+=VérifierNum(N20);nb+=VérifierNumMatr(M20);    nb+=VérifierNum(N30);nb+=VérifierNumMatr(M30);    nb+=VérifierNum(N40);nb+=VérifierNumMatr(M40);
        System.out.println("Isan'ny Diso :"+nb);
        if (nb == 0) {
            notification.setText("");
            return true;
        } else {
            return false;
        }
    }
    public int VérifierNum(JFXTextField Num_M1){
        
        int nb_erreur=0;
        Année_Scolaire AS=AS_Repart.getValue();
        int id_AS=AS.getId_AS();
        boolean foundNum = false;
       try{
           Classe Cl= ClasseRepart.getValue();
        int id_Classe=Cl.getId_Classe();
         if(Num_M1.getText().length() != 0) {
             try {
                int nb=Integer.parseInt(Num_M1.getText());
                ChangeCorrectColor(Num_M1);
            } catch (NumberFormatException e) {
                ChangeErrorColor(Num_M1);
            notification.setText("Le Numéro Matricule doit être un entier");
            nb_erreur+=1;
            }
             
               try{
                java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
                java.sql.ResultSet numero= stmt1.executeQuery("SELECT Numero from appartenir where id_AS="+id_AS+" and id_Classe="+id_Classe+" ORDER BY Numero;");
               int Numer=Integer.parseInt(Num_M1.getText());
            while(numero.next()){
                if (Numer == numero.getInt("Numero")) {
                foundNum= true;
            ChangeCorrectColor(Num_M1);
                System.out.println("Num Client Trouvée: ");
                break;
                    }  else foundNum= false;               
            } 
         if (foundNum){
             notification.setText("Le numéro que vous avez inséré déja existe");
             ChangeErrorColor(Num_M1);
             nb_erreur+=1;
         };  
        }catch(Exception e){

        }
    }
    }catch(NullPointerException ex){
            notification.setText("Selectionner un Classe");
        }
         return nb_erreur;
    }
    public int VérifierNumMatr(JFXTextField Num_M1){
        int nb_erreur=0;
        Année_Scolaire AS=AS_Repart.getValue();
        int id_AS=AS.getId_AS();
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
         return nb_erreur;
    }
    
    
     void ExportDoneNotification() throws InterruptedException{  
         String title = "Ajout Complète";
        String message ="Repartion des élèves a réussi \n";
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
          String title = "Repartition Echoué";
        String Message ="Il y a un erreur \n Peut-être que le numéro Matricule a déja Classé";
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
    private void EffacerTOUT(MouseEvent event) {
         N1.setText("");M1.setText("");     N11.setText("");M11.setText("");      N21.setText("");M21.setText("");      N31.setText("");M31.setText("");
         N2.setText("");M2.setText("");     N12.setText("");M12.setText("");      N22.setText("");M22.setText("");      N32.setText("");M32.setText("");
         N3.setText("");M3.setText("");     N13.setText("");M13.setText("");      N23.setText("");M23.setText("");      N33.setText("");M33.setText("");
         N4.setText("");M4.setText("");     N14.setText("");M14.setText("");      N24.setText("");M24.setText("");      N34.setText("");M34.setText("");
         N5.setText("");M5.setText("");     N15.setText("");M15.setText("");      N25.setText("");M25.setText("");      N35.setText("");M35.setText("");
         N6.setText("");M6.setText("");     N16.setText("");M16.setText("");      N26.setText("");M26.setText("");      N36.setText("");M36.setText("");
         N7.setText("");M7.setText("");     N17.setText("");M17.setText("");      N27.setText("");M27.setText("");      N37.setText("");M37.setText("");
         N8.setText("");M8.setText("");     N18.setText("");M18.setText("");      N28.setText("");M28.setText("");      N38.setText("");M38.setText("");
         N9.setText("");M9.setText("");     N19.setText("");M19.setText("");      N29.setText("");M29.setText("");     N39.setText("");M39.setText("");
         N10.setText("");M10.setText("");     N20.setText("");M20.setText("");     N30.setText("");M30.setText("");      N40.setText("");M40.setText("");
}
    
    // Charger la table LISTE des élèves Par Classe
    void loadListe_Par_Classe_Table()
    {  
        Num_Tbl.setCellValueFactory(new PropertyValueFactory<>("NumE"));
        Matr_RepartTableClasse.setCellValueFactory(new PropertyValueFactory<>("NumMatrC"));
        nom_RepartTableClasse.setCellValueFactory(new PropertyValueFactory<>("NomC"));
        prenom_RepartTableCasse.setCellValueFactory(new PropertyValueFactory<>("PrenomsC"));
      ObservableList<List_Classe> list = FXCollections.observableArrayList();        
        list=getListTByClasse();
      RepartionTable1.setItems(list);      
      NbClasse.setText(""+list.size());
    }
     
      public ObservableList<List_Classe> getListTByClasse() {
      ObservableList<List_Classe> list = FXCollections.observableArrayList();
      try {
          Année_Scolaire AS=Année_ScoRepart_Classe.getValue();
            int id_AS=AS.getId_AS();  
        Classe Cl=ClasseComboBOX.getValue(); 
        int id_Classe=Cl.getId_Classe();
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT appartenir.Numero,etudiant.Matricule,etudiant.nom,etudiant.Prenoms "
                 + "FROM appartenir,etudiant where appartenir.Matricule=etudiant.Matricule and appartenir.id_AS="+id_AS+" "
                 + "and appartenir.id_Classe="+id_Classe+" ORDER BY Numero;");
         
         while (resultat.next()) {
            List_Classe p = createList_by_ClasseTable(resultat);
            list.add(p);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException ex) {
      }      
      return list;
   }
       private List_Classe createList_by_ClasseTable(ResultSet rs) {
      List_Classe T = new List_Classe();
      try {
          T.setNumE(rs.getInt("Numero"));
          T.setNumMatrC(rs.getInt("Matricule"));
          T.setNomC(rs.getString("Nom"));
          T.setPrenomsC(rs.getString("Prenoms"));  
      } catch (SQLException ex) {
      }
      return T;
   }
 // Charger la table LISTE des élèves TSY nahavita écolge
    void loadListe_PasEncorePayéEcolage_Table()
    {  
        Num_Eco.setCellValueFactory(new PropertyValueFactory<>("NumE"));
        Matr_EcolageTable1.setCellValueFactory(new PropertyValueFactory<>("NumMatrC"));
        nom_ClasseTable1.setCellValueFactory(new PropertyValueFactory<>("NomC"));
        prenom_ClasseTable1.setCellValueFactory(new PropertyValueFactory<>("PrenomsC"));
      ObservableList<List_Classe> list = FXCollections.observableArrayList();        
        list=getListTEcolageByClasse();
      NonPayéEcolageTable.setItems(list);      
      nb_EcoLbl.setText(""+list.size());
    }
        public ObservableList<List_Classe> getListTEcolageByClasse() {
      ObservableList<List_Classe> list = FXCollections.observableArrayList();
      try {
          Année_Scolaire AS=AS_Combo_Ecolage.getValue();
            int id_AS=AS.getId_AS();  
        Classe Cl=ClasseCombo_Box1.getValue(); 
        int id_Classe=Cl.getId_Classe();
        Mois Mois=MoisCombo_Box.getValue(); 
        int id_Eco=Mois.getId_Mois();
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT DISTINCT appartenir.Numero,etudiant.Matricule,etudiant.Nom,etudiant.Prenoms"
                 + " from etudiant,payer,ecolage,appartenir where etudiant.Matricule=payer.Matricule and "
                 + "appartenir.Matricule=etudiant.Matricule and ecolage.id_Eco=payer.id_Eco and appartenir.id_Classe="+id_Classe+" "
                 + "and payer.id_AS="+id_AS+" and appartenir.id_AS="+id_AS+" and etudiant.Matricule Not In (SELECT DISTINCT etudiant.Matricule "
                 + "from etudiant,payer,ecolage where etudiant.Matricule=payer.Matricule and ecolage.id_Eco=payer.id_Eco "
                 + "and ecolage.id_Eco="+id_Eco+" and payer.id_AS="+id_AS+") ORDER BY Numero;");
         
         while (resultat.next()) {
            List_Classe p = createList_by_ClasseTable(resultat);
            list.add(p);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException ex) {
      }      
      return list;
   }
      // Charger la table LISTE des élèves TSY nahavita Construction/Subvention
    void loadListe_PasEncorePayéCons_Subv_Table()
    {  
        Num_TblCS.setCellValueFactory(new PropertyValueFactory<>("NumE"));
        Matr_RepartTableCS.setCellValueFactory(new PropertyValueFactory<>("NumMatrC"));
        nom_RepartTableCS.setCellValueFactory(new PropertyValueFactory<>("NomC"));
        prenom_RepartTableCS.setCellValueFactory(new PropertyValueFactory<>("PrenomsC"));
      ObservableList<List_Classe> listCS = FXCollections.observableArrayList();        
        listCS=getListTConsSubvByClasse();
      NonPayéCS_Table.setItems(listCS);      
      C_S_Lbl.setText(""+listCS.size());
    }
        public ObservableList<List_Classe> getListTConsSubvByClasse() {
      ObservableList<List_Classe> listCS = FXCollections.observableArrayList();
      try {
          Année_Scolaire AS=Année_ScoCons_Subv.getValue();
            int id_AS=AS.getId_AS();  
        Classe Cl=Cons_SubvClasseComboBox.getValue(); 
        int id_Classe=Cl.getId_Classe();
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT DISTINCT appartenir.Numero,etudiant.Matricule,etudiant.Nom,etudiant.Prenoms FROM etudiant,appartenir where etudiant.Matricule=appartenir.Matricule and etudiant.Matricule IN (SELECT DISTINCT etudiant.Matricule "
                 + "FROM etudiant,appartenir where appartenir.Matricule=etudiant.Matricule and appartenir.id_AS="+id_AS+" "
                 + "and appartenir.id_Classe="+id_Classe+" and etudiant.Matricule Not In (SELECT DISTINCT etudiant.Matricule "
                 + "from etudiant,payercs,autres where etudiant.Matricule=payercs.Num_Matr and autres.id_Autres=payercs.id_Autre "
                 + "and autres.id_Autres=1 and payercs.id_AS="+id_AS+") UNION SELECT DISTINCT etudiant.Matricule"
                 + " FROM etudiant,appartenir where appartenir.Matricule=etudiant.Matricule and appartenir.id_AS="+id_AS+" and "
                 + "appartenir.id_Classe="+id_Classe+"  and etudiant.Matricule Not In (SELECT DISTINCT etudiant.Matricule from etudiant,payercs,autres"
                 + " where etudiant.Matricule=payercs.Num_Matr and autres.id_Autres=payercs.id_Autre and autres.id_Autres=2 and payercs.id_AS="+id_AS+")) ORDER BY appartenir.Numero;");
         
         while (resultat.next()) {
            List_Classe p = createList_by_ClasseTable(resultat);
            listCS.add(p);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException ex) {
      }      
      return listCS;
   }  
    @FXML
    private void MoisHaveChanged(ActionEvent event) {
        loadListe_PasEncorePayéEcolage_Table();
    }

    @FXML
    private void ClasseHaveChanged(ActionEvent event) {
        loadListe_PasEncorePayéEcolage_Table();
    }

    @FXML
    private void AS_EcolageChanged(ActionEvent event) {
        loadListe_PasEncorePayéEcolage_Table();
    }

    @FXML
    private void Cons_SubvComboBoxChanged(ActionEvent event) {
        loadListe_PasEncorePayéCons_Subv_Table();
    }

    @FXML
    private void ShowEcolageList(ActionEvent event) {   
        loadListe_PasEncorePayéEcolage_Table();
        ChangerCouleurControle(Ecolage_Contrôle,Cons_Subv,SearchBTN,Exporter);
             Liste_Pas_PayéECOLAGE.toFront();
            new FadeIn(Liste_Pas_PayéECOLAGE).play();
    }

    @FXML
    private void ShowConstruction_Subvention(ActionEvent event) {
        loadListe_PasEncorePayéCons_Subv_Table();
        ChangerCouleurControle(Cons_Subv,SearchBTN,Exporter,Ecolage_Contrôle);
             Listes_PasPayéCS.toFront();
            new FadeIn(Listes_PasPayéCS).play();
    }

    @FXML
    private void ShowSearchPanel(ActionEvent event) {
        LoadSuggest();
        ChangerCouleurControle(SearchBTN,Cons_Subv,Exporter,Ecolage_Contrôle);
             RechercheElève.toFront();
            new FadeIn(RechercheElève).play();
    }
    
    public void LoadSuggest(){
        ArrayList<String> NumM = new ArrayList<String>();
        //NumM.clear();
         NumM=getSuggest();
        TextFields.bindAutoCompletion(SearchBar, NumM);
    }
 //GET num Matricule Suggestion
    public ArrayList<String> getSuggest(){
        ArrayList<String> Num = new ArrayList<String>();
     try {    
         Année_Scolaire A_S=AS_Search.getValue();
            int id_AS=A_S.getId_AS();      
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet rs= stmt1.executeQuery("SELECT etudiant.Matricule,etudiant.Nom,etudiant.Prenoms From etudiant,inscription where etudiant.Matricule=inscription.Matricule and inscription.id_AS="+id_AS+" ORDER BY etudiant.Matricule;"); 
             
         while (rs.next()) {
             Num.add(rs.getString("Matricule")+"- "+rs.getString("Nom")+" "+rs.getString("Prenoms"));
         }
         rs.close();
         stmt1.close();
      } catch (SQLException | NullPointerException ex) {
      } 
         return Num;
     
    }
    @FXML
    private void ImprimerListe(ActionEvent event) throws InterruptedException, Exception {  
        ChangerCouleurControle(Exporter,SearchBTN,Cons_Subv,Ecolage_Contrôle);
             ExportRECAP.toFront();
            new FadeIn(ExportRECAP).play();
    }

    @FXML
    private void Annee_ScolaireChanged_Cons_Subv(ActionEvent event) {
        loadListe_PasEncorePayéCons_Subv_Table();
    }

    @FXML
    private void ChercherElèveByID(ActionEvent event) {    
        loadMatriculeInfo();
        ChargerCons_Subv_INFO();
        ChargeEcolageLabel();
    }
    public String Get_Num_Matricule(String Text){
        int taille = Text.length();
         String Matr="";
        String Ch = "-";
            for(int i=0;i<taille;i++){
                if(Text.charAt(i) == Ch.charAt(0)){ break;}
                Matr+=Text.charAt(i);
            }
            System.out.println("Ny matricule azo:"+Matr);
            return Matr;
    }
    public void loadMatriculeInfo(){   
        try{
            loadHistorique_Table();
          Année_Scolaire AS=AS_Search.getValue();
            int id_AS=AS.getId_AS(); 
            String Num=Get_Num_Matricule(SearchBar.getText());
        if(FoundMatricule(Num,id_AS)){
            notificationSearch.setVisible(false);panelResult.setVisible(true);
         try{ObservableList<Catégorie> listC = FXCollections.observableArrayList();
         listC=getCategData();
            java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT classe.Label_C,appartenir.Numero,inscription.Date_Insc,"
                 + "inscription.Ancien,etudiant.Nom,etudiant.Prenoms,etudiant.OBS FROM classe,appartenir,inscription,etudiant "
                 + "WHERE classe.id_classe=appartenir.id_Classe and appartenir.Matricule=etudiant.Matricule and "
                 + "inscription.Matricule=etudiant.Matricule and inscription.id_AS="+id_AS+" and appartenir.id_AS="+id_AS+" and etudiant.Matricule="+Num+";");
         while (resultat.next()) {
             ClasseLabel.setText(resultat.getString("Label_C"));
             NuméroLbl.setText(resultat.getString("Numero"));
             NomLbl.setText(resultat.getString("Nom"));
             PrenomsLbl.setText(resultat.getString("Prenoms"));             
             NbLbl.setText(""+listC.get(resultat.getInt("OBS")-1));
             String Etat=resultat.getString("Ancien");
             if(Etat.equals("false")){
                 EtatLbl.setText("Nouveau élève");
             }else EtatLbl.setText("Ancien élève");
             SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");             
             Date_InscLbl.setText(""+sdf.format(resultat.getDate("Date_Insc")));
         }
        }catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      } 
        }
        else {notificationSearch.setVisible(true);panelResult.setVisible(false);}
    }catch(NullPointerException e){}
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

    @FXML
    private void AS_Search_Chandeg(ActionEvent event) {
        loadMatriculeInfo();
        LoadSuggest();
    }
    private void ChargerCons_Subv_INFO() {
     try {
         String NumMatr=Get_Num_Matricule(SearchBar.getText());
        Année_Scolaire AS=AS_Search.getValue();
        int id_AS=AS.getId_AS();
        int Num=Integer.parseInt(NumMatr);
        Get_Id_Fin_Eco(Num,id_AS);
         
             Boolean P=false;
                ConstructionLbl.setText("NON Payé");
                SubvLbl.setText("NON Payé");
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT id_Autre from payercs where id_AS="+id_AS+" and Num_Matr="+NumMatr+";");
         while (resultat.next()) {
             P=true;
             if(resultat.getInt("id_Autre")==1){
                    ConstructionLbl.setText("Payé");
             }else 
             if(resultat.getInt("id_Autre")==2){
                    SubvLbl.setText("Payé");         
             } 
         }
         if(!P){
                ConstructionLbl.setText("NON Payé");
                SubvLbl.setText("NON Payé"); 
             }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException | NumberFormatException ex) {
          System.out.println("Exception <>"+ ex);
      }   
          
    }
    
    public void ChargeEcolageLabel(){
        try{
            String Eco_Payé="";
            String Eco_Reste="";
            int i=1;
            // Ecolage déja Payé TEXT
         while(i<=Id_Fin_Eco){
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
          java.sql.ResultSet titre= stmt1.executeQuery("SELECT Libelle_Eco FROM ecolage WHERE id_Eco="+i+";");
            while(titre.next()){                    
                    Eco_Payé+=" +"+titre.getString("Libelle_Eco");         
            } 
         i++;
         }         
         if(Eco_Payé.equals("")){EcolagePayéLbl.setText("Aucun");}
         else EcolagePayéLbl.setText(""+Eco_Payé);
         // Ecolage RESTE Payé TEXT
         EcolageController Eco=new EcolageController();
         int Id_NIV=0;int Mois;
          Année_Scolaire AS=AS_Search.getValue();
            int id_AS=AS.getId_AS(); 
            String Num=Get_Num_Matricule(SearchBar.getText());
         Id_NIV=Eco.Get_Id_NIVEAU(Num, id_AS);
         if(Id_NIV==3){Mois=11;}else Mois=10;
         int nb=Id_Fin_Eco+1;
         while(nb<=Mois){
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
          java.sql.ResultSet titre= stmt1.executeQuery("SELECT Libelle_Eco FROM ecolage WHERE id_Eco="+nb+";");
            while(titre.next()){                    
                    Eco_Reste+=" +"+titre.getString("Libelle_Eco");         
            } 
         nb++;
         }
         if(Eco_Reste.equals("")){EcolageRestantLbl.setText("Aucun");}
         else EcolageRestantLbl.setText(""+Eco_Reste);
        }catch(SQLException | NullPointerException | IndexOutOfBoundsException ex){}
    }
void loadHistorique_Table()
    {  
        try{
        Date_C.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Design_C.setCellValueFactory(new PropertyValueFactory<>("Design"));
        Vola_C.setCellValueFactory(new PropertyValueFactory<>("Montant"));
      ObservableList<Historique> list = FXCollections.observableArrayList();        
        list=getHistorique();
      History_Tbl.setItems(list);  
        } catch (NullPointerException ex){
        }
    }
     
      public ObservableList<Historique> getHistorique() {
      ObservableList<Historique> list = FXCollections.observableArrayList();
      try {          
          Année_Scolaire AS=AS_Search.getValue();
            int id_AS=AS.getId_AS();  
            String Num=Get_Num_Matricule(SearchBar.getText());
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Date,Paiment,Vola From caisse where id_AS="+id_AS+" and Num_Matr="+Num+" ORDER BY Date ASC;");
         
         while (resultat.next()) {
            Historique p = createHistoriqueTable(resultat);
            list.add(p);
         }
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException ex) {
      }      
      return list;
   }
       private Historique createHistoriqueTable(ResultSet rs) {
      Historique T = new Historique();
      try {
          T.setDate(rs.getDate("Date"));
          T.setDesign(rs.getString("Paiment"));
          T.setMontant(rs.getDouble("Vola"));
      } catch (SQLException ex) {
      }
      return T;
   }
    @FXML
    private void ShowAnnée_Scolaire_Modal(MouseEvent event) {        
        loadStage("/gestion/ecolage/View/Annee_Scolaire.fxml");  
    }

    @FXML
    private void ShowDroit_Modal(MouseEvent event) {  
        loadStage("/gestion/ecolage/View/Droit.fxml");  
    }

    @FXML
    private void ShowEcolage_By_Nivevau_Modal(MouseEvent event) {
        loadStage("/gestion/ecolage/View/Ecolage_By_Niveau.fxml");  
    }

    @FXML
    private void ShowClasse_Modal(MouseEvent event) {   
        loadStage("/gestion/ecolage/View/Classe.fxml");  
    }

    @FXML
    private void ShowCons_Subv_Modal(MouseEvent event) {   
        loadStage("/gestion/ecolage/View/Montant_Cons_Subv.fxml");  
    }
 
      
    void ExportFailNotification(String Message) throws InterruptedException{  
          String title = "Un erreur";
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
    void ExportDoneNotification(String filePath) throws InterruptedException{  
         String title = "Exportation Complète";
        String message ="L'emplacement de la liste est \n"+ filePath;
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
// EMPLACEMENT DES PDF
    
        LocalDateTime myDateObj = LocalDateTime.now();  
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH_mm_ss");    
        String NOW = myDateObj.format(myFormatObj); 
    public final String DEST_1 = "C:/Users/"+System.getProperty("user.name")+"/Documents/MARSIHAY/Liste des élèves "+NOW+".pdf";
    public final String DEST_2 = "C:/Users/"+System.getProperty("user.name")+"/Documents/MARSIHAY/Cahier d'enregistrement "+NOW+".pdf";
   
    @FXML
    private void ExportFrais_Restant(MouseEvent event) throws Exception {
           try{ 
        File file = new File(DEST_1);
        file.getParentFile().mkdirs();
        Année_Scolaire AS=AS_EXPORT.getValue();
        int id_AS=AS.getId_AS();
        String AnnneeS=AS.getLibellé();
        new ExportElèveListeEco_CS().manipulatePdf(DEST_1,id_AS,AnnneeS);
            ExportDoneNotification(DEST_1);
       }catch(FileNotFoundException e){
           String mess="Ce fichier est encore ouvert par une autre application.";
           ExportFailNotification(mess);
       }
    }

    @FXML
    private void ExportCahierEnregistrement(MouseEvent event) throws Exception {
         try{ 
        File file = new File(DEST_2);
        file.getParentFile().mkdirs();
        Année_Scolaire AS=AS_EXPORT.getValue();
        int id_AS=AS.getId_AS();
        String AnnneeS=AS.getLibellé();
        new ExportRecap().manipulatePdf(DEST_2,id_AS,AnnneeS);
            ExportDoneNotification(DEST_2);
       }catch(FileNotFoundException e){
           String mess="Ce fichier est encore ouvert par une autre application.";
           ExportFailNotification(mess);
       }
    }

    @FXML
    private void ShowEditPanelElève(ActionEvent event) {   
        Année_Scolaire AS =Année_ScoRepart_Classe.getValue();
        IdentifiantAS=AS.getId_AS();
        loadStage("/gestion/ecolage/View/EDIT_Elèves.fxml");  
    }

    @FXML
    private void ShowInscriptionINFO(MouseEvent event) {
             Paiments.toFront();
            new FadeIn(Paiments).play();
            ChangeLine(lineBtn2,lineBtn1,lineBtn3,lineBtn4,lineBtn5);
            ChangerCouleur(BTN2,BTN1,BTN3,BTN4,BTN5);            
            loadAll_AS_ComboBox(); 
            loadClasseComboBox();
            Rafraichir_tous_les_tables();
    }

    @FXML
    private void ShowRepartitionINFO(MouseEvent event) {
             Repartition.toFront();
            new FadeIn(Repartition).play();
            ChangeLine(lineBtn3,lineBtn2,lineBtn1,lineBtn4,lineBtn5);
            ChangerCouleur(BTN3,BTN2,BTN1,BTN4,BTN5);
            //loadINSCRIT_Table();
            loadAll_AS_ComboBox(); 
            loadClasseComboBox();
            Rafraichir_tous_les_tables();
    }

    @FXML
    private void ShowControlesINFO(MouseEvent event) {
             Controles.toFront();
            new FadeIn(Controles).play();
            ChangeLine(lineBtn4,lineBtn2,lineBtn3,lineBtn1,lineBtn5);
            ChangerCouleur(BTN4,BTN2,BTN3,BTN1,BTN5);
            loadAll_AS_ComboBox(); 
            loadClasseComboBox();
            Rafraichir_tous_les_tables();
    }

    @FXML
    private void ShowDeletePanelElève(ActionEvent event) { 
        Année_Scolaire AS =Année_ScoRepart_Classe.getValue();
        IdentifiantAS=AS.getId_AS();
        loadStage("/gestion/ecolage/View/DELETE_Appartenir.fxml");  
    }


    
 
}
