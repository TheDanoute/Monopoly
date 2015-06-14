package Jeu;


public abstract class CarreauPropriete extends Carreau {
	private int prix;
	private Joueur proprietaire;
        private boolean hypotheque;

	public CarreauPropriete(int num,String nom,Monopoly m,int p) {
            super(num,nom,m);
            this.setPrix(p);
            proprietaire=null;
            this.setHypotheque(false);
        }
        
        public Joueur getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Joueur aJ) {
		this.proprietaire = aJ;
	}
        
	public void setPrix(int aPrix) {
		this.prix = aPrix;
	}

       public int getPrix() {
            return prix;
       }

    public boolean isHypotheque() {
        return hypotheque;
    }

    private void setHypotheque(boolean hypotheque) {
        this.hypotheque = hypotheque;
    }
    
        public int getPrixHypotheque(){
            return (int)((prix/2)*1.1);
        }    
    
        public void leverHypotheque() {
             this.getProprietaire().removeCash(this.getPrixHypotheque());
             this.setHypotheque(false);
        }
        
        public void hypotheqer() {
            this.getProprietaire().addCash(prix/2);
            this.setHypotheque(true);
        }

	public abstract int getNbPropriete();
        
        public abstract int getLoyer();
        
        public void retourBanque() {
            this.setHypotheque(false);
        }
        
        
}