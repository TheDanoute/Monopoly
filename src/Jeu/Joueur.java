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
	private ArrayList<ProprieteAConstruire> proprietesAConstruire;
        private int compDouble;
        private int enPrison;
        private ArrayList<Carte> cartePrison ;
        private int desDepart;

    public Joueur(String nomJoueur, Monopoly monopoly) {
        this.nomJoueur = nomJoueur;
        this.monopoly = monopoly;
        this.setCash(1500);
        this.setPrison(false);
        compagnies = new ArrayList<>();
        gares = new ArrayList<>();
        proprietesAConstruire = new ArrayList<>();
        desDepart = monopoly.lancerDe();
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
    
    public ArrayList<ProprieteAConstruire> getProprietesAConstruire(CouleurPropriete c) {
        ArrayList<ProprieteAConstruire> list = new ArrayList<>();
        for (ProprieteAConstruire p : proprietesAConstruire) {
            if (p.getCouleur()==c) {
                list.add(p);
            }
        }
        return list;
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
            Carte c = cartePrison.get(0);
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
        
        public void addCartePrison(Carte c) {
            cartePrison.add(c);
        }
        
	public int getCash() {
		return this.cash;
	}

	private void setCash(int aC) {
		this.cash = aC;
	}

	public void addCash(int aC) {
            // Ajoute au joueur le montant aC
            if (aC>0){
                this.cash+=aC;
            } else {// Dans le cas où le joueur tire une carte avec un malus, aC sera négatif
                //this.removeCash(-aC);
            }
	}

	public void removeCash(int aC){
                // Soustrait au joueur le montant aC
		this.cash-=aC;
                // Si le cash du joueur est négatif après le retrait, il fait faillite, il a donc perdu et il est retiré de la partie.
                if (this.getCash()<0){
                TexteUI.message("Le joueur "+this.getNomJoueur()+" n'a pas assez d'argent pour payer. Il doit detruire et/ou hypothequer");
                boolean vendre = true ;
                boolean hypotheque = true ;
                    while (this.getCash()<0 && (vendre || hypotheque))    {
                        String rep = TexteUI.question("Que veut il faire ? (hypotheque/detruire)");
                        switch(rep) {
                            case "detruire":
                            {
                                if (vendre) {
                                    try {
                                        this.getMonopoly().detruire(this);
                                    } catch(Exception e){
                                        TexteUI.message(e.getMessage());
                                        vendre = false;
                                    }
                                } else {
                                    TexteUI.message("Vous n'avez plus d'immobilier à détruire");
                                }
                                 break;
                            }
                            case "hypotheque":
                            {
                                if(hypotheque) {
                                    try {
                                        this.getMonopoly().hypotheque(this,false);
                                    } catch(Exception e){
                                        TexteUI.message(e.getMessage());
                                        hypotheque = false;
                                    }
                                } else {
                                    TexteUI.message("Vous n'avez plus de propriete à hypothequer");
                                }
                                 break;
                            }
                        }
                        TexteUI.message("Vous avez " + cash + "€");
                    }
                    if (this.getCash()<0) {
                        TexteUI.message("Le joueur " + this.nomJoueur + " n'a pas de quoi payer, il fait donc faillite et sort du jeu...");
                        for (ProprieteAConstruire p : proprietesAConstruire) {
                            p.retourBanque();
                        }
                        for (Compagnie c : compagnies) {
                           c.retourBanque();
                        }
                        for (Gare g : gares) {
                            g.retourBanque();
                        }
                    } else {
                        TexteUI.message("La faillite à été épargnée pour le moment");
                    }
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
        
        public int getDesDepart() {
            return desDepart;
        }
        
        protected void setDesDepart(int newsomme) {
            this.desDepart=newsomme;
        }
        
        public void addPropriete(ProprieteAConstruire p) {
            proprietesAConstruire.add(p);
        }
        public void removePropriete(ProprieteAConstruire p) {
            proprietesAConstruire.remove(p);
        }
        public void addGare(Gare g){
            gares.add(g);
        }
        public void removeGare(Gare g){
            gares.remove(g);
        }
        public void addCompagnie(Compagnie c){
            compagnies.add(c);
        }
        public void removeCompagnie(Compagnie c){
            compagnies.remove(c);
        }
        public void addCartePrison (){
            cartePrison.add(new CartePrison("CC","P"));
        }
        public void removeCartePrison(){
            cartePrison.remove(0);
        }
}