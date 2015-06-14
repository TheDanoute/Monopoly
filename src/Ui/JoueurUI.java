/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;

import Jeu.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author gueganb
 */
public class JoueurUI {
    
    public static void debutTour(ArrayList<Joueur> list) {
        System.out.println("\u001B[44m\u001B[36mDébut du break d'entre-tour :\u001B[0m");
        for (Joueur j : list) {
            System.out.println("\u001B[35m\u001B[43m*** Joueur : " + j.getNomJoueur()+" ***\u001B[0m");
            if (j.getPrison()) {
                System.out.println("\u001B[37m\u001B[41mEN PRISON\u001B[0m");
            }
            System.out.println("Ses propriété(s) :");
            for (Compagnie c : j.getCompagnies()){
                ProprieteUI.printCompagnie(c);
            }
            for (Gare g : j.getGares()){
                ProprieteUI.printGare(g);
            }
            for (ProprieteAConstruire p : j.getProprietesAConstruire()){
                ProprieteUI.printPropriete(p);
            }
            System.out.println("Nombre de carte(s) sortie de prison : \u001B[36m"+j.getNBCartePrison()+"\u001B[0m");
            System.out.println("Position actuelle : \u001B[34m" + j.getPositionCourante().getNom() + "\u001B[0m n°\u001B[36m" + j.getPositionCourante().getNum());
            System.out.println("\u001B[0mArgent disponible : \u001B[33m" + j.getCash() + "\u001B[0m€");
        }
        System.out.println("\u001B[44m\u001B[36mFin du break d'entre-tour\u001B[0m");
        TexteUI.question("Appuyer sur \u001B[32mentrée \u001B[0mpour continuer...");
    }
    public static void afficheProprietes (Joueur j, int i){
        System.out.println("\u001B[35m;43m*** Joueur n°"+i+" : " + j.getNomJoueur()+" ***\u001B[0m");
        System.out.println("Ses propriété(s) :");
        for (Compagnie c : j.getCompagnies()){
            System.out.println(c.getNom());
        }
        for (Gare g : j.getGares()){
            System.out.println(g.getNom());
        }
        for (ProprieteAConstruire p : j.getProprietesAConstruire()){
            ProprieteUI.printPropriete(p);
        }
        System.out.println("Nombre de carte(s) sortie de prison : \u001B[36m"+j.getNBCartePrison()+"\u001B[0m");
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
                            System.out.println("Groupe : "+p.getCouleur().toString());
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
        System.out.println("Vous avez maintenant \u001B[33m" + j.getCash() + "\u001B[0m€");
    }
     public static void printCashLe(Joueur j) {
        System.out.println("Le joueur " + j.getNomJoueur() + " a maintenant \u001B[33m"+ j.getCash() + "\u001B[0m€");
    }
    
     public static void errorArgent(Joueur j){
         System.out.println("Vous n'avez pas assez d'argent : vous avez \u001B[33m" + j.getCash() + "\u001B[0m€");
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
         System.out.println("\u001B[36mVous venez de passer par la case départ, vous recevez \u001B[33m200\u001B[36m€\u001B[0m");
         JoueurUI.printCashVous(j);
     }
     
     public static void cLaFin(Joueur j) {
         System.out.println("\u001B[41mLe joueur " + j.getNomJoueur() + " n'a pas de quoi payer, il fait donc faillite et sort du jeu...\u001B[0m");
     }
     
     public static void okJeGere() {
         System.out.println("\u001B[31mLa faillite à été épargnée pour le moment\u001B[0m");
     }
     
     public static void cBientotLaFin(Joueur j) {
        System.out.println("Le joueur "+j.getNomJoueur()+" n'a pas assez d'argent pour payer. Il doit detruire et/ou hypothequer"); 
     }
     public static void cLaFinDetruire() {
            System.out.println("\u001B[31mVous n'avez plus d'immobilier à détruire\u001B[0m");
        }
        
        public static void cLaFinHypo() {
            System.out.println("\u001B[31mVous n'avez plus de propriete à hypothequer\u001B[0m");
        }
        
        public static String chooseLaFin() {
        String rep = TexteUI.question("Que voulez-vous faire ? \u001B[32m(vendre/hypotheque)\u001B[0m");
        while (!rep.equals("vendre")&&!rep.equals("hypotheque")) {
            System.out.println("Erreur : vous devez répondre par :\u001B[32m vendre\u001B[31m ou \u001B[32mhypotheque\u001B[31m ! Recommencez :");
            rep = TexteUI.question("Que voulez-vous faire ? \u001B[32m(vendre/hypotheque)\u001B[0m");
        }
        return rep;
        }

    public static boolean sortDuJeu(Joueur j) {
        if (TexteUI.bool("\u001B[31mEtes-vous sûr de vouloir arreter ? \u001B[32m(oui/non)\u001B[0m")) {
            System.out.println("\u001B[41mLe joueur " + j.getNomJoueur() + " sort du jeu... Ses possessions retournent à la banque\u001B[0m");
            return true;
        } else {
            return false;
        }
    }
    
   
}
