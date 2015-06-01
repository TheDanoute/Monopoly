/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

import Ui.TexteUI;

/**
 *
 * @author devaucod
 */
public class CarreauPrison extends Carreau {
    
    public CarreauPrison(int n,String nom,Monopoly m) {
        super(n,nom,m);
    }
    
    @Override
    public void action(Joueur j){
        if (j.getPrison()) {
            int nbCarte = j.getNBCartePrison();
            TexteUI.message("Vous êtes en prison, nombre de carte sortie de prison disponible : " + nbCarte);
            boolean fini = false;
            while (!fini) {
            String rep = TexteUI.question("Que voulez-vous faire ? (payer/carte/des)");
            if (j.getNBTourPrison()>3 && rep.equals("des")) {
                rep = TexteUI.question("Vous ne pouvez pas lancer les dés quatre fois de suite, vous devez payer ou utiliser une carte. (payer/carte)");
            }
            if (rep.equals("payer")) {
                j.removeCash(50);
                j.sortPrison();
                fini = true;
            } else if (rep.equals("carte")) {
                if (nbCarte==0) {
                    TexteUI.message("Vous n'avez pas de carte sortie de prison...");
                } else {
                    j.utilCartePrison();
                    fini = true;
                }
            } else {
                int d1,d2;
                d1 = this.getMonopoly().lancerDe();
                d2 = this.getMonopoly().lancerDe();
                TexteUI.message("Vous avez obtenu : D1 = " + d1 + " ; D2 = " + d2);
                if (d1==d2) {
                    TexteUI.message("Vous sortez de prison.");
                    j.sortPrison();
                    this.getMonopoly().jouerUnCoup(j, d1+d2);
                    fini = true ;
                } else {
                    TexteUI.message("Vous restez en prison.");
                    j.ajouterTourPrison();
                }
            }
            }
        } else {
            TexteUI.message("Vous êtes en visite à la prison.");
        }
            
    }
}
