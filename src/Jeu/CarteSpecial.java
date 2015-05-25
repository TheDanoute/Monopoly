/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

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
    
    private int getPrixHotelMaison(Joueur j){
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
        return nbm*pm+nbh*ph;
    }
    public void action(Joueur j){
        switch(specialNumber) {
            case 0:
                 for (Joueur jou : j.getMonopoly().getJoueurs()){
                     if (jou != j){
                        jou.removeCash(10);
                        j.addCash(10);
                     }
                 }
            break;
            case 1:
                //A faire
            break;
            case 2:
                j.removeCash(this.getPrixHotelMaison(j));
            break;
            case 3:
                j.removeCash(this.getPrixHotelMaison(j));
            break;
            case 4:
                j.setPositionCourante(j.getPositionCourante().getNum()-3);
            break;
        }
    }
    
}
