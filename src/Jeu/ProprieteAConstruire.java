package Jeu;


import java.util.ArrayList;
import java.util.Scanner;

public class ProprieteAConstruire extends CarreauPropriete {
	private int immobilier;
	private ArrayList<Integer> loyers;
	private Groupe groupe;

	public ProprieteAConstruire(int num,String nom,Monopoly m,int p,ArrayList<Integer> l,Groupe g){
            super(num,nom,m,p);
            this.setImmobilier(0);
            this.setLoyers(l);
            this.setGroupe(g);
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
		throw new UnsupportedOperationException();
	}
        
        public CouleurPropriete getCouleur(){
            return this.groupe.getCouleur();
        }
        
        @Override
        public void action (Joueur aJ){
            Scanner sa = new Scanner(System.in);
            if (aJ==this.getProprietaire()){
                System.out.println("Cette propriété vous appartient");
                boolean groupe = true;
                for (CarreauPropriete c : g.getProprietes()){
                    if (c.getProprietaire()!=aJ){
                        groupe = false;
                    }
                }
                if (groupe){
                    System.out.println("Voulez-vous acheter une maison / un hôtel ? (oui/non)");
                    String rep = sa.nextLine();
                    if (rep=="oui"){
                        // achat d'une maison
                    }
                }
            }else if (this.getProprietaire()==null){
                System.out.println("Vous êtes sur "+this.getNom());
                System.out.println("Cette propriété est disponible à l'achat");
                if (aJ.getCash()>=this.getPrix()){
                    System.out.println("Cette propriété coûte "+this.getPrix()+"€, voulez-vous l'acheter ? (oui/non)");
                    String rep = sa.nextLine();
                    if (rep == "oui"){
                        this.setProprietaire(aJ);
                        aJ.removeCash(this.getPrix());
                        System.out.println(aJ.getNomJoueur()+" est désormais le propriétaire de "+this.getNom());
                    }
                }else{
                    System.out.println("Vous n'avez pas assez d'argent pour acheter cette propriété");
                }
            }else{
                System.out.println(this.getProprietaire().getNomJoueur()+" est le propriétaire de ce carreau");
                System.out.println("Vous devez payer "+this.getLoyer()+"€");
                if (aJ.getCash()>=this.getLoyer()){
                    aJ.removeCash(this.getLoyer());
                    
                }
            }
        }
}