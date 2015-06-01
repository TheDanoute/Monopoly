/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import Ui.TexteUI;

/**
 *
 * @author DanJeux
 */
public class CartePrison extends Carte{
    
    public CartePrison(String t,String d){
        super(t,d);
    }
    
    @Override
    public void action(Joueur j){
        if (j.getPrison()) {
            j.sortPrison();
        } else {
            TexteUI.message(this.getDescription());
        }
    }
    
}
