package Jeu;

import Jeu.Joueur;
import Ui.TexteUI;
import java.util.Random;

public class Compagnie extends CarreauPropriete {
        
        public Compagnie(int num,String nom,Monopoly m,int p) {
            super(num,nom,m,p);
        }

	public int getLoyer() {
            Random gene = new Random();
                if (this.getNbPropriete()>1) {
                    int i = (gene.nextInt(6)+1)*10;
                    TexteUI.message("Vous avez abtenu " + i/10 + " aux dés, donc vous payer : " + i +"€");
                    return i;
                } else {
                    int i = (gene.nextInt(6)+1)*4;
                    TexteUI.message("Vous avez abtenu " + i/4 + " aux dés, donc vous payer : " + i +"€");
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
                TexteUI.message("Cette compagnie : " + this.getNom() + " est disponible à l'achat au prix de " + this.getPrix() +"€");
                String r = TexteUI.question("Voulez-vous acheter cette compagnie ? (oui/non)");
                if (r.equals("oui")) {
                    if (j.getCash()>this.getPrix()) {
                        j.removeCash(this.getPrix());
                        TexteUI.message("Il vous reste " + j.getCash() + "€");
                    } else {
                        TexteUI.message("Vous n'avez pas assez d'argent");
                    }
                }
            } else if (this.getProprietaire()==j) {
                TexteUI.message("Vous êtes sur votre : " + this.getNom());
            } else {
                TexteUI.message("Vous êtes tomber sur une compagnie qui à déjà un propriétaire.");
                int l = this.getLoyer();
                j.removeCash(l);
                this.getProprietaire().addCash(l);
            }
        }
}