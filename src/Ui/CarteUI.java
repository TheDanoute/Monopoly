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
        System.out.println("Vous êtes donc a la case n°" + c.getNum() + ":" + c.getNom());
    }
    
    public static void payerHotelMaison(int pM, int nbM, int pH, int nbH) {
        System.out.println("Prix pour les maisons : " + pM + "*" + nbM + "=" + pM*nbM);
        System.out.println("Prix pour les hotels : " + pH + "*" + nbH + "=" + pH*nbH);
        int somme = nbM*pM+nbH*pH;
        System.out.println("Total : " + somme + "€");
    }
    
    public static String jAiDeLaChance() {
        String rep = TexteUI.question("Vous choisissez ? (chance/payer)");
        while (!rep.equals("payer")&&!rep.equals("chance")) {
            System.out.println("Erreur : vous devez répondre par : chance/payer ! Recommencez :");
            rep = TexteUI.question("Que voulez-vous faire ? (chance/payer)");
        }
        return rep;
    }
    
    public static void printInfo(Carte c) {
        System.out.println("Carte type : " + c.getType() + " ; " + c.getDescription());
    }

    public static void laChance() {
        System.out.println("Vous êtes passé par la case départ");
    }
}
