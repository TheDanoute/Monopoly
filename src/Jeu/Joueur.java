package Jeu;

import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import Ui.TexteUI;
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
        private int enPrison;
        private ArrayList<CartePrison> cartePrison ;
        private int sommeDesDepart;

    public Joueur(String nomJoueur, Monopoly monopoly) {
        this.nomJoueur = nomJoueur;
        this.monopoly = monopoly;
        this.setCash(1500);
        this.setPrison(false);
        compagnies = new ArrayList<>();
        gares = new ArrayList<>();
        proprietesAConstruire = new ArrayList<>();
        sommeDesDepart = monopoly.lancerDe();
        cartePrison = new ArrayList<>();
        this.setPositionCourante(1);
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
    
    public int getNBTourPrison(){
        return enPrison;
    }
    
    public void ajouterTourPrison() {
        enPrison++;
    }
    
    public void setPositionCourante(int p) {
       if (p>40) {
           p-=40; 
           if (p!=1) {
           TexteUI.message("Vous venez de passer par la case départ, vous recevez 200€");
           this.addCash(200);
           }
       }
       this.setPositionCourante(monopoly.getCarreau(p));
    }

        
        
        public void enPrison() {
            this.setPositionCourante(monopoly.getCarreau(11));
            this.setPrison(true);
        }
        
        public void sortPrison() {
            this.setPrison(false);
        }
        
        public void utilCartePrison(){
            CartePrison c = cartePrison.get(0);
            c.action(this);
            cartePrison.remove(c);
            monopoly.retourCarte(c);
        }
        private void setPrison(boolean p){
            if (p) {
                enPrison=1;
            } else {
                enPrison=0;
            }
        }
        public boolean getPrison(){
            if (enPrison>0) {
                return true;
            } else {
                return false;
            }
        }
        
        public int getNBCartePrison() {
            return cartePrison.size();
        }
	public int getCash() {
		return this.cash;
	}

	private void setCash(int aC) {
		this.cash = aC;
	}

	public void addCash(int aC) {
            // Ajoute au joueur le montant aC
            this.cash+=aC;
            // Dans le cas où le joueur tire une carte avec un malus, aC sera négatif, et si après le retrait, le cash du joueur est négatif, il fait
            // faillite, il a donc perdu et il est retiré de la partie.
            if (this.getCash()<0){
                TexteUI.message("Le joueur "+this.getNomJoueur()+" n'a pas assez d'argent pour payer. \n Il a perdu !");
                this.getMonopoly().getJoueurs().remove(this);
            }
	}

	public void removeCash(int aC) {
                // Soustrait au joueur le montant aC
		this.cash-=aC;
                // Si le cash du joueur est négatif après le retrait, il fait faillite, il a donc perdu et il est retiré de la partie.
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

	public int getNombreGare() {
		return gares.size();
	}
        
        public int getNombreCompagnies() {
            return compagnies.size();
        }
        
        public int getSommeDesDepart() {
            return sommeDesDepart;
        }
       

	/**
	 * @return ArrayList<Joueur>
	 */
	public ArrayList<Joueur> getJoueurs() {
		throw new UnsupportedOperationException();
	}
}