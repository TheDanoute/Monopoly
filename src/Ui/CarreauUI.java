/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;

import Jeu.*;

/**
 *
 * @author devaucod
 */
public class CarreauUI {
    
    public static void accueilPrison(int nb){
        System.out.println("Vous êtes en prison, nombre de carte sortie de prison disponible : " + nb);
    }
    
    public static String choixActionPrison() {
        String rep = TexteUI.question("Que voulez-vous faire ? (payer/carte/des)");
        while (!rep.equals("payer")&&!rep.equals("carte")&&!rep.equals("des")) {
            System.out.println("Erreur : vous devez répondre par : payer/carte/des ! Recommencez :");
            TexteUI.question("Que voulez-vous faire ? (payer/carte/des)");
        }
        return rep;
    }
    
    public static String toMuchWastedTime() {
        String rep = TexteUI.question("Vous ne pouvez pas lancer les dés quatre fois de suite, vous devez payer ou utiliser une carte. (payer/carte)");
        while (!rep.equals("payer")&&!rep.equals("carte")) {
            System.out.println("Erreur : vous devez répondre par : payer/carte ! Recommencez :");
            rep = TexteUI.question("Que voulez-vous faire ? (payer/carte)");
        }
        return rep;
    }
    
    public static void prisonPayer(Joueur j) {
        System.out.println("Vous devez payer 50€");
        JoueurUI.printCashVous(j);
    }
    
    public static void errorCartePrison() {
        System.out.println("Vous n'avez pas de carte sortie de prison...");
    }

    public static void desPrison(int d1, int d2) {
        System.out.println("Vous avez obtenu : D1 = " + d1 + " ; D2 = " + d2);
        if (d1==d2) {
           System.out.println("Vous sortez de prison");
        } else {
           System.out.println("Vous restez en prison"); 
        }
    }
    
    public static void allerEnPrison() {
        System.out.println("Vous êtes tombé sur la case Allez en Prison ! Vous allez directement en prison sans passer par la case départ");
    }
    
    public static void visitePrison() {
        System.out.println("Vous êtes en visite à la prison.");
    }
    
    public static void removeArgent(int value) {
        System.out.println("Vous devez payer : "+value+"€");
    }
    
    public static void addArgent(int value) {
        System.out.println("Vous recevez : "+value+"€");
    }
    
    public static void piocherCarte(CarteType type) {
        System.out.println("Vous piochez une carte " + type.toString());
    }
}
