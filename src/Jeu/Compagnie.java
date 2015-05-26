package Jeu;

import Jeu.Joueur;
import java.util.Random;

public class Compagnie extends CarreauPropriete {
	
    //private int loyer;
        
        public Compagnie(int num,String nom,Monopoly m,int p) {
            super(num,nom,m,p);
        }

	public int getLoyer() {
            Random gene = new Random();
                if (this.getNbPropriete()>1) {
                    return gene.nextInt(6)+1*10;
                } else {
                    return gene.nextInt(6)+1*4;
                }
	}
           
        @Override
	public int getNbPropriete() {
		return super.getProprietaire().getNombreCompagnies();
	}
        
        @Override
        public void action(Joueur j){
            j.removeCash(this.getLoyer());
        }
}