package Jeu;

import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import Ui.TexteUI;
import java.util.ArrayList;
import java.util.HashSet;

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
        private HashSet<CartePrison> cartePrison ;

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

    public ArrayList<Compagnie> getCompagnies() {
        return compagnies;
    }

    public ArrayList<Gare> getGares() {
        return gares;
    }

    public ArrayList<ProprieteAConstruire> getProprietesAConstruire() {
        return proprietesAConstruire;
    }
    
    public Monopoly getMonopoly(){
        return monopoly;
    }
    
    public void setPositionCourante(Carreau positionCourante) {
        this.positionCourante = positionCourante;
    }
    
    public void setPositionCourante(int p) {
       this.setPositionCourante(monopoly.getCarreau(p));
    }

        
        
        public void enPrison() {
            this.setPositionCourante(monopoly.getCarreau(11));
            this.setPrison(true);
        }
        
        public void sortPrison() {
            this.setPrison(false);
        }
        
        public void utilCartePrison(CartePrison c){
            cartePrison.remove(c);
            monopoly.retourCarte(c);
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
            if (this.getCash()<0){
                TexteUI.message("Le joueur "+this.getNomJoueur()+" n'a pas assez d'argent pour payer. \n Il a perdu !");
                this.getMonopoly().getJoueurs().remove(this);
            }
	}

	public void removeCash(int aC) {
		this.cash-=aC;
                if (this.getCash()<0){
                TexteUI.message("Le joueur "+this.getNomJoueur()+" n'a pas assez d'argent pour payer. \n Il a perdu !");
                this.getMonopoly().getJoueurs().remove(this);
            }
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