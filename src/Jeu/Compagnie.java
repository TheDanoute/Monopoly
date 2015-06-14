package Jeu;

import Ui.JoueurUI;
import Ui.ProprieteUI;
import Ui.TexteUI;
import java.util.Random;

public class Compagnie extends CarreauPropriete {
        
        public Compagnie(int num,String nom,Monopoly m,int p) {
            super(num,nom,m,p);
        }

        @Override
	public int getLoyer() {
                int i = super.getMonopoly().lancerDe();
                i += super.getMonopoly().lancerDe();
                if (this.getNbPropriete()>1) {
                    ProprieteUI.payerCompagnies(i);
                    return i;
                } else {
                     ProprieteUI.payerCompagnie(i);
                    return i;
                }
	}
           
        @Override
	public int getNbPropriete() {
		return super.getProprietaire().getNombreCompagnies();
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
                if (ProprieteUI.toucherLoyerCompagnie(this)) {
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
                this.getProprietaire().removeCompagnie(this);
            }
            super.setProprietaire(j);
            if (j!=null) {
                 j.addCompagnie(this);
            }
        }
        
        @Override
        public void retourBanque(){
            super.retourBanque();
            this.setProprietaire(null);
        }
}