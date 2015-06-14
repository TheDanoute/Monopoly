package Jeu;


import Ui.JoueurUI;
import Ui.ProprieteUI;
import Ui.TexteUI;
import java.util.ArrayList;
import java.util.Scanner;

public class ProprieteAConstruire extends CarreauPropriete {
	private int immobilier;
	private ArrayList<Integer> loyers;
	private Groupe groupe;
        private int prixMaison;

	public ProprieteAConstruire(int num,String nom,Monopoly m,int p,ArrayList<Integer> l,Groupe g,int pM){
            super(num,nom,m,p);
            this.setImmobilier(0);
            this.setLoyers(l);
            this.setGroupe(g);
            this.setPrixMaison(pM);
        }
        
        @Override
        public int getLoyer(){
            if (immobilier==0 && super.getProprietaire().getProprietesAConstruire(this.getCouleur()).size()==this.getNbPropriete() && this.noHypo()) {
                return loyers.get(immobilier)*2;//Si la propriété est vide et que le joueur possède tous les terrains
            } else {
                return loyers.get(immobilier);
            }
        }
        
        public ArrayList<Integer> getListLoyers() {
            return loyers;
        }

        private void setLoyers(ArrayList<Integer> l){
            loyers=l;
        }
        
        public int getImmobilier() {
		return immobilier;
	}

	public void setImmobilier(int i) {
		immobilier=i;
	}
        
        private void addImmobilier() {
		immobilier++;
	}
        
        private void removeImmobilier() {
		immobilier--;
	}
        
        public Groupe getGroupe(){
            return groupe;
        }
        
        private void setGroupe(Groupe g){
            groupe=g;
            groupe.ajouterPropriete(this);
        }
        
        @Override
	public int getNbPropriete() {//Renvoies le nombre de propriété total du groupe
            return groupe.getNbPropriete();
	}
        
        public CouleurPropriete getCouleur(){
            return this.groupe.getCouleur();
        }

        public int getPrixMaison() {
            return prixMaison;
        }

        public void setPrixMaison(int prixMaison) {
            this.prixMaison = prixMaison;
        }

        public void construire() {
            if (this.getProprietaire().getCash()<this.getPrixMaison()) {//Vérification argent joueur
                JoueurUI.errorArgent(super.getProprietaire());
            } else {
                this.getProprietaire().removeCash(this.getPrixMaison());
                this.addImmobilier();
                if (this.getImmobilier()<5) {
                    super.getMonopoly().removeMaison();//Prise de maison à la banque
                } else {
                    super.getMonopoly().addMaison(4);//Retour maison banque
                    super.getMonopoly().removeHotel();//Prise d'un hotel a la banque
                }
                ProprieteUI.nouvelleConstruction(this);
            }
        }
        
        public void detruire() {
            int argent;
            if(this.getImmobilier()>4) {
                this.setImmobilier(0);
                super.getMonopoly().addHotel();//Retour hotel banque
                argent = (int)(prixMaison*2.5);
                super.getProprietaire().addCash(argent);
                ProprieteUI.destructionHotel(this,argent);
            } else {
                this.removeImmobilier();
                super.getMonopoly().addMaison();//Retour maison banque
                argent = prixMaison/2;
                super.getProprietaire().addCash(argent);
                ProprieteUI.destructionMaison(this,argent);
            }
        }
        
        @Override
        public void setProprietaire(Joueur j) {
            if (this.getProprietaire()!=null) {//Si la propriete a déjà un propriétaire
                this.getProprietaire().removePropriete(this);
            }
            super.setProprietaire(j);
            if (j!=null) {//Si ce n'est pas le cas d'un retour de la propriété a la banque
                j.addPropriete(this);
                if (j.getProprietesAConstruire(this.getCouleur()).size()==this.getNbPropriete()) {//Si le joueur possède les trois terrains
                    ProprieteUI.possession(super.getProprietaire());
                }
            }
        }
        @Override
        public void action(Joueur j){
            if (this.getProprietaire()==null) {//Achat possible
                if (ProprieteUI.printAchat(this)) {
                    if (j.getCash()>=this.getPrix()) {//Si le joueur a assez d'argent
                        j.removeCash(this.getPrix());
                        JoueurUI.printCashVous(j);
                        this.setProprietaire(j);
                    } else {//Si le joueur n'a pas assez d'argent
                        JoueurUI.errorArgent(j);
                        if (ProprieteUI.jEssaye()) {//Proposition de vente ou hypotheque pour gagner de l'argent
                            if (j.jEssaye(super.getPrix())) {
                                j.removeCash(this.getPrix());
                                JoueurUI.printCashVous(j);
                                this.setProprietaire(j);
                            }
                        }
                    }
                }
            } else if (this.getProprietaire()==j) {//Propre propriete
                ProprieteUI.printProprePAC(this);
            } else {//Déjà un propriétaire
                if (ProprieteUI.toucherLoyer(this)) {//Vérification si la propriété n'est pas hypothéquée
                    int l = this.getLoyer();
                    j.removeCash(l);
                    this.getProprietaire().addCash(l);
                    JoueurUI.printCashLe(super.getProprietaire());
                    JoueurUI.printCashVous(j);
                 }
            }
        }
        
        
        public boolean noHypo() {//Vérification du groupe s'il n'y a pas de propriete hypothequer pour construction
            boolean retour = true;
            ArrayList<ProprieteAConstruire> listP = groupe.getProprietes();
            int i = 0;
            while (retour && i<listP.size()) {
                retour = !listP.get(i).isHypotheque();
                i++;
            }
            return retour;
        }
        
        @Override
        public void retourBanque() {
            super.retourBanque();
            if (this.getImmobilier()>4) {
                this.getMonopoly().addHotel();
            } else {
                this.getMonopoly().addMaison(immobilier);
            }
            this.setImmobilier(0);
            this.setProprietaire(null);
        }
}