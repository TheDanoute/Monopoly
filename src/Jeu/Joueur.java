package Jeu;

import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import java.util.ArrayList;

public class Joueur {
	private String nomJoueur;
	private int cash = 1500;
	private Monopoly monopoly;
	private ArrayList<Compagnie> compagnies = new ArrayList<Compagnie>();
	private ArrayList<Gare> gares = new ArrayList<Gare>();
	private Carreau positionCourante;
	private ArrayList<ProprieteAConstruire> proprietesAConstruire = new ArrayList<ProprieteAConstruire>();

    public Joueur(String nomJoueur, Monopoly monopoly) {
        this.nomJoueur = nomJoueur;
        this.monopoly = monopoly;
        compagnies = new ArrayList<>();
        gares = new ArrayList<>();
        proprietesAConstruire = new ArrayList<>();
        //positionCourante=Carreau.Depart;
    }

    public Carreau getPositionCourante() {
        return positionCourante;
    }

        
        
        
	public int getCash() {
		return this.cash;
	}

	private void setCash(int aC) {
		this.cash = aC;
	}

	public void addCash(int aC) {
		
	}

	public void removeCash(int aC) {
		this.cash-=aC;
	}

	public String getNomJoueur() {
		return this.nomJoueur;
	}

	private void setNomJoueur(String aN) {
		this.nomJoueur = aN;
	}

	public int payerLoyer(int aLoyer) {
		throw new UnsupportedOperationException();
	}

	public void recevoirLoyer(int aLoyer) {
		throw new UnsupportedOperationException();
	}

	public int getNombreGare() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return ArrayList<Joueur>
	 */
	public ArrayList<Joueur> getJoueurs() {
		throw new UnsupportedOperationException();
	}
}