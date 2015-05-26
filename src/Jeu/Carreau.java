package Jeu;

public abstract class Carreau {
	private int numero;
	private String nom;
	private Monopoly monopoly;

	public Carreau(int n,String nom,Monopoly m){
            this.setNum(n);
            this.setNom(nom);
            this.setMonop(m);
        }
        
        public int getNum() {
		return numero;
	}

	private void setNum(int aN) {
		numero=aN;
	}

	public String getNom() {
		return nom;
	}

	private void setNom(String aN) {
		nom=aN;
	}
        private void setMonop(Monopoly m){
            monopoly=m;
        }
        public Monopoly getMonopoly(){
            return monopoly;
        }
        
        public abstract void action(Joueur aJ);
}