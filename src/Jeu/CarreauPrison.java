/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

import Ui.CarreauUI;
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
            CarreauUI.accueilPrison(nbCarte);
            boolean fini = false;
            while (!fini) {
            String rep = CarreauUI.choixActionPrison();
            if (j.getNBTourPrison()>3 && rep.equals("des")) {
                rep = CarreauUI.toMuchWastedTime();
            }
            if (rep.equals("payer")) {
                j.removeCash(50);
                j.sortPrison();
                fini = true;
                CarreauUI.prisonPayer(j);
            } else if (rep.equals("carte")) {
                if (nbCarte==0) {
                    CarreauUI.errorCartePrison();
                } else {
                    j.utilCartePrison();
                    fini = true;
                }
            } else {
                int d1,d2;
                d1 = this.getMonopoly().lancerDe();
                d2 = this.getMonopoly().lancerDe();
                CarreauUI.desPrison(d1,d2);
                if (d1==d2) {
                    j.sortPrison();
                    this.getMonopoly().jouerUnCoup(j, d1+d2);
                    fini = true ;
                } else {
                    j.ajouterTourPrison();
                    fini = true ;
                }
            }
            }
        } else {
            CarreauUI.visitePrison();
        }
            
    }
}
