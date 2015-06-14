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
		return loyer*(int)Math.pow(2,(double)this.getNbPropriete()-1);//Echelle : 25-50-100-200€
	}
        private void setLoyer(int l){
            loyer=l;//Toujours égal à 25€
        }

        @Override
	public int getNbPropriete() {
		return super.getProprietaire().getNombreGare();//Revoies le nombre de gare du propriétaire
	}

        @Override
        public void action(Joueur j){//Achat possible
            if (this.getProprietaire()==null) {//Si le joueur a assez d'argent
                if (ProprieteUI.printAchat(this)) {
                    if (j.getCash()>=this.getPrix()) {
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
            } else if (this.getProprietaire()==j) {//Propre gare
                ProprieteUI.printProprePAC(this);
            } else {//Déjà un propriétaire
                if (ProprieteUI.toucherLoyer(this)) {//Vérification si la propriété n'est pas hypothéquée
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
            if (this.getProprietaire()!=null) {//Si la gare a déjà un propriétaire
                this.getProprietaire().removeGare(this);
            }
            super.setProprietaire(j);
            if (j!=null) {//Si ce n'est pas le cas d'un retour de la propriété a la banque
                j.addGare(this);
            }
        }
        
        @Override
        public void retourBanque(){
            super.retourBanque();//Leve l'hypotheque
            this.setProprietaire(null);
        }
}