package Jeu;

import Jeu.Monopoly;

public abstract class Carreau {
	private int numero;
	private String nomCarreau;
	private Monopoly monopoly;

	public Carreau(int n,String nom,Monopoly m){
            numero=n;
            nomCarreau=nom;
            monopoly=m;
        }
        
        public int getNum() {
		throw new UnsupportedOperationException();
	}

	private void setNum(int aN) {
		throw new UnsupportedOperationException();
	}

	public String getNom() {
		throw new UnsupportedOperationException();
	}

	private void setNom(String aN) {
		throw new UnsupportedOperationException();
	}
}