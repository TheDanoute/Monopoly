package Jeu;

import Ui.TexteUI;

public class Gare extends CarreauPropriete {
        
        private int loyer;
        
        public Gare (int num,String nom,Monopoly m,int p) {
            super(num,nom,m,p);
            this.setLoyer(25);
        }
        
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
        public void action(Joueur j) {
            if (this.getProprietaire()==null) {
                TexteUI.message("Cette gare : " + this.getNom() + " est disponible à l'achat au prix de " + this.getPrix() +"€");
                if (j.getCash()>=200){
                    String r = TexteUI.question("Voulez-vous acheter cette gare ? (oui/non)");
                    if (r.equals("oui")) {
                        j.removeCash(this.getPrix());
                        TexteUI.message("Il vous reste " + j.getCash() + "€");
                        this.setProprietaire(j);
                    }
                }else{
                    TexteUI.message("Vous n'avez pas assez d'argent pour acheter cette gare");
                }
            } else if (this.getProprietaire()==j) {
                TexteUI.message("Vous êtes sur votre gare : " + this.getNom());
            } else {
                TexteUI.message("Vous êtes tomber sur une gare qui à déjà un propriétaire, vous payez : "+this.getLoyer()+"€");
                int l = this.getLoyer();
                j.removeCash(l);
                this.getProprietaire().addCash(l);
            }
        }
}