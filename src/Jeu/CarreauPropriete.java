package Jeu;

import Jeu.Joueur;

public abstract class CarreauPropriete extends Carreau {
	private int loyerBase;
	private int prixAchat;
	private int prix;
	private Joueur proprietaire;

	public Joueur getProprietaire() {
		return this.proprietaire;
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
		throw new UnsupportedOperationException();
	}

	protected abstract int getNbPropriete();

	public Groupe getGroupe() {
		throw new UnsupportedOperationException();
	}
}