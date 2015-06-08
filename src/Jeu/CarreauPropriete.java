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

    public void setHypotheque(boolean hypotheque) {
        this.hypotheque = hypotheque;
    }

	public abstract int getNbPropriete();
        
        public String getDescription() {
            return super.getNom() + " n°" + super.getNum();
        }
        
        
}