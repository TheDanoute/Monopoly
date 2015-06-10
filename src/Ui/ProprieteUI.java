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
 * @author devaucod
 */
public class ProprieteUI {
    
    public static void construction() {
        System.out.println("Construction :");
    }
    
    public static void aucunGroupeComplet() {
        System.out.println("Vous ne possédez aucun groupe de proprietes complet...");
    }
    
    public static CouleurPropriete chooseGroupe(HashMap<CouleurPropriete,ArrayList<ProprieteAConstruire>> list) {
        System.out.println("Vous possédez les groupes modifiables suivants :");
        for (CouleurPropriete c : list.keySet()) {
            System.out.println(c.toString());
        }
        boolean ok = false; //Boolean anti-faute de frappe
        CouleurPropriete coul = CouleurPropriete.bleuCiel; //Initialisation de variable
        do {
            try {
                coul = CouleurPropriete.valueOf(TexteUI.question("Sur quel groupe voulez-vous agir ?"));
                if (list.containsKey(coul)) {
                    ok = true;
                } else {
                    System.out.println("Cette couleur ne fait pas partie de la liste");
                }
            } catch(java.lang.IllegalArgumentException e) {
                System.out.println("Erreur, recommencez. (Sensible à la casse)");
            }
        } while (!ok);
        return coul;
    }
    
    public static void erreurHypo() {
        System.out.println("Au moins une des propriétés de ce groupe est hypothéquée, impossible de construire");
    }
    
    public static void erreurHotel() {
        System.out.println("Tous ces terrains on déjà des hotels, impossible de construire dessus");
    }
    
    public static void construireSur() {
        System.out.println("Vous pouvez construire sur :");
    }
    
    public static void printPropriete(ProprieteAConstruire p) {
        System.out.println(p.getNom() + " ; Construction existante : "+ getImmobilier(p) + " ; n°" + p.getNum());
    }
    
    public static String getImmobilier(ProprieteAConstruire p) {
		String rep;
        switch (p.getImmobilier()) {
                    case 0:
                    {
                        rep = " vide";
                        break;
                    }
                    case 1:
                    {
                        rep = " une maison";
                        break;
                    }
                    case 2:
                    {
                        rep = " deux maisons";
                        break;
                    }
                    case 3:
                    {
                        rep = " trois maisons";
                        break;
                    }
                    case 4:
                    {
                        rep = " quatre maisons";
                        break;
                    }
                    case 5:
                    {
                        rep = " un hôtel";
                        break;
                    }
                    default:
                    {
                        rep = "error";
                        break;
                    }
                }
                return rep;
	}
    
        public static boolean errorBanque(boolean c) {
            String rep;
            if (c) {
                rep = " d'hotel ";
            } else {
                rep = " de maison ";
            }
            System.out.println("La banque n'a malheuresement plus" + rep + "en stock, vous ne pouvez pas construire sur ces terrains...");
            return true;
        }
        
        public static int chooseNum() {
            return TexteUI.inte("Sur quel numéro voulez construire ? (numéro)");
        }
        
        public static void errorConstruction(){
            System.out.println("Ce terrain n'est pas constructible pour le moment");
        }
        
        public static void errorDestruction() {
            System.out.println("Ce terrain n'est pas destructible pour le moment");
        }
        
        public static void errorHypoNonProposee(){
            System.out.println("Erreur : Ce terrain n'est pas disponible à l'hypothèque, recommencez :");
        }
        
        public static void errorNonHypo(){
            System.out.println("Erreur : Ce terrain n'est pas hypothéqué, recommencez :");
        }
        
        public static int chooseHypo() {
            return TexteUI.inte("Sur quelle propriété voulez-vous lever a ? (numéro)");
        }
        
        public static boolean encoreConstruire() {
            return TexteUI.bool("Voulez-vous construire une autre maison/hotel ? (oui/non)");
        }
        
        public static boolean encoreDetruire() {
            return TexteUI.bool("Voulez-vous detruire une autre maison/hotel ? (oui/non)");
        }
        
        public static void printDetruire() {
            System.out.println("Vous pouvez détruire sur les proprietes suivantes : (attention les hotels ne rendront pas des maisons)");
        }
        
        public static boolean errorVide() {
            System.out.println("Les terrains de ce groupe sont tous vides, vous ne pouvez plus rien détruire");
            return true;
        }
        
        public static String menuHypo(boolean display) {
            String rep;
            if (display) {
                rep = TexteUI.question("Voulez-vous lever une hypotheque ou hypothequer ? (lever/hypotheque)");
                while (rep.equals("lever") && rep.equals("hypotheque")) {
                    System.out.println("Erreur : vous devez repondre par lever ou hypotheque ! Recommencez :");
                    rep = TexteUI.question("Voulez-vous lever une hypotheque ou hypothequer ? (lever/hypotheque)");
                }
            } else {
                rep = "hypotheque";
            }
            return rep;    
        }
        
        public static void printListHypo(){
            System.out.println("Liste des proprietes hypothéquées :");
        }
        
        public static void printGare(Gare g) {
            System.out.println("Gare : " + g.getNom() + " ; n°" + g.getNum());
        }
        
        public static void printCompagnie(Compagnie c) {
            System.out.println("Compagnie : " + c.getNom() + " ; n°" + c.getNum());
        }
        
        public static void errorHypo() {
            System.out.println("Vous n'avez aucune propriété hypothequée");
        }
         
        public static void leverHypo(CarreauPropriete c) {
            System.out.println("Lever l'hypothèque de cette propriété coute : " + c.getPrixHypotheque() + "€");
        }
        
        public static boolean continuerHypo() {
            return TexteUI.bool("Voulez-vous continuer ? (oui/non)");
        }
        
        public static void hypoDispo() {
            System.out.println("Liste des proprietes disponible à l'hypotheque :");
        }
        
        public static void printHypo(CarreauPropriete c) {
            System.out.println("L'hypotheque vous rapporte" + c.getPrix()/2 + "€");
        }
        
        public static boolean printAchat(CarreauPropriete c) {
        System.out.println("Cette " + c.getClass().getSimpleName() + " : " + c.getNom() + " est disponible à l'achat au prix de " + c.getPrix() +"€");
        return TexteUI.bool("Voulez-vous acheter cette " + c.getClass().getSimpleName() + " ? (oui/non)");
    }
        
        public static void printProprePAC(CarreauPropriete c) {
            System.out.println("Vous êtes sur votre : " + c.getNom());
        }
        
        public static boolean toucherLoyer(CarreauPropriete c) {
             System.out.println("Vous êtes tomber sur une " + c.getClass().getSimpleName() + " qui à déjà un propriétaire.");
             if (c.isHypotheque()) {
                 System.out.println("Cette " + c.getClass().getSimpleName() + " est hypothéquée, vous ne payez rien");
                 return false;
             } else {
                System.out.println("Vous devez payer " + c.getLoyer() + "€");
                return true;
             }
        }
        
        public static void nouvelleConstruction(ProprieteAConstruire p) {
            System.out.println("Ce terrain dispose maintenant de : " + getImmobilier(p));
            System.out.println("Les joueurs qui passeront sur ce terrain payeront : " + p.getLoyer() + "€");
            JoueurUI.printCashVous(p.getProprietaire());
        }
        
        public static void destructionMaison(ProprieteAConstruire p,int argent) {
            System.out.println("La vente de cette maison vous rapporte " + argent + "€");
            if(p.getImmobilier()>0) {
                System.out.println("Il reste sur ce terrain : " + getImmobilier(p));
            } else {
                System.out.println("Ce terrain est maintenant vide de construction");
            }
            JoueurUI.printCashVous(p.getProprietaire());
        }
        
        public static void destructionHotel(ProprieteAConstruire p,int argent) {
            System.out.println("La vente de cet hotel vous rapporte " + argent + "€");
            System.out.println("Ce terrain est maintenant vide de construction");
            JoueurUI.printCashVous(p.getProprietaire());
        }
        
        public static void possession(Joueur j) {
            System.out.println(j.getNomJoueur() + " possède maintenant tout les propriétés du groupe, il peut désormais construire dessus");
        }
        
        
}