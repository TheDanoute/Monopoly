/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;

import Jeu.*;
import java.util.HashMap;

/**
 *
 * @author gueganb
 */
public class JoueurUI {
    public static void afficheProprietes (Joueur j, int i){
        System.out.println("*** Joueur n°"+i+" : " + j.getNomJoueur()+" ***");
        System.out.println("Ses propriété(s) :");
        for (Compagnie c : j.getCompagnies()){
            System.out.println(c.getNom());
        }
        for (Gare g : j.getGares()){
            System.out.println(g.getNom());
        }
        for (ProprieteAConstruire p : j.getProprietesAConstruire()){
            System.out.println(p.getNom());
            System.out.println("Groupe : "+p.getGroupe());
            System.out.println("Avec "+p.getImmobilier()+" construction(s)");
        }
        System.out.println("Nombre de carte(s) sortie de prison : "+j.getNBCartePrison());
    }
    
    public static HashMap<Integer, CarreauPropriete> afficheProprietesEchangeables (Joueur j, Echange e,  HashMap<Integer,CarreauPropriete> listP){
        int i = 1;
                // Affichages des propriétés + intégration dans listP2 avec un indice
        for (Compagnie c : j.getCompagnies()){
            if (!e.getListP().contains(c)){
                System.out.println("Propriété n°"+i+" : "+c.getNom());
                listP.put(i, c);
                i++;
            }
        }
        for (Gare g : j.getGares()){
            if (!e.getListP().contains(g)){
                System.out.println("Propriété n°"+i+" : "+g.getNom());
                listP.put(i, g);
                i++;
            }
        } 
        for (CouleurPropriete c : CouleurPropriete.values()) {
            if (!j.getProprietesAConstruire(c).isEmpty()) {
                int immo = 0;
                for (ProprieteAConstruire p : j.getProprietesAConstruire(c)) {
                    immo+=p.getImmobilier();
                }
                if (immo==0) {
                    for (ProprieteAConstruire p : j.getProprietesAConstruire(c)) {
                        if (!e.getListP().contains(p)){
                            System.out.println("Propriété n°"+i+" : "+p.getNom());
                            System.out.println("Groupe : "+p.getGroupe());
                            listP.put(i,p);
                            i++; 
                        } 
                    }
                }
            }
        }
        return listP;
    }
    
    public static void printCashVous(Joueur j) {
        System.out.println("Vous avez maintenant " + j.getCash() + "€");
    }
     public static void printCashLe(Joueur j) {
        System.out.println("Le joueur " + j.getNomJoueur() + " a maintenant "+ j.getCash() + "€");
    }
    
     public static void errorArgent(Joueur j){
         System.out.println("Vous n'avez pas assez d'argent : vous avez " + j.getCash() + "€");
     }
    
   
}
