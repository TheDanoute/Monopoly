package Jeu;

import Jeu.Joueur;

public abstract class CarreauPropriete extends Carreau {
	private int prix;
	private Joueur proprietaire;

	public CarreauPropriete(int num,String nom,Monopoly m,int p) {
            super(num,nom,m);
            this.setPrix(p);
            this.setProprietaire(null);
        }
        
        public Joueur getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Joueur aJ) {
		this.proprietaire = aJ;
	}

	public int getPrix() {
		return this.prix;
	}
        
	public void setPrix(int aPrix) {
		this.prix = aPrix;
	}

	public int payerLoyer(Joueur aJ) {
		//Passage de l'argent
	}

	public abstract int getNbPropriete();
}