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
     
     public static void printVosProprietes(Joueur j){
         System.out.println("Vos proriété(s) :");
         if (j.getProprietesAConstruire().isEmpty() && j.getGares().isEmpty() && j.getCompagnies().isEmpty()) {
             System.out.println("Vous n'avez aucune propriété...");
         } else {
             if (!j.getProprietesAConstruire().isEmpty()) {
                  System.out.println("Vos proriété(s) à construire :");
                  for (ProprieteAConstruire p : j.getProprietesAConstruire()){
                    ProprieteUI.printProprieteProprietaire(p);
                    }
             }
            if (!j.getGares().isEmpty()) {
                  System.out.println("Vos gare(s) :");
                  for (Gare g : j.getGares()){
                    ProprieteUI.printGare(g);
                    }
             }
            if (!j.getCompagnies().isEmpty()) {
                  System.out.println("Vos compagnies :");
                  for (Compagnie c : j.getCompagnies()){
                    ProprieteUI.printCompagnie(c);
                    }
             }
         }
     }
     
     public static void passageDepart(Joueur j) {
         System.out.println("Vous venez de passer par la case départ, vous recevez 200€");
         JoueurUI.printCashVous(j);
     }
     
     public static void cLaFin(Joueur j) {
         System.out.println("Le joueur " + j.getNomJoueur() + " n'a pas de quoi payer, il fait donc faillite et sort du jeu...");
     }
     
     public static void okJeGere() {
         System.out.println("La faillite à été épargnée pour le moment");
     }
     
     public static void cBientotLaFin(Joueur j) {
        System.out.println("Le joueur "+j.getNomJoueur()+" n'a pas assez d'argent pour payer. Il doit detruire et/ou hypothequer"); 
     }
     public static void cLaFinDetruire() {
            System.out.println("Vous n'avez plus d'immobilier à détruire");
        }
        
        public static void cLaFinHypo() {
            System.out.println("Vous n'avez plus de propriete à hypothequer");
        }
        
        public static String chooseLaFin() {
        String rep = TexteUI.question("Que voulez-vous faire ? (vendre/hypotheque)");
        while (!rep.equals("vendre")&&!rep.equals("hypotheque")) {
            System.out.println("Erreur : vous devez répondre par : vendre/hypotheque ! Recommencez :");
            rep = TexteUI.question("Que voulez-vous faire ? (vendre/hypotheque)");
        }
        return rep;
        }

    public static boolean sortDuJeu(Joueur j) {
        if (TexteUI.bool("Etes-vous sûr de vouloir arreter ? (oui/non)")) {
            System.out.println("Le joueur " + j.getNomJoueur() + " sort du jeu... Ses possessions retournent à la banque");
            return true;
        } else {
            return false;
        }
    }
    
   
}
