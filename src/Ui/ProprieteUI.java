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
        System.out.println("\u001B[35m*********Construction :*************\u001B[0m");
    }
    
    public static void aucunGroupeComplet() {
        System.out.println("\u001B[31mVous ne possédez aucun groupe de proprietes complet...\u001B[0m");
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
                coul = CouleurPropriete.valueOf(TexteUI.question("Sur quel groupe voulez-vous agir ? \u001B[32m(couleur)\u001B[0m"));
                if (list.containsKey(coul)) {
                    ok = true;
                } else {
                    System.out.println("\u001B[31mCette couleur ne fait pas partie de la liste\u001B[0m");
                }
            } catch(java.lang.IllegalArgumentException e) {
                System.out.println("\u001B[31mErreur, recommencez. \u001B[40m(Sensible à la casse)\u001B[0m");
            }
        } while (!ok);
        return coul;
    }
    
    public static void erreurHypo() {
        System.out.println("\u001B[31mAu moins une des propriétés de ce groupe est hypothéquée, impossible de construire\u001B[0m");
    }
    
    public static void erreurHotel() {
        System.out.println("\u001B[31mTous ces terrains on déjà des hotels, impossible de construire dessus\u001B[0m");
    }
    
    public static void construireSur() {
        System.out.println("Vous pouvez construire sur :");
    }
    
    public static void printPropriete(ProprieteAConstruire p) {
        System.out.println("\u001B[34m" + p.getNom() + "\u001B[0m ; Groupe : " + p.getCouleur().toString() + " ; Construction existante : "+ getImmobilier(p) + " ; n°\u001B[36m" + p.getNum() + "\u001B[0m");
    }
    
    public static void printProprieteProprietaire(ProprieteAConstruire p) {
        System.out.println("\u001B[34m" +p.getNom() + "\u001B[0m ; Groupe : " + p.getCouleur().toString() + "(" + p.getProprietaire().getProprietesAConstruire(p.getCouleur()).size() + "/" + p.getNbPropriete() + ") ; Construction existante : "+ getImmobilier(p) + " ; n°\u001B[36m" + p.getNum()+ "\u001B[0m");
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
            System.out.println("\u001B[31mLa banque n'a malheuresement plus" + rep + "en stock, vous ne pouvez pas construire sur ces terrains...\u001B[0m");
            return true;
        }
        
        public static int chooseNum(int prix) {
            return TexteUI.inte("Sur quel numéro voulez agir (\u001B[33m"+ prix + "\u001B[0m€) ? \u001B[32m(numéro)\u001B[0m");
        }
        
        public static void errorConstruction(){
            System.out.println("Ce terrain n'est pas constructible pour le moment");
        }
        
        public static void errorDestruction() {
            System.out.println("Ce terrain n'est pas destructible pour le moment");
        }
        
        public static void errorHypoNonProposee(){
            System.out.println("\u001B[31mErreur : Ce terrain n'est pas disponible à l'hypothèque, \u001B[32mrecommencez :\u001B[0m");
        }
        
        public static void errorNonHypo(){
            System.out.println("\u001B[31mErreur : Ce terrain n'est pas hypothéqué, \u001B[32mrecommencez :\u001B[0m");
        }
        
        public static int chooseHypo() {
            return TexteUI.inte("Sur quelle propriété voulez-vous agir ? \u001B[32m(numéro)\u001B[0m");
        }
        
        public static boolean encoreConstruire() {
            return TexteUI.bool("Voulez-vous construire une autre maison/hotel ? \u001B[32m(oui/non)\u001B[0m");
        }
        
        public static boolean encoreDetruire() {
            return TexteUI.bool("Voulez-vous detruire une autre maison/hotel ? \u001B[32m(oui/non)\u001B[0m");
        }
        
        public static void printDetruire() {
            System.out.println("Vous pouvez détruire sur les proprietes suivantes : \u001B[31m(attention les hotels ne rendront pas des maisons)\u001B[0m");
        }
        
        public static boolean errorVide() {
            System.out.println("Les terrains de ce groupe sont tous vides, vous ne pouvez plus rien détruire");
            return true;
        }
        
        public static String menuHypo(boolean display) {
            String rep;
            if (display) {
                rep = TexteUI.question("Voulez-vous lever une hypotheque ou hypothequer ? \u001B[32m(lever/hypotheque)\u001B[0m");
                while (rep.equals("lever") && rep.equals("hypotheque")) {
                    System.out.println("\u001B[31mErreur : vous devez repondre par \u001B[32mlever \u001B[31mou\u001B[32m hypotheque\u001B[31m ! Recommencez :\u001B[0m");
                    rep = TexteUI.question("Voulez-vous lever une hypotheque ou hypothequer ? \u001B[32m(lever/hypotheque)\u001B[0m");
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
            System.out.println("Gare : \u001B[34m" + g.getNom() + "\u001B[0m ; n°\u001B[36m" + g.getNum() + "\u001B[0m");
        }
        
        public static void printCompagnie(Compagnie c) {
            System.out.println("Compagnie : \u001B[34m" + c.getNom() + "\u001B[0m ; n°\u001B[36m" + c.getNum()+ "\u001B[0m");
        }
        
        public static void errorHypo() {
            System.out.println("Vous n'avez aucune propriété hypothequée");
        }
         
        public static void leverHypo(CarreauPropriete c) {
            System.out.println("Lever l'hypothèque de cette propriété coute : \u001B[33m" + c.getPrixHypotheque() + "\u001B[0m€");
        }
        
        public static boolean continuerHypo() {
            return TexteUI.bool("Voulez-vous continuer ? \u001B[32m(oui/non)\u001B[0m");
        }
        
        public static void hypoDispo() {
            System.out.println("Liste des proprietes disponible à l'hypotheque :");
        }
        
        public static void printHypo(CarreauPropriete c) {
            System.out.println("L'hypotheque vous rapporte \u001B[33m" + c.getPrix()/2 + "\u001B[0m€");
        }
        
        public static boolean printAchat(CarreauPropriete c) {
        System.out.println("Cette " + c.getClass().getSimpleName() + " : \u001B[34m" + c.getNom() + "\u001B[0m est disponible à l'achat au prix de \u001B[33m" + c.getPrix() +"\u001B[0m€");
        return TexteUI.bool("Voulez-vous acheter cette " + c.getClass().getSimpleName() + " ? \u001B[32m(oui/non)\u001B[0m");
    }
        
        public static void printProprePAC(CarreauPropriete c) {
            System.out.println("Vous êtes sur votre : " + c.getNom());
        }
        
        public static boolean toucherLoyer(CarreauPropriete c) {
             System.out.println("Vous êtes tomber sur une " + c.getClass().getSimpleName() + " \u001B[31mqui à déjà un propriétaire.\u001B[0m");
             if (c.isHypotheque()) {
                 System.out.println("Cette " + c.getClass().getSimpleName() + " \u001B[31mest hypothéquée, vous ne payez rien\u001B[0m");
                 return false;
             } else {
                System.out.println("Vous devez payer \u001B[33m" + c.getLoyer() + "\u001B[0m€");
                return true;
             }
        }
        
        public static boolean toucherLoyerCompagnie(CarreauPropriete c) {
             System.out.println("Vous êtes tomber sur une " + c.getClass().getSimpleName() + " \u001B[31mqui à déjà un propriétaire.\u001B[0m");
             if (c.isHypotheque()) {
                 System.out.println("Cette " + c.getClass().getSimpleName() + " \u001B[31mest hypothéquée, vous ne payez rien\u001B[0m");
                 return false;
             } else {
                return true;
             }
        }
        
        public static void nouvelleConstruction(ProprieteAConstruire p) {
            System.out.println("Ce terrain dispose maintenant de : " + getImmobilier(p));
            System.out.println("Les joueurs qui passeront sur ce terrain payeront : \u001B[33m" + p.getLoyer() + "\u001B[0m€");
            JoueurUI.printCashVous(p.getProprietaire());
        }
        
        public static void destructionMaison(ProprieteAConstruire p,int argent) {
            System.out.println("La vente de cette maison vous rapporte \u001B[33m" + argent + "\u001B[0m€");
            if(p.getImmobilier()>0) {
                System.out.println("Il reste sur ce terrain : " + getImmobilier(p));
            } else {
                System.out.println("Ce terrain est maintenant vide de construction");
            }
            JoueurUI.printCashVous(p.getProprietaire());
        }
        
        public static void destructionHotel(ProprieteAConstruire p,int argent) {
            System.out.println("La vente de cet hotel vous rapporte \u001B[33m" + argent + "\u001B[0m€");
            System.out.println("Ce terrain est maintenant vide de construction");
            JoueurUI.printCashVous(p.getProprietaire());
        }
        
        public static void possession(Joueur j) {
            System.out.println("\u001B[31m" + j.getNomJoueur() + " possède maintenant tout les propriétés du groupe, il peut désormais construire dessus\u001B[0m");
        }
        
        public static void payerCompagnies(int value) {
            System.out.println("Le proprietaire possédant les \u001B[31mdeux\u001B[0m compagnies, vous allez payer dix fois la somme de votre lancer de dés");
            System.out.println("Vous avez abtenu \u001B[36m" + value/10 + "\u001B[0m aux dés, donc vous payer : \u001B[33m" + value +"\u001B[0m€");
        }
        
        public static void payerCompagnie(int value) {
            System.out.println("Le proprietaire possédant une seule compagnie, vous allez payer 4 fois la somme de votre lancer de dés");
            System.out.println("Vous avez abtenu \u001B[36m" + value/4 + "\u001B[0m aux dés, donc vous payer : \u001B[33m" + value +"\u001B[0m€");
        }
        
        public static boolean jEssaye() {
            return TexteUI.bool("Voulez-vous essayé de vendre ou d'hypothequer pour avoir assez d'argent ? \u001B[32m(oui/non)\u001B[0m");
        }
        
        
}