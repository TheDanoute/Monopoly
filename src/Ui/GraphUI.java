/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author gueganb
 */
public class GraphUI {
  
    public static String questionBinaire(JFrame fenetre, String question) {
        boolean res = false;

        if (question != null) {
            String [] choix = new String[] { "Oui", "Non" }; 
            
            Object selectedValue = JOptionPane.showOptionDialog(fenetre,
                  question,
                  "Question",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE, 
                  null,
                  choix,
                  choix[1]);
            res = (((Integer) selectedValue) == 0);
        }
        
        if (res) {
            return "oui";
        }else{
            return "non";
        }
    }
}
