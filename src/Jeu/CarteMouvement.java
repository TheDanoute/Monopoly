/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import Ui.CarteUI;
import Ui.JoueurUI;
import Ui.TexteUI;

/**
 *
 * @author DanJeux
 */
public class CarteMouvement extends Carte{
    private int carreau;
    private boolean verifDep; //Boolean de vérification si le joueur passe par la case départ (contraire seulement pour les cartes allez en prison)
    
    public CarteMouvement(String t,String d,int c,boolean vD){
        super(t,d);
        this.setCarreau(c);
        this.setVerifDep(vD);
    }

    public int getCarreau() {
        return carreau;
    }

    private void setCarreau(int carreau) {
        this.carreau = carreau;
    }

    public boolean isVerifDep() {
        return verifDep;
    }

    private void setVerifDep(boolean verifDep) {
        this.verifDep = verifDep;
    }
    
    @Override
    public void action(Joueur j){
        TexteUI.message(this.getDescription());
        if ((verifDep && j.getPositionCourante().getNum() > carreau) && carreau>1){ //Vérification si le joueur passe par la case départ (et ne tombe pas dessus)
            j.addCash(200);
            CarteUI.laChance();
            JoueurUI.printCashVous(j);
        }
        j.setPositionCourante(carreau);
        if (verifDep) { //Si ce n'est pas la case prison, le joueur doit avoir l'action du carreau
            j.getMonopoly().getCarreau(carreau).action(j);
        } else { //Si !verifDep la carte est forcement une carte allez en prison
            j.enPrison();
        }
    }
}
