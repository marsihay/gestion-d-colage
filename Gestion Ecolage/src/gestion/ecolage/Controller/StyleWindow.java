/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.ecolage.Controller;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marsihay
 */
public class StyleWindow {
    static void makeBlur(AnchorPane pnl){
    GaussianBlur gaussianBlur = new GaussianBlur();     
       gaussianBlur.setRadius(10.5);
       pnl.setEffect(gaussianBlur); 
    }
    static void EjectBlur(AnchorPane pnl){
    GaussianBlur gaussianBlur = new GaussianBlur();     
       gaussianBlur.setRadius(0);
       pnl.setEffect(gaussianBlur); 
    }
}
