package Jeu;


import java.util.ArrayList;

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
}