/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

import java.util.ArrayList;

/**
 *
 * @author devaucod
 */
public class Echange {
    
    private Joueur joueur;
    
    private ArrayList<ProprieteAConstruire> pro;
    private ArrayList<Gare> gare;
    private ArrayList<Compagnie> comp;
    private ArrayList<CartePrison> pri;
    private int somme;
    
    public Echange(Joueur j) {
        this.setJoueur(j);
        pro = new ArrayList<>();
        gare = new ArrayList<>();
        comp = new ArrayList<>();
        pri = new ArrayList<>();
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public ArrayList<ProprieteAConstruire> getPro() {
        return pro;
    }

    public ArrayList<Gare> getGare() {
        return gare;
    }

    public ArrayList<Compagnie> getComp() {
        return comp;
    }

    public ArrayList<CartePrison> getPri() {
        return pri;
    }

    public int getSomme() {
        return somme;
    }
    
    public void description() {
        
    }
    
}
