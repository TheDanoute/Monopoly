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
                i += super.getMonopoly().lancerDe(); //Lancement des deux dés
                if (this.getNbPropriete()>1) {//Si le propriétaire possède les deux compgnies
                    i = i*10;
                    ProprieteUI.payerCompagnies(i);
                    return i;
                } else { //S'il n'en possède qu'une
                    i = i * 4;
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
            if (this.getProprietaire()==null) {//Achat possible
                if (ProprieteUI.printAchat(this)) {
                    if (j.getCash()>=this.getPrix()) {//Si le joueur a assez d'argent
                        j.removeCash(this.getPrix());
                        JoueurUI.printCashVous(j);
                        this.setProprietaire(j);
                    } else {//Si le joueur n'a pas assez d'argent
                        JoueurUI.errorArgent(j);
                        if (ProprieteUI.jEssaye()) {//Proposition de vente ou hypotheque pour gagner de l'argent
                            if (j.jEssaye(super.getPrix())) {
                                j.removeCash(this.getPrix());
                                JoueurUI.printCashVous(j);
                                this.setProprietaire(j);
                            }
                        }
                    }
                }
            } else if (this.getProprietaire()==j) {//Propre compagnie
                ProprieteUI.printProprePAC(this);
            } else {//Déjà un propriétaire
                if (ProprieteUI.toucherLoyerCompagnie(this)) {//Vérification si la propriété n'est pas hypothéquée
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
            if (this.getProprietaire()!=null) {//Si la comgagnie a déjà un propriétaire
                this.getProprietaire().removeCompagnie(this);
            }
            super.setProprietaire(j);
            if (j!=null) {//Si ce n'est pas le cas d'un retour de la propriété a la banque
                 j.addCompagnie(this);
            }
        }
        
        @Override
        public void retourBanque(){
            super.retourBanque(); //Leve l'hypotheque
            this.setProprietaire(null);
        }
}