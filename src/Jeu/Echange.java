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
    
    private ArrayList<ProprieteAConstruire> pro;
    private ArrayList<Gare> gare;
    private ArrayList<Compagnie> comp;
    private ArrayList<CartePrison> pri;
    private ArrayList<CarreauPropriete> listP;
    private int somme;
    
    public Echange(Joueur j) {
        this.setJoueur(j);
        pro = new ArrayList<>();
        gare = new ArrayList<>();
        comp = new ArrayList<>();
        pri = new ArrayList<>();
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
    
    public void addP (CarreauPropriete cp){
        listP.add(cp);
    }

    public ArrayList<CarreauPropriete> getListP() {
        return listP;
    }
    
    
    
    public int getSomme() {
        return somme;
    }
    
    public void description() {
        TexteUI.message("************************************************");
        TexteUI.message("Argent à donner : " + somme);
        TexteUI.message("************************************************");
        if (!pro.isEmpty()) {
            TexteUI.message("Liste de propriete(s) (" + pro.size() + ") :");
            for (ProprieteAConstruire p : pro) {
                TexteUI.message(p.getDescription());
            }
            TexteUI.message("************************************************");
        }
        if (!gare.isEmpty()) {
            TexteUI.message("Liste de gare(s) (" + gare.size() + ") :");
            for (Gare g : gare) {
                TexteUI.message(g.getDescription());
            }
            TexteUI.message("************************************************");
        }
        if (!comp.isEmpty()) {
            TexteUI.message("Liste de compagnie(s) (" + comp.size() + ") :");
            for (Compagnie c : comp) {
                TexteUI.message(c.getDescription());
            }
            TexteUI.message("************************************************");
        }
        if (!pri.isEmpty()) {
           TexteUI.message("Nombre de carte sortie de prison : " + pri.size());
           TexteUI.message("************************************************");
        }
    }

    public void addPropriete(ProprieteAConstruire get) {
        if (pro.contains(get)) {
            TexteUI.message("L'échange contient déjà cette propriete");
        } else {
            pro.add(get);
            TexteUI.message(get.getDescription() + "ajoutée à l'échange");
        }
    }
    
}
