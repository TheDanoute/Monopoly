package Jeu;

import Jeu.ProprieteAConstruire;
import java.util.ArrayList;

public class Groupe {
	private int prixAchatMaison;
	private int prixAchatHotel;
	private CouleurPropriete couleur;
	private ArrayList<ProprieteAConstruire> proprietes = new ArrayList<ProprieteAConstruire>();

	public CouleurPropriete getCouleur() {
		return this.couleur;
	}

	private void setCouleur(CouleurPropriete aC) {
		this.couleur = aC;
	}
}