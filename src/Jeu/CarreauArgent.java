package Jeu;

public class CarreauArgent extends CarreauAction {
	
        private int prix;

	public CarreauArgent(int n,String nom,Monopoly m,int p){
            super(n,nom,m);
            this.setPrix(p);
        }
        
        public int getPrix() {
		return this.prix;
	}

	private void setPrix(int p) {
		this.prix = p;
	}
        
        @Override
        public void action(Joueur j){
            j.addCash(prix);
        }
}