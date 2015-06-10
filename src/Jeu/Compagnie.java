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
            Random gene = new Random();
                if (this.getNbPropriete()>1) {
                    int i = (gene.nextInt(6)+1)*10;
                    ProprieteUI.payerCompagnies(i);
                    return i;
                } else {
                    int i = (gene.nextInt(6)+1)*4;
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
                this.getProprietaire().removeCompagnie(this);
            }
            super.setProprietaire(j);
            j.addCompagnie(this);
        }
        /*@Override
        public String getDescription() {
            if (super.isHypotheque()) {
                return "Compagnie HYPOTHEQUEE : " + super.getDescription();
            } else {
                return "Compagnie : " + super.getDescription();
            }
        }*/
        
        @Override
        public void retourBanque(){
            super.retourBanque();
            this.setProprietaire(null);
        }
}