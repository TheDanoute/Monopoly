package Jeu;


public abstract class CarreauPropriete extends Carreau {
	private int prix;
	private Joueur proprietaire;

	public CarreauPropriete(int num,String nom,Monopoly m,int p) {
            super(num,nom,m);
            this.setPrix(p);
            this.setProprietaire(null);
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

        
        
	public int payerLoyer(Joueur aJ) {
            return 1;
		//Passage de l'argent
	}

	public abstract int getNbPropriete();
        
        
        
}