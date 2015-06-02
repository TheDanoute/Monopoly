package Jeu;


import Ui.TexteUI;
import java.util.ArrayList;
import java.util.Scanner;

public class ProprieteAConstruire extends CarreauPropriete {
	private int immobilier;
	private ArrayList<Integer> loyers;
	private Groupe groupe;
        private int prixMaison;

	public ProprieteAConstruire(int num,String nom,Monopoly m,int p,ArrayList<Integer> l,Groupe g,int pM){
            super(num,nom,m,p);
            this.setImmobilier(0);
            this.setLoyers(l);
            this.setGroupe(g);
            this.setPrixMaison(pM);
        }
        
        public int getLoyer(){
            return loyers.get(immobilier);
        }
        
        public ArrayList<Integer> getListLoyers() {
            return loyers;
        }
        
        private void setLoyers(ArrayList<Integer> l){
            loyers=l;
        }
        
        public int getImmobilier() {
		return immobilier;
	}

	private void setImmobilier(int i) {
		immobilier=i;
	}
        
        public Groupe getGroupe(){
            return groupe;
        }
        
        private void setGroupe(Groupe g){
            groupe=g;
            groupe.ajouterPropriete(this);
        }
        
        @Override
	public int getNbPropriete() {
            return groupe.getNbPropriete();
	}
        
        public CouleurPropriete getCouleur(){
            return this.groupe.getCouleur();
        }

        public int getPrixMaison() {
            return prixMaison;
        }

        public void setPrixMaison(int prixMaison) {
            this.prixMaison = prixMaison;
        }


        
         @Override
        public void action (Joueur aJ){
            Scanner sa = new Scanner(System.in);
            if (aJ==this.getProprietaire()){
                TexteUI.message("Cette propriété vous appartient");
                boolean groupe = true;
                for (CarreauPropriete c : this.getGroupe().getProprietes()){
                    if (c.getProprietaire()!=aJ){
                        groupe = false;
                    }
                }
                if (groupe){
                    if (this.getImmobilier()<4){
                        TexteUI.message("Vous avez actuellement "+this.getImmobilier()+" maisons sur cette propriété");
                        String rep = TexteUI.question("Voulez-vous acheter une maison / un hôtel ? (oui/non)");
                    if (rep.equals("oui")){
                        boolean okM = true;
                        for (CarreauPropriete c : this.getGroupe().getProprietes()){
                            if (c.getNbPropriete()<this.getNbPropriete()){
                                okM=false;
                            }
                        }
                        if (okM){
                            TexteUI.message("Une maison / un hôtel coûte "+this.getPrixMaison());
                            String rep2 = TexteUI.question(("Voulez vous l'acheter . (oui/non)"));
                            if (rep2.equals("oui")){
                                aJ.removeCash(this.getPrixMaison());
                                TexteUI.message("Il vous reste " + aJ.getCash() + "€");
                                this.immobilier++;
                                if (this.getImmobilier()<4){
                                    TexteUI.message("Vous avez actuellement "+this.getImmobilier()+" maisons sur cette propriété");
                                }else{
                                    TexteUI.message("Vous avez actuellement 1 hôtel sur cette propriété");
                                }
                                
                            }
                        }else{
                            TexteUI.message("Vous ne pouvez pas acheter d'immobilier, tant que les autres propriétes de ce groupe n'en ont pas autant que celle là");
                        }
                        
                    }
                    }else{
                        TexteUI.message("Vous avez actuellement 1 hôtel sur cette propriété");
                    }
                }
            }else if (this.getProprietaire()==null){
                TexteUI.message("Vous êtes sur "+this.getNom());
                TexteUI.message("Cette propriété est disponible à l'achat");
                if (aJ.getCash()>=this.getPrix()){
                    String rep = TexteUI.question("Cette propriété coûte "+this.getPrix()+"€, voulez-vous l'acheter ? (oui/non)");
                    if (rep.equals("oui")){
                        this.setProprietaire(aJ);
                        aJ.removeCash(this.getPrix());
                        TexteUI.message(aJ.getNomJoueur()+" est désormais le propriétaire de "+this.getNom());
                    }
                }else{
                    TexteUI.message("Vous n'avez pas assez d'argent pour acheter cette propriété");
                }
            }else{
                TexteUI.message(this.getProprietaire().getNomJoueur()+" est le propriétaire de ce carreau");
                TexteUI.message("Vous devez payer "+this.getLoyer()+"€");
                aJ.removeCash(this.getLoyer());
                this.getProprietaire().addCash(this.getLoyer());
                TexteUI.message("Le propriétaire a reçu son argent !");    
            }
        }
}