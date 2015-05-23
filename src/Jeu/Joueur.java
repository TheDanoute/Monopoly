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
        private int compDouble;
        private boolean enPrison;
        //private HashSet<> cartePrison ;

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

    public void setPositionCourante(Carreau positionCourante) {
        this.positionCourante = positionCourante;
    }

        
        
        public void enPrison() {
            this.setPositionCourante(monopoly.getCarreau(11));
            this.setPrison(true);
        }
        private void setPrison(boolean p){
            enPrison=p;
        }
        public boolean getPrison(){
            return enPrison;
        }
	public int getCash() {
		return this.cash;
	}

	private void setCash(int aC) {
		this.cash = aC;
	}

	public void addCash(int aC) {
		this.cash+=aC;
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

	public void payerLoyer(int aLoyer) {
		this.cash-=aLoyer;
	}

	public void recevoirLoyer(int aLoyer) {
		this.cash+=aLoyer;
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