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
		return loyer*2^(this.getNbPropriete()-1);
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
                ProprieteUI.toucherLoyer(this);
                if (ProprieteUI.toucherLoyer(this)) {
                    int l = this.getLoyer();
                    j.removeCash(l);
                    this.getProprietaire().addCash(l);
                    JoueurUI.printCashLe(super.getProprietaire());
                    JoueurUI.printCashLe(j);
                 }
            }
        }
        @Override
        public void setProprietaire(Joueur j) {
            if (this.getProprietaire()!=null) {
                this.getProprietaire().removeGare(this);
            }
            super.setProprietaire(j);
            j.addGare(this);
        }
        
        /*@Override
        public String getDescription() {
            if (super.isHypotheque()) {
                return "Gare HYPOTHEQUEE : " + super.getDescription();
            } else {
                return "Gare : " + super.getDescription();
            }
        }*/
        
        @Override
        public void retourBanque(){
            super.retourBanque();
            this.setProprietaire(null);
        }
}