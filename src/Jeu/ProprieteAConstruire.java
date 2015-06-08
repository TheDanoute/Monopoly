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
            if (super.getProprietaire().getProprietesAConstruire(this.getCouleur()).size()==this.getNbPropriete() && immobilier==0) {
                return loyers.get(immobilier)*2;
            } else {
                return loyers.get(immobilier);
            }
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
        
        public String getImmobilierString() {
            String rep;
		switch (immobilier) {
                    case 0:
                    {
                        rep = " vide";
                        break;
                    }
                    case 1:
                    {
                        rep = " une maison";
                        break;
                    }
                    case 2:
                    {
                        rep = " deux maisons";
                        break;
                    }
                    case 3:
                    {
                        rep = " trois maisons";
                        break;
                    }
                    case 4:
                    {
                        rep = " quatre maisons";
                        break;
                    }
                    case 5:
                    {
                        rep = " un hôtel";
                        break;
                    }
                    default:
                    {
                        rep = "error";
                        break;
                    }
                }
                return rep;
	}

	private void setImmobilier(int i) {
		immobilier=i;
	}
        
        private void addImmobilier() {
		immobilier++;
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

        public void construire() {
            if (this.getProprietaire().getCash()<this.getPrixMaison()) {
                TexteUI.message("Vous n'avez pas assez d'argent...");
            } else {
                this.getProprietaire().removeCash(this.getPrixMaison());
                this.addImmobilier();
                if (this.getImmobilier()<5) {
                    super.getMonopoly().removeMaison();
                } else {
                    super.getMonopoly().addMaison(4);
                    super.getMonopoly().removeHotel();
                }
                TexteUI.message("Ce terrain dispose maintenant de : " + this.getImmobilierString());
                TexteUI.message("Les joueurs qui passeront sur ce terrain payeront : " + this.getLoyer() + "€");
                TexteUI.message("Il vous reste " + this.getProprietaire().getCash() + "€");
            }
        }
        @Override
        public void setProprietaire(Joueur j) {
            if (this.getProprietaire()!=null) {
                this.getProprietaire().removePropriete(this);
            }
            super.setProprietaire(j);
            j.addPropriete(this);
        }
        @Override
        public void action (Joueur aJ){
            Scanner sa = new Scanner(System.in);
            if (aJ==this.getProprietaire()){
                TexteUI.message("Cette propriété vous appartient");
            } else if (this.getProprietaire()==null){
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
            } else{
                TexteUI.message(this.getProprietaire().getNomJoueur()+" est le propriétaire de ce carreau");
                TexteUI.message("Vous devez payer "+this.getLoyer()+"€");
                aJ.removeCash(this.getLoyer());
                this.getProprietaire().addCash(this.getLoyer());
                TexteUI.message("Le propriétaire a reçu son argent !");    
            }
        }
        
        @Override
        public String getDescription() {
            if (super.isHypotheque()) {
                return "Propriete HYPOTHEQUEE : " + super.getDescription() + " ; Groupe : " + this.getGroupe() + " ; Construction : " + this.getImmobilierString();
            } else {
               return "Propriete : " + super.getDescription() + " ; Groupe : " + this.getGroupe() + " ; Construction : " + this.getImmobilierString();
            }
        }
}