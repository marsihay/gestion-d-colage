/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 *
 * @author Marsihay
 */
public class ExportRecap {
     Statement stmt;
    Connexion maConnexion=new Connexion();    
    int Id_Fin_Eco;
    public void ExportRecap(){}
    
     protected void manipulatePdf(String dest,int id_AS,String A_S) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(1000, 842));
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new ExportRecap.TextFooterEventHandler(doc));        
        doc.setMargins(55, 15, 35, 15);
        ILineDrawer line = new SolidLine(2);
        line.setColor(ColorConstants.LIGHT_GRAY);

        LineSeparator tableEndSeparator = new LineSeparator(line);
        //tableEndSeparator.setMarginTop(10);
        tableEndSeparator.setMarginBottom(10);
        doc.add(WriteTitre(""+A_S).setUnderline().setTextAlignment(TextAlignment.CENTER));
        
         java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT  * from classe order by id_classe;");
            while (resultat.next()) {       
                doc.add(WriteClasse(resultat.getString("Label_C")));
                doc.add(createTable(id_AS,resultat.getInt("id_classe"),resultat.getInt("id_Niv")));
                doc.add(tableEndSeparator);
                //Saut à la Ligne
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }   

        doc.close();
    }
       public Paragraph WriteTitre(String AS) throws IOException {
     Text blackText = new Text("Enregistrement des frais de scolarité pour l'Année Scolaire ")
                .setFontColor(ColorConstants.DARK_GRAY)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));
        Text greenText = new Text(AS)
                .setFontColor(ColorConstants.DARK_GRAY)//new DeviceRgb(46, 117, 182)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE));
        Paragraph p2 = new Paragraph().setMargin(0);
        p2.add(blackText);
        p2.add(greenText);
        return p2;
}
       public Paragraph WriteClasse(String Classe) throws IOException {
     Text blackText = new Text("Classe : ")
                .setFontColor(ColorConstants.DARK_GRAY)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));
        Text greenText = new Text(Classe)
                .setFontColor(ColorConstants.BLUE)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE));
        Paragraph p2 = new Paragraph().setMargin(0);
        p2.add(blackText);
        p2.add(greenText);
        return p2;
}
      public Table createTable(int id_AS,int id_Classe,int Id_Niv) throws IOException {
          if(Id_Niv==3){              
      Table table = new Table(UnitValue.createPercentArray(new float[] {1, 2, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1})).useAllAvailableWidth();
            table.setMarginTop(15);
            String[] header = new String[]{"N°", "Matricule", "NOM & PRENOMS","Droit", "Oct","Nov", "Dec", "Janv","Fév", "Mars","Avril", "Mai", "Juin","Juillet", "Août", "Cons","Subv"};
            for (String columnHeader : header) {               
                table.addHeaderCell( createHeaderCell(columnHeader));
            }

            try{
                java.sql.Statement stmt_2=maConnexion.ObtenirConnexion().createStatement();
                String requete="SELECT DISTINCT appartenir.Numero,etudiant.Matricule,etudiant.Nom,etudiant.Prenoms "
                        + "FROM appartenir,etudiant WHERE etudiant.Matricule=appartenir.Matricule and appartenir.id_AS="+id_AS+" and etudiant.Matricule "
                        + "IN(SELECT Matricule from inscription where id_AS="+id_AS+") and appartenir.id_Classe="+id_Classe+" ORDER BY appartenir.Numero;";
                 java.sql.ResultSet rs= stmt_2.executeQuery(requete);  
                while (rs.next()) {   
                   String NumMatr=rs.getString("Matricule");
                table.addCell( createTableCell(rs.getString("Numero")))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(rs.getString("Matricule"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(createTableCell(rs.getString("Nom")+" "+rs.getString("Prenoms")))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                
                table.addCell( createTableCell(ChargerDroit(NumMatr,id_AS)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,1)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,2)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,3)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,4)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,5)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,6)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,7)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,8)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,9)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,10)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,11)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerCS(NumMatr,id_AS,1)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerCS(NumMatr,id_AS,2)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
            }
                 }
         catch (Exception e){}
            
         return table;
            
          }else {
      Table table = new Table(UnitValue.createPercentArray(new float[] {1, 2, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1})).useAllAvailableWidth();
            table.setMarginTop(15);
            String[] header = new String[]{"N°", "Matricule", "NOM & PRENOMS","Droit", "Oct","Nov", "Dec", "Janv","Fév", "Mars","Avril", "Mai", "Juin","Juillet", "Cons","Subv"};
            for (String columnHeader : header) {               
                table.addHeaderCell( createHeaderCell(columnHeader));
            }

            try{
                java.sql.Statement stmt_2=maConnexion.ObtenirConnexion().createStatement();
                String requete="SELECT DISTINCT appartenir.Numero,etudiant.Matricule,etudiant.Nom,etudiant.Prenoms "
                        + "FROM appartenir,etudiant WHERE etudiant.Matricule=appartenir.Matricule and appartenir.id_AS="+id_AS+" and etudiant.Matricule "
                        + "IN(SELECT Matricule from inscription where id_AS="+id_AS+") and appartenir.id_Classe="+id_Classe+" ORDER BY appartenir.Numero;";
                 java.sql.ResultSet rs= stmt_2.executeQuery(requete);  
                while (rs.next()) {   
                   String NumMatr=rs.getString("Matricule");
                table.addCell( createTableCell(rs.getString("Numero")))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(rs.getString("Matricule"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(createTableCell(rs.getString("Nom")+" "+rs.getString("Prenoms")))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                
                table.addCell( createTableCell(ChargerDroit(NumMatr,id_AS)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,1)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,2)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,3)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,4)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,5)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,6)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,7)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,8)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,9)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerEco(NumMatr,id_AS,10)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerCS(NumMatr,id_AS,1)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell( createTableCell(ChargerCS(NumMatr,id_AS,2)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
            }
                 }
         catch (Exception e){}
            
         return table;
          }
}
     public Cell createHeaderCell(String columnHeader) throws IOException {
        Paragraph paragraph = new Paragraph(columnHeader)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9);

                Cell headerCell = new Cell()
                        .add(paragraph)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 1))
                        .setPaddingLeft(8)
                        .setPaddingTop(8)
                        .setPaddingRight(8)
                        .setPaddingBottom(8);
       return headerCell;
  } 
  public Cell createTableCell(String content) throws IOException {
      if(content.equals("Tout payé")){
        Paragraph paragraph = new Paragraph(content)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                        .setFontSize(9);
       Cell cell = new Cell()
                       .add(paragraph)
                        .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 1))
                        .setPaddingLeft(5)
                        .setPaddingTop(5)
                        .setPaddingRight(5)
                        .setBackgroundColor(ColorConstants.GREEN)
                        .setPaddingBottom(5);
       
       return cell;
      }else{
      Paragraph paragraph = new Paragraph(content)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                        .setFontSize(9);
       Cell cell = new Cell()
                       .add(paragraph)
                        .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 1))
                        .setPaddingLeft(5)
                        .setPaddingTop(5)
                        .setPaddingRight(5)
                        .setPaddingBottom(5);
       
       return cell;       
       }
  }
        int i=1;
  protected class TextFooterEventHandler implements IEventHandler {
        protected Document doc;

        public TextFooterEventHandler(Document doc) {
            this.doc = doc;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
            PdfCanvas canvas_1 = new PdfCanvas(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();
            canvas.beginText();
            canvas_1.beginText();
            try {
                canvas.setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE), 8);
                canvas_1.setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE), 8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            canvas.moveText((pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 2 + doc.getLeftMargin(), pageSize.getTop() - doc.getTopMargin() + 30)
                    .showText("Page "+i)
                    .moveText(0, (pageSize.getBottom() + doc.getBottomMargin()) - (pageSize.getTop() + doc.getTopMargin()) - 20)
                    .showText("Lycée Saint Joseph Ambalavao")
                    .endText()
                    .release();
            canvas_1.moveText( -(pageSize.getRight() - doc.getRightMargin() - (pageSize.getLeft() + doc.getLeftMargin())) / 60 + doc.getLeftMargin()-285, pageSize.getTop() - doc.getTopMargin() + 95)
                    .showText("Lycée Saint Joseph Ambalavao")
                    .moveText(0, (pageSize.getBottom() + doc.getBottomMargin()) - (pageSize.getTop() + doc.getTopMargin()) - 20)
                    .showText("this is a footer")
                    .endText()
                    .release();
            System.out.println("Nombre de page "+i);
            i++;
        }
    }
  
  
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM"); 
    private String ChargerDroit(String NumMatr,int id_AS) {
             System.out.println("misy Num tafiditra"+ NumMatr);
        int Num=Integer.parseInt(NumMatr);
        String Lbl="";
         try {
             Boolean P=false;             
             Boolean Construction=false;
             Boolean Subvention=false;
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Date_Insc FROM inscription Where Matricule="+Num+" and id_AS="+id_AS+";");
         while (resultat.next()) {
             P=true;
             Lbl=""+sdf.format(resultat.getDate("Date_Insc"));
         }
         
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
        return Lbl;  
    }
    
    private String ChargerEco(String NumMatr,int id_AS,int id_Eco) {
             System.out.println("misy Num tafiditra"+ NumMatr);
        int Num=Integer.parseInt(NumMatr);
        String Lbl="";
         try {
             Boolean P=false;             
             Boolean Construction=false;
             Boolean Subvention=false;
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Date_paiment FROM payer Where Matricule="+Num+" and id_AS="+id_AS+" and id_Eco="+id_Eco+";");
         while (resultat.next()) {
             P=true;
             Lbl=""+sdf.format(resultat.getDate("Date_paiment"));
         }
         
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
        return Lbl;  
    }
    
    private String ChargerCS(String NumMatr,int id_AS,int id_CS) {
             System.out.println("misy Num tafiditra"+ NumMatr);
        int Num=Integer.parseInt(NumMatr);
        String Lbl="";
         try {
             Boolean P=false;             
             Boolean Construction=false;
             Boolean Subvention=false;
          java.sql.Statement stmt1=maConnexion.ObtenirConnexion().createStatement();
         java.sql.ResultSet resultat= stmt1.executeQuery("SELECT Date_P FROM payercs Where Num_Matr="+Num+" and id_AS="+id_AS+" and id_Autre="+id_CS+";");
         while (resultat.next()) {
             P=true;
             Lbl=""+sdf.format(resultat.getDate("Date_P"));
         }
         
         resultat.close();
         stmt1.close();
      } catch (SQLException | NullPointerException | IndexOutOfBoundsException ex) {
      }   
        return Lbl;  
    }
}
