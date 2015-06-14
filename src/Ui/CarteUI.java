/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;

import Jeu.Carreau;
import Jeu.Carte;

/**
 *
 * @author devaucod
 */
public class CarteUI {
    
    public static void deplacement(Carreau c){
        System.out.println("Vous êtes donc a la case n°\u001B[36m" + c.getNum() + "\u001B[0m:\u001B[34m" + c.getNom() + "\u001B[0m");
    }
    
    public static void payerHotelMaison(int pM, int nbM, int pH, int nbH) {
        System.out.println("Prix pour les maisons : \u001B[33m" + pM + "\u001B[0m€*" + nbM + "=\u001B[33" + pM*nbM+ "\u001B[0m€");
        System.out.println("Prix pour les hotels : \u001B[33m" + pH + "\u001B[0m€*" + nbH + "=\u001B[33" + pH*nbH+ "\u001B[0m€");
        int somme = nbM*pM+nbH*pH;
        System.out.println("Total : \u001B[33m" + somme + "\u001B[0m€");
    }
    
    public static String jAiDeLaChance() {
        String rep = TexteUI.question("Vous choisissez ? \u001B[32m(chance/payer)\u001B[0m");
        while (!rep.equals("payer")&&!rep.equals("chance")) {
            System.out.println("\u001B[31mErreur : vous devez répondre par : \u001B[32mchance \u001B[31mou \u001B[32mpayer\u001B[31m ! Recommencez :\u001B[0m");
            rep = TexteUI.question("Que voulez-vous faire ? \u001B[32mchance/payer)\u001B[0m");
        }
        return rep;
    }
    
    public static void printInfo(Carte c) {
        System.out.println("Carte type : " + c.getType() + " ; " + c.getDescription());
    }

    public static void laChance() {
        System.out.println("\u001B[31mVous êtes passé par la case départ\u001B[0m");
    }
}
