package Jeu;

import java.util.ArrayList;

public class Groupe {
	private int prixAchat;
	private CouleurPropriete couleur;
	private ArrayList<ProprieteAConstruire> proprietes;
        private int nbPropriete;

	public Groupe(){}
        
        public Groupe(int p,String c) {
            this.setPrix(p);
            this.setCouleur(CouleurPropriete.valueOf(c));
            proprietes = new ArrayList<>();
            this.setNbPropriete();
        }
        
        private void setPrix(int p) {
            prixAchat=p;
        }
        
        public int getPrix() {
            return prixAchat    ;
        }
        
        public CouleurPropriete getCouleur() {
		return this.couleur;
	}

	private void setCouleur(CouleurPropriete aC) {
		this.couleur = aC;
	}
        private void setNbPropriete() {
            if (this.getCouleur()==CouleurPropriete.mauve || this.getCouleur()==CouleurPropriete.bleuFonce) {
                nbPropriete=2;
            } else {
                nbPropriete=3;
            }
        }
        public int getNbPropriete() {
            return nbPropriete;
        }
        
        public void ajouterPropriete(ProprieteAConstruire p) {
            this.addPropriete(p);
        }
        
        private void addPropriete(ProprieteAConstruire p) {
            proprietes.add(p);
        }

        public ArrayList<ProprieteAConstruire> getProprietes() {
            return proprietes;
        }
        
        
}