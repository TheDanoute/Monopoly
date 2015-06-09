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
public class CarteSpecial extends Carte{
    
    private int specialNumber;
    
    public CarteSpecial(String t,String d,int s){
        super(t,d);
        this.setSpecialNumber(s);
    }
    
    private void setSpecialNumber(int s){
        specialNumber=s;
    }
    
    /*private int getPrixHotelMaison(Joueur j){
        int pm,ph;
        if (specialNumber==2) {
            pm = 25;
            ph = 100;
        } else {
            pm = 40;
            ph = 115;
        }
        int nbh = 0,nbm = 0;
        for (ProprieteAConstruire p : j.getProprietesAConstruire()){
            if (p.getImmobilier()>4){
                 nbh++;
            } else {
                nbm+=p.getImmobilier();
            }
        }
        TexteUI.message("Prix pour les maisons : " + pm + "*" + nbm + "=" + pm*nbm);
        TexteUI.message("Prix pour les hotels : " + ph + "*" + nbh + "=" + ph*nbh);
        TexteUI.message("Total : " + nbm*pm+nbh*ph + "€");
        return nbm*pm+nbh*ph;
    }*/
    @Override
    public void action(Joueur j){
        TexteUI.message(super.getDescription());
        switch(specialNumber) {
            case 0:
                 for (Joueur jou : j.getMonopoly().getJoueurs()){
                     if (jou != j){
                        jou.removeCash(10);
                        j.addCash(10);
                     }
                 }
                 TexteUI.message("Vous avez " + j.getCash() + "€");
            break;
            case 1:
                String s = "";
                while (s.equals("chance")||s.equals("payer")){
                s = TexteUI.question("(chance/payer)");
                if (s.equals("payer")) {
                    j.removeCash(10);
                } else if (s.equals("chance")) {
                    Carte c = j.getMonopoly().getCartes().piochezCarteChance();
                    c.action(j);
                    j.getMonopoly().getCartes().retourCarte(c);
                }
                }
            break;
            case 2:
            case 3:
                int pm,ph;
                if (specialNumber==2) {
                    pm = 25;
                    ph = 100;
                } else {
                    pm = 40;
                    ph = 115;
                }
                int nbh = 0,nbm = 0;
                for (ProprieteAConstruire p : j.getProprietesAConstruire()){
                    if (p.getImmobilier()>4){
                         nbh++;
                    } else {
                        nbm+=p.getImmobilier();
                    }
                }
                TexteUI.message("Prix pour les maisons : " + pm + "*" + nbm + "=" + pm*nbm);
                TexteUI.message("Prix pour les hotels : " + ph + "*" + nbh + "=" + ph*nbh);
                TexteUI.message("Total : " + nbm*pm+nbh*ph + "€");
                j.removeCash(nbm*pm+nbh*ph);
                TexteUI.message("Vous avez " + j.getCash() + "€");
            break;
            case 4:
                j.setPositionCourante(j.getPositionCourante().getNum()-3);
                TexteUI.message("Vous êtes donc a la case n°" + j.getPositionCourante().getNum() + ":" + j.getPositionCourante().getNom());
            break;
        }
    }
    
}
