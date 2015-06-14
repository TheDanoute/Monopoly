package Jeu;

import Ui.JoueurUI;
import Ui.ProprieteUI;
import Ui.TexteUI;

public class Gare extends CarreauPropriete {
        
        private int loyer;
        
        public Gare (int num,String nom,Monopoly m,int p) {
            super(num,nom,m,p);
            this.setLoyer(25);
        }
        
        @Override
	public int getLoyer() {
		return loyer*(int)Math.pow(2,(double)this.getNbPropriete()-1);
	}
        private void setLoyer(int l){
            loyer=l;
        }

        @Override
	public int getNbPropriete() {
		return super.getProprietaire().getNombreGare();
	}

        @Override
        public void action(Joueur j){
            if (this.getProprietaire()==null) {
                if (ProprieteUI.printAchat(this)) {
                    if (j.getCash()>=this.getPrix()) {
                        j.removeCash(this.getPrix());
                        JoueurUI.printCashVous(j);
                        this.setProprietaire(j);
                    } else {
                        JoueurUI.errorArgent(j);
                        if (ProprieteUI.jEssaye()) {
                            if (j.jEssaye(super.getPrix())) {
                                j.removeCash(this.getPrix());
                                JoueurUI.printCashVous(j);
                                this.setProprietaire(j);
                            }
                        }
                    }
                }
            } else if (this.getProprietaire()==j) {
                ProprieteUI.printProprePAC(this);
            } else {
                if (ProprieteUI.toucherLoyer(this)) {
                    int l = this.getLoyer();
                    j.removeCash(l);
                    this.getProprietaire().addCash(l);
                    JoueurUI.printCashLe(super.getProprietaire());
                    JoueurUI.printCashVous(j);
                 }
            }
        }
        @Override
        public void setProprietaire(Joueur j) {
            if (this.getProprietaire()!=null) {
                this.getProprietaire().removeGare(this);
            }
            super.setProprietaire(j);
            if (j!=null) {
                j.addGare(this);
            }
        }
        
        @Override
        public void retourBanque(){
            super.retourBanque();
            this.setProprietaire(null);
        }
}