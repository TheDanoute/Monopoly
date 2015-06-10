/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;

import Jeu.Compagnie;
import Jeu.CouleurPropriete;
import Jeu.Gare;
import Jeu.ProprieteAConstruire;
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
         
        
}