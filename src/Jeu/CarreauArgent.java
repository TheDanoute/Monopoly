package Jeu;

import Ui.CarreauUI;
import Ui.JoueurUI;
import Ui.TexteUI;

public class CarreauArgent extends CarreauAction {
	
        private int prix; //Peut-être positif ou negatif

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
            CarreauUI.addArgent(prix); //Si le montant est négatif, la methode addCash appelera la methode removeCash
            j.addCash(prix);
            JoueurUI.printCashVous(j);
        }
}