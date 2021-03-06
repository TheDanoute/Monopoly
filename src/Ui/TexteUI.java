/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;
import Jeu.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public static boolean demo(){
        return TexteUI.bool("Voulez-vous lancer le mode de démonstration ? \u001B[32m(oui/non)\u001B[0m");
    }
    
    public static void echange(){
        System.out.println("\u001B[35m********** Echange **********\u001B[0m");
    }
    
    public static Joueur joueurChoisisEchange(ArrayList<Joueur> listJ){
        String rep = TexteUI.question("Avec quel joueur voulez-vous échanger ? \u001B[32m(numéro)\u001B[0m");
        boolean ok = false;
        while (!ok){
            try {
                return listJ.get(Integer.valueOf(rep)-1);
            }
            catch (Exception e) {
                System.out.println("\u001B[31mErreur, vous devez saisir un entier correspondant à un joueur : \u001B[0m") ;
                rep = TexteUI.question("Avec quel joueur voulez-vous échanger ? \u001B[32m(numéro)\u001B[0m");

            }
        }
        return null;
    }
    
    public static String typeEchange (){
        String rep = TexteUI.question("Que voulez-vous de ce joueur ? \u001B[32m(propriete / carte)\u001B[0m");
        while (!rep.equals("propriete") && !rep.equals("carte")) {
            System.out.println("\u001B[31mErreur, vous devez saisir \u001B[32mpropriete \u001B[31mou \u001B[32mcarte \u001B[31m! Recommencez : \u001B[0m") ;
            rep = TexteUI.question("Que voulez-vous de ce joueur ? \u001B[32m(propriete / carte)\u001B[0m");
        }
        return rep;
    }
    
    public static int choixPropEch (HashMap<Integer,CarreauPropriete> cp){
        String rep =TexteUI.question("Quelle propriete voulez-vous ? \u001B[32m(nombre)\u001B[0m");
        boolean ok = false;
        while (!ok){
            try {
                int i = Integer.valueOf(rep);  
                if (cp.containsKey(i)){   // test si la réponse est bien un indice de la liste (ne marche pas)
                    return i;
                }
            }
            catch (Exception e) {
                System.out.println("\u001B[31mErreur, vous devez saisir un entier correspondant à une propriété : \u001B[0m") ;
                rep =TexteUI.question("Quelle propriete voulez-vous ? \u001B[32m(nombre)\u001B[0m");
            }
        }
        return 0;
    }
    
    public static String autrePropEch (){
        return TexteUI.question("Voulez-vous une autre propriété de ce joueur ? \u001B[32m(oui/non)\u001B[0m");
    }
    
    public static String rajouteArgent(){
        String rep = TexteUI.question("Voulez-vous rajouter de l'argent ? \u001B[32m(oui/non)\u001B[0m");
        while (!rep.equals("non") && !rep.equals("oui")) {
            System.out.println("\u001B[31mErreur, vous devez saisir \u001B[32moui \u001B[31mou \u001B[32mnon \u001B[31m! Recommencez : \u001B[0m") ;
            rep = TexteUI.question("Voulez-vous rajouter de l'argent ? \u001B[32m(oui/non)\u001B[0m");
        }
        return rep;
    }
    
    public static int combienArgent(){
        return TexteUI.inte("Combien d'argent voulez-vous ? \u001B[32m(nombre)\u001B[0m");
    }
    
    public static void pasPropEch (Joueur j){
        System.out.println("\u001B[31m" + j.getNomJoueur()+" n'a pas de propriété échangeable\u001B[0m");
    }
    
    public static void vosProps(){
        System.out.println("\u001B[35m*** Vos propriétés ***\u001B[0m");
    }
    
    public static int choixPropEch2(){
        return TexteUI.inte("Quelle propriete proposez-vous ? \u001B[32m(numéro)\u001B[0m");
    }
    
    public static String autrePropEch2 (){
        String rep = TexteUI.question("Voulez-vous proposer une autre propriété ? \u001B[32m(oui/non)\u001B[0m");
        while (!rep.equals("non") && !rep.equals("oui")) {
            System.out.println("\u001B[31mErreur, vous devez saisir \u001B[32moui \u001B[31mou \u001B[32mnon \u001B[31m! Recommencez : \u001B[0m") ;
            rep = TexteUI.question("Voulez-vous proposer une autre propriété ? \u001B[32m(oui/non)\u001B[0m");
        }
        return rep;
    }
    
    public static void pasEchRienProp(){
        System.out.println("\u001B[31mPas d'échange possible, vous n'avez rien proposé.\u001B[0m");
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
        String rep = TexteUI.question("Acceptez-vous l'échange ? \u001B[32m(oui/non)\u001B[0m");
        while (!rep.equals("non") && !rep.equals("oui")) {
            System.out.println("\u001B[31mErreur, vous devez saisir \u001B[32moui \u001B[31mou \u001B[32mnon \u001B[31m! Recommencez : \u001B[0m") ;
            rep = TexteUI.question("Acceptez-vous l'échange ? \u001B[32m(oui/non)\u001B[0m");
        }
        return rep;
    }
    
    public static void pasCartePrison (Joueur j){
        System.out.println(j.getNomJoueur()+" n'a pas de carte sortie de prison.");
    }
    
    public static int offreCartePrison (Joueur j){
        return Integer.valueOf(TexteUI.question(j.getNomJoueur()+" a une carte sortie de prison, combien lui en proposez-vous ?"));
    }
    
    public static String propositionEchange (Joueur j2, Joueur j1, int somme){
        return TexteUI.question(j2.getNomJoueur()+", "+j1.getNomJoueur()+" vous propose "+somme+"€ contre une carte sortie de prison, êtes-vous d'accord? \u001B[32m(oui/non)\u001B[0m");
    }
    
    public static void echAnnul(Joueur j){
        System.out.println("Echange annulé, "+j.getNomJoueur()+" l'a refusé.");
    }
    
    public static void sesProps(){
        System.out.println("\u001B[35m*** Ses propriétés ***\u001B[0m");
    }
    
    public static void pasEncoreEch(){
        System.out.println("\u001B[31mVous ne pouvez pas faire d'échange, toutes les propriétés n'ont pas encore été vendues\u001B[0m");
    }
    
    public static boolean bool(String g) {
        String rep = question(g);
        while (!rep.equals("non") && !rep.equals("oui")) {
            System.out.println("\u001B[31mErreur, vous devez saisir \u001B[32moui \u001B[31mou \u001B[32mnon \u001B[31m! Recommencez : \u001B[0m") ;
            rep = question(g);
        }
        return rep.equals("oui");
    }
    
    public static int inte(String g) {
        do {
            try { 
                return Integer.valueOf(question(g));
            } catch(java.lang.NumberFormatException e) {
                System.out.println("\u001B[31mVous devez rentrer un \u001B[32mnuméro\u001B[31m, recommencez :\u001B[0m");
            }
        } while(true);
    }
    
    public static void dés(int d1,int d2) {
       int sD = d1+d2;
       System.out.println("D1 = " + d1 + " ; D2 = " + d2 + " ; Somme des dés = \u001B[36m" + sD + "\u001B[0m"); 
    }
    
    public static void printAncienCarreau(Carreau c) {
        System.out.println("Ancien Carreau : \u001B[34m" + c.getNom()+ "\u001B[0m");
    }
    
    public static void printNouveauCarreau(Carreau c) {
        System.out.println("Nouveau Carreau : \u001B[34m" + c.getNom()+ "\u001B[0m");
    }

    public static void printInfo(Carreau c) {
        String info = "";
        try {
            ProprieteAConstruire pAC = (ProprieteAConstruire)c;
            info = "Propriété : " + c.getNom() + " ; groupe : " + pAC.getCouleur().toString() +" ; case : \u001B[36m" + c.getNum()+ "\u001B[0m";
        } catch(java.lang.ClassCastException e) {    
            info = c.getClass().getSimpleName() + " : " + c.getNom() + " ; case : \u001B[36m" + c.getNum()+ "\u001B[0m";
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
            System.out.println("\u001B[31mAttention ! Dernier joueur à rajouter :\u001B[0m");
        }
        return TexteUI.question("Nom du joueur n°" + nbJoueur + " ?");
    }
    
    public static boolean ajouterJoueur() {
        return bool("Voules-vous ajouter un nouveau joueur? \u001B[32m(oui/non)\u001B[0m");
    }
    
    public static void afficherJoueurs(ArrayList<Joueur> list) {
        System.out.println("Ordre des joueurs");
        for (Joueur jou : list) {
            System.out.println(" - "+ jou.getNomJoueur());
        }
    }
    
    public static void ajoutJoueur(Joueur j) {
        System.out.println(j.getNomJoueur() + " a été ajouté.");
        System.out.println("Son premier lancé de dés : \u001B[36m" + j.getDesDepart() + "\u001B[0m");
    }
    
    public static void trieJoueur(Joueur j,int value) {
        System.out.println("Etant arriver ex aequo, " + j.getNomJoueur() + " relance le dé et obtient : \u001B[36m" + value+"\u001B[0m");
    }

    public static String choixTour() {
        String rep = TexteUI.question("Que voulez-vous faire ? \u001B[32m(avancer/construire/echanger/detruire/hypotheque/sortir)\u001B[0m");
        while (!rep.equals("avancer")&&!rep.equals("construire")&&!rep.equals("echanger")&&!rep.equals("detruire")&&!rep.equals("hypotheque")&&!rep.equals("sortir")&&!rep.equals("setPosition")&&!rep.equals("setDes")&&!rep.equals("toutVendre")) {
            System.out.println("\u001B[31mErreur : vous devez répondre par : \u001B[32mavancer/construire/echanger/detruire/hypotheque/sortir\u001B[31m ! Recommencez :\u001B[0m");
            rep = TexteUI.question("Que voulez-vous faire ? \u001B[32m(avancer/construire/echanger/detruire/hypotheque/sortir)\u001B[0m");
        }
        return rep;
    }
    
    public static void avancer(){
        System.out.println("Vous avancer");
    }
    
    public static void troisDouble(Joueur j) {
        System.out.println("\u001B[31mLe joueur "+j.getNomJoueur()+" a fait trois double de suite, il est envoyé en prison!\u001B[0m");
    }
    
    public static void nouveauTour(Joueur j){
        System.out.println("\u001B[35m***************************************");
        System.out.println("Au tour de "+ j.getNomJoueur() + "\u001B[0m");
    }

    public static void dooble() {
        TexteUI.question("\u001B[34mVous avez fait un double, vous rejouez (appuyer sur entrée pour continuer)\u001B[0m");
    }

    public static void finTour() {
        TexteUI.question("Fin du tour ! \u001B[32m(appuyez sur entrée pour continuer)\u001B[0m");
    }

    public static void errorNouveauJoueur() {
        System.out.println("\u001B[31mErreur : vous devez au moins avoir deux joueurs, veillez rentrer le nom du deuxième joueur : \u001B[0m");
    }

    public static void taGagne(Joueur get) {
        System.out.println("\u001B[41mLe joueur : " +get.getNomJoueur() +" à gagné ! Félicitation à lui !\u001B[41m");
    }

    public static void cheater() {
        System.out.println("\u001B[41mUn tricheur est apparu dans la partie... Il possède toutes les propriétés invendues et a beaucoup d'argent...\u001B[0m");
    }

    
    
}
