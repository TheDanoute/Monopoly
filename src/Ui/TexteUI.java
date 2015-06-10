/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;
import Jeu.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author devaucod
 */
public class TexteUI {
    public static void message (String s){
        System.out.println(s);
    }
    
    public static String question(String question){
        System.out.println(question);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    
    public static void echange(){
        System.out.println("********** Echange **********");
    }
    
    public static Joueur joueurChoisisEchange(ArrayList<Joueur> listJ){
        return listJ.get(Integer.valueOf(TexteUI.question("Avec quel joueur voulez-vous échanger ? (numéro)"))-1);   
    }
    
    public static String typeEchange (){
        return TexteUI.question("Que voulez-vous de ce joueur ? (propriete / carte)");
    }
    
    public static int choixPropEch (){
        return Integer.valueOf(TexteUI.question("Quelle propriete voulez-vous ? (nombre)"));
    }
    
    public static String autrePropEch (){
        return TexteUI.question("Voulez-vous une autre propriété de ce joueur ? (oui/non)");
    }
    
    public static String rajouteArgent(){
        return TexteUI.question("Voulez-vous rajouter de l'argent ? (oui/non)");
    }
    
    public static int combienArgent(){
        return Integer.parseInt(TexteUI.question("Combien d'argent voulez-vous ? (nombre)"));
    }
    
    public static void pasPropEch (Joueur j){
        System.out.println(j.getNomJoueur()+" n'a pas de propriété échangeable");
    }
    
    public static void vosProps(){
        System.out.println("*** Vos propriétés ***");
    }
    
    public static int choixPropEch2(){
        return Integer.valueOf(TexteUI.question("Quelle propriete proposez-vous ? (numéro)"));
    }
    
    public static String autrePropEch2 (){
        return TexteUI.question("Voulez-vous proposer une autre propriété ? (oui/non)");
    }
    
    public static void pasEchRienProp(){
        System.out.println("Pas d'échange possible, vous n'avez rien proposé.");
    }
    
    public static String propositionEchange(Echange echangeJ1, Echange echangeJ2, Joueur j1, Joueur j2){      
        System.out.println(j2.getNomJoueur()+", "+j1.getNomJoueur()+" vous a proposé un échange.\nIl vous demande :");
        for (CarreauPropriete cp : echangeJ2.getListP()){
            System.out.println(cp.getNom());
        }
        System.out.println(String.valueOf(echangeJ2.getSomme())+"€");
        System.out.println("Et il vous propose :");
        for (CarreauPropriete cp : echangeJ1.getListP()){
            System.out.println(cp.getNom());
        }
        System.out.println(String.valueOf(echangeJ1.getSomme())+"€");
        return TexteUI.question("Acceptez-vous l'échange ? (oui/non)");
    }
    
    public static void pasCartePrison (Joueur j){
        System.out.println(j.getNomJoueur()+" n'a pas de carte sortie de prison.");
    }
    
    public static int offreCartePrison (Joueur j){
        return Integer.valueOf(TexteUI.question(j.getNomJoueur()+" a une carte sortie de prison, combien lui en proposez-vous ?"));
    }
    
    public static String propositionEchange (Joueur j2, Joueur j1, int somme){
        return TexteUI.question(j2.getNomJoueur()+", "+j1.getNomJoueur()+" vous propose "+somme+"€ contre une carte sortie de prison, êtes-vous d'accord? (oui/non)");
    }
    
    public static void echAnnul(Joueur j){
        System.out.println("Echange annulé, "+j.getNomJoueur()+" l'a refusé.");
    }
    
    public static void sesProps(){
        System.out.println("*** Ses propriétés ***");
    }
    
    public static void pasEncoreEch(){
        System.out.println("Vous ne pouvez pas faire d'échange, toutes les propriétés n'ont pas encore été vendues");
    }
    
    public static boolean bool(String g) {
        String rep = question(g);
        while (!rep.equals("non") && !rep.equals("oui")) {
            System.out.println("Erreur, vous devez saisir oui ou non ! Recommencez : ") ;
            rep = question(g);
        }
        return rep.equals("oui");
    }
    
    public static int inte(String g) {
        do {
            try { 
                return Integer.valueOf(question(g));
            } catch(java.lang.NumberFormatException e) {
                System.out.println("Vous devez rentrer un numéro, recommencez :");
            }
        } while(true);
    }
    
    public static void dés(int d1,int d2) {
       int sD = d1+d2;
       System.out.println("D1 = " + d1 + " ; D2 = " + d2 + " ; Somme des dés = " + sD); 
    }
    
    public static void printAncienCarreau(Carreau c) {
        System.out.println("Ancien Carreau : " + c.getNom());
    }
    
    public static void printNouveauCarreau(Carreau c) {
        System.out.println("Nouveau Carreau : " + c.getNom());
    }
    
    
}
