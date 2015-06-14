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

/*
Description de la classe :
5 cartes n'étaient pas classables avec les autres cas,
c'est pourquoi une classe CarteSpecial gère les cartes au cas par cas
*/
public class CarteSpecial extends Carte{
    
    private int specialNumber; //Désigne de quelle carte s'agit-il (donner dans le fichier cartes_data.txt)
    
    public CarteSpecial(String t,String d,int s){
        super(t,d);
        this.setSpecialNumber(s);
    }
    
    private void setSpecialNumber(int s){
        specialNumber=s;
    }
    
    @Override
    public void action(Joueur j){
        TexteUI.message(super.getDescription());
        switch(specialNumber) {
            case 0://Carte c'est votre anniversaire
                 for (Joueur jou : j.getMonopoly().getJoueurs()){
                     if (jou != j){ //Pour tous les joueurs
                        jou.removeCash(10);
                        j.addCash(10);
                     }
                 }
                 JoueurUI.printCashVous(j);
            break;
            case 1://Carte piocher une carte chance ou payer 10€
                String s = CarteUI.jAiDeLaChance(); //Choix du joueurs
                if (s.equals("payer")) {
                    j.removeCash(10);
                } else { //Tire une carte chance et fait son action
                    Carte c = j.getMonopoly().getCartes().piochezCarteChance();
                    c.action(j);
                    j.getMonopoly().getCartes().retourCarte(c);
                }
            break;
            /*Cas 2 et 3 : payer pour maisons et hotel */
            case 2: //Utilise case 3
            case 3:
                int pm,ph;
                if (specialNumber==2) {//Prix pour la carte 2
                    pm = 25;
                    ph = 100;
                } else {//Prix pour la carte 3
                    pm = 40;
                    ph = 115; 
                }
                int nbh = 0,nbm = 0;
                for (ProprieteAConstruire p : j.getProprietesAConstruire()){ //Boucle pour récupérer le nombre de maisons et d'hotel du joueur
                    if (p.getImmobilier()>4){//C'est hotel
                         nbh++;
                    } else {//Sinon il a y soit des maisons, soit c'est vide alors p.getImmobilier()=0
                        nbm+=p.getImmobilier();
                    }
                }
                CarteUI.payerHotelMaison(pm,nbm,ph,nbh);
                j.removeCash(nbm*pm+nbh*ph);
                JoueurUI.printCashVous(j);
            break;
            case 4://Reculer de 3 cases
                j.setPositionCourante(j.getPositionCourante().getNum()-3);//Déplace le joueur
                CarteUI.deplacement(j.getPositionCourante());
                j.getPositionCourante().action(j);//Action de la case
            break;
        }
    }
    
}
