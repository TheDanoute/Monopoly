/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

import Ui.TexteUI;
import java.util.ArrayList;

/**
 *
 * @author devaucod
 */
public class Echange {
    
    private Joueur joueur;
    private ArrayList<CarreauPropriete> listP;
    private int somme;
    
    public Echange(Joueur j) {
        this.setJoueur(j);
        listP = new ArrayList<>();
        somme =0;
    }
    public Joueur getJoueur() {
        return joueur;
    }
    public void setSomme(int somme) {
        this.somme = somme;
    }
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
    public void addP (CarreauPropriete cp){
        listP.add(cp);
    }
    public ArrayList<CarreauPropriete> getListP() {
        return listP;
    }
    public int getSomme() {
        return somme;
    }
}
