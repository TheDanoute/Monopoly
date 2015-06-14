package Jeu;

import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import Ui.JoueurUI;
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
        private int enPrison; //0=pas en prison ; 1 à 3 = en prison et nombre de tour passé
        private ArrayList<CartePrison> cartePrison ;
        private int desDepart; //Dés de choix de l'ordre de départ

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
        this.setPositionCourante(1);//Sur la case départ
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
    
    public ArrayList<ProprieteAConstruire> getProprietesAConstruire(CouleurPropriete c) {//Revoies les propriétés du joueur d'une couleur
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
    
    public void setPositionCourante(Carreau positionCourante) {//setter public pour la démo
        this.positionCourante = positionCourante;
    }
    
    public int getNBTourPrison(){
        return enPrison;
    }
    
    public void ajouterTourPrison() {
        enPrison++;
    }
    
    public void setPositionCourante(int p) {//Methode utiliser pour jouerUnCoup et avancer
       if (p>40) {//Si le joueur passe pas la case départ il reçoit 200€
           p-=40; 
           if (p!=1) {
           this.addCash(200);
           JoueurUI.passageDepart(this);
           }
       }
       this.setPositionCourante(monopoly.getCarreau(p));
    }

        
        
        public void enPrison() {//Mets le joueur en prison
            this.setPositionCourante(monopoly.getCarreau(11));
            this.setPrison(true);
        }
        
        public void sortPrison() {
            this.setPrison(false);
        }
        
        public void utilCartePrison(){
            CartePrison c = cartePrison.get(0);
            c.action(this);
            cartePrison.remove(c);//Remet la carte automatiquement dans le paquet
            monopoly.retourCarte(c);
        }
        private void setPrison(boolean p){//Transforme le boolean en integer
            if (p) {
                enPrison=1;
            } else {
                enPrison=0;
            }
        }
        public boolean getPrison(){
            return enPrison>0; //Transforme integer en boolean
        }
        
        public int getNBCartePrison() {
            return cartePrison.size();
        }
        
        public void addCartePrison(Carte c) {
            CartePrison cP = (CartePrison) c;
            cartePrison.add(cP);
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
                this.removeCash(-aC);
            }
	}

	public void removeCash(int aC){
                // Soustrait au joueur le montant aC
		this.cash-=aC;
                // Si le cash du joueur est négatif après le retrait il doit vendre pour récuperer son négatif ou il fait faillite
                if (this.getCash()<0){
                JoueurUI.cBientotLaFin(this);
                    if (this.jEssaye(0)) {
                        JoueurUI.okJeGere();
                    } else {
                        JoueurUI.cLaFin(this);
                        this.faillite();
                    }
                }
	}
        
        public boolean jEssaye(int objectif) {//Fonnction pour faillite ou achat de propriété quand pas assez d'argent (objectif=0 quand faillite)
            boolean vendre = true ; //S'il peut encore vendre
                boolean hypotheque = true ; //S'il peut encore hypothequer
                    while (this.getCash()<objectif && (vendre || hypotheque))    {
                        String rep = JoueurUI.chooseLaFin(); //Choix entre hypothequer ou vendre
                        switch(rep) {
                            case "vendre":
                            {
                                if (vendre) {
                                    try {
                                        this.getMonopoly().detruire(this);
                                    } catch(Exception e){//Exception levée si plus rien a vendre
                                        TexteUI.message(e.getMessage());
                                        vendre = false;
                                    }
                                } else {
                                    JoueurUI.cLaFinDetruire();
                                }
                                 break;
                            }
                            case "hypotheque":
                            {
                                if(hypotheque) {
                                    try {
                                        this.getMonopoly().hypotheque(this,false);
                                    } catch(Exception e){//Exception levée si plus rien a hypothequé
                                        TexteUI.message(e.getMessage());
                                        hypotheque = false;
                                    }
                                } else {
                                    JoueurUI.cLaFinHypo();
                                }
                                 break;
                            }
                        }
                    }
                   return !(this.getCash()<objectif);
                }
        
        public boolean sortDuJeu() {
            if (JoueurUI.sortDuJeu(this)) {//Confirmation par le joueur
                this.faillite();//Utilisation de la fonction faillite
                return true;
            } else {
                return false;
            }
        }
        
        private void faillite() {//Retourne toutes les propriétés à la banque
                        while (!proprietesAConstruire.isEmpty()){
                            proprietesAConstruire.get(0).retourBanque();
                        }
                        while (!compagnies.isEmpty()) {
                           compagnies.get(0).retourBanque();
                        }
                        while (!gares.isEmpty()) {
                            gares.get(0).retourBanque();
                        }
                        this.getMonopoly().getJoueurs().remove(this);
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
        public void addCartePrison (){//Utilisation pour la démo et les échanges
            cartePrison.add(new CartePrison("CC","P"));
        }
        public void removeCartePrison(){
            cartePrison.remove(0);
        }
}