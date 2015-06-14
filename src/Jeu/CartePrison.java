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
    public void action(Joueur j){//Deux cas d'utilisation
        if (j.getPrison()) {//Le joueur est en prison donc il utilise la carte
            j.sortPrison();
        } else {//Le joueur vient de piocher la carte
            TexteUI.message(this.getDescription());
            j.addCartePrison(this);
        }
    }
    
}
