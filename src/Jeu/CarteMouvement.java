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
public class CarteMouvement extends Carte{
    private int carreau;
    private boolean verifDep;
    
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
        if (verifDep && j.getPositionCourante().getNum()>carreau){
            j.addCash(200);
           
        }
        j.setPositionCourante(carreau);
        if (verifDep) {
            j.getMonopoly().getCarreau(carreau).action(j);
        } else {
            j.enPrison();
        }
    }
}
