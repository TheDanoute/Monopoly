/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author DanJeux
 */
public class PaquetsCartes {
    private ArrayList<Carte> cartesChances;
    private ArrayList<Carte> cartesChancesPoubelle;
    private ArrayList<Carte> cartesCommunautaires;
    private ArrayList<Carte> cartesCommunautairesPoubelle;
    
    public PaquetsCartes() {
        cartesChances = new ArrayList<>();
        cartesChancesPoubelle = new ArrayList<>();
        cartesCommunautaires = new ArrayList<>();
        cartesCommunautairesPoubelle = new ArrayList<>();
        this.initPaquetsCartes();
        Collections.shuffle(cartesChances);
        Collections.shuffle(cartesCommunautaires);
    }
    
    private boolean initPaquetsCartes() {
        return false;
    }
    
    public Carte piochezCarteChance() {
        if(cartesChances.isEmpty()) {
            cartesChances = cartesChancesPoubelle;
            Collections.shuffle(cartesChances);
            cartesChancesPoubelle.clear();
        }
        Random gene = new Random();
        int rang = gene.nextInt(cartesChances.size());
        Carte c = cartesChances.get(rang);
        cartesChances.remove(rang);
        return c;
    }
    
    public Carte piochezCarteCommunautaire() {
        if(cartesCommunautaires.isEmpty()) {
            cartesCommunautaires = cartesCommunautairesPoubelle;
            Collections.shuffle(cartesCommunautaires);
            cartesCommunautairesPoubelle.clear();
        }
        Random gene = new Random();
        int rang = gene.nextInt(cartesCommunautaires.size());
        Carte c = cartesCommunautaires.get(rang);
        cartesCommunautaires.remove(rang);
        return c;
    }
    
    public void retourCarte(Carte c){
        if (c.getType()==CarteType.chance) {
            cartesChancesPoubelle.add(c);
        } else {
            cartesCommunautairesPoubelle.add(c);
        }
    }


}
