/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

/**
 *
 * @author devaucod
 */
public abstract class Carte {
    private CarteType type;
    private String description;
    
    public Carte(String t,String d) {
        this.setDescription(d);
        this.setType(t);
    }

    public CarteType getType() {
        return type;
    }

    private void setType(String type) {
        // Si la chaine de caractères de la carte correspond à CC, la carte sera de type communautaire, sinon elle sera de type chance.
        if(type.equals("CC")){
            this.type=CarteType.communautaire;
        } else {
            this.type=CarteType.chance;
        }
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }
    
    public abstract void action(Joueur j);
    
}
