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

    public static void printInfo(Carreau c) {
        String info = "";
        try {
            ProprieteAConstruire pAC = (ProprieteAConstruire)c;
            info = "Propriété : " + c.getNom() + " ; groupe : " + pAC.getCouleur().toString() +" ; case : " + c.getNum();
        } catch(java.lang.ClassCastException e) {    
            info = c.getClass().getSimpleName() + " : " + c.getNom() + " ; case : " + c.getNum();
        } finally {
            System.out.println(info);
        }
    }
    
    public static void errorFile(int error) {
       switch (error) {
           case 0:
           {
                System.err.println("[buildGamePleateau()] : Invalid Data type");
                break;
           }
           case 1:
           {
                System.err.println("[buildGamePlateau()] : File is not found!");
                break;
           }
           case 2:
           {
                System.err.println("[buildGamePlateau()] : Error while reading file!");
                break;
           }
        }
    }

    public static String nouveauJoueur(int nbJoueur) {
        if (nbJoueur>5) {
            System.out.println("Attention ! Dernier joueur à rajouter :");
        }
        return TexteUI.question("Nom du joueur n°" + nbJoueur + " ?");
    }
    
    public static boolean ajouterJoueur() {
        return bool("Voules-vous ajouter un nouveau joueur? (oui/non)");
    }
    
    public static void afficherJoueurs(ArrayList<Joueur> list) {
        System.out.println("Ordre des joueurs");
        for (Joueur jou : list) {
            System.out.println(" - "+ jou.getNomJoueur());
        }
    }
    
    public static void ajoutJoueur(Joueur j) {
        System.out.println(j.getNomJoueur() + " a été ajouté.");
        System.out.println("Son premier lancé de dés : " + j.getDesDepart());
    }
    
    public static void trieJoueur(Joueur j,int value) {
        System.out.println("Etant arriver ex aequo, " + j.getNomJoueur() + " relance le dé et obtient : " + value);
    }

    public static String choixTour() {
        String rep = TexteUI.question("Que voulez-vous faire ? (avancer/construire/echanger/detruire/hypotheque/sortir)");
        while (!rep.equals("avancer")&&!rep.equals("construire")&&!rep.equals("echanger")&&!rep.equals("detruire")&&!rep.equals("hypotheque")&&!rep.equals("sortir")) {
            System.out.println("Erreur : vous devez répondre par : avancer/construire/echanger/detruire/hypotheque/sortir ! Recommencez :");
            rep = TexteUI.question("Que voulez-vous faire ? (avancer/construire/echanger/detruire/hypotheque/sortir)");
        }
        return rep;
    }
    
    public static void avancer(){
        System.out.println("Vous avancer");
    }
    
    public static void troisDouble(Joueur j) {
        System.out.println("Le joueur "+j.getNomJoueur()+" a fait trois double de suite, il est envoyé en prison!");
    }
    
    public static void nouveauTour(Joueur j){
        System.out.println("***************************************");
        System.out.println("Au tour de "+ j.getNomJoueur());
    }

    public static void dooble() {
        TexteUI.question("Vous avez fait un double, vous rejouez (appuyer sur entrée pour continuer)");
    }

    public static void finTour() {
        TexteUI.question("Fin du tour ! (appuyez sur entrée pour continuer)");
    }

    public static void errorNouveauJoueur() {
        System.out.println("Erreur : vous devez au moins avoir deux joueurs, veillez rentrer le nom du deuxième joueur : ");
    }

    public static void taGagne(Joueur get) {
        System.out.println("Le joueur : " +get.getNomJoueur() +" à gagné ! Félicitation à lui !");
    }

    
    
}
