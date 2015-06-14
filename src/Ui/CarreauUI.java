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
        System.out.println("Vous êtes en prison, nombre de carte sortie de prison disponible : \u001B[36m" + nb + "\u001B[0m");
    }
    
    public static String choixActionPrison() {
        String rep = TexteUI.question("Que voulez-vous faire ? \u001B[32m(payer/carte/des)\u001B[0m");
        while (!rep.equals("payer")&&!rep.equals("carte")&&!rep.equals("des")) {
            System.out.println("\u001B[31mErreur : vous devez répondre par : \u001B[32mpayer\u001B[31m/\u001B[32mcarte\u001B[31m/\u001B[32mdes\u001B[31m ! Recommencez :\u001B[0m");
            TexteUI.question("Que voulez-vous faire ? \u001B[32m(payer/carte/des)\u001B[0m");
        }
        return rep;
    }
    
    public static String toMuchWastedTime() {
        String rep = TexteUI.question("Vous ne pouvez pas lancer les dés quatre fois de suite, vous devez payer ou utiliser une carte. \u001B[32m(payer/carte)\u001B[0m");
        while (!rep.equals("payer")&&!rep.equals("carte")) {
            System.out.println("\u001B[31mErreur : vous devez répondre par : \u001B[32mpayer \u001B[31mou \u001B[32mcarte\u001B[31m ! Recommencez :\u001B[0m");
            rep = TexteUI.question("Que voulez-vous faire ? \u001B[32m(payer/carte)\u001B[0m");
        }
        return rep;
    }
    
    public static void prisonPayer(Joueur j) {
        System.out.println("Vous devez payer \u001B[33m50\u001B[0m€");
    }
    
    public static void errorCartePrison() {
        System.out.println("Vous n'avez pas de carte sortie de prison...");
    }

    public static void desPrison(int d1, int d2) {
        System.out.println("Vous avez obtenu : D1 = \u001B[36m" + d1 + "\u001B[0m ; D2 = \u001B[36m" + d2 + "\u001B[0m");
        if (d1==d2) {
           System.out.println("\u001B[31mVous sortez de prison\u001B[0m");
        } else {
           System.out.println("Vous restez en prison"); 
        }
    }
    
    public static void allerEnPrison() {
        System.out.println("Vous êtes tombé sur la case Allez en Prison ! \u001B[31mVous allez directement en prison sans passer par la case départ\u001B[0m");
    }
    
    public static void visitePrison() {
        System.out.println("Vous êtes en visite à la prison.");
    }
    
    public static void removeArgent(int value) {
        System.out.println("Vous devez payer : \u001B[33m"+value+"\u001B[0m€");
    }
    
    public static void addArgent(int value) {
        System.out.println("Vous recevez : \u001B[33m"+value+"\u001B[0m€");
    }
    
    public static void piocherCarte(CarteType type) {
        System.out.println("Vous piochez une carte \u001B[35m" + type.toString()+"\u001B[0m");
    }
}
