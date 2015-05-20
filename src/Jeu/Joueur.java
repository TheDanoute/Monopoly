package Jeu;

import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import java.util.ArrayList;

public class Joueur {
	private String nomJoueur;
	private int cash;
	private Monopoly monopoly;
	private ArrayList<Compagnie> compagnies;
	private ArrayList<Gare> gares;
	private Carreau positionCourante;
	private ArrayList<ProprieteAConstruire> proprietesAConstruire = new ArrayList<ProprieteAConstruire>();

    public Joueur(String nomJoueur, Monopoly monopoly) {
        this.nomJoueur = nomJoueur;
        this.monopoly = monopoly;
        this.setCash(1500);
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
		return gares.size();
	}
        
        public int getNombreCompagnies() {
            return compagnies.size();
        }

	/**
	 * @return ArrayList<Joueur>
	 */
	public ArrayList<Joueur> getJoueurs() {
		throw new UnsupportedOperationException();
	}
}