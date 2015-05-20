package Jeu;

public class Gare extends CarreauPropriete {
        
        private int loyer;
        
        public Gare (int num,String nom,Monopoly m,int p) {
            super(num,nom,m,p);
            this.setLoyer(25);
        }
        
	public int getLoyer() {
		return loyer*2^(this.getNbPropriete()-1);
	}
        private void setLoyer(int l){
            loyer=l;
        }

        @Override
	public int getNbPropriete() {
		return super.getProprietaire().getNombreGare();
	}
}